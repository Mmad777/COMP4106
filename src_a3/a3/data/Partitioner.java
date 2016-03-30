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
	
	public static PartitionedData getPartitionedData(int testGroup, int kFold, Map<String, List<DataModel>> classMap) {
		
		List<DataModel> trainingData = new ArrayList<DataModel>();
		List<DataModel> testData = new ArrayList<DataModel>();
		
		classMap.keySet().forEach(c -> {
			
			List<DataModel> data = classMap.get(c);
			
			int groupSize = (data.size() / kFold);
			List<List<DataModel>> splitData = splitArray(data, groupSize);			
			for (int i=0; i<splitData.size(); i++) {
				
				if (i == testGroup) {
					testData.addAll(splitData.get(i));
				} else {
					trainingData.addAll(splitData.get(i));
				}
				
			}	
			
		});
		
		return new PartitionedData(trainingData, testData);
		
	}
	
	private static List<List<DataModel>> splitArray(List<DataModel> arr, int size) {

		List<List<DataModel>> groups = new ArrayList<List<DataModel>>();
		for (int i = 0; i < arr.size(); i += size) {
			groups.add(arr.subList(i,
		            Math.min(i + size, arr.size())));
		}
		return groups;
		
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
