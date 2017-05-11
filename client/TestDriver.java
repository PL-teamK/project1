import java.util.*;

public class TestDriver{
	private GameModel model;
	private boolean canPutFlag;
	private boolean canPut[][];
	Scanner scan;
	
	TestDriver(){
		model = new GameModel();
		scan = new Scanner(System.in);
		play();
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
	
	public void play(){
		int color = GameModel.BLACK;
		canPutFlag = true;
		boolean enemyPassFlag = false;
		while(true){
			enemyPassFlag = !canPutFlag;
			printBoard(color);
			if(canPutFlag){
				choose(color);
			} else if (enemyPassFlag){
				break;
			}
			color = (color == GameModel.BLACK ? GameModel.WHITE : GameModel.BLACK);
		}
	}
	
	private void choose(int color){
		int x;
		int y;
		while(true){
			System.out.print("x:");
			x = scan.nextInt();
			System.out.print("y:");
			y = scan.nextInt();
			if(x >= 0 && x <= 7 && y >= 0 && y <= 7){
				if(canPut[x][y]){
					break;
				}
			}
		}
		model.putPiece(color, x, y);
	}
	
}