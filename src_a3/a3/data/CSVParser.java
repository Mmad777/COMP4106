package a3.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import a3.model.Animal;

public class CSVParser {
	
	private final static String DATASETS_DIR = "datasets/";
	private final static String ZOO_PATH = DATASETS_DIR + "zoo.csv";
	
	public static List<Animal> parseZooDataset() throws IOException {
		
		List<Animal> result = new ArrayList<Animal>();
		
		CSVReader reader = new CSVReader(new FileReader(ZOO_PATH), ',');
		List<String[]> entries = reader.readAll();
		entries.forEach(e -> {
			
			// TODO - Do this with annotations/more dynamically
			Animal animal = new Animal();
			animal.setName(e[0]);
			animal.setHair(strToBoolean(e[1]));
			animal.setFeathers(strToBoolean(e[2]));
			animal.setEggs(strToBoolean(e[3]));
			animal.setMilk(strToBoolean(e[4]));
			animal.setAirborne(strToBoolean(e[5]));
			animal.setAquatic(strToBoolean(e[6]));
			animal.setPredator(strToBoolean(e[7]));
			animal.setToothed(strToBoolean(e[8]));
			animal.setBackbone(strToBoolean(e[9]));
			animal.setBreathes(strToBoolean(e[10]));
			animal.setVenomous(strToBoolean(e[11]));
			animal.setFins(strToBoolean(e[12]));
			animal.setLegs(Integer.parseInt(e[13]));
			animal.setTail(strToBoolean(e[14]));
			animal.setDomestic(strToBoolean(e[15]));
			animal.setCatsize(strToBoolean(e[16]));
			animal.setType(Integer.parseInt(e[17]));
			result.add(animal);
			
		});
	    
	    reader.close();
	    return result;
		
	}
	
	private static boolean strToBoolean(String str) {
		return str.equals("1") ? true : false;
	}

}
