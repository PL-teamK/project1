import java.util.Scanner;

// 作成者 吉瀬
// GameControllerに接続してサーバーの代わりを行う．

public class TestStub {
	private GameController gameController;
	private GameModel model;
	private Scanner scan;
	private boolean canPutFlag;
	private boolean canPut[][];
	
	public TestStub(GameController gameController) {
		this.gameController = gameController;
		model = new GameModel();
		scan = new Scanner(System.in);
	}
	
	public void printBoard(int color){
		canPutFlag = false;
		int[][] board = model.getBoard();
		canPut = model.searchPlaceToPut(color);
		System.out.println(color);
		System.out.print("  ");
		for (int i = 0; i < board.length; i++){
			System.out.print(" " + i);
		}
		System.out.println();
		
		for (int i = 0; i < board.length; i++){
			System.out.print(i + " ");
			for (int j = 0; j < board[i].length; j++){
				String str;
				if (board[j][i] == GameModel.BLACK){
					str = " B";
				} else if (board[j][i] == GameModel.WHITE){
					str = " W";
				} else if (canPut[j][i] == true){
					str = " c";
					canPutFlag = true;
				} else {
					str = "  ";
				}
				System.out.print(str);
			}
			System.out.println();
		}
		return;
	}
}