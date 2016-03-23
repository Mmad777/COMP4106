package a3.model;

public class Animal {
	
	public static final int NUM_INT_FEATURES = 16;
	public static final Integer[] NON_INT_FEATURE_INDICES = { 0 };
	public static final int TYPE_INDEX = 18 - NON_INT_FEATURE_INDICES.length;
	
	private Integer[] features;
	private Integer dataClass;
	
	public Animal(Integer[] features, int dataClass) {
		this.features = features;
		this.dataClass = dataClass;
	}

	public Integer getDataClass() {
		return dataClass;
	}

	public Integer[] getFeatures() {
		return features;
	}
	
}
