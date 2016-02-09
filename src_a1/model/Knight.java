package model;

public class Knight extends BoardEntity {

	public Knight(int x, int y) {
		super(x, y);
	}

	@Override
	public String getIcon() {
		return "K";
	}

}
