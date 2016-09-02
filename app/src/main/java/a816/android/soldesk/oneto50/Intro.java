package a816.android.soldesk.oneto50;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Intro extends Activity {
    //배경음악 변수 선언
    MediaPlayer mp;
    Boolean isPlaying;
    int musicPosition;

    Button btnStart,btnRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        mp = MediaPlayer.create(getApplicationContext(),R.raw.backgroundmusic);
        mp.setLooping(true);
        mp.start();
        btnStart= (Button) findViewById(R.id.btnMainStart);
        btnRank= (Button) findViewById(R.id.btnMainRanking);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GamePlay.class);
                musicPosition= mp.getCurrentPosition();
                intent.putExtra("musicPosition",musicPosition);
                startActivity(intent);
            }
        });



    }


}
