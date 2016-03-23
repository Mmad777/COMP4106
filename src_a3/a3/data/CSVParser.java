package a3.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

import a3.model.Animal;

public class CSVParser {
	
	private final static String DATASETS_DIR = "datasets/";
	private final static String ZOO_PATH = DATASETS_DIR + "zoo.csv";
	
	public static List<Animal> parseZooDataset() throws IOException {
		
		List<Animal> result = new ArrayList<Animal>();
		
		CSVReader reader = new CSVReader(new FileReader(ZOO_PATH), ',');
		reader.readAll().forEach(e -> {			
			Integer[] features = parseIntArray(e, Animal.NON_INT_FEATURE_INDICES);
			result.add(new Animal(features, features[Animal.TYPE_INDEX - 1] - 1));			// subtract 1 from class to allow it to be indexed from 0
		});	    
	    reader.close();
	    
	    return result;
		
	}
	
	private static Integer[] parseIntArray(String[] arr, Integer[] skipIndices) {

		List<Integer> result = new ArrayList<Integer>();
		
		List<Integer> skip = Arrays.asList(skipIndices);
		for (int i=0; i<arr.length; i++) {
			if (skip.contains(i)) continue;
			result.add(Integer.parseInt(arr[i]));
		}
		
		return result.toArray(new Integer[arr.length]);
		
	}

}
