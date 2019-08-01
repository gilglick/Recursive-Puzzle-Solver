
public class CornerPiece extends Piece {

	public final static int CORNER_NUM_OF_ZEROS = 2;
	public String type;

	// Constructor
	public CornerPiece(int numOfPiece, int topSlice, int rightSlice, int bottomSlice, int leftSlice) {
		super(numOfPiece, topSlice, rightSlice, bottomSlice, leftSlice);
		checkIfCornerPiece();
		setType();
	}

	// Checks if a piece is a Corner Piece
	public void checkIfCornerPiece() {
		int numOfZeroSlices = 0;
		if (getTopSlice() == 0)
			numOfZeroSlices++;
		if (getRightSlice() == 0)
			numOfZeroSlices++;
		if (getBottomSlice() == 0)
			numOfZeroSlices++;
		if (getLeftSlice() == 0)
			numOfZeroSlices++;
		if (numOfZeroSlices != CORNER_NUM_OF_ZEROS)
			throw new IllegalArgumentException("The Piece number " + getNumOfPiece() + " is not an invalid "
					+ getClass().getCanonicalName() + ".\nIt has only " + numOfZeroSlices
					+ " \"Zero slices\" and it should have " + CORNER_NUM_OF_ZEROS);
	}

	// Sets the type of the Corner Piece
	@Override
	public void setType() {
		if (getTopSlice() + getLeftSlice() == 0)
			type = PIECE_TYPE[TOP_LEFT];
		if (getTopSlice() + getRightSlice() == 0)
			type = PIECE_TYPE[TOP_RIGHT];
		if (getBottomSlice() + getRightSlice() == 0)
			type = PIECE_TYPE[BOTTOM_RIGHT];
		if (getBottomSlice() + getLeftSlice() == 0)
			type = PIECE_TYPE[BOTTOM_LEFT];
	}

	// Gets the type of the Corner Piece
	public String getType() {
		return this.type;
	}

	// Change the orientation of a piece
	@Override
	public void changeOrientationOfAPiece(int orientation) {
		if (!(0 <= orientation && orientation <= 3)) {
			throw new IllegalArgumentException(
					"The orientation of a Corner Piece should be in range of 0-3 (Top-Left, Top-Right, Bottom-Right, Bottom-Left)");
		}
		while (!getType().equals(PIECE_TYPE[orientation]))
			rotateClockwise();
	}

	// Clones the Corner Piece
	@Override
	public CornerPiece clone() throws CloneNotSupportedException {
		return (CornerPiece) super.clone();
	}
}
