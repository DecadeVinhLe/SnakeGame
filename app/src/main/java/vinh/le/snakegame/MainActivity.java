package vinh.le.snakegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
   private SurfaceView surfaceView;
   private TextView scoreTV;

   private SurfaceHolder surfaceHolder;

   // Default Moving state for snake 
   private String movingPosition = "right";
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
                if(movingPosition.equals("bottom")) {
                    movingPosition = "top";
                }

            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check previous state of snake
                if(movingPosition.equals("right")) {
                    movingPosition = "left";
                }

            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check previous state of snake
                if(movingPosition.equals("left")) {
                    movingPosition = "right";
                }


            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check previous state of snake
                if(movingPosition.equals("top")) {
                    movingPosition = "bottom";
                }


            }
        });


    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        // surfaceHolder is assigned when surface is created
        this.surfaceHolder = surfaceHolder;

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

    }
}