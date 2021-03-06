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
	private int decideButtonWidth = ViewParam.WIDTH * 4 / 10;
	private int decideButtonHeight = ViewParam.HEIGHT / 10;
	
	private JLabel imageLabel;
	private int imageWidth = ViewParam.WIDTH;
	private int imageHeight = ViewParam.HEIGHT * 14 / 20;
	
	public DecideNamePanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		// レイアウトマネージャをオフにする
		setLayout(null);
		
		// イメージ
		imageLabel = new JLabel();
		imageLabel.setOpaque(false);
		imageLabel.setIcon(gameView.getPanda().usernamePanda());
		imageLabel.setBounds(0, 0, imageWidth, imageHeight);
		add(imageLabel);
		// 入力欄の作成
		nameField = new JTextField(16);
		nameField.setBounds((ViewParam.WIDTH - nameFieldWidth) / 2, ViewParam.HEIGHT * 14 / 20, nameFieldWidth, nameFieldHeight);
		nameField.setHorizontalAlignment(JTextField.CENTER);
		
		// 決定ボタンの作成
		decideButton = new JButton("名前を入力してください");
		decideButton.setEnabled(true);
		decideButton.setBounds(ViewParam.WIDTH / 2 - decideButtonWidth / 2, ViewParam.HEIGHT * 16 / 20, decideButtonWidth, decideButtonHeight);
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
