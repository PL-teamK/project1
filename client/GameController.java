// 作成者
// ゲームコントローラー
// サーバーへの通信なども管理する

public class GameController {
	private GameView gameView;
	private GameModel gameModel;
	// サーバーの向こう側を想定したスタブでテストを行う
	private TestStub testStub;
	
	public GameController() {
		// ゲームビューとゲームモデルの生成を行う
		gameModel = new GameModel();
		gameView = new GameView(this);
		testStub = new TestStub(this);
	}
	
	public void sendPlayerNameAndRoom(String name, int room) {
		// サーバーにプレイヤー名を送信する。
		// プレイヤー名はString型の変数である。
		// ルーム種別も送信する．
	}
	
	public void setChosenPos(int x, int y) {
		gameModel.putPiece(gameView.getPlayerColor(), x, y);
		// サーバーに送信する．
	}
	
	public void waitOpponent() {
		// 相手の処理を待つ
	}
	
	public int[][] sendBoard() {
		// Viewから呼びだして，Modelの盤面を取得し返す
		int[][] board = gameModel.getBoard();
		return board;	
	}
	
	public boolean[][] getWhereCanIPut(int color) {
		return gameModel.searchPlaceToPut(color);
	}
}