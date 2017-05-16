// 作成者 吉瀬
// オセロゲーム画面

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OthelloPanel extends JPanel {
	GameView gameView;
	GameController gameController;
	
	// 盤面描画を独立させる。
	private JPanel boardPanel;
	private int boardPanelWidth = ViewParam.HEIGHT * 8 / 10;
	
	// 盤面以外のコンポーネントの宣言
	private JLabel opponentLabel;
	private JLabel playerLabel;
	private int labelsWidth = ViewParam.WIDTH * 2 / 10;
	private int labelsHeight = ViewParam.HEIGHT * 3 / 10;
	
	
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
		JButton testButton = new JButton("repaint");
		testButton.addActionListener(e -> {
			boardPanel.repaint();
		});
		testButton.setBounds(ViewParam.WIDTH / 30, ViewParam.HEIGHT / 30, ViewParam.WIDTH / 20, ViewParam.HEIGHT / 20);
		add(testButton);
		
		
		// ゲーム画面の設定
		setSize(ViewParam.WIDTH, ViewParam.HEIGHT);
		
		
	}
}