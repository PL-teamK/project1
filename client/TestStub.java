import java.util.Scanner;

// 作成者 吉瀬
// GameControllerに接続してサーバーの代わりを行う．

public class TestStub implements Runnable{
	private GameController gameController;
	private GameModel model;
	private Scanner scan;
	private boolean canPutFlag;
	private boolean canPut[][];
	private int color = GameModel.WHITE;
	private String buffer = "";
	private String receiveBuffer = "";
	
	public TestStub(GameController gameController) {
		this.gameController = gameController;
		model = new GameModel();
		scan = new Scanner(System.in);
	}
	
	public void printBoard(int color){
		System.out.println("printBoard() in testStub"); 
		canPutFlag = false;
		int[][] board = model.getBoard();
		canPut = model.searchPlaceToPut(color);
		System.out.println(color);
		System.out.print("       ");
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
	
	public void putPiece() {
		
	}
	
	public String fetchBuf() {
		if (buffer.length() != 0) {
			String ans = buffer;
			buffer = "";
			return ans;
		} else {
			return "";
		}
	}
	
	
	public void opponentPut() {
		// 文字列をかえす
		printBoard(color);
		System.out.print("x: ");
		int x = scan.nextInt();
		System.out.print("y: ");
		int y = scan.nextInt();
		model.putPiece(color, x, y);
		printBoard(color);
		buffer =  x + " " + y;
	}
	
	public void run() {
		while(true) {
			
		}
	}
	
	public void receiveMessage() {
		String[] tokens;
		do {
			tokens = receiveBuffer.split(",");
		} while (tokens.length == 0);
		receiveBuffer = "";
		// 1個目のトークンを通信種別にする
		if (tokens[0].equals("put")) {
			// 相手が駒を置いた場合
			int x = Integer.parseInt(tokens[1]);
			int y = Integer.parseInt(tokens[2]);
			// 盤面更新
			model.putPiece(color, x, y);
			
		} else if (tokens[0].equals("pass")) {
			// 相手がパスした場合
			
		} else if (tokens[0].equals("finish")) {
			
		}
	}
}