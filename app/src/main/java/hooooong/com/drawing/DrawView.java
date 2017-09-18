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
    // Paint 변수 설정 ( 붓 )
    Paint paint;
    // Path 변수 설정 ( 경로 )
    Path currentPath;
    // Path 마다 각 Color, StrokeWidth 설정
    List<PathTool> pathTools;

    // 소스코드에서 사용하기 때문에 생성자 파라미터는 Context 필요
    public DrawView(Context context) {
        super(context);
        pathTools = new ArrayList<>();
        initPaint();
    }

    /**
     * Paint 생성 및 Resource 초기화 작업
     */
    public void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        setResource(Color.BLACK, 0F);
    }

    /**
     * Resource 설정
     *
     * @param color : 색
     * @param size : 크기
     */
    public void setResource(int color, float size) {
        PathTool pathTool = new PathTool();
        // 색 설정
        pathTool.setColor(color);
        // 크기 설정
        pathTool.setSize(size);

        currentPath = pathTool;
        pathTools.add(pathTool);
    }

    /**
     * CustomView 의 Touch Event 설정
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 눌렀을 때 현재 x, y 좌표 이동 ( MOVE 에선 이전 x, y 가 된다 )
                currentPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                // 누르고 이동할 때 이전 x, y 좌표와 현재 x, y 좌표를 긋는다.
                currentPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                // Noting
                break;
        }
        // onDraw 호출하는 함수
        // 안드로이드 같은 경우에는 새로 그리는 역할?
        invalidate();
        return true;
    }

    // 화면을 그려주는 onDraw 오버라이드
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (PathTool tool : pathTools) {
            // paint 객체에 color 를 설정
            paint.setColor(tool.getColor());
            // paint 객체에 size 를 설정
            paint.setStrokeWidth(tool.getSize());
            canvas.drawPath(tool, paint);
        }
    }
}

/**
 * Path 클래스를 상속받아 color 와 size 를 저장할 수 있는 class 생성
 */
class PathTool extends Path {
    // color 변수
    private int color;
    // size 변수
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