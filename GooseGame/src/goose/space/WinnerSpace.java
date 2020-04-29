package goose.space;

public class WinnerSpace extends Space {

	public WinnerSpace(int position) {
		super(position);
	}
	
	public WinnerSpace(int position, String name) {
		super(position, name);
	}

	public int land(Space lastPosition, int dice1, int dice2) {
		int nextValue = getValue();
		int sum = lastPosition.getValue() + dice1 + dice2;
		if (sum > getValue()) {
			//bounce
			nextValue = getValue() - (sum - getValue());
		}
		return nextValue;
	}
}
