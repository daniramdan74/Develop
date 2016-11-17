package com.dipesan.miniatm.miniatm.Activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.services.BluetoothConnexionManager;
import com.dipesan.miniatm.miniatm.services.YoucubeService;
import com.youTransactor.uCube.LogManager;
import com.youTransactor.uCube.mdm.MDMManager;
import com.youTransactor.uCube.rpc.RPCManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.R.id.login_logo_image_view;
import static com.dipesan.miniatm.miniatm.R.id.settings_text_view;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_ACCOUNT;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_MERCHANT;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_SETTINGS;

public class LoginActivity extends AppCompatActivity {
    Locale myLocale;
    @BindView(R.id.settings_text_view) TextView settingsTextView;
    @BindView(R.id.merchant_text_view) TextView merchantTextView;
    @BindView(R.id.login_logo_image_view) ImageView loginLogoImageView;
    @BindView(R.id.login_indonesia_image_button) ImageButton loginIndonesiaImageButton;
    @BindView(R.id.login_english_image_button) ImageButton loginEnglishImageButton;
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
        merchantTextView.setVisibility(View.INVISIBLE);
        loginLogoImageView.setEnabled(false);
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

    @OnClick({login_logo_image_view, R.id.login_button, R.id.login_register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case login_logo_image_view:
                Intent intentMainActivity = new Intent(this, MainActivity.class);
                startActivity(intentMainActivity);
                overridePendingTransition(0, R.anim.fade_out);
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
    }


    @OnClick(R.id.merchant_text_view)
    public void onClickMerchant() {
        Intent intentMerchant = new Intent(this, MainSubActivity.class);
        intentMerchant.putExtra(MENU, MENU_MERCHANT);
        startActivity(intentMerchant);
        overridePendingTransition(0, R.anim.fade_out);
    }

    @OnClick(settings_text_view)
    public void onClickSettings() {
        Intent intentMerchant = new Intent(this, MainDetailActivity.class);
        intentMerchant.putExtra(MENU, MENU_SETTINGS);
        startActivity(intentMerchant);
        overridePendingTransition(0, R.anim.fade_out);
    }

    @OnClick({R.id.login_indonesia_image_button, R.id.login_english_image_button})
    public void onClickLanguage(View view) {
        switch (view.getId()) {
            case R.id.login_indonesia_image_button:
                Toast.makeText(this, "Bahasa Indonesia", Toast.LENGTH_SHORT).show();
                setLocale("in");
                break;
            case R.id.login_english_image_button:
                Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
                setLocale("en");
                break;
        }
    }


    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, LoginActivity.class);
        startActivity(refresh);
    }

}
