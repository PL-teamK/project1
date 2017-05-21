// 作成者
// ゲームコントローラー
// サーバーへの通信なども管理する

import java.io.*;
import java.net.*;

public class GameController implements Runnable {
	private GameView gameView;
	private GameModel gameModel;
	// サーバーの向こう側を想定したスタブでテストを行う
	private TestStub testStub;
	private boolean isPassedFlag = false;
	
	// 通信部分をスレッド処理にするためのスレッド
	private Thread thread;
	// 通信待機状態を示すフラグ
	private boolean comFlag = false;
	private boolean gameNotComplete = true;
	
	// 通信関連
	private String serverAddress = "";
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader bufReader;
	
	public GameController() {
		gameInit();
		//testStub = new TestStub(this);
	}
	
	 public void gameInit() {
		// このメソッドでゲームの初期化を行う．
		// ゲームビューとゲームモデルの生成を行う
		gameModel = new GameModel();
		gameView = new GameView(this);
	 }
	
	public void setServerAddress(String address) {
		serverAddress = address;
	}
	
	public void sendPlayerNameAndRoom(String name, int room) {
		// サーバーにプレイヤー名を送信する。
		// プレイヤー名はString型の変数である。
		// ルーム種別も送信する．
		InetSocketAddress socketAddress =
                new InetSocketAddress("localhost", 10000);
        //socketAddressの値に基づいて通信に使用するソケットを作成する。

        socket = new Socket();
        //タイムアウトは10秒(10000msec)
        try {
        	socket.connect(socketAddress, 10000);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
      //接続先の情報を入れるInetAddress型のinadrを用意する。
        InetAddress inadr;

        //inadrにソケットの接続先アドレスを入れ、nullである場合には接続失敗と判断する。
        //nullでなければ、接続確立している。
        if ((inadr = socket.getInetAddress()) != null) {
            System.out.println("Connect to " + inadr);
        } else {
            System.out.println("Connection failed.");
            return;
        }
        
        //PrintWriter型のwriterに、ソケットの出力ストリームを渡す。(Auto Flush)
        
        try {
        	 writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        	e.printStackTrace();
        }
       
        
        // サーバーからのデータ受信を行えるようにする．
        try {
        	bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        // 通信待ち状態に入る
        thread = new Thread(this);
        thread.start();
        
        // ログイン内容を送信
        String sendingMessage = "login," + gameView.getPlayerName() + "," + String.valueOf(room); 
        System.out.println("send:" + sendingMessage);
        writer.println(sendingMessage);
	}
	
	public void setChosenPos(int x, int y) {
		//System.out.println(x + " " + y);
		gameModel.putPiece(gameView.getPlayerColor(), x, y);
		isPassedFlag = false;
		// サーバーに送信する．
		// "put,1,4"のような形式で送信する．
		String sendingMessage = "put," + x + "," + y;
		System.out.println("send:" + sendingMessage);
		writer.println(sendingMessage);
	}
	
	public int[][] sendBoard() {
		// Viewから呼びだして，Modelの盤面を取得し返す
		int[][] board = gameModel.getBoard();
		return board;	
	}
	
	public boolean[][] getWhereCanIPut(int color) {
		return gameModel.searchPlaceToPut(color);
	}
	
	public void startCommunication() {
		thread = new Thread(this);
		thread.start();
		return;
	}
	
	public void run() {
		// 通信部分をスレッド処理にする．
		// 相手からの通信を常時受け付けるような構造にする．
		while (gameNotComplete) {
			// gameNotCompleteはゲームで必要な処理が全て終了したタイミングで呼ばれる
			// 無限ループで処理を受け付ける．
			if (/**comFlag*/true) {
				// comFlagがtrueの時は通信待機状態である．
				
				// わすれないうちにフラグをfalseにする
				comFlag = false;
				
				receiveMessage();
			} else {
				try {
					// 負荷低減のため，処理遅延を行う
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	 		}
			
		}
	}
	
	public void receiveMessage() {
		// 通信待機メソッド
		// スレッド処理からフラグ変数の変化で呼び出す．
		String[] tokens;
		
		String recievedStr = "";
		try {
			// カンマ区切りの文字列を取得
			recievedStr = bufReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// デバック用
		System.out.println(recievedStr);
		// カンマ区切りで文字列を分割
		tokens = recievedStr.split(",");
		
		if (tokens[0].equals("start")) {
			// ゲーム開始のサイン
			String opponentName = tokens[1];
			String myColorStr  = tokens[2];
			// 自分のカラーを定数に変換する．
			int myColor = (myColorStr.equals("black") ? GameModel.BLACK : GameModel.WHITE);
			gameView.setOpponentName(opponentName);
			// カラーをセットタイミングで初期手番が設定されている．
			gameView.setPlayerColor(myColor);
			gameView.getMatchingPanel().finishMatching();
			
			if (myColor == GameModel.WHITE) {
				// 自分の手番が白だったら相手スタートなので，受信待機
				comFlag = true;
			} else {
				comFlag = false;
			}
		} else if (tokens[0].equals("put")) {
			// 相手が駒を置いた場合
			int x = Integer.parseInt(tokens[1]);
			int y = Integer.parseInt(tokens[2]);
			// model更新
			int opponentColor = gameView.getPlayerColor() == GameModel.BLACK ? GameModel.WHITE : GameModel.BLACK;
			gameModel.putPiece(opponentColor, x, y);
			
			// 置ける場所があるかを確認する.
			if (canIPut()) {
				// 自分の番に変更
				// 盤面更新などを行なってくれる
				comFlag = false;
				gameView.getOthelloPanel().setIsMyTurn(true);
			} else {
				// 置ける場所がないのでパスになる．
				comFlag = true;
				isPassedFlag = true;
				// 送信しなきゃ
				writer.println("pass");
				isPassedFlag = true;
				
				// 自分に手番回ってこないけど，更新しなきゃ
				gameView.getOthelloPanel().setIsMyTurn(false);
			}
			
			
		} else if (tokens[0].equals("pass")) {
			// 相手がパスをした場合
			isPassedFlag = true;
			if (!canIPut()) {
				// パスが2回連続で発生したので終了条件を満たす
				gameView.switchViewToResult(countPieces());
				// 終了を送信
				System.out.println("send:finish");
				writer.println("finish");
				// 通信の切断
				gameComplete();
				// 画面遷移をする
				// 駒数のカウントし、勝敗の判定を行う
				gameView.switchViewToResult(countPieces());
			} else {
				// まだ自分が置けるので継続
				comFlag = false;
				isPassedFlag = false;
				// 自分の番に変更する．
				gameView.getOthelloPanel().setIsMyTurn(true);
			}
			
		} else if (tokens[0].equals("finish")) {
			// ゲームが終了した場合
			// 普通に終了なので，コマ数計算して送信
			gameView.switchViewToResult(countPieces());
			// 通信を切断
			gameComplete();
		} else if (tokens[0].equals("timeout")) {
			// 相手がタイムアウトになった場合
			// もれなく勝ち
			gameView.switchViewToResult(ResultPanel.FINISH_BY_OPPONENTS_TIMEOUT);
			gameComplete();	
		} else if (tokens[0].equals("disconnect")) {
			// 相手の通信が切断された場合
			gameView.switchViewToResult(ResultPanel.FINISH_BY_OPPONENTS_COM_FAILURE);
			// 通信の終了
			gameComplete();
		}
		
		
	}
	
	public boolean canIPut() {
		// 置ける場所が存在するかを検査する
		boolean canput[][] = gameModel.searchPlaceToPut(gameView.getPlayerColor());
		// 全ての場所を検査する．
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (canput[j][i]) {
					// trueの場合，置ける場所はある
					return true;
				}
			}
		}
		// ここに到達した場合，置ける場所は存在しない．
		return false;
	}
	
	public int countPieces() {
		// 勝敗を判定する．
		int myColor = gameView.getPlayerColor();
		int myNum = 0;
		int opponentNum = 0;
		
		int board[][] = gameModel.getBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[j][i] == myColor) {
					myNum++;
				} else {
					opponentNum++;
				}
			}
		}
		if(myNum > opponentNum) {
			return ResultPanel.FINISH_BY_PASS_WIN;
		} else if (opponentNum > myNum) {
			return ResultPanel.FINISH_BY_PASS_LOSE;
		} 
		return ResultPanel.FINISH_BY_PASS_DRAW;
	}
	
	public void waitCom() {
		// 通信待機状態に入るために，フラグ変数を変更する．
		comFlag = true;
	}
	
	public void sendTimeUpToServer() {
		// このメソッドが呼ばれた時点でプレーヤの負けが確定する．System.out.println(recievedStr);
		System.out.println("send:timeup");
		writer.println("timeup");
		gameView.switchViewToResult(ResultPanel.FINISH_BY_MY_TIMEOUT);
		// もう相手からの通信は発生しないので通信を終了する．
		gameComplete();
	}
	
	public void gameComplete() {
		// 通信処理を終了する．
		// 処理待ち処理が終了する．(スレッド処理の終了)
		gameNotComplete = false;
		try {
			// 通信切断
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}