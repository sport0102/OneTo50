package a816.android.soldesk.oneto50;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.util.ArrayList;

public class GamePlay extends AppCompatActivity {

    Button btnStart;
    Button bt;
    Button[] btn = new Button[25];
    static int answerNo = 1, random, random2, n = 25, n2 = 25, i;

    TextView nextNo;
    TextView timeView;

    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;

    int cur_Status = Init;
    int myCount = 1;
    long myBaseTime;
    long myPauseTime;
    boolean reset=true;

    ArrayList<String> viewNo = new ArrayList<String>();
    ArrayList<String> viewNo2 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview);
        getSupportActionBar().hide();
        btnStart = (Button) findViewById(R.id.startButton);
        nextNo = (TextView) findViewById(R.id.nextNo);
        timeView = (TextView) findViewById(R.id.timeView);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewNo.removeAll(viewNo);
                viewNo2.removeAll(viewNo2);
                cur_Status=Init;
                answerNo=1;
                n=25;
                n2=25;

                for (int j = 1; j < 26; j++) {
                    viewNo.add("" + j);
                    viewNo2.add("" + (j + 25));
                }
                start();
                myOnClick();
                reset=false;
            }



        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void myOnClick() {
        switch (cur_Status) {
            case Init:
                myBaseTime = SystemClock.elapsedRealtime();
                System.out.println(myBaseTime);
                //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                myTimer.sendEmptyMessage(0);
                cur_Status = Run; //현재상태를 런상태로 변경
                break;
            case Run:
                myTimer.removeMessages(0); //핸들러 메세지 제거
                myPauseTime = SystemClock.elapsedRealtime();
                cur_Status = Pause;
                break;
            case Pause:
                long now = SystemClock.elapsedRealtime();
                myTimer.sendEmptyMessage(0);
                myBaseTime += (now - myPauseTime);
                cur_Status = Run;
                break;


        }


    }



    Handler myTimer = new Handler() {
        public void handleMessage(Message msg) {
            timeView.setText(getTimeOut());
            myTimer.sendEmptyMessage(0);
        }
    };


    String getTimeOut() {
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 60, (outTime / 1000) % 60, (outTime % 1000) / 10);
        return easy_outTime;

    }


    public void start() {

        nextNo.setText("1");

        for (i = 0; i < 25; i++) {
            btn[i] = (Button) findViewById(R.id.button01 + i);
            random = (int) (Math.random() * n);
            n--;
            btn[i].setText(viewNo.get(random));
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt = (Button) v;
                    random2 = (int) (Math.random() * n2);
                    n2--;

                    if (bt.getText().equals("" + answerNo)) {
                        if (answerNo < 26) {
                            answerNo++;
                            bt.setText(viewNo2.get(random2));
                            viewNo2.remove(random2);
                            nextNo.setText("" + answerNo);
                        } else if (answerNo > 25 && answerNo < 50) {
                            answerNo++;
                            bt.setText(" ");
                            nextNo.setText("" + answerNo);
                        } else if (answerNo == 50) {
                            bt.setText(" ");
                            end();
                        }

                    }

                }
            });

            viewNo.remove(random);

        }


    }

    public void end() {
        myTimer.removeMessages(0);
        cur_Status = Init;
        myCount = 1;
        Toast.makeText(getApplicationContext(), "게임 끝", Toast.LENGTH_SHORT).show();
    }


}
