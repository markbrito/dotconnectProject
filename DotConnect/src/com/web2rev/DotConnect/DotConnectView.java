package com.web2rev.DotConnect;

import java.util.Vector;

import android.util.Log;
import android.view.MotionEvent;
import android.graphics.*;
import android.graphics.Rect;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.lang.Runnable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

public class DotConnectView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {

    final int ANIMATION_SLEEP = 8;
    private Bitmap mBitmap = null;
    public Bitmap blue_animation[] = new Bitmap[13];
    public Bitmap red_animation[] = new Bitmap[13];
    public Bitmap animation[] = null;
    public Bitmap board_image = null;
    public Bitmap red_checker = null;
    public Bitmap blue_checker = null;
    public Canvas mCanvas;
    public DotConnectActivity mainActivity = null;
    public SurfaceHolder mSurfaceHolder;
    public int enteredMove;
    public int searchedMove;
    public boolean initialPaint = true;
    public Thread viewThread = null;
    public int mBoardHeight = 1;
    public int mBoardWidth = 1;
    public int mCanvasHeight = 1;
    public int mCanvasWidth = 1;
    public Bitmap mBackgroundImage = null;

    public DotConnectView(Context context) {
        super(context);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setSizeFromLayout();
        mSurfaceHolder.addCallback(this);
        mainActivity = (DotConnectActivity) context;
        loadImages();
    }
    protected void loadImages() {
        mBackgroundImage = board_image = BitmapFactory
                .decodeStream(mainActivity.getResources().openRawResource(
                        R.drawable.con4));
        mCanvasWidth = mBoardWidth = board_image.getWidth();
        mCanvasHeight = mBoardHeight = board_image.getHeight();

        blue_checker = BitmapFactory.decodeStream(mainActivity.getResources()
                .openRawResource(R.drawable.blue));
        red_checker = BitmapFactory.decodeStream(mainActivity.getResources()
                .openRawResource(R.drawable.red));
        blue_animation[0] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b0));
        red_animation[0] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r0));
        blue_animation[1] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b1));
        red_animation[1] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r1));
        blue_animation[2] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b2));
        red_animation[2] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r2));
        blue_animation[3] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b3));
        red_animation[3] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r3));
        blue_animation[4] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b4));
        red_animation[4] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r4));
        blue_animation[5] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b5));
        red_animation[5] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r5));
        blue_animation[6] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b6));
        red_animation[6] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r6));
        blue_animation[7] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b7));
        red_animation[7] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r7));
        blue_animation[8] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b8));
        red_animation[8] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r8));
        blue_animation[9] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b9));
        red_animation[9] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r9));
        blue_animation[10] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b10));
        red_animation[10] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r10));
        blue_animation[11] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b11));
        red_animation[11] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r11));
        blue_animation[12] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.b12));
        red_animation[12] = BitmapFactory.decodeStream(mainActivity
                .getResources().openRawResource(R.drawable.r12));
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w<1 || h<1)
            return;
        mBackgroundImage = this.board_image.createScaledBitmap(this.board_image,
                w, h, true);
        Bitmap newBitmap = Bitmap.createBitmap(w, h,
                Bitmap.Config.RGB_565);
        Canvas newCanvas = new Canvas();
        newCanvas.setBitmap(newBitmap);
        if (mBitmap != null) {
            newCanvas.drawBitmap(mBitmap, 0, 0, null);
        }
        mCanvasWidth =   w;
        mCanvasHeight =   h;
        mBitmap = newBitmap;
        mCanvas = newCanvas;
        drawMainBoard(mCanvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(DotConnectActivity.TAG, "onDraw");
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
    public void drawMainBoard(Canvas c) {
        Canvas canvas = c;
        DotConnectActivity mainActivity = DotConnectActivity.mainActivity;
        GameEngine gameEngine = mainActivity.gameEngine;
        canvas.drawRGB(1, 0, 1);
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        float yratio = (((float) this.mCanvasHeight / (float) this.mBoardHeight));
        float xratio = (((float) this.mCanvasWidth / (float) this.mBoardWidth));
        int x, y;
        for (x = 0; x < 7; ++x)
            for (y = 0; y < 7; ++y)
                if (gameEngine.draw_board[(y << 3) + x] != 0){
                    Bitmap bitmap=gameEngine.draw_board[
                            (y << 3) + x] == 1 ?
                            red_checker: blue_checker;
                    Rect bitSrc=new Rect(0,0,
                            bitmap.getWidth(),bitmap.getHeight());
                    Rect bitDest=new Rect();
                    bitDest.left=(int)
                            ( 70.0 * (float) xratio+((float)x * 29.0) *  xratio);
                    bitDest.top=(int) ( (6.0 +
                            (float)x * 3.0 +28.0 *(float) y + 9.0) *  yratio);
                    bitDest.right=(int)( bitSrc.right*(float)xratio+
                            (float)bitDest.left);
                    bitDest.bottom=(int)( bitSrc.bottom*(float)yratio+
                            (float)bitDest.top);
                    canvas.drawBitmap(
                            bitmap,
                            bitSrc,bitDest,
                            null);
                }
        if (gameEngine.won) {
            ShowWonLabel(canvas);
            mainActivity.gameEngine.moveinprogress= true;
            mainActivity.gameEngine.EnableRestartButton(true);
        }
        else if (gameEngine.players_move)
            ShowYourTurnLabel(canvas);
        else
            ShowThinkingLabel(canvas);
    }
    public void removeTouchEvent(){
        try{
        mainActivity.gameEngine.moveinprogress = true;
        mSurfaceHolder.removeCallback(this);
        }catch(Exception e){;}
    }
    public void addTouchEvent(){
        try{
            mainActivity.gameEngine.moveinprogress = false;
            mSurfaceHolder.addCallback(this);
        }catch(Exception e){;}

    }
    protected Object sync=new Object();
    @Override
    public synchronized boolean onTouchEvent(MotionEvent event) {
        if (mainActivity.gameEngine.moveinprogress)
            return false;
        Log.i(DotConnectActivity.TAG, "onTouchEvent");
        int action = event.getActionMasked();
        if (action != MotionEvent.ACTION_UP
                && action != MotionEvent.ACTION_CANCEL) { ; }
        else return false;
        try{
                Vector<Float> vx = new Vector<Float>();
                Vector<Float> vy = new Vector<Float>();
                Vector<Float> vPressure = new Vector<Float>();
                Vector<Float> vTouchMajor = new Vector<Float>();
                int N = event.getHistorySize();
                int P = event.getPointerCount();
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < P; j++) {
                        vx.add(event.getHistoricalX(j, i));
                        vy.add(event.getHistoricalY(j, i));
                        vPressure.add(event.getHistoricalPressure(j, i));
                        vTouchMajor.add(event.getHistoricalTouchMajor(j, i));
                    }
                }
                int cx = (int) event.getX(0);
                int cy = (int) event.getY(0);
                TryMove(cx, cy);
        }
        catch(Exception e){
            Log.e(DotConnectActivity.TAG,
                    "Exception onTouchEvent "+e.getMessage()+e.getStackTrace());
        }
        return true;
    }

    public boolean TryMove(int x, int y) {
        synchronized (sync) {
            if (initialPaint)
            return false;
        if (mainActivity.gameEngine.moveinprogress)
            return false;
        removeTouchEvent();
        Log.i("DotConnectActivity", "DotConnectActivity.TryMove(" + x + "," + y
                + ")");
        DotConnectActivity mainActivity = DotConnectActivity.mainActivity;
        GameEngine gameEngine = mainActivity.gameEngine;
        float yr=((float) this.mBoardHeight / (float) this.mCanvasHeight);
        float xr=((float) this.mBoardWidth / (float) this.mCanvasWidth);
        float yy = ((float) y * yr);
        float xx = ((float) x * xr);
        if (yy > (5) && yy < (225) && xx > (60) && xx < (265)
                && gameEngine.players_move
                && gameEngine.game_board[enteredMove] == 0) {
            enteredMove = (int) Math.floor((float)(xx-60) / 29.0);
            searchedMove = enteredMove;
            animation = this.blue_animation;
            initialPaint=false;
            viewThread = new Thread(this);
            mainActivity.runOnUiThread(viewThread);
        }
        else{
            addTouchEvent();
        }
        return true;
        }
    }

    @Override
    public void run() {
        Log.i(DotConnectActivity.TAG, "DotConnectView.run");
        try {
            if (initialPaint) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        try {
                            Thread.sleep(ANIMATION_SLEEP);
                        } catch (Exception e) {
                            ;
                        }
                        drawMainBoard(mCanvas);
                        drawMainBoard(c);
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                initialPaint = false;
                addTouchEvent();
            } else {
                float yratio = (((float) this.mCanvasHeight / (float) this.mBoardHeight));
                float xratio = (((float) this.mCanvasWidth / (float) this.mBoardWidth));
                mainActivity.gameEngine.players_move = DotConnectActivity.gameEngine.player == 1;
                mainActivity.gameEngine.EnableRestartButton(false);
                Canvas c = null;
                for (int xx = 0; xx < 64; xx++)
                    mainActivity.gameEngine.draw_board[xx] = mainActivity.gameEngine.game_board[xx];
                    DotConnectActivity.mainActivity.gameEngine.make_move(searchedMove,
                        DotConnectActivity.gameEngine.player);
                Log.i(mainActivity.gameEngine.TAG, "make_move_game=" + searchedMove
                        + " " + DotConnectActivity.gameEngine.player);
                for (int yy = 0; yy < 7
                        && mainActivity.gameEngine.draw_board[(yy << 3)
                        + mainActivity.gameView.searchedMove] == 0; ++yy) {
                    if (yy == 6   // a > 6
                            || (yy < 6 && mainActivity.gameEngine.game_board[((yy + 1) << 3)
                            + mainActivity.gameView.searchedMove] != 0)) {
                        break;
                    }
                    for (int a = 0; a < 13; ++a) {
                        try {
                            c = mSurfaceHolder.lockCanvas(null);
                            synchronized (mSurfaceHolder) {
                                try {
                                    Thread.sleep(ANIMATION_SLEEP);
                                } catch (Exception e) {
                                    ;
                                }
                                drawMainBoard(mCanvas);
                                drawMainBoard(c);
                                Bitmap bitmap=animation[a];
                                Rect bitSrc=new Rect(0,0,
                                        bitmap.getWidth(),bitmap.getHeight());
                                Rect bitDest=new Rect();
                                bitDest.left=(int)
                                        ( 70.0 * (float) xratio+
                                                ((float)(searchedMove) * 29.0) *  xratio);
                                bitDest.top=(int) ( (6.0 +
                                        (float)searchedMove * 3.0 +28.0 *(float) yy + 9.0) *  yratio);
                                bitDest.right=(int)( bitSrc.right*(float)xratio+
                                        (float)bitDest.left);
                                bitDest.bottom=(int)( bitSrc.bottom*(float)yratio+
                                        (float)bitDest.top);
                                mCanvas.drawBitmap(
                                        bitmap,
                                        bitSrc,bitDest,
                                        null);
                                c.drawBitmap(
                                        bitmap,
                                        bitSrc,bitDest,
                                        null);
                            }
                        } finally {
                            if (c != null) {
                                mSurfaceHolder.unlockCanvasAndPost(c);
                            }
                        }
                    }
                }
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        try {
                            Thread.sleep(ANIMATION_SLEEP);
                        } catch (Exception e) {
                            ;
                        }
                         drawMainBoard(mCanvas);
                        drawMainBoard(c);
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                mainActivity.gameEngine.won=mainActivity.gameEngine.win()!=0;
                if (!mainActivity.gameEngine.won ) {
                    mainActivity.gameEngine.players_move=true;
                    mainActivity.gameEngine.ply = 0;

                    mainActivity.gameEngine.EnableRestartButton(true);
                    mainActivity.gameEngine.moveinprogress = false;
                    mainActivity.gameEngine.player = -1;

                } else {
                    ShowWonLabel(mCanvas);
                    ShowWonLabel(c);
                    mainActivity.gameEngine.player = 1;
                    mainActivity.gameEngine.moveinprogress = true;
                    mainActivity.gameEngine.EnableRestartButton(true);
                }
            }
        } catch (Exception e) {
            Log.i(DotConnectActivity.TAG, e.getMessage() + e.getStackTrace());
        }
    }

    public void ShowWonLabel(Canvas c) {
        DrawText(c,mainActivity.gameEngine.win() == 1 ? "I Won! Try Again!"
                : "You Won! Press Restart!",this.mCanvasWidth / 2 - 145,
                25, 24, 0xFF00FF00, null);
    }

    public void ShowYourTurnLabel(Canvas c) {
        DrawText(c, "Your Move", this.mCanvasWidth / 2 - 46,
                25, 24, 0xFF00FF00, null);
    }

    public void ShowThinkingLabel(Canvas c) {
        DrawText(c, "Thinking", this.mCanvasWidth / 2 - 46,
                25, 24, 0xFF00FF00, null);
    }

    protected void DrawText(Canvas canvas, String text, int x, int y,
                            int fontSize, int c, Paint p) {
        Paint mTextPaint = p == null ? new Paint() : p;
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(fontSize);
        mTextPaint.setColor(c);
        canvas.drawText(text, x, y, mTextPaint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.i(DotConnectActivity.TAG, "surfaceChanged");
        setSurfaceSize(width, height);
    }

    public void setSurfaceSize(int width, int height) {
        Log.i(DotConnectActivity.TAG, "setSurfaceSize");
        if(width>0 && height>0){
            mCanvasWidth = width;
            mCanvasHeight = height;
            mBackgroundImage = this.board_image.createScaledBitmap(this.board_image,
                    width, height, true);

            Bitmap newBitmap = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight,
                    Bitmap.Config.RGB_565);
            Canvas newCanvas = new Canvas();
            newCanvas.setBitmap(newBitmap);

            if (mBitmap != null) {
                newCanvas.drawBitmap(mBitmap, 0, 0, null);
            }
            mCanvas = newCanvas;
            drawMainBoard(mCanvas);
            mBitmap = newBitmap;
            mCanvas = newCanvas;
            drawMainBoard(mCanvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        initialPaint = true;
        (viewThread = new Thread(this)).start();
        this.getResetButton().setEnabled(true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        try {
            boolean retry = true;
            while (retry) {
                try {
                    viewThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            ;
        }
        try {
            boolean retry = true;
            while (retry) {
               // try {
                    //DotConnectActivity.mainActivity.gameEngine
                      //      .join();
                    retry = false;
              //  } catch (InterruptedException e) {
              //  }
            }
        } catch (Exception ex) {
            ;
        }
    }

    public ImageButton getResetButton() {
        return (ImageButton) DotConnectActivity.mainActivity
                .findViewById(R.id.buttonRestart);
    }
}
