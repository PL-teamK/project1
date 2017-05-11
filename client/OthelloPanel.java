// 作成者 吉瀬
// オセロゲーム画面

import javax.swing.*;

public class OthelloPanel extends JPanel {
	GameView gameView;
	GameController gameController;
	
	public OthelloPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
	}
}