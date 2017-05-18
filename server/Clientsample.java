/**
 * Created by SEIYA on 2017/05/14.
 */


import java.net.*;
import java.io.*;

class Clientsample{


    Socket socket;
    static int select_room = 1;

    class Receiver extends  Thread{

        public void run(){
            while(true){
                try {

                        //サーバからのデータの受信待ち
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

                        String getline = rd.readLine();
                        if(getline != null) {
                            System.out.println("Message from Server:" + getline);
                        }

                } catch (IOException e) {
                    System.err.println("ERROR: Data received error");
                    System.exit(1);
                }

            }
        }

    }

    class Sender extends Thread {


        public void run() {

            while(true) {
                try {
                    //ルーム情報の作成、サーバへ送信する
                    if (select_room == 1) {
                        String str;
                
                        
                        System.out.print("input room number(0～5):");
                        select_room = 0;
                        BufferedReader input =
                                    new BufferedReader(new InputStreamReader(System.in));
                        str = input.readLine();


                        //PrintWriter型のwriterに、ソケットの出力ストリームを渡す。(Auto Flush)
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                        System.out.println("Send to server: " + str);
                        System.out.println("Searching player...");

                        writer.println(str);
                        //PrintWriterがAutoFlushでない場合
                        //writer.flush();

                    } else {
                            //コンソール入力によりサーバへデータを送る
                            System.out.print("input message:");

                            BufferedReader input =
                                    new BufferedReader(new InputStreamReader(System.in));
                            String str = input.readLine();

                            //writerに、ソケットの出力ストリームを渡す。
                            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                            System.out.println("Send to server: " + str);

                            writer.println(str);
                            //PrintWriterがAutoFlushでない場合
                            //writer.flush();

                    }

                } catch (IOException e) {
                    System.err.println("ERROR: Data sent error");

                }
            }

        }
    }



    public void connect_to_server() {
        try {


            String server_addr;
            //コンソール入力によりサーバのIPアドレスを取得
            System.out.print("Input Server IP Address:");
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(System.in));
            server_addr = input.readLine();


            InetAddress addr = InetAddress.getByName(server_addr);
            //アドレス情報を保持するsocketAddressを作成。
            InetSocketAddress socketAddress =
                    new InetSocketAddress(addr.getHostAddress(), 10000);
            //第一引数が直接のIPアドレス指定、addr.getHostName(),addr.getHostAddress()どれでも接続可能なことを確認済み(自宅環境)

            //socketAddressの値に基づいて通信に使用するソケットを作成する。

            socket = new Socket();
            //タイムアウトは10秒(10000msec)
            socket.connect(socketAddress, 10000);

            //接続先の情報を入れるInetAddress型のinadrを用意する。
            InetAddress inadr;

            //inadrにソケットの接続先アドレスを入れ、nullである場合には接続失敗と判断する。
            if ((inadr = socket.getInetAddress()) != null) {
                System.out.println("Connect to " + inadr);
            } else {
                System.out.println("Connection failed.");
                return;
            }

            Sender sender = new Sender();
            Receiver receiver = new Receiver();
            sender.start();         //送信待ち状態スタート
            receiver.start();       //受信待ち状態スタート
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void check_data(String data){
       //未実装
    }

    public static void main(String[] args) {
        Clientsample cs = new Clientsample();
        cs.connect_to_server();



    }
}
