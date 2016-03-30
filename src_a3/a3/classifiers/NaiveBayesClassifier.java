package a3.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import a3.model.Iris;

public class NaiveBayesClassifier {
	
	public void train(List<Iris> animals) {
		
		// Partition data into sets for each class
		Map<String, List<Iris>> classMap = getClassMap(animals);
		
		// Do pairwise classification on all the classes
		String[] classes = classMap.keySet().toArray(new String[classMap.size()]);
		for (int i=0; i<classes.length; i++) {
			for (int j=i+1; j<classes.length; j++) {
				
				List<Iris> classA = classMap.get(classes[i]);
				List<Iris> classB = classMap.get(classes[j]);
				
				Integer[] meanA = calculateSampleMean(classA);
				Integer[] meanB = calculateSampleMean(classB);
				
				logMean("A" + i, meanA);
				logMean("B" + j, meanB);
				
				Integer[][] covarianceA = calculateSampleVariance(classA, meanA);
				Integer[][] covarianceB = calculateSampleVariance(classB, meanB);
				
				logCovariance("A" + i, covarianceA);
				logCovariance("B" + j, covarianceB);
				
			}
		}
		
	}
	
	private void logMean(String className, Integer[] arr) {
		
		System.out.print("Mean of " + className + ": [");
		Stream.of(arr).forEach(i -> {
			System.out.print(i + " ");
		});
		System.out.println("]");
	
	}
	
	private void logCovariance(String className, Integer[][] matrix) {
		
		System.out.println("Covariance of " + className + ": \n{");
		Stream.of(matrix).forEach(r -> {
			System.out.print("\t[");
			Stream.of(r).forEach(c -> {
				System.out.print(c == null ? " " : c + " ");
			});
			System.out.println("\t]");
		});
		System.out.println("}");
	
	}
	
	private Integer[] calculateSampleMean(List<Iris> irises) {
		
		Integer[] result = new Integer[Iris.NUM_INT_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			int sum = 0;
			for (Iris a : irises) {
				sum += a.getFeatures()[i];
			}
			
			result[i] = sum / irises.size();
			
		}
		
		return result;
		
	}
	
	private Integer[][] calculateSampleVariance(List<Iris> irises, Integer[] sampleMean) {
		
		Integer[][] result = new Integer[Iris.NUM_INT_FEATURES][Iris.NUM_INT_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			int sum = 0;
			for (Iris a : irises) {
				sum += Math.pow(a.getFeatures()[i] - sampleMean[i], 2);
			}
			
			result[i][i] = (int) Math.pow(sum / irises.size(), 2);
			
		}
		
		return result;
		
	}
	
	private Map<String, List<Iris>> getClassMap(List<Iris> animals) {
		
		Map<String, List<Iris>> classMap = new HashMap<String, List<Iris>>();
		for (Iris a : animals) {
			
			String dataClass = a.getDataClass();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<Iris>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;		
		
	}

}
