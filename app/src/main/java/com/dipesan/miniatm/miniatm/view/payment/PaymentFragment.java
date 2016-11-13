package com.dipesan.miniatm.miniatm.view.payment;


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
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_INTERNET;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_BPJS;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_CREDIT_CARD;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_ELECTRIC;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_INSURANCE;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_POST_PAID_PHONE;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_TELEVISION;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_PAYMENT_WATER;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {


    @BindView(R.id.payment_activity_credit_card_button) Button paymentActivityCreditCardButton;
    @BindView(R.id.payment_activity_post_paid_phone_button) Button paymentActivityPostPaidPhoneButton;
    @BindView(R.id.payment_activity_electric_button) Button paymentActivityElectricButton;
    @BindView(R.id.payment_activity_water_button) Button paymentActivityWaterButton;
    @BindView(R.id.payment_activity_insurance_button) Button paymentActivityInsuranceButton;
    @BindView(R.id.payment_activity_televesion_button) Button paymentActivityTelevesionButton;
    @BindView(R.id.payment_activity_bpjs_button) Button paymentActivityBpjsButton;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.payment_activity_credit_card_button, R.id.payment_activity_post_paid_phone_button, R.id.payment_activity_electric_button, R.id.payment_activity_water_button, R.id.payment_activity_insurance_button, R.id.payment_activity_televesion_button, R.id.payment_activity_bpjs_button})
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MainDetailActivity.class);
        switch (view.getId()) {
            case R.id.payment_activity_credit_card_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_CREDIT_CARD);
                break;
            case R.id.payment_activity_post_paid_phone_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_POST_PAID_PHONE);
                break;
            case R.id.payment_activity_electric_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_ELECTRIC);
                break;
            case R.id.payment_activity_water_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_WATER);
                break;
            case R.id.payment_activity_insurance_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_INSURANCE);
                break;
            case R.id.payment_activity_televesion_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_TELEVISION);
                break;
            case R.id.payment_activity_bpjs_button:
                intent.putExtra(MENU, SUB_MENU_PAYMENT_BPJS);
                break;
        }
        startActivity(intent);
    }

    @OnClick(R.id.payment_activity_internet_button)
    public void onClickInternet() {
        Intent intent = new Intent(getActivity(), MainDetailActivity.class);
        intent.putExtra(MENU, SUB_MENU_INTERNET);
        startActivity(intent);
    }
}
