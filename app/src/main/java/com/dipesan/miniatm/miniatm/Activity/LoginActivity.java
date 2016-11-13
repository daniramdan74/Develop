package com.dipesan.miniatm.miniatm.Activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.BluetoothConnexionManager;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.youTransactor.uCube.LogManager;
import com.youTransactor.uCube.mdm.MDMManager;
import com.youTransactor.uCube.rpc.RPCManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_ACCOUNT;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_MERCHANT;

public class LoginActivity extends AppCompatActivity {
    private YoucubeService youcubeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBluetoothConnection();
        // activity fullscreen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        overridePendingTransition(0, R.anim.fade_out);
        youcubeService = new YoucubeService(this);
    }

    private void initBluetoothConnection() {
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getBaseContext().registerReceiver(BluetoothConnexionManager.getInstance(), filter);
        LogManager.initialize(this);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        BluetoothConnexionManager.getInstance().initialize(settings);
        MDMManager.getInstance().initialize(this);
        RPCManager.getInstance().setConnexionManager(BluetoothConnexionManager.getInstance());
    }

    @OnClick({R.id.login_logo_image_view, R.id.login_button, R.id.login_register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_logo_image_view:
                Intent intentMainActivity = new Intent(this, MainActivity.class);
                startActivity(intentMainActivity);
                overridePendingTransition(0, R.anim.fade_out);
                finish();
                break;
            case R.id.login_button:
                youcubeService.setIsMessage(true);
                youcubeService.setMessage("Login Merchant");
                youcubeService.enterCard(new YoucubeService.OnEnterCardListener() {
                    @Override
                    public void onApproved() {
                        moveActivity();
                    }
                });
                break;
            case R.id.login_register_button:
                Intent intentSettings = new Intent(this, MainSubActivity.class);
                intentSettings.putExtra(MENU, MENU_ACCOUNT);
                startActivity(intentSettings);
                overridePendingTransition(0, R.anim.fade_out);
                break;
        }
    }


    private void moveActivity() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0, R.anim.fade_out);
        finish();
    }


    @OnClick(R.id.merchant_text_view)
    public void onClickMerchant() {
        Intent intentMerchant = new Intent(this, MainSubActivity.class);
        intentMerchant.putExtra(MENU, MENU_MERCHANT);
        startActivity(intentMerchant);
        overridePendingTransition(0, R.anim.fade_out);
    }
}
