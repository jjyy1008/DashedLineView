package com.xyan.dotlineviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xyan.dotlineview.DashedLineView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DashedLineView dot1, dot2, dot3;
        dot1 = (DashedLineView) findViewById(R.id.dotted_line_vertical);
        dot2 = (DashedLineView) findViewById(R.id.dotted_line_hori);
        dot3 = (DashedLineView) findViewById(R.id.dotted_line_vertical_2);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomColor();
                dot1.setLineColor(getRandomColor());
                dot2.setLineColor(getRandomColor());
                dot3.setLineColor(getRandomColor());
            }
        });
    }

    private int getRandomColor() {
        Random random = new Random();
        return 0xff000000 | random.nextInt(0x00ffffff);
    }
}
