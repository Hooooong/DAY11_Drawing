package hooooong.com.drawing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

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
                    case R.id.radioCyan:
                        draw.setResource(Color.CYAN, seekBar.getProgress());
                        break;
                    case R.id.radioMagenta:
                        draw.setResource(Color.MAGENTA, seekBar.getProgress());
                        break;
                    case R.id.radioYellow:
                        draw.setResource(Color.YELLOW, seekBar.getProgress());
                        break;
                    case R.id.radioBlack:
                        draw.setResource(Color.BLACK, seekBar.getProgress());
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