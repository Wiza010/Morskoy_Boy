package com.example.morskoyboy;

//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.FrameLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PlaceShipsActivity extends AppCompatActivity {
//    private CustomGridView customGridView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        customGridView = new CustomGridView(this);
//        customGridView.setBackgroundColor(Color.WHITE);
//
//        setContentView(customGridView);
//
//        List<RectF> shapes = getIntent().getParcelableArrayListExtra("shapes");
//        if (shapes != null) {
//            customGridView.setShapes(shapes);
//        }
//
//        // Create the button
//        Button nextButton = new Button(this);
//        nextButton.setText("Следующий ход");
//
//        // Set the click listener
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Transition to SavedActivity
//                Intent intent = new Intent(PlaceShipsActivity.this, SavedActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Add the button to the activity layout
//        FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT
//        );
//        buttonParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
//        buttonParams.bottomMargin = 16;
//        addContentView(nextButton, buttonParams);
//    }
//
//    private class CustomGridView extends View {
//        private static final int GRID_SIZE = 10;
//        private static final int CELL_SIZE = 80;
//
//        private Paint gridPaint;
//        private Paint squarePaint;
//        private Paint rectanglePaint;
//        private List<RectF> shapes;
//        private int[][] cellCoordinates;
//
//        public CustomGridView(Context context) {
//            super(context);
//
//            gridPaint = new Paint();
//            gridPaint.setColor(Color.LTGRAY);
//            gridPaint.setStyle(Paint.Style.STROKE);
//            gridPaint.setStrokeWidth(2f);
//
//            squarePaint = new Paint();
//            squarePaint.setColor(Color.BLUE);
//            squarePaint.setStyle(Paint.Style.FILL);
//
//            rectanglePaint = new Paint();
//            rectanglePaint.setStyle(Paint.Style.FILL);
//
//            cellCoordinates = new int[GRID_SIZE][GRID_SIZE];
//            initializeCellCoordinates();
//        }
//
//        private void initializeCellCoordinates() {
//            for (int row = 0; row < GRID_SIZE; row++) {
//                for (int col = 0; col < GRID_SIZE; col++) {
//                    int x = col * CELL_SIZE;
//                    int y = row * CELL_SIZE;
//                    cellCoordinates[row][col] = x + y * GRID_SIZE;
//                }
//            }
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            drawGrid(canvas);
//            drawShapes(canvas);
//        }
//
//        private void drawGrid(Canvas canvas) {
//            int gridSize = CELL_SIZE * GRID_SIZE;
//
//            for (int i = 0; i <= GRID_SIZE; i++) {
//                float x = i * CELL_SIZE;
//                canvas.drawLine(x, 0, x, gridSize, gridPaint);
//            }
//
//            for (int i = 0; i <= GRID_SIZE; i++) {
//                float y = i * CELL_SIZE;
//                canvas.drawLine(0, y, gridSize, y, gridPaint);
//            }
//        }
//
//        private void drawShapes(Canvas canvas) {
//            for (RectF shape : shapes) {
//                if (isSquare(shape)) {
//                    canvas.drawRect(shape, squarePaint);
//                } else {
//                    canvas.drawRect(shape, rectanglePaint);
//                }
//            }
//        }
//
//        private boolean isSquare(RectF shape) {
//            float width = shape.width();
//            float height = shape.height();
//            return width == height;
//        }
//
//        public void setShapes(List<RectF> shapes) {
//            this.shapes = shapes;
//            invalidate();
//        }
//
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            int desiredWidth = GRID_SIZE * CELL_SIZE;
//            int desiredHeight = GRID_SIZE * CELL_SIZE;
//
//            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//            int width;
//            int height;
//
//            // Measure width
//            if (widthMode == MeasureSpec.EXACTLY) {
//                width = widthSize;
//            } else if (widthMode == MeasureSpec.AT_MOST) {
//                width = Math.min(desiredWidth, widthSize);
//            } else {
//                width = desiredWidth;
//            }
//
//            // Measure height
//            if (heightMode == MeasureSpec.EXACTLY) {
//                height = heightSize;
//            } else if (heightMode == MeasureSpec.AT_MOST) {
//                height = Math.min(desiredHeight, heightSize);
//            } else {
//                height = desiredHeight;
//            }
//
//            setMeasuredDimension(width, height);
//        }
//    }
//}