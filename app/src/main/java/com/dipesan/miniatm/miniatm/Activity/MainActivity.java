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
import com.youTransactor.uCube.LogManager;
import com.youTransactor.uCube.mdm.MDMManager;
import com.youTransactor.uCube.rpc.RPCManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_ABOUT;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_BALANCE;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_PAYMENT;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_PURCHASE;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_SETTINGS;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU_TRANSFER;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBluetoothConnection();
    }

    @OnClick({R.id.main_activity_logo, R.id.main_activity_balance_button, R.id.main_activity_purchase_button, R.id.main_activity_payment_button, R.id.main_activity_transfer_button})
    public void onClick(View view) {
        Intent intent = new Intent(this, MainSubActivity.class);
        switch (view.getId()) {
            case R.id.main_activity_logo:
                intent.putExtra(MENU, MENU_ABOUT);
                break;
            case R.id.main_activity_balance_button:
                intent.putExtra(MENU, MENU_BALANCE);
                overridePendingTransition(0, R.anim.fade_out);

                break;
            case R.id.main_activity_purchase_button:
                intent.putExtra(MENU, MENU_PURCHASE);
                overridePendingTransition(0, R.anim.fade_out);
                break;
            case R.id.main_activity_payment_button:
                intent.putExtra(MENU, MENU_PAYMENT);
                overridePendingTransition(0, R.anim.fade_out);
                break;
            case R.id.main_activity_transfer_button:
                intent.putExtra(MENU, MENU_TRANSFER);
                overridePendingTransition(0, R.anim.fade_out);
                break;
        }
        startActivity(intent);
    }



    @OnClick(R.id.main_activity_settings_button)
    public void onClickSettings() {
        Intent intentSettings = new Intent(this, MainSubActivity.class);
        intentSettings.putExtra(MENU, MENU_SETTINGS);
        startActivity(intentSettings);
        overridePendingTransition(0, R.anim.fade_out);
    }

//    @OnClick(R.id.main_activity_account_button)
//    public void onClickAccount() {
//        Intent intentSettings = new Intent(this, MainSubActivity.class);
//        intentSettings.putExtra(MENU, MENU_ACCOUNT);
//        startActivity(intentSettings);
//        overridePendingTransition(0, R.anim.fade_out);
//    }


    //function button
//    @OnClick(R.id.main_activity_function_button)
//    public void onClickFunction() {
//        Intent intentSettings = new Intent(this, MainSubActivity.class);
//        intentSettings.putExtra(MENU,MENU_FUNCTION);
//        startActivity(intentSettings);
//        overridePendingTransition(0, R.anim.fade_out);
//    }



    private void initBluetoothConnection() {
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getBaseContext().registerReceiver(BluetoothConnexionManager.getInstance(), filter);
        LogManager.initialize(this);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        BluetoothConnexionManager.getInstance().initialize(settings);
        MDMManager.getInstance().initialize(this);
        RPCManager.getInstance().setConnexionManager(BluetoothConnexionManager.getInstance());
    }

}
