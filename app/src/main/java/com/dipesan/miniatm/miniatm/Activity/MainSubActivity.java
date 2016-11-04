package com.dipesan.miniatm.miniatm.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.utils.AppConstant;

import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;

public class MainSubActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int titleId = getIntent().getIntExtra(MENU, 0);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main_sub_activity));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(AppConstant.title(titleId)));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_sub_activity_frame_layout, AppConstant.fragment(titleId)).commit();
        overridePendingTransition(0, R.anim.fade_out);

    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.fade_out);
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(0, R.anim.fade_out);
        return super.onSupportNavigateUp();
    }
}
