// 作成者 吉瀬
// OthelloPanelに配置するパネルを定義
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel {
	private GameController gameController;
	private GameView gameView;
	private OthelloPanel othelloPanel;
	
	
	
	private CustomButton buttons[][] = new CustomButton[8][8];
	// ボタン(マス目)の1辺
	private float buttonSize;
	// 盤面の枠線の太さ
	private float lineWidth;
	
	public BoardPanel(GameController gameController, GameView gameView, OthelloPanel othelloPanel, int boardWidth) {
		
		this.gameController = gameController;
		this.gameView = gameView;
		this.othelloPanel = othelloPanel;
		buttonSize = boardWidth * 9 / 10 / 8;
		lineWidth = boardWidth / 10 / 9;
		
		for (int i = 0; i < 8; i++ ) {
			for (int j = 0 ; j < 8; j++) {
				buttons[j][i] = new CustomButton(j, i);
				buttons[j][i].setActionCommand(j+ "" +i);
				buttons[j][i].setBounds((int)(buttonSize * j + lineWidth * (j + 1) + 3), (int)(buttonSize * i + lineWidth * (i + 1) + 3), (int)buttonSize, (int)buttonSize);
				buttons[j][i].addActionListener(e -> {
					//　クリックされるとgameControllerに選択された座標を送信する
					String actionCmd = e.getActionCommand();
					// ボタンとハイライトをオフにする
					changeButtonsStateTo(false);
					deleteHighlight();
					// Modelの盤面更新と送信
					gameController.setChosenPos(actionCmd.charAt(0) - '0', actionCmd.charAt(1) - '0');
					System.out.println((actionCmd.charAt(0) - '0') + " " + (actionCmd.charAt(1) - '0')); 
					
					// 更新したModelの盤面を取得
					printBoard();
					
					// 相手の操作を待つ
					gameController.waitOpponent();
					
				});
				//buttons[j][i].setOpaque(true);
				buttons[j][i].setBackground(ViewParam.BOARD_COLOR);
				add(buttons[j][i]);
				
			}
		}
		printBoard();
		printHighlight();
		// 背景色(= 縁線色）
		setBackground(ViewParam.BOARD_LINE);
		// レイアウトマネージャ無効
		setLayout(null);
		
	}
	
	private int testCount = 0;
	
	private void initBoard() {
		buttons[3][4].setBackground(ViewParam.OTHELLO_WHITE);
		buttons[3][3].setBackground(ViewParam.OTHELLO_BLACK);
		buttons[4][4].setBackground(ViewParam.OTHELLO_BLACK);
		buttons[4][3].setBackground(ViewParam.OTHELLO_WHITE);
		
	}

	public void printBoard() {
		// 盤面の描画を行う
		int[][] board = gameController.sendBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// 全ての駒にアクセス
				if ((board[j][i] & 1) == 1) {
					if ((board[i][j] & 2) == 2) {
						// white
						buttons[j][i].setBackground(ViewParam.OTHELLO_WHITE);
					} else {
						// black
						buttons[j][i].setBackground(ViewParam.OTHELLO_BLACK);
					}
				} else {
					// 駒なし
					buttons[j][i].setBackground(ViewParam.BOARD_COLOR);
				}
			}
		}
	}
	
	public void changeButtonsStateTo(boolean bool) {
		boolean canput[][] = gameController.getWhereCanIPut(gameView.getPlayerColor());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (canput[j][i]) {
					buttons[j][i].setEnabled(bool);
				} else {
					// 置けないところは全てfalse
					buttons[j][i].setEnabled(false);
				}
			}
		}
	}
	
	public void printHighlight() {
		// 自分の手番開始時に呼ぶ
		// 置ける場所はハイライトする
		boolean canput[][] = gameController.getWhereCanIPut(gameView.getPlayerColor());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (canput[j][i]) {
					buttons[j][i].setBorder(new LineBorder(ViewParam.HIGHLIGHT, 2, true));
				}
			}
		}
	}
	
	public void deleteHighlight() {
		// 自分の手番終了時に呼ぶ
		// 全てのハイライトを消去
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttons[j][i].setBorder(null);
			}
		}
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