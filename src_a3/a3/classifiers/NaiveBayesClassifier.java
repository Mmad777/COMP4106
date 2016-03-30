package a3.classifiers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import a3.model.Iris;

public class NaiveBayesClassifier {
	
	private final static int NUM_DEC_DIGITS = 12;
	
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
				
				Double[] test = {5.0425, 3.445, 1.4675, 0.25};
				
				logMean(classes[i], meanA);
				logMean(classes[j], test);
				
				Double[][] covarianceA = calcFullCovarianceMatrix(classA, meanA);
				Double[][] covarianceB = calcFullCovarianceMatrix(classB, test);
				
				logCovariance(classes[i], covarianceA);
				logCovariance(classes[j], covarianceB);
				
			}
		}
		
	}
	
	private void logMean(String className, Double[] arr) {
		
		System.out.print("Mean of " + className + ": [");
		Stream.of(arr).forEach(i -> {
			System.out.print(i + " ");
		});
		System.out.println("]");
	
	}
	
	private void logCovariance(String className, Double[][] matrix) {
		
		System.out.println("Covariance of " + className + ": \n{");
		Stream.of(matrix).forEach(r -> {
			System.out.print("\t[");
			Stream.of(r).forEach(c -> {
				System.out.print(c == null ? "0 " : c + " ");
			});
			System.out.println("]");
		});
		System.out.println("}");
	
	}
	
	private Double[] calculateSampleMean(List<Iris> irises) {
		
		Double[] result = new Double[Iris.NUM_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			Double sum = new Double(0);
			for (Iris a : irises) {
				sum += a.getFeatures()[i];
			}
			
			result[i] = round(sum / irises.size(), NUM_DEC_DIGITS);
			
		}
		
		return result;
		
	}
	
	private Double[][] calcFullCovarianceMatrix(List<Iris> irises, Double[] sampleMean) {
		
		Double[][] result = new Double[Iris.NUM_FEATURES][Iris.NUM_FEATURES];
		for (int r=0; r<result.length; r++) {
			for (int c=0; c<result.length; c++) {
				
				double sum = 0;
				for (Iris iris : irises) {
					sum += ((iris.getFeatures()[r] - sampleMean[r]) * (iris.getFeatures()[c] - sampleMean[c]));
				}
				
				result[r][c] = round(sum / irises.size(), NUM_DEC_DIGITS);
				
			}
			
		}
		
		return result;
		
		
	}
	
	private Double[][] calcDiagonalCovarianceMatrix(List<Iris> irises, Double[] sampleMean) {
		
		Double[][] result = new Double[Iris.NUM_FEATURES][Iris.NUM_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			double sum = 0;
			for (Iris a : irises) {
				sum += Math.pow(a.getFeatures()[i] - sampleMean[i], 2);
			}
			
			result[i][i] = round(Math.pow(sum / irises.size(), 2), NUM_DEC_DIGITS);
			
		}
		
		return result;
		
	}
	
	/* TODO - Move to clibrary */
	private static double round(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	    
	}
	
	private Map<String, List<Iris>> getClassMap(List<Iris> irisMap) {
		
		Map<String, List<Iris>> classMap = new HashMap<String, List<Iris>>();
		for (Iris a : irisMap) {
			
			String dataClass = a.getDataClass();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<Iris>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;		
		
	}

}
