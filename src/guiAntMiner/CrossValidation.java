package guiAntMiner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Copyright (C) 2007 Fernando Meyer
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * A full copy of the license is available in gpl.txt and online at
 * http://www.gnu.org/licenses/gpl.txt
 */

public class CrossValidation implements Runnable {
	private Attribute[] attributesArray;
	private DataInstance[] dataInstancesArray;

	private int folds;

	private DataInstance[] testSet;
	private DataInstance[] trainingSet;
	private int numAnts;
	private double[][] pheromoneArray;
	private int numClasses;
	private int[][][] freqTij;
	private double[][] infoTij;
	private int[] freqT;
	private int[] control;
	private double[][] hArray;
	private double[][] probabilitiesArray;
	private boolean[][] unusableAttributeVsValueArray;
	private int minCasesRule;
	private int convergenceTest;
	private int numIterations;
	private int maxUncoveredCases;
	private GUIAntMinerJFrame caller;
	private boolean interrupted;
	private Thread cvThread;

	public CrossValidation(GUIAntMinerJFrame caller) {
		this.caller = caller;
		interrupted = false;
	}

	public void setAttributesArray(Attribute[] attributesArray) {
		this.attributesArray = attributesArray;
	}

	public void setDataInstancesArray(DataInstance[] dataInstancesArray) {
		this.dataInstancesArray = dataInstancesArray;
	}

	public void setNumAnts(int numAnts) {
		this.numAnts = numAnts;
	}

	public void setFolds(int folds) {
		this.folds = folds;
	}

	public void setMinCasesRule(int minCasesRule) {
		this.minCasesRule = minCasesRule;
	}

	public void setConvergenceTest(int convergenceTest) {
		this.convergenceTest = convergenceTest;
	}

	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	public void setMaxUncoveredCases(int maxUncoveredCases) {
		this.maxUncoveredCases = maxUncoveredCases;
	}

