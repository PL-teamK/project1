// 作成者 吉瀬
// 名前決定パネル

import javax.swing.*;

public class DecideNamePanel extends JPanel {
	GameView gameView;
	GameController gameController;
	
	private JTextField nameField;
	private int nameFieldWidth = ViewParam.WIDTH / 4;
	private int nameFieldHeight = 30;
	private JButton decideButton;
	private int decideButtonWidth = ViewParam.WIDTH / 10;
	private int decideButtonHeight = ViewParam.HEIGHT / 10;
	
	public DecideNamePanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		// レイアウトマネージャをオフにする
		setLayout(null);
		
		// 入力欄の作成
		nameField = new JTextField(16);
		nameField.setBounds((ViewParam.WIDTH - nameFieldWidth) / 2, ViewParam.HEIGHT / 2, nameFieldWidth, nameFieldHeight);
		nameField.setHorizontalAlignment(JTextField.CENTER);
		
		// 決定ボタンの作成
		decideButton = new JButton("決定");
		decideButton.setEnabled(true);
		decideButton.setBounds(ViewParam.WIDTH / 2 - decideButtonWidth / 2, ViewParam.HEIGHT * 4 / 7, decideButtonWidth, decideButtonHeight);
		decideButton.addActionListener(e -> {
			// ボタンが押された時の処理を記述する。
			String playerName = nameField.getText();
			if (playerName.length() > 0){
				// nameFieldに何も入力されていない場合を除外する。
				// GameViewクラスにプレイヤー名を送る。
				gameView.setPlayerName(playerName);
				
				// ルーム決定の際にプレイヤー名とルーム種別を送るようにする
				
				// 画面遷移
				gameView.switchViewToChooseRoom();
			}
		});
		
		// コンポーネントの追加
		add(nameField);
		add(decideButton);
		
		// サイズの指定
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
	}
}
