// 作成者 吉瀬
// オセロゲーム画面

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OthelloPanel extends JPanel {
	private GameView gameView;
	private GameController gameController;
	
	// 盤面描画を独立させる。
	private JPanel boardPanel;
	private int boardPanelWidth = ViewParam.HEIGHT * 8 / 10;
	
	// 盤面以外のコンポーネントの宣言
	private JLabel opponentLabel;
	private JLabel playerLabel;
	private int labelsWidth = ViewParam.WIDTH * 2 / 10;
	private int labelsHeight = ViewParam.HEIGHT * 3 / 10;
	
	// 変数の宣言
	private boolean highlightFlag = true;
	private boolean isMyTurn;
	
	
	public OthelloPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		// レイアウトマネジャーの無効化
		setLayout(null);
		
		// 盤面描画パネルの設定
		boardPanel = new BoardPanel(gameController, gameView, this, boardPanelWidth);
		// 盤面パネルは正方形にする。
		boardPanel.setSize(boardPanelWidth, boardPanelWidth);
		boardPanel.setBounds(ViewParam.WIDTH / 2 - boardPanelWidth / 2, ViewParam.HEIGHT / 2 - boardPanelWidth / 2, boardPanelWidth, boardPanelWidth);
		
		add(boardPanel);
		
		// コンポーネントの定義
		opponentLabel = new JLabel("<html>対戦相手名<br>制限時間: xx:xx<br>駒の数: xx枚</html>");
		playerLabel = new JLabel("<html>プレイヤー名<br>制限時間: xx:xx<br>駒の数: xx枚</html>");
		opponentLabel.setBounds(ViewParam.WIDTH * 17 / 20 - labelsWidth / 2, ViewParam.HEIGHT * 2 / 10 - labelsHeight / 2, labelsWidth, labelsHeight);
		playerLabel.setBounds(ViewParam.WIDTH * 3 / 20 - labelsWidth / 2, ViewParam.HEIGHT * 8 / 10 - labelsHeight / 2, labelsWidth, labelsHeight);
		opponentLabel.setHorizontalAlignment(JLabel.CENTER);
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		opponentLabel.setBackground(Color.MAGENTA);
		playerLabel.setBackground(Color.BLUE);
		opponentLabel.setOpaque(true);
		playerLabel.setOpaque(true);
		
		// コンポーネントを画面に追加する。
		add(opponentLabel);
		add(playerLabel);
		
		// 再描画テスト用
		JButton testButton = new JButton("ハイライトをOFFにする");
		testButton.addActionListener(e -> {
			if (highlightFlag) {
				// ハイライトをOFFにする
				highlightFlag = false;
				testButton.setText("ハイライトをONにする");
				
			} else {
				highlightFlag = true;
				testButton.setText("ハイライトをONにする");
			}
		});
		testButton.setBounds(ViewParam.WIDTH / 30, ViewParam.HEIGHT / 30, ViewParam.WIDTH / 20, ViewParam.HEIGHT / 20);
		add(testButton);
		
		
		// ゲーム画面の設定
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
		
		
	}
	
	public void myTurn() {
		((BoardPanel) boardPanel).changeButtonsStateTo(true);
		((BoardPanel) boardPanel).printBoard();
		if (highlightFlag) {
			// ハイライトを行う．
			((BoardPanel) boardPanel).printHighlight();
		}
	}
	
	public void setIsMyTurn(boolean bool) {
		isMyTurn = bool;
	}
	
	public boolean getIsMyTurn(boolean bool) {
		return isMyTurn;
	}
	
}

class TimerLabel extends JLabel implements Runnable {
	// 制限時間表示用のラベル
	private String header = "<html>残り時間<br>";
	private String footer = "</html>";
	private String body = "";
	private GameView gameView;
	// trueの場合には，自分の制限時間を示し，falseの場合は相手の制限時間を示す
	private boolean flag;
	// ルームによって制限時間は決まっている
	private int room;
	private int timeLimit;
	
	
	public TimerLabel(boolean bool, GameView gameView, int room) {
		this.gameView = gameView;
		flag = bool;
		this.room = room;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				timeLimit--;
				int minute = timeLimit / 60;
				int seconds = timeLimit % 60;
				body = minute + ":" + seconds;
				setText(header + body + footer);
			}
		}
	}
	
	public void setFlag(boolean bool) {
		// カウントダウンのスタート&ストップを行う
		// ルーム種別により，リセットも行うかも決める
		flag = bool;
	}
}