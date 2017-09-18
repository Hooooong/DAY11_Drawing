# CustomView 로 만든 그림판

### 설명
____________________________________________________

![Drawing](https://github.com/Hooooong/DAY11_Drawing/blob/master/image/Drawing.gif)

- CustomView 로 만든 그림판

### 소스코드
____________________________________________________

- Java 코드

  - MainActivity.java

      ```java
      public class MainActivity extends AppCompatActivity {

          FrameLayout stage;
          RadioGroup radioColor;
          SeekBar seekBar;
          TextView seekBarValues;
          DrawView draw;

          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              /**
               * ActionBar 없애기
               */
              getSupportActionBar().hide();
              setContentView(R.layout.activity_main);

              initView();
              initListener();
          }

          /**
           * View 초기화
           */
          public void initView(){
              radioColor = (RadioGroup) findViewById(R.id.radioColor);
              seekBar = (SeekBar)findViewById(R.id.seekBar);
              seekBarValues = (TextView) findViewById(R.id.seekBarValues);
              stage = (FrameLayout) findViewById(R.id.stage);
              draw = new DrawView(this);
              stage.addView(draw);
          }

          /**
           * Listener 초기화
           */
          public void initListener(){
              // 라디오 버튼이 선택되면 Draw 의 paint 의 Color, Size 를 바꿔준다
              radioColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                      switch(id){
                          case R.id.radioBlack:
                              draw.setResource(Color.BLACK, seekBar.getProgress());
                              break;
                          case R.id.radioCyan:
                              draw.setResource(Color.CYAN, seekBar.getProgress());
                              break;
                          case R.id.radioMagenta:
                              draw.setResource(Color.MAGENTA, seekBar.getProgress());
                              break;
                          case R.id.radioYellow:
                              draw.setResource(Color.YELLOW, seekBar.getProgress());
                              break;
                      }
                  }
              });

              // SeekBar ChangeListener 설정
              seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                  // SeekBar 를 변경할 경우 선택되어진 RadioButton 의 Color 와 SeekBar value 를 통해
                  // paint 의 Color, Size 를 변경한다.
                  @Override
                  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                      switch (radioColor.getCheckedRadioButtonId()){
                          case R.id.radioBlack:
                              draw.setResource(Color.BLACK, progress);
                              break;
                          case R.id.radioCyan:
                              draw.setResource(Color.CYAN, progress);
                              break;
                          case R.id.radioMagenta:
                              draw.setResource(Color.MAGENTA, progress);
                              break;
                          case R.id.radioYellow:
                              draw.setResource(Color.YELLOW, progress);
                              break;
                      }
                      seekBarValues.setText(progress + "");
                  }

                  @Override
                  public void onStartTrackingTouch(SeekBar seekBar) {

                  }

                  @Override
                  public void onStopTrackingTouch(SeekBar seekBar) {

                  }
              });
          }
      }
      ```

  - DrawView.java

      ```java
      public class DrawView extends View {
          // Paint 변수 설정 ( 붓 )
          Paint paint;
          // Paht 변수 설정 ( 경로 )
          Path currentPath;
          // Path 마다 각 Color, StrokeWidth 설정
          List<PathTool> pathTools = new ArrayList<>();

          // 소스코드에서 사용하기 때문에 생성자 파라미터는 Context 필요
          public DrawView(Context context) {
              super(context);
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
      ```

  - PathTool.java

      ```java

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
      ```

- xml 코드

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="hooooong.com.drawing.MainActivity">

        <FrameLayout
            android:id="@+id/stage"
            android:layout_width="0dp"
            android:layout_height="500dp"
            app:layout_constraintBottom_toTopOf="@+id/button"
            app:layout_constraintHorizontal_bias="1.0"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.135" />

        <RadioGroup
            android:id="@+id/radioColor"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="8dp"
            android:checkedButton="@+id/radioBlack"
            android:orientation="horizontal"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            android:layout_marginTop="8dp"
            app:layout_constraintTop_toBottomOf="@+id/stage"
            app:layout_constraintVertical_bias="0.142"
            android:layout_marginRight="8dp"
            app:layout_constraintRight_toRightOf="parent">

            <RadioButton
                android:id="@+id/radioCyan"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Cyan" />

            <RadioButton
                android:id="@+id/radioMagenta"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Magenta" />

            <RadioButton
                android:id="@+id/radioYellow"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="Yellow" />

            <RadioButton
                android:id="@+id/radioBlack"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Black" />
        </RadioGroup>

        <SeekBar
            android:id="@+id/seekBar"
            android:layout_width="228dp"
            android:layout_height="23dp"
            android:layout_marginTop="8dp"
            app:layout_constraintTop_toBottomOf="@+id/radioColor"
            android:layout_marginLeft="16dp"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintVertical_bias="0.272" />

        <TextView
            android:id="@+id/seekBarValues"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="8dp"
            android:layout_marginTop="8dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/radioColor"
            app:layout_constraintVertical_bias="0.607"
            app:layout_constraintRight_toLeftOf="@+id/button"
            android:layout_marginRight="2dp"
            app:layout_constraintLeft_toRightOf="@+id/seekBar"
            android:layout_marginLeft="2dp"
            app:layout_constraintHorizontal_bias="0.525" />

        <Button
            android:id="@+id/button"
            android:layout_width="80dp"
            android:layout_height="40dp"
            android:text="Button"
            app:layout_constraintLeft_toLeftOf="@+id/seekBarValues"
            android:layout_marginLeft="2dp"
            app:layout_constraintBottom_toBottomOf="parent"
            android:layout_marginBottom="8dp"
            android:layout_marginRight="16dp"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintHorizontal_bias="1.0"
            android:layout_marginTop="0dp"
            app:layout_constraintTop_toBottomOf="@+id/radioColor" />

    </android.support.constraint.ConstraintLayout>
    ```
