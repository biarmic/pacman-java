
public enum Direction {
	Right, Left, Up, Down;
	public static Direction random() {
		return Direction.values()[(int)(Math.random()*4)];
	}
}
