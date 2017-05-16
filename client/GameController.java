// 作成者
// ゲームコントローラー
// サーバーへの通信なども管理する

public class GameController {
	GameView gameView;
	GameModel gameModel;
	public GameController() {
		// ゲームビューとゲームモデルの生成を行う
		gameModel = new GameModel();
		gameView = new GameView(this);
		
	}
	
	public void sendPlayerName() {
		// サーバーにプレイヤー名を送信する。
		// プレイヤー名はString型の変数である。
		
	}
	
	public void setChosenPos(int x, int y) {
		
	}
	
	public int[][] sendBoard() {
		int[][] board = gameModel.getBoard();
		return board;
		
	}
}