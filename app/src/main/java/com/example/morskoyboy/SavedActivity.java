package com.example.morskoyboy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SavedActivity extends AppCompatActivity {

    private CustomGridView customGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        List<RectF> savedShapes = getIntent().getParcelableArrayListExtra("shapes");
        customGridView = new CustomGridView(this, savedShapes);
        customGridView.setBackgroundColor(Color.WHITE);


        setContentView(customGridView);
    }

    private class CustomGridView extends View {

        private static final int GRID_SIZE = 10;
        private static final int CELL_SIZE = 80;

        private Paint squarePaint;
        private Paint rectanglePaint;

        private List<RectF> shapes;

        public CustomGridView(Context context, List<RectF> shapes) {

            super(context);
            this.shapes = shapes;

            squarePaint = new Paint();
            squarePaint.setColor(Color.RED);
            squarePaint.setStyle(Paint.Style.FILL);

            rectanglePaint = new Paint();
            rectanglePaint.setColor(Color.BLUE);
            rectanglePaint.setStyle(Paint.Style.FILL);


            int gridSize = CELL_SIZE * GRID_SIZE;
            setLayoutParams(new ViewGroup.LayoutParams(gridSize, gridSize));

        }
        public List<RectF> getShapes() {
            return shapes;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            drawShapes(canvas);
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
    }
}