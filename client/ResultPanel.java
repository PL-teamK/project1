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
	private JLabel resultImage;
	
	public ResultPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		resultLabel = new JLabel();
		resultImage = new JLabel();

		// 座標で位置を指定するためにレイアウトマネージャを無効にする。
		setLayout(null);
	}
	
	public void setReason(int reason) {
		// ゲームが終了した理由をセットする．
		switch (reason) {
		// 終了理由別に処理分岐
		case FINISH_BY_PASS_WIN:
			resultLabel.setText("配置できるマスがないため、あなたの勝ちです");
			//resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数
			break;
		case FINISH_BY_PASS_LOSE:
			resultLabel.setText("配置できるマスがないため、あなたの負けです");
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数


            break;
		case FINISH_BY_MY_TIMEOUT:
			resultLabel.setText("制限時間を超えたため、あなたの負けです");
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		case FINISH_BY_OPPONENTS_TIMEOUT:
			resultLabel.setText("対戦相手が制限時間を超えたため、あなたの勝ちです");
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		case FINISH_BY_MY_COM_FAILURE:
			resultLabel.setText("接続が切れたため、あなたの負けです");
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		case FINISH_BY_OPPONENTS_COM_FAILURE:
			resultLabel.setText("対戦相手の接続が切れたため、あなたの勝ちです");
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		
		}
        //resultLabel.setBounds();  ラベルの位置指定
        //resultImage.setBounds();
        add(resultLabel);
		add(resultImage);


	}
}