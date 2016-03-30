package a3.classifiers;

import java.util.List;
import java.util.Map;

import a3.data.Partitioner.PartitionedData;
import a3.model.DataModel;

public interface IClassifier {
	
	public Map<String, List<DataModel>> classify(PartitionedData partitionedData);

}
