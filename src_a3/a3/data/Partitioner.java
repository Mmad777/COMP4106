package a3.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.model.Iris;

public class Partitioner {
	
	public static Map<String, List<Iris>> getClassMap(List<Iris> data) {
		
		Map<String, List<Iris>> classMap = new HashMap<String, List<Iris>>();
		for (Iris a : data) {
			
			String dataClass = a.getDataClass();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<Iris>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;	
		
	}
	
	public static PartitionedData getPartitionedData(int kFold, Map<String, List<Iris>> classMap) {
		
		List<Iris> trainingData = new ArrayList<Iris>();
		List<Iris> testData = new ArrayList<Iris>();
		
		classMap.keySet().forEach(c -> {
			
			List<Iris> data = classMap.get(c);
			int numTraining = (data.size() / kFold) * (kFold - 1);
			trainingData.addAll(data.subList(0, numTraining));					
			testData.addAll(data.subList(numTraining, data.size()));
			
		});
		
		return new PartitionedData(trainingData, testData);
		
	}
	
	public static class PartitionedData {
		
		private List<Iris> trainingData;
		private List<Iris> testData;
		
		public PartitionedData(List<Iris> trainingData, List<Iris> testData) {
			this.trainingData = trainingData;
			this.testData = testData;			
		}

		public List<Iris> getTrainingData() {
			return trainingData;
		}
		public List<Iris> getTestData() {
			return testData;
		}
		
	}

}
