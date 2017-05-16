// 作成者 吉瀬
// OthelloPanelに配置するパネルを定義
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel implements MouseListener {
	private GameController gameController;
	private GameView gameView;
	private OthelloPanel othelloPanel;
	private Graphics graphics;
	private Dimension dimension;
	
	
	private CustomButton buttons[][] = new CustomButton[8][8];
	// ボタン(マス目)の1辺
	private float buttonSize;
	// 盤面の枠線の太さ
	private float lineWidth;
	
	public BoardPanel(GameController gameController, GameView gameView, OthelloPanel othelloPanel, int boardWidth) {
		this.gameController = gameController;
		this.gameView = gameView;
		this.othelloPanel = othelloPanel;
		dimension = getSize();
		buttonSize = boardWidth * 19 / 20 / 8;
		lineWidth = boardWidth / 20 / 9;
		System.out.println(boardWidth + " " + buttonSize + " " + lineWidth);
		for (int i = 0; i < 8; i++ ) {
			for (int j = 0 ; j < 8; j++) {
				buttons[j][i] = new CustomButton(j, i);
				buttons[j][i].setActionCommand(j+ "" +i);
				buttons[j][i].setBounds((int)(buttonSize * j + lineWidth * (j + 1) + 3), (int)(buttonSize * i + lineWidth * (i + 1) + 3), (int)buttonSize, (int)buttonSize);
				buttons[j][i].addActionListener(e -> {
					String actionCmd = e.getActionCommand();
					gameController.setChosenPos(actionCmd.charAt(0) - '0', actionCmd.charAt(1) - '0');
					System.out.println((actionCmd.charAt(0) - '0') + " " + (actionCmd.charAt(1) - '0')); 
				});
				buttons[j][i].setBackground(ViewParam.BOARD_BACKGROUND);
				
				add(buttons[j][i]);
				
			}
		}
		
		
		// 背景色(= 縁線色）
		setBackground(ViewParam.BOARD_BACKGROUND);
		// レイアウトマネージャ無効
		setLayout(null);
		
	}
	
	private int testCount = 0;
//	public void paintComponent(Graphics g) {
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setColor(ViewParam.BOARD_COLOR);
//		testCount++;
//		g2.drawString("" + testCount, dimension.width / 2, dimension.height / 2);
//		
//	} 

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// クリックしたタイミングでクリック座標をGameControllerに渡す。
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class CustomButton extends JButton {
	private int x;
	private int y;
	
	
	CustomButton(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	public int getXPos() {
		return x;
	}
	
	public int getYPos() {
		return y;
	}
}