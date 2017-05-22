// 作成者 吉瀬
// 勝敗結果表示画面

import javax.swing.*;
import java.awt.event.*;

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
	private int imageWidth = ViewParam.WIDTH;
	private int imageHeight = ViewParam.HEIGHT * 4 / 5;
	
	private int restartWidth = ViewParam.WIDTH * 3 / 10;
	private int restartHeight = ViewParam.HEIGHT * 1 / 10;
	private JButton restartButton;
	
	public ResultPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		resultLabel = new JLabel();
		resultImage = new JLabel();
		
		// イメージの作成
		resultImage = new JLabel();
		resultImage.setOpaque(false);
		resultImage.setHorizontalAlignment(JLabel.CENTER);
		resultImage.setVerticalAlignment(JLabel.TOP);
		resultImage.setBounds(0, 0, imageWidth, imageHeight);
		add(resultImage);

		// 座標で位置を指定するためにレイアウトマネージャを無効にする。
		setLayout(null);
		
		resultLabel.setBounds(ViewParam.WIDTH * 3 / 10,ViewParam.HEIGHT * 9 / 10 , ViewParam.WIDTH * 4 / 10, ViewParam.HEIGHT * 1 / 20);
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		// ラベルの追加
		add(resultLabel);
		add(resultImage);
		
		// 再起動ボタンを作成する。
		restartButton = new JButton("タイトルへ戻る");
		restartButton.setBounds(ViewParam.WIDTH * 7 / 10, ViewParam.HEIGHT * 8 / 10, restartWidth, restartHeight);
		restartButton.addActionListener(e -> {
			gameController.resetController();
			gameView.resetView();
		});
		add(restartButton);
	}
	
	public void setReason(int reason) {
		// ゲームが終了した理由をセットする．
		switch (reason) {
		// 終了理由別に処理分岐
		case FINISH_BY_PASS_WIN:
			resultLabel.setText("あなたの勝ちです");
			resultImage.setIcon(gameView.getPanda().resultPanda(0));
			break;
		case FINISH_BY_PASS_LOSE:
			resultLabel.setText("あなたの負けです");
			resultImage.setIcon(gameView.getPanda().resultPanda(1));
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数


            break;
            
		case FINISH_BY_PASS_DRAW:
			resultLabel.setText("引き分けです");
			resultImage.setIcon(gameView.getPanda().resultPanda(2));
			// resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数
			break;
		case FINISH_BY_MY_TIMEOUT:
			resultLabel.setText("制限時間を超えたため、あなたの負けです");
			resultImage.setIcon(gameView.getPanda().resultPanda(1));
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		case FINISH_BY_OPPONENTS_TIMEOUT:
			resultLabel.setText("対戦相手が制限時間を超えたため、あなたの勝ちです");
			resultImage.setIcon(gameView.getPanda().resultPanda(0));
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		case FINISH_BY_MY_COM_FAILURE:
			resultLabel.setText("接続が切れたため、あなたの負けです");
			resultImage.setIcon(gameView.getPanda().resultPanda(1));
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		case FINISH_BY_OPPONENTS_COM_FAILURE:
			resultLabel.setText("対戦相手の接続が切れたため、あなたの勝ちです");
			resultImage.setIcon(gameView.getPanda().resultPanda(0));
            //resultImage.setIcon(対応するパンダメソッド);メソッドの戻り値はImageIconクラスの変数

            break;
		
		}
        //resultLabel.setBounds();  ラベルの位置指定
        //resultImage.setBounds();
//        add(resultLabel);
//		add(resultImage);


	}
}