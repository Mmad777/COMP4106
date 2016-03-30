package a3.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.opencsv.CSVReader;

import a3.model.Iris;

public class CSVParser {
	
	private final static String DATASETS_DIR = "datasets/";
	private final static String IRIS_PATH = DATASETS_DIR + "iris.csv";
	
	public static List<Iris> parseIrisDataset() throws IOException {
		
		List<Iris> result = new ArrayList<Iris>();
		
		CSVReader reader = new CSVReader(new FileReader(IRIS_PATH), ',');
		reader.readAll().forEach(e -> {			
			double[] features = parseDoubleArray(e, Iris.NON_INT_FEATURE_INDICES);
			result.add(new Iris(features, e[Iris.TYPE_INDEX]));			// subtract 1 from class to allow it to be indexed from 0
		});	    
	    reader.close();
	    
	    return result;
		
	}
	
	private static double[] parseDoubleArray(String[] arr, Integer[] skipIndices) {

		List<Double> result = new ArrayList<Double>();
		
		List<Integer> skip = Arrays.asList(skipIndices);
		for (int i=0; i<arr.length; i++) {
			if (skip.contains(i)) continue;
			result.add(Double.parseDouble(arr[i]));
		}
		
		Double[] nonPrimArr = result.toArray(new Double[result.size()]);
		return ArrayUtils.toPrimitive(nonPrimArr);
		
	}

}
