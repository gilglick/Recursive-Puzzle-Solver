
public class EdgePiece extends Piece {

	public final static int EDGE_NUM_OF_ZEROS = 1;
	public String type;

	// Constructor
	public EdgePiece(int numOfPiece, int topSlice, int rightSlice, int bottomSlice, int leftSlice) {
		super(numOfPiece, topSlice, rightSlice, bottomSlice, leftSlice);
		checkIfEdgePiece();
		setType();
	}

	// Checks if a piece is a Edge Piece
	public void checkIfEdgePiece() {
		int numOfZeroSlices = 0;
		if (getTopSlice() == 0)
			numOfZeroSlices++;
		if (getRightSlice() == 0)
			numOfZeroSlices++;
		if (getBottomSlice() == 0)
			numOfZeroSlices++;
		if (getLeftSlice() == 0)
			numOfZeroSlices++;
		if (numOfZeroSlices != EDGE_NUM_OF_ZEROS)
			throw new IllegalArgumentException("The Piece number " + getNumOfPiece() + " is not an invalid "
					+ getClass().getCanonicalName() + ".\nIt has only " + numOfZeroSlices
					+ " \"Zero slices\" and it should have " + EDGE_NUM_OF_ZEROS);
	}

	// Sets the type of the Edge Piece
	@Override
	public void setType() {
		if (getTopSlice() == 0)
			type = PIECE_TYPE[TOP];
		if (getRightSlice() == 0)
			type = PIECE_TYPE[RIGHT];
		if (getBottomSlice() == 0)
			type = PIECE_TYPE[BOTTOM];
		if (getLeftSlice() == 0)
			type = PIECE_TYPE[LEFT];
	}

	// Gets the type of the Edge Piece
	public String getType() {
		return this.type;
	}

	// Clones the Edge Piece
	@Override
	public EdgePiece clone() throws CloneNotSupportedException {
		return (EdgePiece) super.clone();
	}

	// Change the orientation of a piece
	@Override
	public void changeOrientationOfAPiece(int orientation) {
		if (!(4 <= orientation && orientation <= 7)) {
			throw new IllegalArgumentException(
					"The orientation of an Edge Piece should be in range of 4-7 (Top, Right, Bottom, Left");
		}
		while (!getType().equals(PIECE_TYPE[orientation]))
			rotateClockwise();
	}
}
