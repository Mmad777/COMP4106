package a3.data;

import java.util.List;

import a3.model.DataModel;

public class DataSet {
	
	private int dimensions;
	private List<DataModel> data;
	private String name;
	
	public DataSet(int dimensions, List<DataModel> data) {
		this.dimensions = dimensions;
		this.data = data;
		this.name = "";
	}
	
	public DataSet(String name, int dimensions, List<DataModel> data) {
		this(dimensions, data);
		this.name = name;
	}
	
	public String getName() {
		return name;	
	}

	public int getDimensions() {
		return dimensions;
	}

	public List<DataModel> getData() {
		return data;
	}
	
	

}
