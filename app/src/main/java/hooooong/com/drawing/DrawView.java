package hooooong.com.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Android Hong on 2017-09-18.
 */
public class DrawView extends View {
    Paint paint;
    Path currentPath;

    List<PathTool> pathTools = new ArrayList<>();

    // 소스코드에서 사용하기 때문에 생성자 파라미터는 Context 필요
    public DrawView(Context context) {
        super(context);
        initPaint();
    }

    public void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        setResource(Color.BLACK, 0F);
    }

    public void setResource(int color, float size) {
        PathTool pathTool = new PathTool();
        pathTool.setColor(color);
        pathTool.setSize(size);
        currentPath = pathTool;
        pathTools.add(pathTool);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    // 화면을 그려주는 onDraw 오버라이드
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathTool tool : pathTools) {
            paint.setColor(tool.getColor());
            paint.setStrokeWidth(tool.getSize());
            canvas.drawPath(tool, paint);
        }
    }
}

class PathTool extends Path {
    private int color;
    private float size;

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}