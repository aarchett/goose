package goose.space;

public class Space {

	private final int value;
	protected String name;
	
	public Space(int position) {
		this(position, null);
	}
	
	public Space(int position, String name) {
		this.value = position;
		this.name = name;
	}
	
	public int land(Space lastPosition, int dice1, int dice2) {
		return value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return (name != null) ? name : String.valueOf(value);
	}
}
