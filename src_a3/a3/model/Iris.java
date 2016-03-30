package a3.model;

public class Iris {
	
	public static final int NUM_INT_FEATURES = 4;
	public static final Integer[] NON_INT_FEATURE_INDICES = { 4 };
	public static final int TYPE_INDEX = 4;
	
	private Double[] features;
	private String dataClass;
	
	public Iris(Double[] features, String dataClass) {
		this.features = features;
		this.dataClass = dataClass;
	}

	public String getDataClass() {
		return dataClass;
	}

	public Double[] getFeatures() {
		return features;
	}
	
}
