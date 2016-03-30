package a3.classifiers;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.correlation.Covariance;

import a3.model.Iris;

public class OptimalBayesianClassifier extends Classifier {
	
	public void train(List<Iris> animals) {
		
		// Partition data into sets for each class
		Map<String, List<Iris>> classMap = getClassMap(animals);
		
		// Do pairwise classification on all the classes
		String[] classes = classMap.keySet().toArray(new String[classMap.size()]);
		for (int i=0; i<classes.length; i++) {
			for (int j=i+1; j<classes.length; j++) {
				
				List<Iris> classA = classMap.get(classes[i]);
				List<Iris> classB = classMap.get(classes[j]);
				
				Double[] meanA = calculateSampleMean(classA);
				Double[] meanB = calculateSampleMean(classB);
				
				logMean(classes[i], meanA);
				logMean(classes[j], meanB);
				
				double[][] covarianceA = calcFullCovarianceMatrix(classA);
				double[][] covarianceB = calcFullCovarianceMatrix(classB);
				
				logCovariance(classes[i], covarianceA);
				logCovariance(classes[j], covarianceB);
				
			}
		}
		
	}
	
	private double[][] calcFullCovarianceMatrix(List<Iris> irises) {
		
		double[][] data = new double[irises.size()][Iris.NUM_FEATURES];
		
		for (int i=0; i<data.length; i++) {
			for (int j=0; j<data[i].length; j++) {
				data[i][j] = irises.get(i).getFeatures()[j];
			}
		}
		
		Covariance cov = new Covariance(data);
		return cov.getCovarianceMatrix().getData();
		
		
	}

}
