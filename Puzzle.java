import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Puzzle {

	private ArrayList<CornerPiece> cornerPieces = new ArrayList<CornerPiece>();
	private ArrayList<EdgePiece> edgePieces = new ArrayList<EdgePiece>();
	private ArrayList<InnerPiece> innerPieces = new ArrayList<InnerPiece>();
	private Piece[][] orderedPieces;
	private int numberOfPieces;
	private int numOfSolutions = 0;
	public final static int TOP_LEFT = 0, TOP_RIGHT = 1, BOTTOM_RIGHT = 2, BOTTOM_LEFT = 3, TOP = 4, RIGHT = 5,
			BOTTOM = 6, LEFT = 7, NOTHING = 8;
	public final static int LINE_1 = 1, LINE_2 = 2, LINE_3 = 3, LINE_4 = 4, LINE_5 = 5, LINE_6 = 6;
	public final static int FIRST_ROW = 0, FIRST_COLUMN = 0;
	public int LAST_ROW = 0, LAST_COLUMN = 0;
	public static Comparator<? super Piece> leftCmp = Comparator.comparing(Piece::getLeftSlice);
	public static Comparator<? super Piece> topCmp = Comparator.comparing(Piece::getTopSlice);
	public static Comparator<? super Piece> leftThenTopCmp = Comparator.comparing(Piece::getLeftSlice)
			.thenComparing(Piece::getTopSlice);

	// Constructor
	public Puzzle(String piecesAsAString) {
		setPieces(piecesAsAString);
		setNumOfPieces();
		resetOrderedPieces();
		changeOrientationOfAllEdgePieces(cornerPieces, TOP_LEFT);
		changeOrientationOfAllEdgePieces(edgePieces, TOP);
	}

	// Set the pieces
	// Inner Pieces have 0 zeroes
	// Edge Pieces have 1 zero
	// Corner Pieces have 2 zeroes
	public void setPieces(String piecesAsAString) {
		String[] piecesAsAStringArray = piecesAsAString.split(";");
		for (int i = 0; i < piecesAsAStringArray.length; i++) {
			String pieceAsAString = piecesAsAStringArray[i].trim();
			int[] pieceAsACollectionOfSlices = convertStringOfAPieceToAnIntArray(pieceAsAString);
			int numOfZeroes = checkNumOfZerosInAPiece(pieceAsACollectionOfSlices);
			Piece piece = null;
			switch (numOfZeroes) {
			case 0:
				piece = new InnerPiece(i, pieceAsACollectionOfSlices[0], pieceAsACollectionOfSlices[1],
						pieceAsACollectionOfSlices[2], pieceAsACollectionOfSlices[3]);
				innerPieces.add((InnerPiece) piece);
				break;
			case 1:
				piece = new EdgePiece(i, pieceAsACollectionOfSlices[0], pieceAsACollectionOfSlices[1],
						pieceAsACollectionOfSlices[2], pieceAsACollectionOfSlices[3]);
				edgePieces.add((EdgePiece) piece);
				break;
			case 2:
				piece = new CornerPiece(i, pieceAsACollectionOfSlices[0], pieceAsACollectionOfSlices[1],
						pieceAsACollectionOfSlices[2], pieceAsACollectionOfSlices[3]);
				cornerPieces.add((CornerPiece) piece);
				break;
			default:
				throw new IllegalArgumentException(
						"Number of Zeroes in a piece should be 0, 1 or 2. Not " + numOfZeroes);
			}
		}
	}

	// Set the ArrayList of all the Corner Pieces
	public void setCornerPieces(ArrayList<CornerPiece> arrayOfCornerPieces) {
		cornerPieces = arrayOfCornerPieces;
	}

	// Get the ArrayList of all the Corner Pieces
	public ArrayList<CornerPiece> getCornerPieces() {
		return cornerPieces;
	}

	// Set the ArrayList of all the Edge Pieces
	public void setEdgePieces(ArrayList<EdgePiece> arrayOfEdgePieces) {
		edgePieces = arrayOfEdgePieces;
	}

	// Get the ArrayList of all the Edge Pieces
	public ArrayList<EdgePiece> getEdgePieces() {
		return edgePieces;
	}

	// Set the ArrayList of all the Inner Pieces
	public void setInnerPieces(ArrayList<InnerPiece> arrayOfInnerPieces) {
		innerPieces = arrayOfInnerPieces;
	}

	// Get the ArrayList of all the Inner Pieces
	public ArrayList<InnerPiece> getInnerPieces() {
		return innerPieces;
	}

	// Set the number of pieces in the Puzzle
	public void setNumOfPieces() {
		this.numberOfPieces = cornerPieces.size() + edgePieces.size() + innerPieces.size();
	}

	// Get the number of pieces in the Puzzle
	public int getNumOfPieces() {
		return this.numberOfPieces;
	}

	// Get the number of solutions - To activate after "solve()"
	public int getNumOfSolutions() {
		return numOfSolutions;
	}

	// Reset the size of the Array 'orderedPieces'
	public void resetOrderedPieces() {
		int size = (int) Math.sqrt(getNumOfPieces());
		this.orderedPieces = new Piece[size][size];
		LAST_ROW = size - 1;
		LAST_COLUMN = size - 1;

	}

	// Convert a piece from a string representation to an Int Array
	public int[] convertStringOfAPieceToAnIntArray(String pieceAsAString) {
		String[] tempStringArray = pieceAsAString.split(",\\[");
		String[] pieceAsAStringArray = tempStringArray[1].substring(0, tempStringArray[1].length() - 1).split(",");
		int[] intArray = new int[4];
		for (int i = 0; i < 4; i++) {
			intArray[i] = Integer.parseInt(pieceAsAStringArray[i].trim());
		}
		return intArray;
	}

	// Check the number of zeros in a piece
	public int checkNumOfZerosInAPiece(int[] pieceAsACollectionOfSlices) {
		int numOfZeroes = 0;
		for (int i = 0; i < 4; i++) {
			if (pieceAsACollectionOfSlices[i] == 0) {
				numOfZeroes++;
			}
		}
		return numOfZeroes;
	}

	// Make a deep copy of ArrayList
	// The Suppress Warnings is necessary here because of the "clone" method
	@SuppressWarnings("unchecked")
	public <T extends Piece> ArrayList<T> makeADeepCopyOfArray(ArrayList<T> arrayList)
			throws CloneNotSupportedException {
		ArrayList<T> copy = new ArrayList<T>();
		for (int i = 0; i < arrayList.size(); i++) {
			copy.add((T) arrayList.get(i).clone());
		}
		return copy;
	}

	// Change the Orientation of an ArrayList of Edge Pieces to a required
	// orientation
	public <T extends Piece> void changeOrientationOfAllEdgePieces(ArrayList<T> arrayList, int orientation) {
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i) instanceof EdgePiece)
				((EdgePiece) arrayList.get(i)).changeOrientationOfAPiece(orientation);
		}
	}

	// Change the Orientation of an ArrayList of Corner Pieces to a required
	// orientation
	public <T extends Piece> void changeOrientationOfAllCornerPieces(ArrayList<T> arrayList, int orientation) {
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i) instanceof CornerPiece)
				((CornerPiece) arrayList.get(i)).changeOrientationOfAPiece(orientation);
		}
	}

	// Rotate an ArrayList of Pieces clockwise
	public <T extends Piece> void rotatePiecesClockwise(ArrayList<T> arrayOfPieces) {
		for (int i = 0; i < arrayOfPieces.size(); i++) {
			arrayOfPieces.get(i).rotateClockwise();
		}
	}

	// Set the orientation of an ArrayList of Pieces
	public <T extends Piece> void setOrientation(ArrayList<T> arrayList, int Orientations) {
		switch (Orientations) {
		case TOP:
		case RIGHT:
		case BOTTOM:
		case LEFT:
			changeOrientationOfAllEdgePieces(arrayList, Orientations);
			break;
		case TOP_LEFT:
		case TOP_RIGHT:
		case BOTTOM_RIGHT:
		case BOTTOM_LEFT:
			changeOrientationOfAllCornerPieces(arrayList, Orientations);
			break;
		default:
			break;
		}
	}

	// Match one piece to the required place
	public <T extends Piece> void onePieceMatch(Piece[][] solutionPieces, int row, int column, int orientation,
			Comparator<? super Piece> comp, T somePiece, int nextRow, int nextColumn, Supplier<ArrayList<T>> getArray,
			Consumer<ArrayList<T>> setArray) throws CloneNotSupportedException {
		ArrayList<T> arrayOfPieces = getArray.get();
		setOrientation(arrayOfPieces, orientation);
		ArrayList<T> savedOriginal = makeADeepCopyOfArray(arrayOfPieces);
		ArrayList<T> optionalPieces = getOptional(arrayOfPieces, comp, somePiece);
		Collections.sort(optionalPieces);
		for (int i = 0; i < optionalPieces.size(); i++) {
			solutionPieces[row][column] = optionalPieces.remove(i).clone();

			if (orientation == BOTTOM_RIGHT) {
				System.out.printf("Solution number %d:\n", ++numOfSolutions);
				System.out.println(this);
				System.out.println();
				break;
			}

			arrayOfPieces.addAll(makeADeepCopyOfArray(optionalPieces));
			optionalPieces.clear();
			solve(solutionPieces, nextRow, nextColumn);
			arrayOfPieces = makeADeepCopyOfArray(savedOriginal);
			setArray.accept(arrayOfPieces);
			optionalPieces = getOptional(arrayOfPieces, comp, somePiece);
			Collections.sort(optionalPieces);

		}
		arrayOfPieces = makeADeepCopyOfArray(savedOriginal);
		setArray.accept(arrayOfPieces);
		solutionPieces[row][column] = new NullPiece();

	}

	// Get optional pieces for the required place (by characteristics)
	// The Suppress Warnings is necessary here because of the "clone" method
	@SuppressWarnings("unchecked")
	public <T extends Piece> ArrayList<T> getOptional(ArrayList<T> arrayListOfPieces, Comparator<? super T> cmp, T key)
			throws CloneNotSupportedException {
		ArrayList<T> optionalPieces = new ArrayList<T>();
		arrayListOfPieces.sort(cmp);
		int index = Collections.binarySearch(arrayListOfPieces, key, cmp);
		while (index >= 0) {
			optionalPieces.add((T) arrayListOfPieces.remove(index).clone());
			index = Collections.binarySearch(arrayListOfPieces, key, cmp);
		}
		return optionalPieces;
	}

	// An helper method for "solve()" method
	public void solve() throws CloneNotSupportedException {
		solve(orderedPieces, FIRST_ROW, FIRST_COLUMN);
	}

	// Solves the Puzzle recursively
	public void solve(Piece[][] solutionPieces, int row, int column) throws CloneNotSupportedException {
		int left = -1, top = -1;
		int nextRow = (column == LAST_COLUMN ? row + 1 : row);
		int nextColumn = (column == LAST_COLUMN ? FIRST_COLUMN : column + 1);
		if (column != FIRST_COLUMN)
			left = solutionPieces[row][column - 1].getRightSlice();
		if (row != FIRST_ROW)
			top = solutionPieces[row - 1][column].getBottomSlice();

		if (row == FIRST_ROW) {
			if (column == FIRST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, TOP_LEFT, leftThenTopCmp, new CornerPiece(-1, 0, -1, -1, 0),
						nextRow, nextColumn, () -> this.getCornerPieces(),
						(arrayOfCornerPieces) -> this.setCornerPieces(arrayOfCornerPieces));
			}

			if (column != FIRST_COLUMN && column != LAST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, TOP, leftCmp, new EdgePiece(-1, 0, -1, -1, left), nextRow,
						nextColumn, () -> this.getEdgePieces(),
						(arrayOfEdgePieces) -> this.setEdgePieces(arrayOfEdgePieces));
			}

			if (column == LAST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, TOP_RIGHT, leftCmp, new CornerPiece(-1, 0, 0, -1, left),
						nextRow, nextColumn, () -> this.getCornerPieces(),
						(arrayOfCornerPieces) -> this.setCornerPieces(arrayOfCornerPieces));
			}
		}

		if (row != FIRST_ROW && row != LAST_ROW) {

			if (column == FIRST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, LEFT, topCmp, new EdgePiece(-1, top, -1, -1, 0), nextRow,
						nextColumn, () -> this.getEdgePieces(),
						(arrayOfEdgePieces) -> this.setEdgePieces(arrayOfEdgePieces));
			}

			if (column != FIRST_COLUMN && column != LAST_COLUMN) {
				int times = 0;
				while (times != 4) {
					onePieceMatch(solutionPieces, row, column, NOTHING, leftThenTopCmp,
							new InnerPiece(-1, top, -1, -1, left), nextRow, nextColumn, () -> this.getInnerPieces(),
							(arrayOfInnerPieces) -> this.setInnerPieces(arrayOfInnerPieces));
					rotatePiecesClockwise(innerPieces);
					times++;
				}
			}

			if (column == LAST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, RIGHT, leftThenTopCmp, new EdgePiece(-1, top, 0, -1, left),
						nextRow, nextColumn, () -> this.getEdgePieces(),
						(arrayOfEdgePieces) -> this.setEdgePieces(arrayOfEdgePieces));
			}
		}

		if (row == LAST_ROW) {
			if (column == FIRST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, BOTTOM_LEFT, topCmp, new CornerPiece(-1, top, -1, 0, 0),
						nextRow, nextColumn, () -> this.getCornerPieces(),
						(arrayOfCornerPieces) -> this.setCornerPieces(arrayOfCornerPieces));
			}

			if (column != FIRST_COLUMN && column != LAST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, BOTTOM, leftThenTopCmp, new EdgePiece(-1, top, -1, 0, left),
						nextRow, nextColumn, () -> this.getEdgePieces(),
						(arrayOfEdgePieces) -> this.setEdgePieces(arrayOfEdgePieces));
			}

			if (column == LAST_COLUMN) {
				onePieceMatch(solutionPieces, row, column, BOTTOM_RIGHT, leftThenTopCmp,
						new CornerPiece(-1, top, 0, 0, left), nextRow, nextColumn, () -> this.getCornerPieces(),
						(arrayOfCornerPieces) -> this.setCornerPieces(arrayOfCornerPieces));
			}
		}
	}

	// A string representation of the Puzzle
	@Override
	public String toString() {
		String graphicalSolution = "", oneLineSolution = "";
		for (int i = 0; i < orderedPieces.length; i++) {
			for (int lineCounter = 1; lineCounter <= 6; lineCounter++) {
				for (int j = 0; j < orderedPieces[0].length; j++) {
					Piece tempPiece = new NullPiece();
					if (orderedPieces[i][j] != null) {
						tempPiece = orderedPieces[i][j];
					}
					switch (lineCounter) {
					case LINE_1:
						graphicalSolution += "-----------";
						break;
					case LINE_2:
						graphicalSolution += String.format("|  \\%2d /  |", tempPiece.getTopSlice());
						break;
					case LINE_3:
						graphicalSolution += String.format("|%2d \\ / %2d|", tempPiece.getLeftSlice(),
								tempPiece.getRightSlice());
						break;
					case LINE_4:
						graphicalSolution += "|   / \\   |";
						break;
					case LINE_5:
						graphicalSolution += String.format("|  /%2d \\  |", tempPiece.getBottomSlice());
						break;
					case LINE_6:
						graphicalSolution += "-----------";
						oneLineSolution += String.format("%d,%d;"
								+ (i == orderedPieces.length - 1 && j == orderedPieces[0].length - 1 ? "" : " "),
								tempPiece.getNumOfPiece(), tempPiece.getNumberOfRotation());
						break;
					}

				}
				graphicalSolution += "\n";
			}
		}
		return graphicalSolution + "\n" + oneLineSolution;
	}
}
