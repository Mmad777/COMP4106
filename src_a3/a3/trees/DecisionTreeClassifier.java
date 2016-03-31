package a3.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import a3.data.DataSet;
import a3.model.DataModel;

public class DecisionTreeClassifier {
	
	private DataSet dataSet;
	
	public DecisionTreeClassifier(DataSet dataSet) {
		this.dataSet = dataSet;
		
		generate();
	}
	
	public void generate() {
		
		AttributeMap map = createAttributeMap();
		
		
	}
	
	private AttributeMap createAttributeMap() {
		
		AttributeMap map = new AttributeMap();
		for (int i=0; i<dataSet.getDimensions(); i++) {

			// Create the splits for that attribute
			double[] splits = calcAttrSplitValues(i, dataSet.getData());
			
			// For every
			
			
		}
		
		return map;
		
	}
	
	private double[] calcAttrSplitValues(final int dimensionIndex, List<DataModel> data) {

		Set<Double> values = data
				.stream()
				.map(m -> m.getDimension(dimensionIndex))
				.collect(Collectors.toSet());
		
		Double[] arr = values.toArray(new Double[values.size()]);
		
		double[] sorted = ArrayUtils.toPrimitive(arr);
		Arrays.sort(sorted);			
		
		double[] splits = new double[arr.length - 1];
		for (int k=0; k<sorted.length-1; k++) {
			splits[k] = (sorted[k] + sorted[k+1]) / 2;
		}
		
		return splits;
		
	}
	
	private class AttributeMap {
		
		Map<Integer, Map<Double, List<DataModel>>> map =  new HashMap<Integer, Map<Double, List<DataModel>>>();
		
		public AttributeMap() {
			// Empty
		}
		
		
	}
	

}
