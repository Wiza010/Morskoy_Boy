package com.example.morskoyboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BattleShip2 extends AppCompatActivity {

    ImageView ship;
    ImageView ship2;
    ImageView ship3;
    ImageView middle;
    ImageView middle2;
    ImageView threeship;
    ImageView threeship2;
    ImageView bigship;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battleship);
        Button button = (Button) findViewById(R.id.ready);

        ship = (ImageView) findViewById(R.id.ship);
        ship2 = (ImageView) findViewById(R.id.ship2);
        ship3 = (ImageView) findViewById(R.id.ship3);
        middle = (ImageView) findViewById(R.id.middle);
        middle2 = (ImageView) findViewById(R.id.midle2);
        threeship = (ImageView) findViewById(R.id.threeship);
        threeship2 = (ImageView) findViewById(R.id.threeship2);
        bigship = (ImageView) findViewById(R.id.bigship);


        ship.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        ship.setX(ship.getX() + distanceX);
                        ship.setY(ship.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        ship2.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        ship2.setX(ship2.getX() + distanceX);
                        ship2.setY(ship2.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        ship3.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        ship3.setX(ship3.getX() + distanceX);
                        ship3.setY(ship3.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        middle.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        middle.setX(middle.getX() + distanceX);
                        middle.setY(middle.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        middle2.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        middle2.setX(middle2.getX() + distanceX);
                        middle2.setY(middle2.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        threeship.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        threeship.setX(threeship.getX() + distanceX);
                        threeship.setY(threeship.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        threeship2.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        threeship2.setX(threeship2.getX() + distanceX);
                        threeship2.setY(threeship2.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
        bigship.setOnTouchListener(new View.OnTouchListener() {
            float xDown, yDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx, dy;
                        dx = event.getX();
                        dy = event.getY();

                        float distanceX = dx - xDown;
                        float distanceY = dy - yDown;

                        bigship.setX(bigship.getX() + distanceX);
                        bigship.setY(bigship.getY() + distanceY);

                        xDown = dx;
                        yDown = dy;
                        break;
                }

                return true;
            }
        });
    }
}

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 50);
//        layoutParams.leftMargin = 50;
//        layoutParams.topMargin = 50;
//        layoutParams.bottomMargin = -250;
//        layoutParams.rightMargin = -250;
//        ship.setLayoutParams(layoutParams);
//
//        ship.setOnTouchListener((View.OnTouchListener) this);
//    }
//
//    public boolean onTouch(View view, MotionEvent event) {
//        final int X = (int) event.getRawX();
//        final int Y = (int) event.getRawY();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                _xDelta = X - lParams.leftMargin;
//                _yDelta = Y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                layoutParams.leftMargin = X - _xDelta;
//                layoutParams.topMargin = Y - _yDelta;
//                layoutParams.rightMargin = -250;
//                layoutParams.bottomMargin = -250;
//                view.setLayoutParams(layoutParams);
//                break;
//        }
//        ship.invalidate();
//        return true;
//    }}

//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(BattleShip2.this, game.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        Button btn_back;
//        btn_back = (Button) findViewById(R.id.btn_back);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent intent = new Intent(BattleShip2.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } catch (Exception e) {
//
//                }
//            }
//        });
//    }
//}