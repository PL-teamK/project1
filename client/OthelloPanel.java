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
		
		
		//othelloPanelInit();
		
		// ゲーム画面の設定
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
		
		
	}
	
	public void othelloPanelInit() {
		// コンストラクタだと，種々の情報が揃う前に始まってしまう
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
				testButton.setText("ハイライトをOFFにする");
			}
		});
		testButton.setBounds(ViewParam.WIDTH / 30, ViewParam.HEIGHT / 30, ViewParam.WIDTH / 5, ViewParam.HEIGHT / 10);
		add(testButton);
				
		// 相手と自分のラベルを設定する．
		opponentNameLabel = new JLabel(gameView.getOpponentName());
		opponentNameLabel.setHorizontalAlignment(JLabel.CENTER);
		opponentNameLabel.setBounds(ViewParam.WIDTH * 39 / 40 - labelsWidth, ViewParam.HEIGHT * 1 / 20, labelsWidth, labelsHeight);
		add(opponentNameLabel);
		playerNameLabel = new JLabel(gameView.getPlayerName());
		playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
		playerNameLabel.setBounds(ViewParam.WIDTH * 1 / 40, ViewParam.HEIGHT * 17 / 20 - labelsHeight, labelsWidth, labelsHeight);
		add(playerNameLabel);
		opponentPieceLabel = new JLabel("");
		opponentPieceLabel.setHorizontalAlignment(JLabel.CENTER);
		opponentPieceLabel.setBounds(ViewParam.WIDTH * 39 / 40 - labelsWidth, ViewParam.HEIGHT * 2 / 20, labelsWidth, labelsHeight);
		add(opponentPieceLabel);
		playerPieceLabel = new JLabel("");
		playerPieceLabel.setHorizontalAlignment(JLabel.CENTER);
		playerPieceLabel.setBounds(ViewParam.WIDTH * 1 / 40, ViewParam.HEIGHT * 18 / 20 - labelsHeight, labelsWidth, labelsHeight);
		add(playerPieceLabel);
		opponentTimerLabel = new TimerLabel(gameView, gameView.getRoomNum(), false);
		opponentTimerLabel.setHorizontalAlignment(JLabel.CENTER);
		opponentTimerLabel.setBounds(ViewParam.WIDTH * 39 / 40 - labelsWidth, ViewParam.HEIGHT * 3 / 20, labelsWidth, labelsHeight);
		add(opponentTimerLabel);
		playerTimerLabel = new TimerLabel(gameView, gameView.getRoomNum(), true);
		playerTimerLabel.setHorizontalAlignment(JLabel.CENTER);
		playerTimerLabel.setBounds(ViewParam.WIDTH * 1 / 40, ViewParam.HEIGHT * 19 / 20 - labelsHeight, labelsWidth, labelsHeight);
		add(playerTimerLabel);		
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
		// 駒数ラベルの更新を行う．
		opponentPieceLabel.setText((gameView.getPlayerColor() == GameModel.BLACK ? "白" : "黒") + ":" + String.valueOf(countNumOf(gameView.getPlayerColor() == GameModel.BLACK ? GameModel.WHITE : GameModel.BLACK)));
		playerPieceLabel.setText((gameView.getPlayerColor() == GameModel.BLACK ? "黒" : "白") + ":" + String.valueOf(countNumOf(gameView.getPlayerColor())));
		
		// timerlabelのステータスを変更する．
		playerTimerLabel.setFlag(bool);
		opponentTimerLabel.setFlag(!bool);
		
	}
	
	public int countNumOf(int color) {
		// 引数で指定した色の数を返す．
		int board[][] = gameController.sendBoard();
		int count = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[j][i] == color) {
					count++;
				}
			}
		}
		return count;
	}
	
	public boolean getIsMyTurn(boolean bool) {
		return isMyTurn;
	}
	
}

class TimerLabel extends JLabel implements Runnable {
	// 制限時間表示用のラベル
	private String header = "残り時間:";
	//private String footer = "</html>";
	private String body = "";
	private GameView gameView;
	// trueの場合には，自分の制限時間を示し，falseの場合は相手の制限時間を示す
	private boolean flag;
	// ルームによって制限時間は決まっている
	private int room;
	private int timeLimit;
	private Thread thread;
	
	// タイマーが相手の物ならタイムアップ処理はいらない
	private boolean isPlayer;
	
	
	public TimerLabel(GameView gameView, int room, boolean isPlayer) {
		this.gameView = gameView;
		// 最初はどちらもタイマーを止めておこう
		flag = false;
		this.room = room;
		// room種別により，timeLimitを設定
		switch (room) {
		case GameView.BY_HANDS_ROOM1: timeLimit = 10; break;
		case GameView.BY_HANDS_ROOM2: timeLimit = 20; break;
		case GameView.BY_HANDS_ROOM3: timeLimit = 30; break;
		case GameView.THROUGH_GAME_ROOM1: timeLimit = 600; break;
		case GameView.THROUGH_GAME_ROOM2: timeLimit = 1200; break;
		case GameView.THROUGH_GAME_ROOM3: timeLimit = 1800; break;
		}
		// このタイマーが自分のかを判別する．
		this.isPlayer = isPlayer;
		// 自分でスレッド作って処理回そう
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		// 1秒ごとにラベル更新を行う
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				timeLimit--;
				int seconds = timeLimit % 60;
				int minute = (timeLimit - seconds) / 60;
				
				body = minute + ":" + seconds;
				setText(header + body);
			}
			
			if (timeLimit == 0 && isPlayer) {
				// 時間切れ
				gameView.timeUp();
			}
		}
	}
	
	public void setFlag(boolean bool) {
		// カウントダウンのスタート&ストップを行う
		// ルーム種別により，リセットも行うかも決める
		flag = bool;
		// ルーム種別が1手ごとなら，更新する．
		switch (room) {
		case GameView.BY_HANDS_ROOM1: timeLimit = 10;	break;
		case GameView.BY_HANDS_ROOM2: timeLimit = 20;	break;
		case GameView.BY_HANDS_ROOM3: timeLimit = 30;	break;
		default: break;
		
		}
	}
}