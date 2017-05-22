// 作成者 吉瀬
// マッチング待機画面

import javax.swing.*;

public class MatchingPanel extends JPanel implements Runnable{
	GameView gameView;
	GameController gameController;
	
	private JLabel messageLabel;
	private int messageLabelWidth = ViewParam.WIDTH * 7 / 10;
	private int messageLabelHeight = ViewParam.HEIGHT / 10;
	
	private boolean flag = true;
	
	// イメージ
	private JLabel imageLabel;
	private int imageWidth = ViewParam.WIDTH;
	private int imageHeight = ViewParam.HEIGHT * 8 / 10;
	
	
	public MatchingPanel(GameView gameview, GameController gameController) {
		this.gameView = gameview;
		this.gameController = gameController;
		
		// レイアウトマネージャを無効
		setLayout(null);
		
		// image
		imageLabel = new JLabel();
		imageLabel.setOpaque(false);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		imageLabel.setVerticalAlignment(JLabel.TOP);
		imageLabel.setIcon(gameView.getPanda().waitPanda());
		imageLabel.setBounds(0, 0, imageWidth, imageHeight);
		add(imageLabel);
		
		// ラベルを設定
		messageLabel = new JLabel("マッチング中");
		messageLabel.setBounds(ViewParam.WIDTH / 2 - messageLabelWidth / 2, ViewParam.HEIGHT * 17 / 20, messageLabelWidth, messageLabelHeight);
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// コンポーネントを追加
		add(messageLabel);
		
		// パネルサイズを指定
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
	}
	
	public void run() {
		boolean point = true;
		int count = 0;
		while (flag) {
			// finishMatching()メソッドが呼ばれるとフラグが変更されて、処理が終了する。
			try {
				Thread.sleep(500);
				point = !point;
				if (point) {
					messageLabel.setText("マッチング中.");
				} else {
					messageLabel.setText("マッチング中");
				}
//				count++;
//				if (count == 5) {
//					finishMatching();
//				}
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void finishMatching(){
		// フラグ変更
		flag = false;
		// 画面遷移を行うメソッドを呼ぶ
		gameView.switchViewToOthello();
		
	}
}