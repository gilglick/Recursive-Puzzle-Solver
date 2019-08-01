
public class NullPiece extends Piece {

	// Constructor
	public NullPiece() {
		super(-1, -1, -1, -1, -1);
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
