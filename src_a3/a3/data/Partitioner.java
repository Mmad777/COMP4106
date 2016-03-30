package a3.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.model.DataModel;

public class Partitioner {
	
	public static Map<String, List<DataModel>> getClassMap(List<DataModel> data) {
		
		Map<String, List<DataModel>> classMap = new HashMap<String, List<DataModel>>();
		for (DataModel a : data) {
			
			String dataClass = a.getClassName();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<DataModel>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;	
		
	}
	
	public static PartitionedData getPartitionedData(int kFold, Map<String, List<DataModel>> classMap) {
		
		List<DataModel> trainingData = new ArrayList<DataModel>();
		List<DataModel> testData = new ArrayList<DataModel>();
		
		classMap.keySet().forEach(c -> {
			
			List<DataModel> data = classMap.get(c);
			int numTraining = (data.size() / kFold) * (kFold - 1);
			trainingData.addAll(data.subList(0, numTraining));					
			testData.addAll(data.subList(numTraining, data.size()));
			
		});
		
		return new PartitionedData(trainingData, testData);
		
	}
	
	public static class PartitionedData {
		
		private List<DataModel> trainingData;
		private List<DataModel> testData;
		
		public PartitionedData(List<DataModel> trainingData, List<DataModel> testData) {
			this.trainingData = trainingData;
			this.testData = testData;			
		}

		public List<DataModel> getTrainingData() {
			return trainingData;
		}
		public List<DataModel> getTestData() {
			return testData;
		}
		
	}

}
