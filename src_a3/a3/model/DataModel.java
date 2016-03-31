package a3.model;

public class DataModel {
	
	private double[] dimensions;
	private String className;
	
	public DataModel(double[] dimensions, String className) {
		this.dimensions = dimensions;
		this.className = className;
	}
	
	public int getNumDimensions() {
		return dimensions.length;
	}
	
	public double getDimension(int index) {
		return dimensions[index];
	}
	
	public double[] getDimensions() {
		return dimensions;
	}
	public String getClassName() {
		return className;
	}

}
