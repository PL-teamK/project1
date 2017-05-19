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
	private JLabel opponentNameLabel;
	private JLabel playerNameLabel;
	private TimerLabel opponentTimerLabel;
	private TimerLabel playerTimerLabel;
	private JLabel opponentPieceLabel;
	private JLabel playerPieceLabel;
	private int labelsWidth = ViewParam.WIDTH * 2 / 10;
	private int labelsHeight = ViewParam.HEIGHT / 20;
	
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
		
		
		
		// ハイライト設定用
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
		testButton.setBounds(ViewParam.WIDTH / 30, ViewParam.HEIGHT / 30, ViewParam.WIDTH / 10, ViewParam.HEIGHT / 10);
		add(testButton);
		
		// 相手と自分のラベルを設定する．
		opponentNameLabel = new JLabel(gameView.getOpponentName());
		opponentNameLabel.setHorizontalAlignment(JLabel.CENTER);
		opponentNameLabel.setBounds(ViewParam.WIDTH * 39 / 40 - labelsWidth, ViewParam.HEIGHT * 1 / 20, labelsWidth, labelsHeight);
		add(opponentNameLabel);
		playerNameLabel = new JLabel(gameView.getPlayerName());
		playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
		playerNameLabel.setBounds(ViewParam.WIDTH * 1 / 40, ViewParam.HEIGHT * 19 / 20 - labelsHeight, labelsWidth, labelsHeight);
		add(playerNameLabel);
		opponentPieceLabel = new JLabel("駒の数");
		// 今書いてる
		
		
		// ゲーム画面の設定
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
		
		
	}
	
	// このメソッドもsetIsMyTurn()に統合できるね
	public void myTurn() {
		((BoardPanel) boardPanel).changeButtonsStateTo(true);
		((BoardPanel) boardPanel).printBoard();
		if (highlightFlag) {
			// ハイライトを行う．
			((BoardPanel) boardPanel).printHighlight();
		}
	}
	
	public void setIsMyTurn(boolean bool) {
		// 手番が変化する度に呼び出される．
		isMyTurn = bool;
		// isMyTurnがtrueならボタンを有効にして，falseなら無効にする．
		((BoardPanel) boardPanel).changeButtonsStateTo(isMyTurn);
		// 画面表示
		((BoardPanel) boardPanel).printBoard();
		if (highlightFlag && isMyTurn) {
			// ハイライト設定がしてあり，自分のターンであればハイライトを行う．
			((BoardPanel) boardPanel).printHighlight();
		} else {
			// そうでない場合にはハイライトを消去する．
			((BoardPanel) boardPanel).deleteHighlight();
		}
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