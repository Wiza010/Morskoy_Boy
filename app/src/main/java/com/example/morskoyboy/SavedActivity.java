package com.example.morskoyboy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final String PREFS_NAME = "ShotPrefs";
    private static final String PREF_SHOT_COUNT = "ShotCount";
    private static final String PREF_SHOT_PREFIX = "Shot_";
    private static final String PREF_SHAPES_COUNT = "ShapesCount";
    private static final String PREF_SHAPE_PREFIX = "Shape_";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int shotCount;

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

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        shotCount = sharedPreferences.getInt(PREF_SHOT_COUNT, 0);

        isPlayerTurn = true;
        shots = new ArrayList<>();
        for (int i = 0; i < shotCount; i++) {
            int x = sharedPreferences.getInt(PREF_SHOT_PREFIX + i + "_x", 0);
            int y = sharedPreferences.getInt(PREF_SHOT_PREFIX + i + "_y", 0);
            Shot shot = new Shot(x, y);
            shots.add(shot);
        }

        shapes = loadShapesFromSharedPreferences();
        if (shapes == null) {
            shapes = generateRandomShapes();
            saveShapesToSharedPreferences(shapes);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear().apply();
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

    private void saveShapesToSharedPreferences(List<RectF> shapes) {
        editor.putInt(PREF_SHAPES_COUNT, shapes.size());
        for (int i = 0; i < shapes.size(); i++) {
            RectF shape = shapes.get(i);
            editor.putFloat(PREF_SHAPE_PREFIX + i + "_left", shape.left);
            editor.putFloat(PREF_SHAPE_PREFIX + i + "_top", shape.top);
            editor.putFloat(PREF_SHAPE_PREFIX + i + "_right", shape.right);
            editor.putFloat(PREF_SHAPE_PREFIX + i + "_bottom", shape.bottom);
        }
        editor.apply();
    }

    private List<RectF> loadShapesFromSharedPreferences() {
        List<RectF> shapes = new ArrayList<>();
        int shapesCount = sharedPreferences.getInt(PREF_SHAPES_COUNT, 0);
        for (int i = 0; i < shapesCount; i++) {
            float left = sharedPreferences.getFloat(PREF_SHAPE_PREFIX + i + "_left", 0);
            float top = sharedPreferences.getFloat(PREF_SHAPE_PREFIX + i + "_top", 0);
            float right = sharedPreferences.getFloat(PREF_SHAPE_PREFIX + i + "_right", 0);
            float bottom = sharedPreferences.getFloat(PREF_SHAPE_PREFIX + i + "_bottom", 0);
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
            shots.add(new Shot(x, y));
            invalidate();
        }

        private boolean isHit(Shot shot) {
            for (RectF shape : shapes) {
                if (shape.contains(shot.getX() * cellSize, shot.getY() * cellSize)) {
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
    }
}
