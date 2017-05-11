// 作成者
// ゲームコントローラー
// サーバーへの通信なども管理する

public class GameController {
	GameView gameView;
	GameModel gameModel;
	public GameController() {
		// ゲームビューとゲームモデルの生成を行う
		gameView = new GameView(this);
		gameModel = new GameModel();
	}
	
	public void sendPlayerName() {
		// サーバーにプレイヤー名を送信する。
		// プレイヤー名はString型の変数である。
		
	}
}