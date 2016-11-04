package com.dipesan.miniatm.miniatm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dipesan.miniatm.miniatm.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity fullscreen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        overridePendingTransition(0, R.anim.fade_out);
    }

    @OnClick({R.id.login_settings_button, R.id.login_logo_image_view, R.id.login_button, R.id.login_register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_settings_button:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                overridePendingTransition(0, R.anim.fade_out);
                break;
            case R.id.login_logo_image_view:
                Intent intentMainActivity = new Intent(this, MainActivity.class);
                startActivity(intentMainActivity);
                overridePendingTransition(0, R.anim.fade_out);
                break;
            case R.id.login_button:
                break;
            case R.id.login_register_button:
                break;
        }
    }

    @OnClick(R.id.pengaturan)
    public void onClickPengaturan() {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_frame_layout,null)//erorr
                .commit();
        overridePendingTransition(0, R.anim.fade_out);
    }
}
