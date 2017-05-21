import sun.applet.AppletAudioClip;
import javax.swing.*;
import java.applet.AudioClip;
import java.util.Random;

/**
 * Created by SEIYA on 2017/05/21.
 *
 *
 */

/**
 *フォルダ設定
 * 画像はImageフォルダをsrcと同じディレクトリ上に作成、各構成は以下のようにする
 *      Image  -    menu        -   roomSelect.jpg , startP.jpg
 *             -    matching    -   p0.jpgからp39.jpg
 *             -    result      -   win.jpg , lose.jpg  , draw.jpg
 *             -    etc         -   kaiten.git  ,   kosigif.gif
 *
 * 音声はPandaSample.classが存在するディレクトリにSoundsフォルダを作成、各構成は以下のようにする
 *      Sounds  -    menu        -   roomM.aiff , startM.aiff
 *              -    matching    -   m0.aiffからm39.aiff  と fight-battleSene.aiff と   jyobann.aiff
 *              -    result      -   winM.aiff , loseM.aiff  , draw.aiff
 *              -    etc         -   bakaM.aiff  ,   loselose-gif.aiff
 *
 * 使用方法
 *  クライアントでPandaSampleインスタンスを生成する。
 *  クライアント対応する画像を返すメソッドを呼び出す。
 *  その後クライアントでsendSoundメソッドを呼び出す。(なおgifよ呼び出すときはsendSoundは呼び出さない)
 */

public class PandaSample{


    public ImageIcon[] image = new ImageIcon[45];
    public ImageIcon[] gif = new ImageIcon[2];
    public AudioClip[] sounds = new AudioClip[5];
    private int send_num;
    private int situation = -1;  //0:自分が勝っている状態  1:負けている状態   2:引き分けの状態


    //コンストラクタで各画像を読み込む
    PandaSample(){

        //画像読み込み
        image[0] = new ImageIcon("./Image/menu/startP.jpg");
        image[1] = new ImageIcon("./Image/menu/roomSelect.jpg");
        for(int i = 2;i < 42; i++){
            int filenum = i - 2;    //画像ファイルはp0.jpgからp39.jpg
            String filepath = "./Image/matching/p" + filenum + ".jpg";
            image[i] = new ImageIcon((filepath));

            //マッチング中の音声も同時に読み込む
            sounds[i] = new AppletAudioClip(getClass().getResource("./Sounds/matching/m"+ filenum +".aiff"));
        }
        image[42] = new ImageIcon("./Image/result/win.jpg");
        image[43] = new ImageIcon("./Image/result/lose.jpg");
        image[44] = new ImageIcon("./Image/result/draw.jpg");
        gif[0] = new ImageIcon("./Image/etc/kosigif.gif");
        gif[1] = new ImageIcon("./Image/etc/kaiten.gif");

    }




    //スタート画面の音声を指定し、画像を返す
    public ImageIcon startPanda(){
        send_num = 0;
        sounds[send_num] = new AppletAudioClip(getClass().getResource("./Sounds/menu/startM.aiff"));
        return image[send_num];
    }




    //public ImageIcon usernamePanda(){
    //  よくわからないメソッド
    //}




    //public ImageIcon waitPanda(){
    //  よくわからないメソッド
    //}





    //ルーム画面の音声を指定し、画像を返す
    public ImageIcon roomPanda(){
        send_num = 1;
        sounds[send_num] = new AppletAudioClip(getClass().getResource("./Sounds/menu/roomM.aiff"));
        return image[1];
    }




    //勝敗画面の音声を指定し、画像を返す     res = 0:win 1:lose 2:draw
    public ImageIcon resultPanda(int res){
        if(res == 0){
            send_num = 42;
            sounds[send_num] = new AppletAudioClip(getClass().getResource("./Sounds/result/winM.aiff"));
        }else if(res == 1){
            send_num = 43;
            sounds[send_num] = new AppletAudioClip(getClass().getResource("./Sounds/result/loseM.aiff"));
        }else if(res == 2){
            send_num = 44;
            sounds[send_num] = new AppletAudioClip(getClass().getResource("./Sounds/result/drawM.aiff"));
        }

        return image[send_num];//対応する画像は音声の配列の添え字と同じ
    }




    //自分が置いたときの音声を指定し、画像を返す     x:盤面の横    y:盤面の縦
    public ImageIcon myturnPanda(int x, int y, int myTotalStone, int oppTotalStone){


        int stonediff = myTotalStone - oppTotalStone;   //自分の石の数-相手の石の数

        if(stonediff > 0 && situation == 1){    //自分が置いて逆転した場合
            Random ran = new Random();
            send_num = 32 + ran.nextInt(4);
            return image[send_num];
        }

        if(stonediff > 0){          //勝っている状態
            situation = 0;
        }else if(stonediff < 0){    //負けている状態
            situation = 1;
        }else if(stonediff == 0) {  //引き分けの状態
            situation = 2;
        }

        //自分が置いて逆転以外の時は適当なGifを表示
        Random ran = new Random();
        int gifnum = ran.nextInt(2);    //値が変わらないようならMath.random()使った方がよい
        return gif[gifnum];
    }




    //相手が置いたときの音声を指定し、画像を返す     x:盤面の横    y:盤面の縦
    public  ImageIcon oppturnPanda(int x, int y, int myTotalStone , int oppTotalStone){



        if((x % 7 == 0) && (y % 7 == 0)){   //相手が置いたマスが角だった場合
            Random ran = new Random();
            send_num = 36 + ran.nextInt(4);
            return image[send_num];

        }else{
            Random ran = new Random();
            send_num = ran.nextInt(0);
            return image[send_num];
        }
    }




    //音声を返す
    public AudioClip sendSound(){
        return sounds[send_num];
    }

}
