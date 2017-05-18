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
	
	// 通信関連
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader bufReader;
	
	public GameController() {
		// ゲームビューとゲームモデルの生成を行う
		gameModel = new GameModel();
		gameView = new GameView(this);
		//testStub = new TestStub(this);
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
        // ログイン内容を送信
        String sendingMessage = "login," + "name" + "," + String.valueOf(room); 
        writer.println(sendingMessage);
        
        // サーバーからのデータ受信を行えるようにする．
        try {
        	bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        // 通信待ち状態に入る
        thread = new Thread(this);
        thread.start();
	}
	
	public void setChosenPos(int x, int y) {
		//System.out.println(x + " " + y);
		gameModel.putPiece(gameView.getPlayerColor(), x, y);
		isPassedFlag = false;
		// サーバーに送信する．
		// "put,1,4"のような形式で送信する．
		String sendingMessage = "put," + x + "," + y;
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
		while (true) {
			// 無限ループで処理を受け付ける．
			if (comFlag) {
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
			
			if (myColor == GameModel.WHITE) {
				// 自分の手番が白だったら相手スタートなので，受信待機
				comFlag = true;
			} else {
				comFlag = false;
			}
		} else if (tokens[1].equals("put")) {
			// 相手が駒を置いた場合
			int x = Integer.parseInt(tokens[1]);
			int y = Integer.parseInt(tokens[2]);
			// model更新
			gameModel.putPiece(gameView.getPlayerColor(), x, y);
			
			// 置ける場所があるかを確認する.
			if (canIPut()) {
				// 自分の番に変更
				// 盤面更新などを行なってくれる
				comFlag = false;
				gameView.getOthelloPanel().setIsMyTurn(true);
			} else {
				// 置ける場所がないのでパスになる．
				comFlag = true;
				
			}
			
			
		} else if (tokens[1].equals("pass")) {
			// 相手がパスをした場合
			
		} else if (tokens[1].equals("finish")) {
			// ゲームが終了した場合
			
		} else if (tokens[1].equals("timeout")) {
			// 相手がタイムアウトになった場合
			
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
	
	public void waitCom() {
		// 通信待機状態に入るために，フラグ変数を変更する．
		comFlag = true;
	}
}