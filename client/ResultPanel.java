// 作成者 吉瀬
// 勝敗結果表示画面

import javax.swing.*;

public class ResultPanel extends JPanel {
	GameView gameView;
	GameController gameController;
	
	public ResultPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
	}
}