package a3.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.model.Animal;

public class NaiveBayesClassifier {
	
	public void train(List<Animal> animals) {
		
		// Partition data into sets for each class
		Map<Integer, List<Animal>> classMap = getClassMap(animals);
		
		// Do pairwise classification on all the classes
		Integer[] classes = classMap.keySet().toArray(new Integer[classMap.size()]);
		for (int i=0; i<classes.length; i++) {
			for (int j=i+1; j<classes.length; j++) {
				
				List<Animal> classA = classMap.get(i);
				List<Animal> classB = classMap.get(j);
				
				Integer[] meanA = calculateSampleMean(classA);
				Integer[] meanB = calculateSampleMean(classB);
				
				Integer[][] covarianceA = calculateSampleVariance(classA, meanA);
				Integer[][] covarianceB = calculateSampleVariance(classB, meanB);
				
			}
		}
		
	}
	
	private Integer[] calculateSampleMean(List<Animal> animals) {
		
		Integer[] result = new Integer[Animal.NUM_INT_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			int sum = 0;
			for (Animal a : animals) {
				sum += a.getFeatures()[i];
			}
			
			result[i] = sum / animals.size();
			
		}
		
		return result;
		
	}
	
	private Integer[][] calculateSampleVariance(List<Animal> animals, Integer[] sampleMean) {
		
		Integer[][] result = new Integer[Animal.NUM_INT_FEATURES][Animal.NUM_INT_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			int sum = 0;
			for (Animal a : animals) {
				sum += a.getFeatures()[i] - sampleMean[i];
			}
			
			result[i][i] = sum / animals.size();
			
		}
		
		return result;
		
	}
	
	private Map<Integer, List<Animal>> getClassMap(List<Animal> animals) {
		
		Map<Integer, List<Animal>> classMap = new HashMap<Integer, List<Animal>>();
		for (Animal a : animals) {
			
			int dataClass = a.getDataClass();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<Animal>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;		
		
	}

}
