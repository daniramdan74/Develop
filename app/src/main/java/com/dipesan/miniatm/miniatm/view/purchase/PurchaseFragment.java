package com.dipesan.miniatm.miniatm.view.purchase;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dipesan.miniatm.miniatm.Activity.MainDetailActivity;
import com.dipesan.miniatm.miniatm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PURCHASE_ELECTRIC_TOKEN;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PURCHASE_PHONE_CREDIT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFragment extends Fragment {


    @BindView(R.id.balance_activity_phone_credit_button) Button balanceActivityPhoneCreditButton;
    @BindView(R.id.balance_activity_electric_token_button) Button balanceActivityElectricTokenButton;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    public static PurchaseFragment newInstance() {
        PurchaseFragment fragment = new PurchaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.balance_activity_phone_credit_button, R.id.balance_activity_electric_token_button})
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MainDetailActivity.class);
        switch (view.getId()) {
            case R.id.balance_activity_phone_credit_button:
                intent.putExtra(MENU, SUB_MENU_PURCHASE_PHONE_CREDIT);
                break;
            case R.id.balance_activity_electric_token_button:
                intent.putExtra(MENU, SUB_MENU_PURCHASE_ELECTRIC_TOKEN);
                break;
        }
        startActivity(intent);
    }
}
