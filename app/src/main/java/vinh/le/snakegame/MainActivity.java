package vinh.le.snakegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    // List of snake points define its length
    private final List<SnakePoints> snakePointList = new ArrayList<>();
   private SurfaceView surfaceView;
   private TextView scoreTV;

   private SurfaceHolder surfaceHolder;

   // Default Moving state for snake 
   private String movingPosition = "right";

   private int score = 0;
   // snake size
   private static final int pointSize = 30;

   private static final int defaultTalePoints = 3;

   private static final int snakeColor = Color.GREEN;

    private static final int snakeMovingSpeed = 800;

    // random point position
    private int positionX, positionY;

    // Timer to move snake
    private Timer timer;

    // draw snake and show on surface
    private Canvas canvas = null;

    // Point color

    private Paint pointColor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting sufView and TV from layout
        surfaceView = findViewById(R.id.surfaceView);
        scoreTV = findViewById(R.id.scoreTV);

        // Image Button from xml

        final ImageButton topBtn = findViewById(R.id.topBtn);
        final ImageButton leftBtn = findViewById(R.id.leftBtn);
        final ImageButton rightBtn = findViewById(R.id.rightBtn);
        final ImageButton bottomBtn = findViewById(R.id.bottomBtn);

        // adding callback to surfaceview
        surfaceView.getHolder().addCallback(this);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check previous state of snake
                if(!movingPosition.equals("top")) {
                    movingPosition = "bottom";
                }

            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check previous state of snake
                if(!movingPosition.equals("right")) {
                    movingPosition = "left";
                }

            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check previous state of snake
                if(!movingPosition.equals("left")) {
                    movingPosition = "right";
                }


            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check previous state of snake
                if(!movingPosition.equals("bottom")) {
                    movingPosition = "top";
                }


            }
        });


    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        // surfaceHolder is assigned when surface is created
        this.surfaceHolder = holder;

        // init data for snake /surfaceView
        init();


    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    private void init(){
        // Clear the snake points/ reset
        snakePointList.clear();

        // Reset the score
        scoreTV.setText("0");

        // make score
        score = 0;

        //setting default moving position
        movingPosition = "right";

        int startPositionX = (pointSize) * defaultTalePoints;
        SnakePoints snakePoints = new SnakePoints(startPositionX,pointSize);
        snakePointList.add(snakePoints);
          // adding points to snake's tale
        for (int i = 0; i < defaultTalePoints; i++){
            // increasing value for next point as snake's tale
            startPositionX = startPositionX - (pointSize * 2);
            snakePoints = new SnakePoints(startPositionX,pointSize);
            snakePointList.add(snakePoints);


        }
            // add random point on the screen that can eaten by snake
          addPoint();
           // start moving the snake
          moveSnake();
        }

    private void addPoint(){
// getting surfaceView width and height to add point
        int surfaceWidth = surfaceView.getWidth() - (pointSize * 2);
        int surfaceHeight = surfaceView.getHeight() - (pointSize *2);

        int randomXPosition = new Random().nextInt(surfaceWidth/ pointSize);
        int randomYPosition = new Random().nextInt(surfaceHeight/pointSize);

        if((randomXPosition % 2) != 0){
            randomXPosition = randomXPosition++;
        }

        if((randomYPosition % 2) != 0) {
            randomYPosition = randomYPosition++;
        }

        positionX = (pointSize * randomXPosition) + pointSize;
        positionY = (pointSize * randomYPosition) + pointSize;
    }
    private void moveSnake(){

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // getting head position
                int headPositionX = snakePointList.get(0).getPositionX();
                int headPositionY = snakePointList.get(0).getPositionY();
                // check if snake eaten
                if (Math.abs(headPositionX - positionX) <= pointSize && Math.abs(headPositionY - positionY) <= pointSize){
                   // grow snake after eaten point
                    growSnake();
                    // add another random point on the screen
                    addPoint();
                }
               // check of which side snake move
                switch (movingPosition){
                    case "right":
                        // move snake head to right
                        snakePointList.get(0).setPositionX(headPositionX + (pointSize * 2));
                        snakePointList.get(0).setPositionY(headPositionY);
                        break;
                    case "left":
                        //move snake head to left
                        snakePointList.get(0).setPositionX(headPositionX - (pointSize * 2));
                        snakePointList.get(0).setPositionY(headPositionY);
                        break;

                    case "top":
                        //move snake head up
                        snakePointList.get(0).setPositionX(headPositionX);
                        snakePointList.get(0).setPositionY(headPositionY + (pointSize * 2));
                        break;

                    case "bottom":
                        //move snake head down
                        snakePointList.get(0).setPositionX(headPositionX);
                        snakePointList.get(0).setPositionY(headPositionY - (pointSize *2));
                        break;
                }
                // check if game over, whether snake touch edge or itself
                if(checkGameOver(headPositionX, headPositionY)){
                    timer.purge();
                    timer.cancel();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Your Score = " + score);
                    builder.setTitle("Game Over");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Start Again", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // restart game
                            init();
                        }
                        });

                    // timer runs in background
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }
                    });
                }

                else{
                    //lock canvas on surfaceHolder
                    canvas = surfaceHolder.lockCanvas();

                    // clean canvas
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                    // change snake's head position, other snake points will follow snake's head
                    canvas.drawCircle(snakePointList.get(0).getPositionX(),snakePointList.get(0).getPositionY(),pointSize,createPointColor());
                    // draw random point circle on the surface to be eaten by the snake
                    canvas.drawCircle(positionX,positionY,pointSize,createPointColor());
                    // other points is following snake's head
                    for(int i = 1; i < snakePointList.size(); i++){
                        int getTempPositionX = snakePointList.get(i).getPositionX();
                        int getTempPositionY = snakePointList.get(i).getPositionY();

                        snakePointList.get(i).setPositionX(headPositionX);
                        snakePointList.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(snakePointList.get(i).getPositionX(),snakePointList.get(i).getPositionY(),pointSize,createPointColor());

                        //change head position
                        headPositionX = getTempPositionX;
                        headPositionY = getTempPositionY;
                    }

                    //unlock canvas to draw on
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }, 1000 - snakeMovingSpeed, 1000 -snakeMovingSpeed);

    }

    private void growSnake(){
        // create new snake point
        SnakePoints lastPoint = snakePointList.get(snakePointList.size() - 1);
        //add point to snake's tale
        int newX = lastPoint.getPositionX();
        int newY = lastPoint.getPositionY();

        // Add new point to snake's tail
        SnakePoints newPoint = new SnakePoints(newX, newY);
        snakePointList.add(newPoint);

        //increase score
        score++;

        //setting score to texxtView
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTV.setText(String.valueOf(score));
            }
        });

    }

    private boolean checkGameOver(int headPositionX, int headPositionY){
        boolean gameOver = false;

        //chec if snake head touches edges
        if(snakePointList.get(0).getPositionX() < 0 ||
                snakePointList.get(0).getPositionY() < 0 ||
                snakePointList.get(0).getPositionX() >= surfaceView.getWidth() ||
                snakePointList.get(0).getPositionY() >= surfaceView.getHeight())
        {
         gameOver = true;
        }
        else {
            //check if snake's head touches snake itself
            for (int i = 1; i < snakePointList.size(); i++){

                if(headPositionX == snakePointList.get(i).getPositionX() &&
                     headPositionY == snakePointList.get(i).getPositionY()){
                    gameOver = true;
                    break;
                }
            }
        }
        return gameOver;
    }

    private Paint createPointColor(){
        if(pointColor == null){
            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setAntiAlias(true); //smoothness
        }
        return pointColor;
    }
}