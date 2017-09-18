package hooooong.com.drawing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
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
         * ActionBar 와 상태 창 없애기
         */
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        radioColor = (RadioGroup) findViewById(R.id.radioColor);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBarValues = (TextView) findViewById(R.id.seekBarValues);
        stage = (FrameLayout) findViewById(R.id.stage);
        draw = new DrawView(this);

        stage.addView(draw);

        // 라디오 버튼이 선택되면 Draw 의 paint 색상의 바꿔준다
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

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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