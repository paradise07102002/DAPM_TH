package edu.huflit.doanpm_th;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        img_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManHinhChinh.class);
                startActivity(intent);
            }
        });
    }
    public void anhXa()
    {
        img_main = (ImageView) findViewById(R.id.img_main);
    }
}