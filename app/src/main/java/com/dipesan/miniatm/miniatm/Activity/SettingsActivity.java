package com.dipesan.miniatm.miniatm.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.dipesan.miniatm.miniatm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {



    @BindView(R.id.settings_save_button) Button settingsSaveButton;

    @BindView(R.id.settings_device_edit_text) EditText settingsDeviceEditText;
    @BindView(R.id.settings_auto_connect_switch) Switch settingsAutoConnectSwitch;
    @BindView(R.id.settings_server_edit_text) EditText settingsServerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.fade_out);
    }


    @OnClick({ R.id.settings_save_button, R.id.settings_device_edit_text, R.id.settings_auto_connect_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_save_button:
                break;
            case R.id.settings_device_edit_text:
                break;
            case R.id.settings_auto_connect_switch:
                break;
        }
    }
}
