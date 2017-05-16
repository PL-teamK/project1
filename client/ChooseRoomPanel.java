// 作成者 吉瀬
// ゲームルール種別決定パネル

import javax.swing.*;

public class ChooseRoomPanel extends JPanel{
	GameView gameView;
	GameController gameController;
	
	// 表示ボタン
	private JButton timeByHandsButton1;
	private JButton timeByHandsButton2;
	private JButton timeByHandsButton3;
	private JButton	timeThroughGameButton1;
	private JButton timeThroughGameButton2;
	private JButton timeThroughGameButton3;
	
	// ボタンサイズ
	private int buttonWidth = ViewParam.WIDTH * 3 / 10;
	private int buttonHeight = ViewParam.HEIGHT * 1 / 10;
	
	
	public ChooseRoomPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		// レイアウトマネージャをオフにする。
		setLayout(null);
		
		// ボタンを生成。
		
		timeByHandsButton1 = new JButton("一手: 10秒");
		timeByHandsButton2 = new JButton("一手: 20秒");
		timeByHandsButton3 = new JButton("一手: 30秒");
		timeThroughGameButton1 = new JButton("対局: 10分");
		timeThroughGameButton2 = new JButton("対局: 20分");
		timeThroughGameButton3 = new JButton("対局: 30分");
		
		// ボタン配置
		timeByHandsButton1.setBounds(ViewParam.WIDTH / 3 - buttonWidth / 2, ViewParam.HEIGHT / 5, buttonWidth, buttonHeight);
		timeByHandsButton2.setBounds(ViewParam.WIDTH / 3 - buttonWidth / 2, ViewParam.HEIGHT * 2 / 5, buttonWidth, buttonHeight);
		timeByHandsButton3.setBounds(ViewParam.WIDTH / 3 - buttonWidth / 2, ViewParam.HEIGHT * 3 / 5, buttonWidth,  buttonHeight);
		timeThroughGameButton1.setBounds(ViewParam.WIDTH * 2 / 3 - buttonWidth / 2, ViewParam.HEIGHT / 5, buttonWidth, buttonHeight);
		timeThroughGameButton2.setBounds(ViewParam.WIDTH * 2 / 3 - buttonWidth / 2, ViewParam.HEIGHT * 2 / 5, buttonWidth, buttonHeight);
		timeThroughGameButton3.setBounds(ViewParam.WIDTH * 2 / 3 - buttonWidth / 2, ViewParam.HEIGHT * 3 / 5, buttonWidth, buttonHeight);
		
		// ボタンが押された時の処理を追加
		timeByHandsButton1.addActionListener(e -> {
			// ボタンが押されたら、GameViewクラスに送る。
			roomSelected(GameView.BY_HANDS_ROOM1);
		});
		timeByHandsButton2.addActionListener(e -> {
			roomSelected(GameView.BY_HANDS_ROOM2);
		});
		timeByHandsButton3.addActionListener(e -> {
			roomSelected(GameView.BY_HANDS_ROOM3);
		});
		timeThroughGameButton1.addActionListener(e -> {
			roomSelected(GameView.THROUGH_GAME_ROOM1);
		});
		timeThroughGameButton2.addActionListener(e -> {
			roomSelected(GameView.THROUGH_GAME_ROOM2);
		});
		timeThroughGameButton3.addActionListener(e -> {
			roomSelected(GameView.THROUGH_GAME_ROOM3);
		});
		
		// ボタンをパネルに追加
		add(timeByHandsButton1);
		add(timeByHandsButton2);
		add(timeByHandsButton3);
		add(timeThroughGameButton1);
		add(timeThroughGameButton2);
		add(timeThroughGameButton3);
		
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
	}
	
	private void roomSelected(int room){
		gameView.setRoomNum(room);
		gameView.switchViewToMatching();
	}
}