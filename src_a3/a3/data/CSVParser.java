package a3.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.opencsv.CSVReader;

import a3.model.DataModel;

public class CSVParser {
	
	private final static String DATASETS_DIR = "datasets/";
	
	private final static String IRIS_PATH = DATASETS_DIR + "iris.csv";	
	private final static String HEART_PATH = DATASETS_DIR + "heartDisease.csv";
	private final static String WINE_PATH = DATASETS_DIR + "wine.csv";
	
	public static DataSet parseHeartDataset() throws IOException {
		
		List<DataModel> result = new ArrayList<DataModel>();
		
		CSVReader reader = new CSVReader(new FileReader(HEART_PATH), ',');
		reader.readAll().forEach(e -> {			
			double[] features = parseDoubleArray(e, 13);
			result.add(new DataModel(features, e[13]));		
		});	    
	    reader.close();

	    return new DataSet("Heart", 13, result);
		
	}
	
	public static DataSet parseIrisDataset() throws IOException {
		
		List<DataModel> result = new ArrayList<DataModel>();
		
		CSVReader reader = new CSVReader(new FileReader(IRIS_PATH), ',');
		reader.readAll().forEach(e -> {			
			double[] features = parseDoubleArray(e, 4);
			result.add(new DataModel(features, e[4]));			
		});	    
	    reader.close();
	    
	    return new DataSet("Iris", 4, result);
		
	}
	
	public static DataSet parseWineDataset() throws IOException {
		
		List<DataModel> result = new ArrayList<DataModel>();
		
		CSVReader reader = new CSVReader(new FileReader(WINE_PATH), ',');
		reader.readAll().forEach(e -> {			
			double[] features = parseDoubleArray(e, 13);
			result.add(new DataModel(features, e[13]));			
		});	    
	    reader.close();

	    return new DataSet("Wine", 13, result);
		
	}
	
	private static double[] parseDoubleArray(String[] arr, int skip) {

		List<Double> result = new ArrayList<Double>();
		
		for (int i=0; i<arr.length; i++) {
			if (i == skip) continue;
			result.add(Double.parseDouble(arr[i]));
		}
		
		Double[] nonPrimArr = result.toArray(new Double[result.size()]);
		return ArrayUtils.toPrimitive(nonPrimArr);
		
	}

}
