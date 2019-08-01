
public class InnerPiece extends Piece {

	public final static int INNER_NUM_OF_ZEROS = 0;

	// Constructor
	public InnerPiece(int numOfPiece, int topSlice, int rightSlice, int bottomSlice, int leftSlice) {
		super(numOfPiece, topSlice, rightSlice, bottomSlice, leftSlice);
		checkIfInnerPiece();
	}

	// Checks if a piece is an Inner Piece
	public void checkIfInnerPiece() {
		int numOfZeroSlices = 0;
		if (getTopSlice() == 0)
			numOfZeroSlices++;
		if (getRightSlice() == 0)
			numOfZeroSlices++;
		if (getBottomSlice() == 0)
			numOfZeroSlices++;
		if (getLeftSlice() == 0)
			numOfZeroSlices++;
		if (numOfZeroSlices != INNER_NUM_OF_ZEROS)
			throw new IllegalArgumentException("The Piece number " + getNumOfPiece() + " is not an invalid "
					+ getClass().getCanonicalName() + ".\nIt has only " + numOfZeroSlices
					+ " \"Zero slices\" and it should have " + INNER_NUM_OF_ZEROS);
	}
	
	// Clones the Inner Piece
	@Override
	public InnerPiece clone() throws CloneNotSupportedException {
		return (InnerPiece) super.clone();
	}

	// We add this empty methods to satisfy the abstract methods in'Piece'
	// class
	@Override
	public void setType() {
	}

	@Override
	public void changeOrientationOfAPiece(int orientation) {
	}
}
