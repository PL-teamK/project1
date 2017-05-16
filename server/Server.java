/**
 * Created by SEIYA on 2017/04/26.
 */

import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Server {

    public static final int ECHO_PORT = 10000;                                      //接続ポート
    public static int thread_number = 0;                                            //スレッド識別番号
    public ArrayList<EchoThread> room_thread_array = new ArrayList<EchoThread>();            //各接続状況配列
    public static int room_status[] = new int[6];                                   //現在のルーム状況配列
    public static int rt_num[][] = new int[5][1000];


    public Server(int port) {
        //Serverコンストラクタ未実装


    }

    //クライアントを受付メソッド
    public void acceptClient() {
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(ECHO_PORT);
            System.out.println("Start Server(port="
                    + serverSocket.getLocalPort() + ")");
            while (true) {
                Socket socket = serverSocket.accept();
                room_thread_array.add(new EchoThread(socket,thread_number));
                room_thread_array.get(thread_number).start();
                thread_number++;

            }
        } catch (IOException e) {
            System.err.println("ERROR:socket make error");
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
            }

        }
    }



    //内部クラス
    //最終的にクラス名をRoomに変更?
    class EchoThread extends Thread {

        private Socket socket;
        private BufferedReader in;          //受信用データ変数
        public PrintWriter out;             //送信用データ変数
        private  int room_select_num = 1;   //ルーム選択情報を識別するための変数
        private int th_num;                 //スレッド識別番号

        //EchoThreadコンストラクタ
        public EchoThread(Socket socket, int thread_num) {
            try {
                this.socket = socket;
                this.th_num = thread_num;

                System.out.println("Connected! "
                        + socket.getRemoteSocketAddress());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            } catch (IOException e) {
                System.err.println("ERROR:Data received error");
            }
        }

        //データ受信用メソッド
        public void run() {
            int partner = 0;
            try {
                String line;
                while (true) {
                    while ((line = in.readLine()) != null) {
                        System.out.println(socket.getRemoteSocketAddress()
                                + " Receive: " + line);
                        //クライアントからの最初の送信データはル-ム番号
                        if(room_select_num == 1){
                            System.out.println("making room...");
                            int room_num = Integer.parseInt(line);
                            room_status[room_num] += 1;
                            for(int i = 0 ; i<6 ; i++) {
                                System.out.print(room_status[i] + " ");
                            }
                            room_select_num -= 1;
                            rt_num[room_num][th_num] = 1;
                            //対戦相手が見つかるまで待機
                            if(room_status[room_num] == 1){
                                while(true){
                                    room_thread_array.get(th_num).sleep(1000);
                                    if(room_status[room_num] >= 2) {
                                        System.out.println("BREAK!");
                                        room_status[room_num] = 0;
                                       break;
                                   }
                                }

                            }
                            //対戦相手のスレッド番号を取得する
                            int i = 0;
                            while(true){

                                if(rt_num[room_num][i] == 1 && i != th_num){
                                    partner = i;
                                    String st = "Find the player! Game Start!";
                                    room_thread_array.get(partner).toMessage(st);
                                    break;
                                }
                                i++;
                            }
                        }else {
                            //対戦相手のスレッドの送信メソッドを呼び出す
                            room_thread_array.get(partner).toMessage(line);
                        }
                        System.out.println(socket.getRemoteSocketAddress()
                                + " Transmit: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                }
                System.out.println("disconnected "
                        + socket.getRemoteSocketAddress());
            }
        }

        //データ送信用メソッド
        public void toMessage(String msg) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(msg);
            } catch (IOException e) {
                System.err.println("ERROR:Data sent error");
            }
        }

    }



    //クライアント受付メソッドを呼び出す
    public static void main(String args[]) {

        Server server = new Server(ECHO_PORT);
        server.acceptClient();
    }
}


