package a3.data;

import java.util.List;

import a3.model.Iris;

public class Partitioner {
	
	public static PartitionedData getPartitionedData(int kFold, List<Iris> data) {
		
		int groupSize = data.size() / kFold;
		//int numTraining = (kFold - 1) * groupSize;		
		int numTraining = 140;
		
		List<Iris> trainingData = data.subList(0, numTraining);
		List<Iris> testData = data.subList(numTraining, data.size());
		
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