	public void start() {
		cvThread = new Thread(this);
		try {
			initialize();
			cvThread.start();
			caller.getJProgressBar1().setIndeterminate(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		cvThread = null;
		interrupted = true;
	}

	private void initialize() throws Exception {
		int n = 0;
		int arraysSize;

		if (numAnts == 0 || folds < 2 || minCasesRule == 0 || convergenceTest == 0 || numIterations == 0
				|| maxUncoveredCases == 0) {
			caller.getJProgressBar1().setIndeterminate(false);
			caller.setIsClassifying(false);
			JOptionPane.showMessageDialog(null,
					"<html><font face=Dialog size=3>At least one of the parameters is invalid.</font></html>", "Error",
					JOptionPane.ERROR_MESSAGE);
			throw new InvalidArgumentException();
		}

		arraysSize = attributesArray.length - 1;

		numClasses = attributesArray[arraysSize].getTypes().length;

		freqT = new int[numClasses];

		// determine the size of the data sets for cross-validation
		int groupSize = dataInstancesArray.length / folds;
		if (dataInstancesArray.length % folds == 0)
			groupSize--;

		control = new int[folds];
		for (n = 0; n < folds; n++)
			control[n] = groupSize;

		pheromoneArray = new double[arraysSize][];
		freqTij = new int[arraysSize][][]; // freqTij[noOfAttributes][noOfValues][noOfClasses]
		infoTij = new double[arraysSize][];
		hArray = new double[arraysSize][];
		unusableAttributeVsValueArray = new boolean[arraysSize][];
		probabilitiesArray = new double[arraysSize][];

		for (n = 0; n < arraysSize; n++) {
			pheromoneArray[n] = new double[attributesArray[n].getTypes().length];
			freqTij[n] = new int[attributesArray[n].getTypes().length][numClasses];
			infoTij[n] = new double[attributesArray[n].getTypes().length];
			hArray[n] = new double[attributesArray[n].getTypes().length];
			unusableAttributeVsValueArray[n] = new boolean[attributesArray[n].getTypes().length];
			probabilitiesArray[n] = new double[attributesArray[n].getTypes().length];
		}
	}

	public void run() {
		Thread currentThread = Thread.currentThread();

		Random random = new Random();
		boolean goOn;

		double[][] pheromoneTempArray = new double[attributesArray.length - 1][];

		List<Double> accuracyRatesList = new LinkedList<Double>();
		List<Double> numberOfTermsList = new LinkedList<Double>();
		List<Double> numberOfRulesList = new LinkedList<Double>();

		double totalTestAccuracyRate = 0.0;
		double totalTrainingAccuracyRate = 0.0;

		Date date = new Date();

		caller.getJTextArea1().setLineWrap(true);
		printHeader();

		group();

		for (int crossValidation = 0; crossValidation < folds && currentThread == cvThread; crossValidation++) {
			System.out.println("Cross Validation " + (crossValidation + 1) + " of " + folds);

			Date date2 = new Date();

			splitDataSet(crossValidation);
			DataInstance[] trainingSetClone = (DataInstance[]) trainingSet.clone();

			int bestAntIndex = -1;
			List<Object> bestIterationAntsList = new ArrayList<Object>();
			List antsFoundRuleList = new ArrayList();

			while (trainingSet.length > maxUncoveredCases && currentThread == cvThread) {
				System.out.print(".");
				bestAntIndex = 0;

				initializePheromoneTrails();

				calculateFreqTij();
				calculateInfoTij();

				int iteration, deltaCount;
				iteration = deltaCount = 0;

				while (iteration < numIterations && deltaCount < convergenceTest) {

					double bestQuality = 0;
					bestAntIndex = 0;

					Ant[] antsArray = new Ant[numAnts];
					for (int x = 0; x < numAnts; x++) {
						antsArray[x] = new Ant(attributesArray.length - 1);
					}

					for (int antIndex = 0; antIndex < numAnts; antIndex++) {
						Ant currentAnt = antsArray[antIndex];

						for (int n = 0; n < pheromoneTempArray.length; n++)
							pheromoneTempArray[n] = (double[]) pheromoneArray[n].clone();

						// attDistinct[attribute i] contains the number of
						// distinct values for attribute i
						int[] attDistinctLeft = new int[attributesArray.length - 1];
						for (int i = 0; i < attDistinctLeft.length; i++)
							attDistinctLeft[i] = attributesArray[i].getTypes().length - 1;

						for (int i = 0; i < unusableAttributeVsValueArray.length; i++)
							for (int j = 0; j < unusableAttributeVsValueArray[i].length; j++)
								unusableAttributeVsValueArray[i][j] = false;

						goOn = true;
						while (goOn && currentThread == cvThread) {
							calculateHeuristicFunction(currentAnt);
							calculateProbabilities(currentAnt, pheromoneTempArray);

							float randomNumber = (random.nextInt() << 1 >>> 1) % 101;
							randomNumber /= 100;
							boolean found = false;
							double sum = 0.0;
							for (int i = 0; i < probabilitiesArray.length; i++) {
								for (int j = 0; j < probabilitiesArray[i].length && !found; j++) {
									sum += probabilitiesArray[i][j];
									if (sum >= randomNumber) {
										if (!ruleConstructor(currentAnt, i, j)) {
											// set to true so that this ant does
											// not try to use term ij again
											unusableAttributeVsValueArray[i][j] = true;
											attDistinctLeft[i]--;
											pheromoneTempArray[i][j] = 0.0;
										} else {
											attDistinctLeft[i] = -1;
										}
										found = true;
									}
								}
							}

							// determine if the ant already tried to use all the
							// possible values of attribute a
							for (int a = 0; a < attDistinctLeft.length; a++) {
								if (attDistinctLeft[a] <= 0) {
									currentAnt.getMemory()[a] = 2;
								}
							}
							goOn = false;
							int a = 0;
							do {
								if (currentAnt.getMemory()[a] == 0)
									goOn = true;
								a++;
							} while (!goOn && a < currentAnt.getMemory().length);
						}

						determineRuleConsequent(currentAnt);
						calculateRuleQuality(currentAnt);

						try {
							currentAnt = pruneRule(currentAnt);
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
						antsArray[antIndex] = currentAnt;

						if (currentAnt.getRuleQuality() >= bestQuality) {
							bestQuality = currentAnt.getRuleQuality();
							bestAntIndex = antIndex;
						}
					}

					try {
						bestIterationAntsList.add(antsArray[bestAntIndex].clone());
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}

					// check if rule quality has stagnated by comparing the last
					// best quality with the previous one
					if (bestIterationAntsList.size() > 1) {
						if (((Ant) bestIterationAntsList.get(bestIterationAntsList.size() - 1))
								.getRuleQuality() == ((Ant) bestIterationAntsList.get(bestIterationAntsList.size() - 2))
										.getRuleQuality())
							deltaCount++;
						else
							deltaCount = 0;
					} else
						deltaCount++;

					updatePheromone(antsArray[bestAntIndex]);

					iteration++;
				}

				// determine which ant has the best quality
				ListIterator li = bestIterationAntsList.listIterator();
				int index = 0;
				bestAntIndex = 0;
				double bestQuality = 0.0;
				while (li.hasNext()) {
					Object temp = li.next();
					if (((Ant) temp).getRuleQuality() >= bestQuality) {
						bestQuality = ((Ant) temp).getRuleQuality();
						bestAntIndex = index;
					}
					index++;
				}

				try {
					antsFoundRuleList.add(((Ant) bestIterationAntsList.get(bestAntIndex)).clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				// remove covered cases from the trainingSet
				int count = 0;
				if (bestAntIndex != -1) {
					li = ((Ant) bestIterationAntsList.get(bestAntIndex)).getInstancesIndexList().listIterator(
							((Ant) bestIterationAntsList.get(bestAntIndex)).getInstancesIndexList().size());
					while (li.hasPrevious()) {
						Object temp = li.previous();
						trainingSet[((Integer) temp).intValue()] = null;
						count++;
					}
				}
				DataInstance[] tempTrainingSet = new DataInstance[trainingSet.length - count];
				count = 0;
				for (int x = 0; x < trainingSet.length; x++) {
					if (trainingSet[x] != null)
						tempTrainingSet[count++] = trainingSet[x];
				}
				trainingSet = tempTrainingSet;

				bestIterationAntsList.clear();
			}

			caller.getJTextArea1().append(
					"\n------------------ Cross Validation #" + (crossValidation + 1) + "------------------\n\n");

			caller.getJTextArea1().append("Cases in the training set: " + trainingSetClone.length + "\n");
			if (caller.getJCheckBox3IsSelected()) {
				caller.getJTextArea1().append("\n");
				for (int x = 0; x < trainingSetClone.length; x++) {
					caller.getJTextArea1().append(getInstanceString(trainingSetClone[x].getValues()) + "\n");
				}
			}

			caller.getJTextArea1().append("\nCases in the test set:     " + testSet.length + "\n");
			if (caller.getJCheckBox2IsSelected()) {
				caller.getJTextArea1().append("\n");
				for (int x = 0; x < testSet.length; x++) {
					caller.getJTextArea1().append(getInstanceString(testSet[x].getValues()) + "\n");
				}
			}

			numberOfRulesList.add(new Double((double) (antsFoundRuleList.size() + 1)));

			int sum = 0;
			ListIterator li = antsFoundRuleList.listIterator();
			while (li.hasNext()) {
				sum += ruleSize(((Ant) li.next()).getRulesArray());
			}
			numberOfTermsList.add(new Double((double) sum));

			caller.getJTextArea1().append("\nRules: " + (antsFoundRuleList.size() + 1) + "\n\n");

			// initializes freqT, which contains the number of cases that
			// identify a class in the trainingSet
			for (int n = 0; n < numClasses; n++) {
				freqT[n] = 0;
			}
			int classIndex;
			int greatest = 0, defaultClassIndex = 0;
			for (int n = 0; n < trainingSet.length; n++) {
				classIndex = trainingSet[n].getValues()[trainingSet[n].getValues().length - 1];
				freqT[classIndex]++;
				if (freqT[classIndex] > greatest) {
					greatest = freqT[classIndex];
					defaultClassIndex = classIndex;
				}
			}

			double trainingAccuracyRate = calculateAccuracyRate(trainingSetClone, antsFoundRuleList, defaultClassIndex);
			totalTrainingAccuracyRate += trainingAccuracyRate;

			double testAccuracyRate = calculateAccuracyRate(testSet, antsFoundRuleList, defaultClassIndex);
			totalTestAccuracyRate += testAccuracyRate;

			accuracyRatesList.add(new Double(testAccuracyRate));

			for (ListIterator i = antsFoundRuleList.listIterator(); i.hasNext();) {
				Object antObj = i.next();
				int[] rule = ((Ant) antObj).getRulesArray();
				caller.getJTextArea1().append(getRuleString(rule, ((Ant) antObj).getRuleConsequent()) + "\n");
			}
			caller.getJTextArea1().append("Default rule: "
					+ attributesArray[attributesArray.length - 1].getTypes()[defaultClassIndex] + "\n");

			System.out.println("\nAccuracy rate on the training set: " + trainingAccuracyRate + " %");
			System.out.println("Accuracy rate on the test set:     " + testAccuracyRate + " %");

			caller.getJTextArea1().append("\nAccuracy rate on the training set: " + trainingAccuracyRate + " %\n");
			caller.getJTextArea1().append("Accuracy rate on the test set:     " + testAccuracyRate + " %\n\n");
			caller.getJTextArea1().append("Time taken:                        "
					+ ((new Date().getTime() - date2.getTime()) / 1000.0) + " s.\n");

			System.out.println("Time taken: " + ((new Date().getTime() - date2.getTime()) / 1000.0) + " s.\n");

		}

		if (!interrupted) {
			DecimalFormat myFormatter = new DecimalFormat("###.##");

			caller.getJTextArea1().append("\n-------------------------------------------------------------------\n");
			caller.getJTextArea1().append("                 " + folds + "-Fold Cross Validation Results\n");
			caller.getJTextArea1().append("-------------------------------------------------------------------\n");
			caller.getJTextArea1().append("Accuracy Rate on Test Set |   Rules Number   | Conditions Number   \n");
			caller.getJTextArea1().append("-------------------------------------------------------------------\n");
			caller.getJTextArea1()
					.append("    " + myFormatter.format(totalTestAccuracyRate / folds) + "%  +/- "
							+ myFormatter.format(
									calculateVariance(accuracyRatesList, (totalTestAccuracyRate / folds), folds))
							+ "%");

			double total = 0.0;
			ListIterator li = numberOfRulesList.listIterator();
			while (li.hasNext()) {
				total += ((Double) li.next()).doubleValue();
			}
			caller.getJTextArea1().append("     |  " + myFormatter.format(total / folds) + "  +/- "
					+ myFormatter.format(calculateVariance(numberOfRulesList, (total / folds), folds)));

			total = 0.0;
			li = numberOfTermsList.listIterator();
			while (li.hasNext()) {
				total += ((Double) li.next()).doubleValue();
			}
			caller.getJTextArea1().append("  |   " + myFormatter.format(total / folds) + "  +/- "
					+ myFormatter.format(calculateVariance(numberOfTermsList, (total / folds), folds)));

			caller.getJTextArea1()
					.append("\n\nTotal elapsed time: " + ((new Date().getTime() - date.getTime()) / 1000) + " s.\n");
		} else
			caller.getJTextArea1().append("\nCLASSIFICATION HAS BEEN CANCELED!");

		caller.getJTextArea1().setCaretPosition(caller.getJTextArea1().getText().length());
		caller.getJProgressBar1().setIndeterminate(false);
		caller.setIsClassifying(false);

	}

	/**
	 * 
	 */
	private void printHeader() {
		if (caller.getJCheckBox1IsSelected())
			caller.getJTextArea1().setText(null);
		caller.getJTextArea1().append("=== Run Information ===\n\n");
		caller.getJTextArea1().append("Relation:   " + caller.getJLabel2().getText() + "\n");
		caller.getJTextArea1().append("Instances:  " + dataInstancesArray.length + "\n");
		caller.getJTextArea1().append("Attributes: " + attributesArray.length + "\n");
		for (int x = 0; x < attributesArray.length; x++) {
			caller.getJTextArea1().append("            " + attributesArray[x].getAttributeName() + "\n");
		}
		caller.getJTextArea1().append("\nUser-defined Parameters\n\n");
		caller.getJTextArea1().append("Folds:                 " + folds + "\n");
		caller.getJTextArea1().append("Number of Ants:        " + numAnts + "\n");
		caller.getJTextArea1().append("Min. Cases per Rule:   " + minCasesRule + "\n");
		caller.getJTextArea1().append("Max. uncovered Cases:  " + maxUncoveredCases + "\n");
		caller.getJTextArea1().append("Rules for Convergence: " + convergenceTest + "\n");
		caller.getJTextArea1().append("Number of Iterations:  " + numIterations + "\n");
	}

	/**
	 * @param instancesArray
	 * @param antsList
	 * @param defaultClassIndex
	 * @return
	 */
	private double calculateAccuracyRate(DataInstance[] instancesArray, List antsList, int defaultClassIndex) {
		int correctlyCovered = 0;
		ListIterator liAnt;
		boolean covering, classesCompared;

		for (int x = 0; x < instancesArray.length; x++) {
			liAnt = antsList.listIterator();
			classesCompared = false;
			while (liAnt.hasNext() && !classesCompared) {
				Object antObj = liAnt.next();
				int[] rulesArray = ((Ant) antObj).getRulesArray();
				covering = true;
				for (int x2 = 0; x2 < rulesArray.length && covering; x2++) {
					if (rulesArray[x2] != -1)
						if (rulesArray[x2] == instancesArray[x].getValues()[x2])
							covering = true;
						else
							covering = false;
				}
				// if the rule covered the case, check if the rule consequent
				// matches the class of the case
				if (covering) {
					if (instancesArray[x].getValues()[rulesArray.length] == ((Ant) antObj).getRuleConsequent())
						correctlyCovered++;
					classesCompared = true;
					// if the case was not covered by any rule so far and there
					// is only the default rule left,
					// check if the case class matches the default rule
					// consequent
				} else if (!liAnt.hasNext()) {
					if (instancesArray[x].getValues()[rulesArray.length] == attributesArray[attributesArray.length - 1]
							.getIntTypesArray()[defaultClassIndex])
						correctlyCovered++;
					classesCompared = true;
				}
			}
		}
		Double result = new Double(((double) correctlyCovered) / ((double) instancesArray.length));
		if (Double.isNaN(result.doubleValue())) {
			result = new Double(0);
		}
		return result.doubleValue() * 100;
	}

	/**
	 * @param valuesList
	 * @param average
	 * @param folds
	 * @return
	 */
	private double calculateVariance(List valuesList, double average, int folds) {
		double calc = 0.0;
		ListIterator li = valuesList.listIterator();
		while (li.hasNext()) {
			calc += Math.pow(((Double) li.next()).doubleValue() - average, 2.0);
		}
		calc /= folds - 1;
		calc /= folds;
		calc = Math.sqrt(calc);
		return calc;
	}

	/**
	 * Assigns each case a number with a value between 0 and the number of
	 * cross-validation folds -1.
	 */
	private void group() {
		Random random = new Random();
		int randomNumber;

		loosenGroups();

		for (int n = 0; n < dataInstancesArray.length; n++)
			while (dataInstancesArray[n].getCrossValidationGroup() == -1) {
				randomNumber = (random.nextInt() << 1 >>> 1) % folds;
				if (control[randomNumber] >= 0) {
					control[randomNumber]--;
					dataInstancesArray[n].setCrossValidationGroup(randomNumber);
				}
			}
	}

	/**
	 * Calculates the number of instances in a certain cross validation group.
	 * 
	 * @param group
	 * @return
	 */
	private int noOfInstancesInGroup(int group) {
		int count = 0;
		for (int n = 0; n < dataInstancesArray.length; n++) {
			if (dataInstancesArray[n].getCrossValidationGroup() == group)
				count++;
		}
		return count;
	}

	/**
	 * Splits dataInstancesArray into testSet and trainingSet.
	 * 
	 * @param crossValidation
	 */
	private void splitDataSet(int crossValidation) {
		int testSetIndex = 0, trainingSetIndex = 0;
		testSet = new DataInstance[noOfInstancesInGroup(crossValidation)];
		trainingSet = new DataInstance[dataInstancesArray.length - noOfInstancesInGroup(crossValidation)];
		for (int n = 0; n < dataInstancesArray.length; n++) {
			try {
				if (dataInstancesArray[n].getCrossValidationGroup() == crossValidation)
					testSet[testSetIndex++] = (DataInstance) dataInstancesArray[n].clone();
				else
					trainingSet[trainingSetIndex++] = (DataInstance) dataInstancesArray[n].clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Unsets previously formed groups by applying -1 to the value of each case
	 * group
	 */
	private void loosenGroups() {
		for (int n = 0; n < dataInstancesArray.length; n++)
			dataInstancesArray[n].setCrossValidationGroup(-1);
	}

	/**
	 * Initializes trails with the same quantity of pheromone
	 */
	private void initializePheromoneTrails() {
		int totalDistinct = totalDistinct();
		for (int n = 0; n < pheromoneArray.length; n++) {
			for (int n2 = 0; n2 < attributesArray[n].getTypes().length; n2++)
				pheromoneArray[n][n2] = log2(numClasses) / totalDistinct;
		}
	}

	/**
	 * Initializes freqTij, which contains the number of cases that identify a
	 * class in the trainingSet.
	 */
	private void calculateFreqTij() {
		for (int n = 0; n < trainingSet.length; n++) {
			int attIndex = 0, attValueIndex, classIndex;
			for (int n2 = 0; n2 < trainingSet[n].getValues().length - 1; n2++) {
				attValueIndex = trainingSet[n].getValues()[n2];
				classIndex = trainingSet[n].getClassValue();
				if (attValueIndex > -1)
					freqTij[attIndex][attValueIndex][classIndex]++;
				attIndex++;
			}
		}
	}

	/**
	 * Initializes infoTij
	 */
	private void calculateInfoTij() {
		for (int n = 0; n < freqTij.length; n++) {
			for (int n2 = 0; n2 < freqTij[n].length; n2++) {
				int sum = 0;
				double hw = 0;
				for (int x = 0; x < numClasses; x++)
					sum += freqTij[n][n2][x];
				for (int x = 0; x < numClasses; x++)
					if (freqTij[n][n2][x] != 0 && sum != 0)
						hw -= (double) freqTij[n][n2][x] / sum * log2((double) freqTij[n][n2][x] / sum);
				infoTij[n][n2] = hw;
			}
		}
	}

	/**
	 * Calculates the heuristic function, given by: hArray -> hij = (log2 k -
	 * H(W|Ai = Vij)) / (S xm (S log2 k - H(W|Am = Vmn)))
	 * 
	 * @param ant
	 */
	private void calculateHeuristicFunction(Ant ant) {
		double sum = 0.0;
		boolean termOccurs;
		int instanceClass;
		for (int c = 0; c < attributesArray.length - 1; c++) {
			if (ant.getMemory()[c] == 0) // if the attribute hasn't been used...
				for (int d = 0; d < infoTij[c].length; d++)
					sum += log2(numClasses) - infoTij[c][d];
		}
		for (int i = 0; i < hArray.length; i++) {
			for (int j = 0; j < hArray[i].length; j++) {
				if (!unusableAttributeVsValueArray[i][j]) {
					termOccurs = false;
					// if all cases with term ij belong to the same class, then
					// infoTij should be zero
					instanceClass = trainingSet[0].getClassValue();
					boolean isEqual = true;
					for (int c = 0; c < trainingSet.length && isEqual; c++) {
						if (trainingSet[c].getValues()[i] == attributesArray[i].getIntTypesArray()[j]) {
							termOccurs = true;
							// compare the last instance class with the current
							// instance class
							if (instanceClass == trainingSet[c].getClassValue())
								instanceClass = trainingSet[c].getClassValue();
							else
								isEqual = false;
						}
					}
					if (!termOccurs)// if trainingSet does not contain term ij
									// then...
						hArray[i][j] = 0.0;
					else if (isEqual)
						hArray[i][j] = (log2(numClasses)) / sum;
					else
						hArray[i][j] = (log2(numClasses) - infoTij[i][j]) / sum;

				} else
					hArray[i][j] = 0.0;
			}
		}
	}

	/**
	 * Calculates the probability that each term ij be selected to constitute
	 * part of the rule. The probability is given by: Pij = nij * tij(t) / S xm
	 * * (S hmn * tmn(t))
	 * 
	 * @param ant
	 * @param pheromoneArray
	 */
	private void calculateProbabilities(Ant ant, double[][] pheromoneArray) {
		double sum = 0.0;
		int x = 0, y = 0;
		for (x = 0; x < pheromoneArray.length; x++)
			if (ant.getMemory()[x] == 0) // if the attribute has not been
											// used...
				for (y = 0; y < hArray[x].length; y++)
					sum += hArray[x][y] * pheromoneArray[x][y];
		for (x = 0; x < probabilitiesArray.length; x++) {
			for (y = 0; y < probabilitiesArray[x].length; y++) {
				if (ant.getMemory()[x] == 0) {
					double result = (hArray[x][y] * pheromoneArray[x][y]) / sum;
					// if division by zero, attribute cannot be used
					if (Double.isNaN(result)) {
						probabilitiesArray[x][y] = 0.0;
						ant.getMemory()[x] = 2;
					} else
						probabilitiesArray[x][y] = result;
				} else
					probabilitiesArray[x][y] = 0.0;
			}
		}
	}

	/**
	 * Tries to add term ij to the rule. If the term is successfully added,
	 * returns true, otherwise returns false.
	 * 
	 * @param ant
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean ruleConstructor(Ant ant, int i, int j) {
		int x;
		int total = 0;
		Object temp;
		List indexToRemoveList = new ArrayList();

		if (!ant.hasRules()) {
			for (x = 0; x < trainingSet.length; x++) {
				if (trainingSet[x].getValues()[i] == attributesArray[i].getIntTypesArray()[j]) {
					ant.getInstancesIndexList().add(new Integer(x));
					total++;
				}
			}
			if (total < minCasesRule) {
				ant.clearRulesArray();
				ant.getInstancesIndexList().clear();
			}
		} else {
			ListIterator liInstancesIndex = ant.getInstancesIndexList().listIterator();
			while (liInstancesIndex.hasNext()) {
				temp = liInstancesIndex.next();
				if (trainingSet[((Integer) temp).intValue()].getValues()[i] == attributesArray[i].getIntTypesArray()[j])
					total++;
				else
					indexToRemoveList.add(temp);
			}
		}

		if (total >= minCasesRule) {
			ant.getRulesArray()[i] = j;
			ant.getMemory()[i] = 1; // cannot use attribute i to construct rule
									// anymore
			ListIterator li = indexToRemoveList.listIterator();
			while (li.hasNext()) {
				ant.getInstancesIndexList().remove(ant.getInstancesIndexList().indexOf(li.next()));
			}
			return true;
		} else
			return false;
	}

	/**
	 * Determines the consequent value of the rule.
	 * 
	 * @param ant
	 * @return
	 */
	private int determineRuleConsequent(Ant ant) {
		int ruleConsequent = -1;
		int[] freqClass = new int[numClasses];
		int trainingSetIndex;
		int trainingSetClass;

		ListIterator li = ant.getInstancesIndexList().listIterator();
		while (li.hasNext()) {
			trainingSetIndex = ((Integer) li.next()).intValue();
			trainingSetClass = trainingSet[trainingSetIndex].getClassValue();
			freqClass[trainingSetClass]++;
		}

		int mostFreq = 0, mostFreqIndex = -1;
		for (int x = 0; x < numClasses; x++) {
			if (mostFreq < freqClass[x]) {
				mostFreq = freqClass[x];
				mostFreqIndex = x;
			}
		}
		if (mostFreqIndex != -1)
			ruleConsequent = attributesArray[attributesArray.length - 1].getIntTypesArray()[mostFreqIndex];
		ant.setRuleConsequent(ruleConsequent);
		return ruleConsequent;
	}

	/**
	 * Calculates the rule quality of the ant.
	 * 
	 * @param ant
	 * @return
	 */
	private double calculateRuleQuality(Ant ant) {
		double quality;
		int vp, fp, fn, vn;
		vp = fp = fn = vn = 0;

		int antDataInstIndex = -1;
		ListIterator li = null;
		if (!ant.getInstancesIndexList().isEmpty()) {
			li = ant.getInstancesIndexList().listIterator();
			antDataInstIndex = (Integer) li.next();
		}

		for (int dataInstanceIndex = 0; dataInstanceIndex < trainingSet.length; dataInstanceIndex++) {
			// if the data instance is covered by the rule...
			if (dataInstanceIndex == antDataInstIndex) {
				if (li.hasNext())
					antDataInstIndex = (Integer) li.next();
				if (trainingSet[dataInstanceIndex].getClassValue() == ant.getRuleConsequent())
					vp++;
				else
					fp++;
			} else {
				if (trainingSet[dataInstanceIndex].getClassValue() == ant.getRuleConsequent())
					fn++;
				else
					vn++;
			}
		}
		quality = ((double) vp / (vp + fn)) * ((double) vn / (fp + vn));
		if (Double.isNaN(quality))
			quality = 0.0;
		ant.setRuleQuality(quality);
		return quality;
	}

	/**
	 * Updates the ant list that contains the indexes of the instances/cases
	 * covered by the ant rule. This method must be run when the ant rule
	 * changes.
	 */
	private void updateInstancesIndexList(Ant ant) {
		ant.getInstancesIndexList().clear();
		boolean isCoveredByRule;

		for (int x = 0; x < trainingSet.length; x++) {
			isCoveredByRule = true;
			for (int y = 0; y < trainingSet[x].getValues().length - 1 && isCoveredByRule; y++)
				if (ant.getRulesArray()[y] != -1 && trainingSet[x].getValues()[y] != ant.getRulesArray()[y])
					isCoveredByRule = false;
			if (isCoveredByRule)
				ant.getInstancesIndexList().add(new Integer(x));
		}
	}

	/**
	 * Prunes the rule of the ant.
	 * 
	 * @param ant
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private Ant pruneRule(Ant ant) throws CloneNotSupportedException {
		int numCond;
		double greatestQuality, currentRuleQuality;
		Ant antClone;
		Ant antCloneWithBestPrunedRule = ant;

		for (int a1 = 0; a1 < ant.getRulesArray().length; a1++) {
			greatestQuality = currentRuleQuality = ant.getRuleQuality();
			numCond = 0;
			for (int a = 0; a < ant.getRulesArray().length; a++) {
				if (ant.getRulesArray()[a] != -1) {
					numCond++;
					antClone = (Ant) ant.clone();
					antClone.getRulesArray()[a] = -1;
					updateInstancesIndexList(antClone);
					determineRuleConsequent(antClone);
					calculateRuleQuality(antClone);
					if (antClone.getRuleQuality() >= greatestQuality) {
						greatestQuality = antClone.getRuleQuality();
						antCloneWithBestPrunedRule = (Ant) antClone.clone();
					}
				}
			}
			if (greatestQuality >= currentRuleQuality && numCond > 1)
				ant = (Ant) antCloneWithBestPrunedRule.clone();
			else // rule could not be improved, so leave block
				a1 = ant.getRulesArray().length;
		}
		return ant;
	}

	/**
	 * Updates the trail of pheromone.
	 * 
	 * @param ant
	 */
	private void updatePheromone(Ant ant) {
		// update pheromone for used terms
		for (int x = 0; x < ant.getRulesArray().length; x++) {
			if (ant.getRulesArray()[x] != -1) {
				double currentValue = pheromoneArray[x][ant.getRulesArray()[x]];
				pheromoneArray[x][ant.getRulesArray()[x]] = currentValue + currentValue * (ant).getRuleQuality();
			}
		}
		// normalize pheromone
		double sum = 0;
		for (int x = 0; x < pheromoneArray.length; x++) {
			for (int y = 0; y < pheromoneArray[x].length; y++) {
				sum += pheromoneArray[x][y];
			}
		}
		for (int x = 0; x < pheromoneArray.length; x++) {
			for (int y = 0; y < pheromoneArray[x].length; y++) {
				pheromoneArray[x][y] /= sum;
			}
		}
	}

	/**
	 * Converts the rule from []int to a readable String
	 * 
	 * @param rule
	 * @param ruleConsequent
	 * @return the rule in String format
	 */
	private String getRuleString(int[] rule, int ruleConsequent) {
		String ruleStr;
		ruleStr = "IF ";
		boolean first = false;
		for (int x = 0; x < rule.length; x++) {
			if (rule[x] != -1) {
				if (!first)
					ruleStr += attributesArray[x].getAttributeName() + " = '" + attributesArray[x].getTypes()[rule[x]]
							+ "' ";
				else
					ruleStr += "AND " + attributesArray[x].getAttributeName() + " = '"
							+ attributesArray[x].getTypes()[rule[x]] + "' ";
				first = true;
			}
		}
		ruleStr += "THEN '" + attributesArray[attributesArray.length - 1].getTypes()[ruleConsequent] + "'";
		return ruleStr;
	}

	private String getInstanceString(int[] instance) {
		String instanceStr = "";
		for (int x = 0; x < instance.length; x++) {
			if (instance[x] == -1)
				instanceStr += "?";
			else
				instanceStr += attributesArray[x].getTypes()[instance[x]];
			if (x < instance.length - 1)
				instanceStr += ", ";
		}
		return instanceStr;
	}

	/**
	 * Calculates the size of the rule.
	 * 
	 * @param rule
	 * @return
	 */
	private int ruleSize(int[] rule) {
		int count = 0;
		for (int x = 0; x < rule.length; x++)
			if (rule[x] != -1)
				count++;
		return count;
	}

	/**
	 * Returns the total number of possible attributes values
	 * 
	 * @return the total number of possible attributes values
	 */
	public int totalDistinct() {
		int count = 0;
		for (int n = 0; n < attributesArray.length; n++) {
			count += attributesArray[n].getTypes().length;
		}
		return count;
	}

	/**
	 * @param d
	 * @return
	 */
	private double log2(double d) {
		return Math.log(d) / Math.log(2.0);
	}

	class InvalidArgumentException extends Exception {
		private static final long serialVersionUID = 1L;

		InvalidArgumentException() {
			super("At least one of the parameters is invalid.");
		}

		InvalidArgumentException(String msg) {
			super(msg);
		}
	}

}
