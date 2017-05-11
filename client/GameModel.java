// 作成者 吉瀬

public class GameModel{
	
	final static public int NONE = 0;
	final static public int BLACK = 1;
	final static public int WHITE = 2;
	
	private int[][] board = new int[8][8];
	
	public GameModel(){
		// constructor initializes state of board.
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j] = NONE;
				// none of pieces is put.
			}
		}
		board[3][3] = BLACK;
		board[4][4] = BLACK;	
		board[3][4] = WHITE;
		board[4][3] = WHITE;	
		// 4 pieces are put.
	}
	
	public boolean putPiece(int color, int x, int y){
		// this method puts piece.
		if (board[x][y] == NONE){
			// put
			board[x][y] = color;
			// reverse
			boardUpdate(color, x, y);
			return true;
		} else {
			return false;
		}
		
	}
	
	public int[][] getBoard(){
		// this method enables other class to get state of board.
		return board;
	}
	
	public boolean[][] searchPlaceToPut(int nextColor){
		boolean placeToPut[][] = new boolean[8][8];
		for(int i = 0; i < placeToPut.length; i++){
			for(int j = 0; j < placeToPut.length; j++){
				if (board[i][j] == NONE){
					placeToPut[i][j] = isExistToReverse(nextColor, i,j);
				} else {
					placeToPut[i][j] = false;
				}
			}
		}
		return placeToPut;
	}
	
	private boolean isExistToReverse(int color, int x, int y){
		
		// upper
		if (searchDirection(color, x, y, 0, -1)){
			return true;
		}
		// upper right
		if (searchDirection(color, x, y, 1, -1)){
			return true;
		}
		// right
		if (searchDirection(color, x, y, 1, 0)){
			return true;
		}
		// lower right
		if (searchDirection(color, x, y, 1, 1)){
			return true;
		}
		// lower
		if (searchDirection(color, x, y, 0, 1)){
			return true;
		}
		// lower left
		if (searchDirection(color, x, y, -1, 1)){
			return true;
		}
		// left
		if (searchDirection(color, x, y, -1, 0)){
			return true;
		}
		// upper left
		if (searchDirection(color, x, y, -1, -1)){
			return true;
		}
		return false;
	}
	private boolean searchDirection(int color, int x, int y, int dx, int dy){
		int opposit = (color == BLACK ? WHITE : BLACK);
		if(isInside(x + dx, y + dy)){
			if (board[x + dx][y + dy] == opposit){
				for (int i = 2; isInside(x + dx * i, y + dy * i); i++){
					if (board[x + dx * i][y + dy * i] == NONE){
						return false;
					} else if (board[x + dx * i][y + dy * i] == color){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isInside(int x, int y){ 
		return (x >= 0 && x <= 7 && y >= 0 && y <= 7);
	}
	
	
	private void boardUpdate(int color, int x, int y){
		// upper
		if (searchDirection(color, x, y, 0, -1)){
			reverse(color, x, y, 0, -1);
		}
		// upper right
		if (searchDirection(color, x, y, 1, -1)){
			reverse(color, x, y, 1, -1);
		}
		// right
		if (searchDirection(color, x, y, 1, 0)){
			reverse(color, x, y, 1, 0);
		}
		// lower right
		if (searchDirection(color, x, y, 1, 1)){
			reverse(color, x, y, 1, 1);
		}
		// lower
		if (searchDirection(color, x, y, 0, 1)){
			reverse(color, x, y, 0, 1);
		}
		// lower left
		if (searchDirection(color, x, y, -1, 1)){
			reverse(color, x, y, -1, 1);
		}
		// left
		if (searchDirection(color, x, y, -1, 0)){
			reverse(color, x, y, -1, 0);
		}
		// upper left
		if (searchDirection(color, x, y, -1, -1)){
			reverse(color, x, y, -1, -1);
		}
	}
	
	private void reverse(int color, int x, int y, int dx, int dy){
		// System.out.println("reverse " + x + "," + y);
		// test output
		int opposit = (color == BLACK ? WHITE : BLACK);
		for (int i = 1; isInside(x + dx * i, y + dy * i); i++){
			if (board[x + dx * i][y + dy * i] == opposit){
				board[x + dx * i][y + dy * i] = color;
				//System.out.println("turn " + (x + dx * i) + "," + (y + dy * i));
				//test output
			} else if (board[x + dx * i][y + dy * i] == color){
				return;
			}
		}
	}
}