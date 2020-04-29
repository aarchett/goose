package goose.space;

public class GooseSpace extends Space {

	public GooseSpace(int position) {
		super(position, "The Goose");
	}

	@Override
	public String toString() {
		return new StringBuffer().append(String.valueOf(getValue())).append(", ").append(name).toString();
	}
	
	@Override
	public int land(Space lastPosition, int dice1, int dice2) {
		return getValue() + dice1 + dice2;
	}
}
