// 作成者 吉瀬

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

//画面遷移等をハンドルすることをメインに行う。

public class GameView extends JFrame{
	// 関連するクラスのインスタンス変数を用意
	GameController	gameController;
	// 画面パネル
	private StartPanel 		startPanel;
	private DecideNamePanel decideNamePanel;
	private ChooseRoomPanel chooseRoomPanel;
	private MatchingPanel 	matchingPanel;
	private OthelloPanel	othelloPanel;
	private ResultPanel		resultPanel;
	
	// 各種値
	private String playerName = "";
	private String opponentName = "";
	private int playerColor = GameModel.BLACK;
	private int roomNum;
	
	// 定数
	// ルーム種別を示す定数
	public static final int BY_HANDS_ROOM1 = 0;
	public static final int BY_HANDS_ROOM2 = 1;
	public static final int BY_HANDS_ROOM3 = 2;
	public static final int THROUGH_GAME_ROOM1 = 3;
	public static final int THROUGH_GAME_ROOM2 = 4;
	public static final int THROUGH_GAME_ROOM3 = 5;
	
	// スレッド処理用のスレッドクラスの変数
	Thread thread;
	
	public GameView(GameController gameController){
		// macでの表示をwindowsでの表示に変える
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
				
		}
		this.gameController = gameController;
		
		// 画面パネルの作成
		startPanel 		= new StartPanel(this, gameController);
		decideNamePanel = new DecideNamePanel(this, gameController);
		chooseRoomPanel = new ChooseRoomPanel(this, gameController);
		matchingPanel 	= new MatchingPanel(this, gameController);
		othelloPanel	= new OthelloPanel(this, gameController);
		resultPanel		= new ResultPanel(this, gameController);
	
		// 最初は、スタート画面を表示する。
		startPanel.setVisible(true);
		decideNamePanel.setVisible(false);
		chooseRoomPanel.setVisible(false);
		matchingPanel.setVisible(false);
		othelloPanel.setVisible(false);
		resultPanel.setVisible(false);
		
		add(startPanel);
		add(decideNamePanel);
		add(chooseRoomPanel);
		add(matchingPanel);
		add(othelloPanel);
		add(resultPanel);
		
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	// 以下画面遷移
	
	public void switchViewToDecideName() {
		// スタート画面から、名前入力画面に遷移する。
		
		startPanel.setVisible(false);
		decideNamePanel.setVisible(true);
	}
	
	public void switchViewToChooseRoom() {
		// 名前が決定したら、ルーム選択画面に遷移する。
		
		decideNamePanel.setVisible(false);
		chooseRoomPanel.setVisible(true);
	}
	
	public void switchViewToMatching() {
		// ルーム選択が終わったら、待機画面への遷移を行う。
		// その際、サーバーへプレイヤー名とルーム種別を送信する必要がある。
		
		chooseRoomPanel.setVisible(false);
		matchingPanel.setVisible(true);
		
		
		
		
		// 待機の間、文字列を変化させ続ける。
		thread = new Thread(matchingPanel);
		thread.start();
		// 待機状態に入ったあとに接続待ち状態に入る．
		gameController.sendPlayerNameAndRoom(playerName, roomNum);
		
		gameController.startCommunication();
		
		
	}
	
	public void switchViewToOthello() {
		// 待機が終わったら、ゲーム画面への遷移を行う。
		
		matchingPanel.setVisible(false);
		othelloPanel.setVisible(true);
	}
	
	public void switchViewToResult(int reason) {
		// ゲームが終了したら、リザルト画面への遷移を行う。
		// リザルト画面に終了事由の設定を行う．
		resultPanel.setReason(reason);
		othelloPanel.setVisible(false);
		resultPanel.setVisible(true);
	}
	
	public OthelloPanel getOthelloPanel() {
		return othelloPanel;
	}
	
	public void setPlayerName(String arg) {
		playerName = arg;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setOpponentName(String arg) {
		opponentName = arg;
	}
	
	public String getOpponentName() {
		return opponentName;
	}
	
	public void setPlayerColor(int arg) {
		playerColor = arg;
		if (playerColor == GameModel.BLACK) {
			othelloPanel.setIsMyTurn(true);
		} else {
			othelloPanel.setIsMyTurn(false);
		}
	}
	
	public int getPlayerColor() {
		return playerColor;
	}
	
	public void setRoomNum(int arg) {
		roomNum = arg;
	}
	
}

