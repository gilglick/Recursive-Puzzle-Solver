
public abstract class Piece implements Cloneable, Comparable<Piece> {

	public final static String[] PIECE_TYPE = { "Top-Left", "Top-Right", "Bottom-Right", "Bottom-Left", "Top", "Right",
			"Bottom", "Left" };
	public final static int TOP_LEFT = 0, TOP_RIGHT = 1, BOTTOM_RIGHT = 2, BOTTOM_LEFT = 3, TOP = 4, RIGHT = 5,
			BOTTOM = 6, LEFT = 7;
	private int numOfPiece;
	private int topSlice;
	private int rightSlice;
	private int bottomSlice;
	private int leftSlice;
	private int numberOfRotation;

	// Constructor
	public Piece(int numOfPiece, int topSlice, int rightSlice, int bottomSlice, int leftSlice) {
		this.numOfPiece = numOfPiece;
		this.topSlice = topSlice;
		this.rightSlice = rightSlice;
		this.bottomSlice = bottomSlice;
		this.leftSlice = leftSlice;
	}

	// Getters and Setters
	public void setNumOfPiece(int numOfPiece) {
		this.numOfPiece = numOfPiece;
	}

	public int getNumOfPiece() {
		return numOfPiece;
	}

	public void setTopSlice(int topSlice) {
		this.topSlice = topSlice;
	}

	public int getTopSlice() {
		return topSlice;
	}

	public void setRightSlice(int rightSlice) {
		this.rightSlice = rightSlice;
	}

	public int getRightSlice() {
		return rightSlice;
	}

	public void setBottomSlice(int bottomSlice) {
		this.bottomSlice = bottomSlice;
	}

	public int getBottomSlice() {
		return bottomSlice;
	}

	public void setLeftSlice(int leftSlice) {
		this.leftSlice = leftSlice;
	}

	public int getLeftSlice() {
		return leftSlice;
	}

	public void setNumberOfRotation(int numberOfRotation) {
		this.numberOfRotation = numberOfRotation;
	}

	public int getNumberOfRotation() {
		return numberOfRotation;
	}

	// Rotate clockwise and return the piece
	public Piece rotateClockwiseAndReturnThePiece(int numberOfRotationToMake) {
		rotateClockwise(numberOfRotationToMake);
		return this;
	}

	// Set the orientation of the piece (by number of rotation to make)
	public void setOrientation(int rotations) {
		while (getNumberOfRotation() != rotations) {
			rotateClockwise();
		}
	}

	// Reset the orientation of the piece (return it to its first state)
	public void resetRotation() {
		while (getNumberOfRotation() != 0) {
			rotateClockwise();
		}
	}

	// Rotate the piece clockwise number of times
	public void rotateClockwise(int numberOfRotationToMake) {
		for (int i = 0; i < numberOfRotationToMake; i++) {
			rotateClockwise();
		}
	}

	// Rotate the piece clockwise one time
	public void rotateClockwise() {
		int temp = getLeftSlice();
		setLeftSlice(getBottomSlice());
		setBottomSlice(getRightSlice());
		setRightSlice(getTopSlice());
		setTopSlice(temp);
		if (getNumberOfRotation() != 3)
			setNumberOfRotation(getNumberOfRotation() + 1);
		else
			setNumberOfRotation(0);
		setType();
	}

	// Rotate the piece counter clockwise number of times
	public void rotateCounterClockwise(int numberOfRotationToMake) {
		for (int i = 0; i < numberOfRotationToMake; i++) {
			rotateCounterClockwise();
		}
	}

	// Rotate the piece counter clockwise
	public void rotateCounterClockwise() {
		int temp = getLeftSlice();
		setLeftSlice(getTopSlice());
		setTopSlice(getRightSlice());
		setRightSlice(getBottomSlice());
		setBottomSlice(temp);
		if (getNumberOfRotation() != 0)
			setNumberOfRotation(getNumberOfRotation() - 1);
		else
			setNumberOfRotation(3);

		setType();
	}

	// Abstract method setType
	public abstract void setType();

	// Abstract method changeOrientationOfAPiece
	public abstract void changeOrientationOfAPiece(int orientation);

	// If all the slices of a piece equal to all the slices of another piece, the
	// pieces are equal
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Piece))
			return false;
		Piece otherPiece = (Piece) other;
		if (topSlice != otherPiece.getTopSlice() || rightSlice != otherPiece.getRightSlice()
				|| bottomSlice != otherPiece.getBottomSlice() || leftSlice != otherPiece.getLeftSlice())
			return false;
		return true;

	}

	// A string presentation of the piece
	@Override
	public String toString() {
		String str = "";
		str += String.format("-----------\r\n");
		str += String.format("|  \\%2d /  |\r\n", getTopSlice());
		str += String.format("|%2d \\ / %2d|\r\n", getLeftSlice(), getRightSlice());
		str += String.format("|   / \\   |\r\n");
		str += String.format("|  /%2d \\  |\r\n", getBottomSlice());
		str += String.format("-----------");
		return str;
	}

	// Clones the piece
	@Override
	public Piece clone() throws CloneNotSupportedException {
		return (Piece) super.clone();
	}

	// Compares the pieces by number
	@Override
	public int compareTo(Piece otherPiece) {
		if (getNumOfPiece() > otherPiece.getNumOfPiece())
			return 1;
		else if (getNumOfPiece() < otherPiece.getNumOfPiece())
			return -1;
		return 0;
	}
}
