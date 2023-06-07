package com.example.morskoyboy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SavedActivity extends AppCompatActivity {
    private SavedGridView savedGridView;
    private boolean isPlayerTurn;
    private List<Shot> shots;
    private List<RectF> shapes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        savedGridView = new SavedGridView(this);
        savedGridView.setBackgroundColor(Color.WHITE);
        savedGridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isPlayerTurn && event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) (event.getX() / savedGridView.getCellSize());
                    int y = (int) (event.getY() / savedGridView.getCellSize());

                    savedGridView.fireShot(x, y);
                    isPlayerTurn = false;
                    return true;
                }
                return false;
            }
        });

        layout.addView(savedGridView);

        Button nextTurnButton = new Button(this);
        nextTurnButton.setText("Следующий ход");
        nextTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        layout.addView(nextTurnButton);

        setContentView(layout);

        isPlayerTurn = true;
        shots = new ArrayList<>();

        if (shapes == null) {
            shapes = generateRandomShapes();
        }
    }

    private List<RectF> generateRandomShapes() {
        List<RectF> shapes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int left = random.nextInt(SavedGridView.GRID_SIZE - 1) * SavedGridView.CELL_SIZE;
            int top = random.nextInt(SavedGridView.GRID_SIZE - 1) * SavedGridView.CELL_SIZE;
            int right = left + SavedGridView.CELL_SIZE;
            int bottom = top + SavedGridView.CELL_SIZE;
            shapes.add(new RectF(left, top, right, bottom));
        }

        return shapes;
    }

    private class SavedGridView extends View {
        private static final int GRID_SIZE = 10;
        private static final int CELL_SIZE = 80;

        private Paint gridPaint;
        private Paint hitPaint;
        private Paint missPaint;

        private float cellSize;

        public SavedGridView(Context context) {
            super(context);

            gridPaint = new Paint();
            gridPaint.setColor(Color.LTGRAY);
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(2f);

            hitPaint = new Paint();
            hitPaint.setColor(Color.RED);
            hitPaint.setStyle(Paint.Style.FILL);

            missPaint = new Paint();
            missPaint.setColor(Color.GRAY);
            missPaint.setStyle(Paint.Style.FILL);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int gridSize = CELL_SIZE * GRID_SIZE;
            setLayoutParams(new ViewGroup.LayoutParams(gridSize, gridSize));

            cellSize = (float) gridSize / GRID_SIZE;
        }

        public float getCellSize() {
            return cellSize;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawGrid(canvas);
            drawShapes(canvas);
            drawShots(canvas);
        }

        private void drawGrid(Canvas canvas) {
            int gridSize = GRID_SIZE * (int) cellSize;

            for (int i = 0; i <= GRID_SIZE; i++) {
                float x = i * cellSize;
                canvas.drawLine(x, 0, x, gridSize, gridPaint);
            }

            for (int i = 0; i <= GRID_SIZE; i++) {
                float y = i * cellSize;
                canvas.drawLine(0, y, gridSize, y, gridPaint);
            }
        }

        private void drawShapes(Canvas canvas) {
            for (RectF shape : shapes) {
                canvas.drawRect(shape, gridPaint);
            }
        }

        private void drawShots(Canvas canvas) {
            for (Shot shot : shots) {
                float centerX = shot.getX() * cellSize + cellSize / 2;
                float centerY = shot.getY() * cellSize + cellSize / 2;

                if (isHit(shot)) {
                    canvas.drawCircle(centerX, centerY, cellSize / 4, hitPaint);
                } else {
                    canvas.drawCircle(centerX, centerY, cellSize / 4, missPaint);
                }
            }
        }

        public void fireShot(int x, int y) {
            Shot shot = new Shot(x, y);
            shots.add(shot);
            invalidate();

            if (isHit(shot)) {
                // Handle hit logic here
            } else {
                // Handle miss logic here
            }
        }

        private boolean isHit(Shot shot) {
            for (RectF shape : shapes) {
                if (RectF.intersects(shape, shot.getRect())) {
                    return true;
                }
            }
            return false;
        }
    }

    private class Shot {
        private int x;
        private int y;

        public Shot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public RectF getRect() {
            float left = x * savedGridView.getCellSize();
            float top = y * savedGridView.getCellSize();
            float right = left + savedGridView.getCellSize();
            float bottom = top + savedGridView.getCellSize();
            return new RectF(left, top, right, bottom);
        }
    }
}












