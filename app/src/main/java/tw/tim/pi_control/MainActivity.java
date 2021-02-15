package tw.tim.pi_control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//  Socket 官方教學
//  https://developer.android.com/reference/java/net/Socket

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Socket socket;

    //    private static final int SERVERPORT = 5000;
//    private static final String SERVER_IP = "192.168.1.104";

    private EditText et_input,et_port;
    private Button btn_connect,btn_interrupt,btn_up,btn_down,btn_left,btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewComponent();
        test2();
        new Thread(new ClientThread()).start();

//        new Thread(new ClientThread()).start();

    }

    private void test2() {
        int pi = 2;
    }

    private void setupViewComponent(){
        // 在 AppCompatActivity 隱藏 ActionBar的方法
        getSupportActionBar().hide();
        et_input = (EditText) findViewById(R.id.et_input);
        et_port = (EditText) findViewById(R.id.et_port);

        btn_connect = (Button)findViewById(R.id.button_connect);
        btn_connect.setOnClickListener(this);
        btn_interrupt = (Button)findViewById(R.id.button_interrupt);
        btn_interrupt.setOnClickListener(this);
        btn_up = (Button)findViewById(R.id.button_up);
        btn_up.setOnClickListener(this);
        btn_down = (Button)findViewById(R.id.button_down);
        btn_down.setOnClickListener(this);
        btn_left = (Button)findViewById(R.id.button_left);
        btn_left.setOnClickListener(this);
        btn_right = (Button)findViewById(R.id.button_right);
        btn_right.setOnClickListener(this);

        // 連線成功後才顯示
        btn_interrupt.setVisibility(View.GONE);
        btn_up.setVisibility(View.GONE);
        btn_down.setVisibility(View.GONE);
        btn_left.setVisibility(View.GONE);
        btn_right.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_connect:
//                try {
//                    String str = et_input.getText().toString();
//                    PrintWriter out = new PrintWriter(new BufferedWriter(
//                            new OutputStreamWriter(socket.getOutputStream())),
//                            true);
//                    out.println(str);
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                Thread connect_thread = new connectThread();
                connect_thread.start();

                break;

            case R.id.button_up:
                try{
                    char str = 'w';
                    // 獲取Socket流以進行讀寫，並把流包裝進BufferWriter或者PrintWriter
                    BufferedWriter data = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    // Client 用Write , Server那邊接收就用 Read
                    data.write(str);
                    // Write發送沒有換行 Server那邊Read沒換行會讀取不到
                    data.newLine();
                    // 重新整理
                    data.flush();
                    // 關閉
                    data.close();
                }catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_down:
                try{
                    char str = 's';
                    BufferedWriter data = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    data.write(str);
                    data.flush();
                    data.close();
                }catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_left:
                try{
                    char str = 'a';
                    BufferedWriter data = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    data.write(str);
                    data.flush();
                    data.close();
                }catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_right:
                try{
                    char str = 'd';
                    BufferedWriter data = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    data.write(str);
                    data.flush();
                    data.close();
                }catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


//    class ClientThread implements Runnable {
//        @Override
//        public void run() {
//            try {
//                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
//                socket = new Socket(serverAddr, SERVERPORT);
//            } catch (UnknownHostException e1) {
//                e1.printStackTrace();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }

    class connectThread extends Thread{
        public void run(){
            try{
                String ip = et_input.getText().toString().trim();
                int port=Integer.parseInt(et_port.getText().toString());
                if(!ip.equals("")){
                    // 建立socket連線  Socket(String host, int port)
                    socket = new Socket(ip,port);
                    Log.d("ip",ip);

                    if(socket.isConnected()){
                        pi_connect_layout();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),R.string.ip_blank,Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.ip_blank,Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                // 斷線跳到這邊 看錯誤原因
//                Toast.makeText(getApplicationContext(),R.string.connect_fail + e.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("text",e.toString());
            }

        }

        private void pi_connect_layout(){
            btn_up.setVisibility(View.VISIBLE);
            btn_down.setVisibility(View.VISIBLE);
            btn_left.setVisibility(View.VISIBLE);
            btn_right.setVisibility(View.VISIBLE);
            btn_connect.setVisibility(View.GONE);
            btn_interrupt.setVisibility(View.VISIBLE);
        }

    }



}