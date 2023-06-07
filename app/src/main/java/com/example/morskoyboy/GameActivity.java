package com.example.morskoyboy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private CustomGridView customGridView;
    private Button nextTurnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customGridView = new CustomGridView(this);
        customGridView.setBackgroundColor(Color.WHITE);

        nextTurnButton = new Button(this);
        nextTurnButton.setText("Next Turn");
        nextTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customGridView.nextTurn();
                customGridView.computerTurn();
            }
        });

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(customGridView);
        layout.addView(nextTurnButton);

        setContentView(layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                customGridView.computerTurn();
            }
        }, 1000);
    }

    private class CustomGridView extends View {
        private static final int GRID_SIZE = 10;
        private static final int CELL_SIZE = 80;

        private Paint gridPaint;
        private Paint emptyCellPaint;
        private Paint hitCellPaint;
        private Paint missCellPaint;

        private List<Rect> shipRects;
        private List<Rect> hitRects;
        private List<Rect> missRects;
        private List<Rect> computerHitRects;
        private List<Rect> computerMissRects;

        private Random random;

        public CustomGridView(Context context) {
            super(context);

            gridPaint = new Paint();
            gridPaint.setColor(Color.LTGRAY);
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(2f);

            emptyCellPaint = new Paint();
            emptyCellPaint.setColor(Color.GRAY);
            emptyCellPaint.setStyle(Paint.Style.FILL);

            hitCellPaint = new Paint();
            hitCellPaint.setColor(Color.YELLOW);
            hitCellPaint.setStyle(Paint.Style.FILL);

            missCellPaint = new Paint();
            missCellPaint.setColor(Color.DKGRAY);
            missCellPaint.setStyle(Paint.Style.FILL);

            shipRects = new ArrayList<>();
            hitRects = new ArrayList<>();
            missRects = new ArrayList<>();
            computerHitRects = new ArrayList<>();
            computerMissRects = new ArrayList<>();

            random = new Random();

            ArrayList<RectF> shapes = getIntent().getParcelableArrayListExtra("shapes");
            if (shapes != null) {
                for (RectF shape : shapes) {
                    shipRects.add(convertRectFToRect(shape));
                }
            }
        }

        private Rect convertRectFToRect(RectF rectF) {
            return new Rect(
                    Math.round(rectF.left),
                    Math.round(rectF.top),
                    Math.round(rectF.right),
                    Math.round(rectF.bottom)
            );
        }

        private void drawGrid(Canvas canvas) {
            int gridSize = CELL_SIZE * GRID_SIZE;

            for (int i = 0; i <= GRID_SIZE; i++) {
                float x = i * CELL_SIZE;
                canvas.drawLine(x, 0, x, gridSize, gridPaint);
            }

            for (int i = 0; i <= GRID_SIZE; i++) {
                float y = i * CELL_SIZE;
                canvas.drawLine(0, y, gridSize, y, gridPaint);
            }
        }

        private void drawCells(Canvas canvas, List<Rect> rects, Paint paint) {
            for (Rect rect : rects) {
                canvas.drawRect(rect, paint);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawGrid(canvas);
            drawCells(canvas, shipRects, emptyCellPaint);
            drawCells(canvas, hitRects, hitCellPaint);
            drawCells(canvas, missRects, missCellPaint);
            drawCells(canvas, computerHitRects, hitCellPaint);
            drawCells(canvas, computerMissRects, missCellPaint);
        }

        private void handleCellClick(float x, float y) {
        }

        public void nextTurn() {
            hitRects.clear();
            missRects.clear();
            invalidate();
        }

        public void computerTurn() {
            if (!computerHitRects.isEmpty() || !computerMissRects.isEmpty()) {
                return;
            }
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);

            Rect cellRect = new Rect(
                    col * CELL_SIZE,
                    row * CELL_SIZE,
                    (col + 1) * CELL_SIZE,
                    (row + 1) * CELL_SIZE
            );

            if (shipRects.contains(cellRect)) {
                computerHitRects.add(cellRect);
            } else {
                computerMissRects.add(cellRect);
            }

            invalidate();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(GameActivity.this, SavedActivity.class);
                    startActivity(intent);
                }
            }, 3000);
        }
    }
}