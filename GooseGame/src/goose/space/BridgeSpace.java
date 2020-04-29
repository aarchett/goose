package goose.space;

public class BridgeSpace extends Space {

	private int bridgeTo = 0;
	
	public BridgeSpace(int position, int positionAfterBridge) {
		super(position, "The Bridge");
		bridgeTo = positionAfterBridge;
	}

	public int land(Space lastPosition, int dice1, int dice2) {
		return bridgeTo;
	}
}
