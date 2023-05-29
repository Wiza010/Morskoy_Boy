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

public class MainActivity extends AppCompatActivity {

    private CustomGridView customGridView;
    private Button randomButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customGridView = new CustomGridView(this);
        customGridView.setBackgroundColor(Color.WHITE);


        randomButton = new Button(this);
        randomButton.setText("Random");
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customGridView.randomizeShapes();
            }
        });


        saveButton = new Button(this);
        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SavedActivity.class);
                intent.putParcelableArrayListExtra("shapes", new ArrayList<>(customGridView.getShapes()));
                startActivity(intent);
            }
        });


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(customGridView);
        layout.addView(randomButton);
        layout.addView(saveButton);


        setContentView(layout);
    }

    private class CustomGridView extends View {

        private static final int GRID_SIZE = 10;
        private static final int CELL_SIZE = 80;
        private static final int SHAPE_DISTANCE = 2; // Distance between shapes (in cells)

        private Paint gridPaint;
        private Paint squarePaint;
        private Paint rectanglePaint;

        private List<RectF> shapes;
        private RectF selectedShape;
        private boolean isShapeSelected;
        private float offsetX;
        private float offsetY;

        public CustomGridView(Context context) {
            super(context);


            gridPaint = new Paint();
            gridPaint.setColor(Color.LTGRAY);
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(2f);

            squarePaint = new Paint();
            squarePaint.setColor(Color.RED);
            squarePaint.setStyle(Paint.Style.FILL);

            rectanglePaint = new Paint();
            rectanglePaint.setColor(Color.BLUE);
            rectanglePaint.setStyle(Paint.Style.FILL);


            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int gridSize = CELL_SIZE * GRID_SIZE;
            setLayoutParams(new ViewGroup.LayoutParams(gridSize, gridSize));


            shapes = new ArrayList<>();


            shapes.add(new RectF(CELL_SIZE, CELL_SIZE, 2 * CELL_SIZE, 2 * CELL_SIZE));
            shapes.add(new RectF(3 * CELL_SIZE, CELL_SIZE, 4 * CELL_SIZE, 2 * CELL_SIZE));
            shapes.add(new RectF(5 * CELL_SIZE, CELL_SIZE, 6 * CELL_SIZE, 2 * CELL_SIZE));
            shapes.add(new RectF(7 * CELL_SIZE, CELL_SIZE, 8 * CELL_SIZE, 2 * CELL_SIZE));


            shapes.add(new RectF(CELL_SIZE, 4 * CELL_SIZE, 3 * CELL_SIZE, 5 * CELL_SIZE));
            shapes.add(new RectF(4 * CELL_SIZE, 4 * CELL_SIZE, 6 * CELL_SIZE, 5 * CELL_SIZE));
            shapes.add(new RectF(7 * CELL_SIZE, 4 * CELL_SIZE, 10 * CELL_SIZE, 5 * CELL_SIZE));
            shapes.add(new RectF(CELL_SIZE, 6 * CELL_SIZE, 5 * CELL_SIZE, 7 * CELL_SIZE));

            isShapeSelected = false;
            selectedShape = null;
            offsetX = 0;
            offsetY = 0;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawGrid(canvas);
            drawShapes(canvas);
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

        private void drawShapes(Canvas canvas) {
            for (RectF shape : shapes) {
                if (isSquare(shape)) {
                    canvas.drawRect(shape, squarePaint);
                } else {
                    canvas.drawRect(shape, rectanglePaint);
                }
            }
        }

        private boolean isSquare(RectF shape) {
            float width = shape.width();
            float height = shape.height();
            return width == height;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            float x = event.getX();
            float y = event.getY();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    selectShape(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveShape(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    deselectShape();
                    break;
            }

            return true;
        }

        private void selectShape(float x, float y) {
            for (RectF shape : shapes) {
                if (shape.contains(x, y)) {
                    selectedShape = shape;
                    isShapeSelected = true;
                    offsetX = x - shape.left;
                    offsetY = y - shape.top;
                    break;
                }
            }
        }

        private void moveShape(float x, float y) {
            if (isShapeSelected && selectedShape != null) {
                float left = x - offsetX;
                float top = y - offsetY;


                float snappedLeft = snapToGrid(left);
                float snappedTop = snapToGrid(top);


                if (!isOverlappingShape(selectedShape, snappedLeft, snappedTop)) {
                    float right = snappedLeft + selectedShape.width();
                    float bottom = snappedTop + selectedShape.height();

                    selectedShape.set(snappedLeft, snappedTop, right, bottom);

                    invalidate();
                }
            }
        }

        private float snapToGrid(float coordinate) {
            int cellIndex = Math.round(coordinate / CELL_SIZE);
            return cellIndex * CELL_SIZE;
        }

        private boolean isOverlappingShape(RectF currentShape, float newX, float newY) {
            for (RectF shape : shapes) {
                if (shape != currentShape && RectF.intersects(shape, new RectF(newX, newY, newX + currentShape.width(), newY + currentShape.height()))) {
                    return true;
                }
            }
            return false;
        }

        private void deselectShape() {
            isShapeSelected = false;
            selectedShape = null;
        }

        public void randomizeShapes() {
            Random random = new Random();

            for (RectF shape : shapes) {
                float newX = random.nextInt(GRID_SIZE - (int) shape.width() / CELL_SIZE) * CELL_SIZE;
                float newY = random.nextInt(GRID_SIZE - (int) shape.height() / CELL_SIZE) * CELL_SIZE;

                shape.offsetTo(newX, newY);
            }

            invalidate();
        }

        public List<RectF> getShapes() {
            return shapes;
        }
    }
}


