// 作成者 吉瀬
// スタート画面パネル

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel {
	GameView gameView;
	GameController gameController;
	
	private JButton startButton;
	
	// ボタンサイズを指定
	private int startButtonWidth = ViewParam.WIDTH * 3 / 10;
	private int startButtonHeight = ViewParam.HEIGHT / 10;
	
	public StartPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		// 座標で位置を指定するためにレイアウトマネージャを無効にする。
		setLayout(null);
		
		startButton = new JButton("click to start");
		startButton.setBounds((ViewParam.WIDTH - startButtonWidth) / 2, ViewParam.HEIGHT * 6 / 7 - startButtonHeight , startButtonWidth, startButtonHeight);
		startButton.addActionListener(e -> {
			// ボタン押された時の処理の追加
			// 名前決定画面への遷移を行う。
			gameView.switchViewToDecideName();
		});
		
		add(startButton);
		
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
	}
	
}