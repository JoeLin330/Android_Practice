package com.example.joe.materialanimate01;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_animate01);
    }

    public void clickButton(View view) {
        Intent intent = new Intent(this, ApiLevelActivity.class);
        // 使用動畫資源啟動元件
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this).toBundle());
    }

}
