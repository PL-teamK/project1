// 作成者 吉瀬
// 勝敗結果表示画面

import javax.swing.*;

public class ResultPanel extends JPanel {
	private GameView gameView;
	private GameController gameController;
	
	// 定数たち
	public static final int FINISH_BY_PASS_WIN = 10;
	public static final int FINISH_BY_PASS_LOSE = 11;
	public static final int	FINISH_BY_PASS_DRAW = 12;
	public static final int FINISH_BY_MY_TIMEOUT = 13;
	public static final int FINISH_BY_OPPONENTS_TIMEOUT = 14;
	public static final int FINISH_BY_MY_COM_FAILURE = 15;
	public static final int FINISH_BY_OPPONENTS_COM_FAILURE =16;
	
	private JLabel resultLabel;
	
	public ResultPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		resultLabel = new JLabel();
	}
	
	public void setReason(int reason) {
		// ゲームが終了した理由をセットする．
		switch (reason) {
		// 終了理由別に処理分岐
		case FINISH_BY_PASS_WIN: break;
		case FINISH_BY_PASS_LOSE: break;
		case FINISH_BY_MY_TIMEOUT: break;
		case FINISH_BY_OPPONENTS_TIMEOUT: break;
		case FINISH_BY_MY_COM_FAILURE: break;
		case FINISH_BY_OPPONENTS_COM_FAILURE: break;
		
		}
	}
}