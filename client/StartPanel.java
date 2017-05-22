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
	private int startButtonWidth = ViewParam.WIDTH * 5 / 10;
	private int startButtonHeight = ViewParam.HEIGHT / 10;
	
	private int fieldWidth = ViewParam.WIDTH * 3 / 10;
	private int fieldHeight = ViewParam.HEIGHT / 10;
	
	// 画像用JLabelを用意
	private JLabel imageLabel;
	private int imageWidth = ViewParam.WIDTH / 2;
	private int imageHeight = 275;
	
	// サーバーアドレス入力フィールドを用意する．
	private JTextField serverField;
	
	public StartPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		// 座標で位置を指定するためにレイアウトマネージャを無効にする。
		setLayout(null);
		
		// イメージラベルの設定
		imageLabel = new JLabel();
		imageLabel.setOpaque(false);
		imageLabel.setBounds(0, 0, ViewParam.WIDTH, ViewParam.HEIGHT);
		imageLabel.setIcon(gameView.getPanda().startPanda());
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		imageLabel.setVerticalAlignment(JLabel.TOP);
		//gameView.getPanda().soundplay();
		add(imageLabel);
		serverField = new JTextField(16);
		serverField.setBounds((ViewParam.WIDTH - fieldWidth) / 2, ViewParam.HEIGHT * 5 / 7 - fieldHeight, fieldWidth, fieldHeight);
		serverField.setHorizontalAlignment(JTextField.CENTER);
		
		
		startButton = new JButton("サーバのipアドレスを入力してスタート");
		startButton.setBounds((ViewParam.WIDTH - startButtonWidth) / 2, ViewParam.HEIGHT * 6 / 7 - startButtonHeight , startButtonWidth, startButtonHeight);
		startButton.addActionListener(e -> {
			String serverAddress = serverField.getText();
			if (serverAddress.length() != 0) {
				// サーバーのアドレスをコントローラに保存する．
				gameController.setServerAddress(serverAddress);
				// ボタン押された時の処理の追加
				// 名前決定画面への遷移を行う。
				gameView.switchViewToDecideName();
			}
		});
		startButton.setVisible(true);
		
		add(startButton);
		add(serverField);
		
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
	}
	
}