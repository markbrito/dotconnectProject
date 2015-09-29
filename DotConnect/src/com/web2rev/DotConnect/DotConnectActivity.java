package com.web2rev.DotConnect;

import android.app.Activity;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class DotConnectActivity extends Activity{
    public static String TAG = "com.web2rev.DotConnect.DotConnectActivity";
    public static DotConnectActivity mainActivity;
    public static GameEngine gameEngine = null;
    public static DotConnectView gameView = null;
    public static Object mutex=new Object();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        mainActivity = this;
        setContentView(R.layout.main);
        Spinner spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.arrayLevels,
                        android.R.layout.simple_spinner_item);
        spinnerLevel.setAdapter(adapter);
        spinnerLevel.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                gameEngine.levelChoice = position;
                Log.i(TAG, "position=" + position + " id=" + id);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "onNothingSelected");
            }
        });
        ShowBoard();
    }

    @Override
    public void onResume() {
        super.onResume();
/*        synchronized (mutex){
            Log.i(TAG, "onResume");

            if(gameView==null)
                ShowBoard();

            if(mainActivity.gameEngine.won)
            {
                mainActivity.gameEngine.moveinprogress = true;
                gameView.initialPaint = true;
            }
            else
            {
                if(!gameEngine.players_move){
                    mainActivity.gameEngine.EnableRestartButton(true);
                    mainActivity.gameEngine.moveinprogress= false;
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DotConnectActivity.mainActivity.gameEngine.ply = 0;
                            DotConnectActivity.mainActivity.gameEngine.player = -1;
                            DotConnectActivity.mainActivity.gameEngine.start();
                        }
                    });
                }
                else if(gameEngine.players_move && gameEngine.moveinprogress)
                {
                    mainActivity.gameEngine.moveinprogress=false;
                    mainActivity.gameEngine.player = 1;
                    mainActivity.gameEngine.EnableRestartButton(true);
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameView.initialPaint = true;
                            (new Thread(DotConnectActivity.mainActivity.gameView)).start();
                        }
                    });
                }
                else if(gameEngine.players_move && !gameEngine.moveinprogress)
                {
                    mainActivity.gameEngine.moveinprogress= false;
                    mainActivity.gameEngine.player = 1;
                    mainActivity.gameEngine.EnableRestartButton(true);
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameView.initialPaint = true;
                            (new Thread(DotConnectActivity.mainActivity.gameView)).start();
                        }
                    });
                }
            }
        }



        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.gameView = new GameEngine(mainActivity);
            }
        });

        //        mainActivity.gameView.viewThread = new Thread(mainActivity.gameView);
  //      mainActivity.runOnUiThread(mainActivity.gameView.viewThread);

        */
    }

    public void ShowBoard() {
        mainActivity.gameEngine = new GameEngine(mainActivity);
        mainActivity.gameView = new DotConnectView(mainActivity);
        LinearLayout ll = (LinearLayout) findViewById(R.id.boardLayout);
        ll.addView(mainActivity.gameView);
        mainActivity.gameView.setSurfaceSize(ll.getWidth(),ll.getHeight());
        mainActivity.gameView.getResetButton().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mainActivity.gameEngine.resetGame();
            }
        });
        mainActivity.gameEngine.moveinprogress= false;
        mainActivity.gameEngine.player = 1;
        mainActivity.gameEngine.players_move = true;
        mainActivity.gameEngine.EnableRestartButton(true);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameView.initialPaint = true;
                (mainActivity.gameView.viewThread=
                        new Thread(DotConnectActivity.
                                mainActivity.gameView)).start();
            }
        });
    }
}