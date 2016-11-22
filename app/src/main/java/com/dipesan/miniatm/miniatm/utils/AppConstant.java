package com.dipesan.miniatm.miniatm.utils;

import android.support.v4.app.Fragment;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.view.About.AboutFragment;
import com.dipesan.miniatm.miniatm.view.Account.AccountFragment;
import com.dipesan.miniatm.miniatm.view.balance.BalanceFragment;
import com.dipesan.miniatm.miniatm.view.function.FunctionFragment;
import com.dipesan.miniatm.miniatm.view.function.FunctionSettelmentFragment;
import com.dipesan.miniatm.miniatm.view.function.FunctionVoidFragment;
import com.dipesan.miniatm.miniatm.view.merchant.MerchantFragment;
import com.dipesan.miniatm.miniatm.view.payment.InternetFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentBpjsFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentCreditCardFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentElectricFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentInsuranceFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentPostPaidPhoneFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentTelevisionFragment;
import com.dipesan.miniatm.miniatm.view.payment.PaymentWaterFragment;
import com.dipesan.miniatm.miniatm.view.purchase.PurchaseElectricTokenFragment;
import com.dipesan.miniatm.miniatm.view.purchase.PurchaseFragment;
import com.dipesan.miniatm.miniatm.view.purchase.PurchasePhoneCreditFragment;
import com.dipesan.miniatm.miniatm.view.settings.SettingsFragment;
import com.dipesan.miniatm.miniatm.view.transfer.FellowBankFragment;
import com.dipesan.miniatm.miniatm.view.transfer.InterBankFragment;
import com.dipesan.miniatm.miniatm.view.transfer.TransferFragment;
import com.dipesan.miniatm.miniatm.view.withdraw.WithdrawFragment;

/**
 * Created by Dani Ramdan on 02/11/2016.
 */

public class AppConstant {
    public static final String DEVICE_AUTO_CONNECT_SETTINGS_KEY = "BT_autoConnect";
    public final static String NFC_ENABLE_DEVICE_SETTINGS_KEY = "NFC_enabled_device";
    public static final String DEVICE_MAC_ADDR_SETTINGS_KEY = "BT_deviceMacAddr";
    public static final String DEVICE_NAME_SETTINGS_KEY = "BT_deviceName";

    public static final int USER_INPUT_FIELD_ID = 0;


    public static final String MENU = "MENU";

//Menu
    public static final int MENU_BALANCE = 1;
    public static final int MENU_PURCHASE = 2;
    public static final int MENU_PAYMENT =3;
    public static final int MENU_TRANSFER =4;
    public static final int MENU_SETTINGS=5;
    public static final int MENU_FUNCTION=6;
    public static final int MENU_ABOUT=7;
    public static final int MENU_ACCOUNT=8;
    public static final int MENU_MERCHANT=9;
    public static final int MENU_WITHDRAW=10;

//SUbMenu

    //balance
    public static final int SUB_MENU_BALANCE = 11;

    //purchase
    public static final int SUB_MENU_PURCHASE_PHONE_CREDIT = 12;
    public static final int SUB_MENU_PURCHASE_ELECTRIC_TOKEN = 13;

    //payment
    public static final int SUB_MENU_PAYMENT_CREDIT_CARD = 14;
    public static final int SUB_MENU_PAYMENT_POST_PAID_PHONE = 15;
    public static final int SUB_MENU_PAYMENT_ELECTRIC = 16;
    public static final int SUB_MENU_PAYMENT_WATER = 17;
    public static final int SUB_MENU_PAYMENT_INSURANCE = 18;
    public static final int SUB_MENU_PAYMENT_TELEVISION = 19;
    public static final int SUB_MENU_PAYMENT_BPJS = 22;
    public static final int SUB_MENU_INTERNET = 25;

    //transfer
    public static final int SUB_MENU_TRANSFER_INTER_BANK = 20;
    public static final int SUB_MENU_TRANSFER_FELLOW_BANK = 21;

    //function
    public static final int SUB_MENU_FUNCTION_VOID = 23;
    public static final int SUB_MENU_FUNCTION_SETTELMENT = 24;

    public static int title (int id){
        switch (id){
            //menu
            case MENU_BALANCE: return R.string.balance;
            case MENU_PURCHASE: return R.string.purchase;
            case MENU_PAYMENT: return R.string.payment;
            case MENU_TRANSFER: return R.string.tranfer;
            case MENU_SETTINGS: return R.string.settings;
            case MENU_FUNCTION: return R.string.function;
            case MENU_ABOUT:return R.string.about;
            case MENU_ACCOUNT:return R.string.account;
            case MENU_MERCHANT:return R.string.register_merchant;
            case MENU_WITHDRAW:return R.string.withdrawfragment;


            //sub menu
            case SUB_MENU_BALANCE : return R.string.balance;

            case SUB_MENU_PURCHASE_PHONE_CREDIT: return R.string.phone_credit;
            case SUB_MENU_PURCHASE_ELECTRIC_TOKEN: return R.string.electric_token;

            case SUB_MENU_PAYMENT_CREDIT_CARD : return R.string.credit_card;
            case SUB_MENU_PAYMENT_POST_PAID_PHONE: return R.string.post_paid_phone;
            case SUB_MENU_PAYMENT_ELECTRIC : return R.string.electric;
            case SUB_MENU_PAYMENT_WATER: return R.string.water;
            case SUB_MENU_PAYMENT_INSURANCE: return R.string.insurance;
            case SUB_MENU_PAYMENT_TELEVISION : return R.string.television;
            case SUB_MENU_PAYMENT_BPJS:return R.string.bpjs;
            case SUB_MENU_INTERNET:return R.string.internet;

            case SUB_MENU_TRANSFER_INTER_BANK:return R.string.interbank;
            case SUB_MENU_TRANSFER_FELLOW_BANK:return R.string.fellow_bank;

            case SUB_MENU_FUNCTION_VOID : return R.string.function_void;
            case SUB_MENU_FUNCTION_SETTELMENT:return R.string.function_settelment;


            default:return R.string.balance;
        }
    }

    public static Fragment fragment(int id){
        switch (id){
            //menu
            case MENU_BALANCE : return BalanceFragment.newInstance();
            case MENU_PURCHASE : return PurchaseFragment.newInstance();
            case MENU_PAYMENT: return PaymentFragment.newInstance();
            case MENU_TRANSFER: return TransferFragment.newInstance();
            case MENU_SETTINGS : return SettingsFragment.newInstance();
            case MENU_FUNCTION: return FunctionFragment.newInstance();
            case MENU_ABOUT:return AboutFragment.newInstance();
            case MENU_ACCOUNT:return AccountFragment.newInstance();
            case MENU_MERCHANT:return MerchantFragment.newIstance();
            case MENU_WITHDRAW:return WithdrawFragment.newInstance();

            //submenu
            //balance
            case SUB_MENU_BALANCE:return BalanceFragment.newInstance();
            //purchase
            case SUB_MENU_PURCHASE_PHONE_CREDIT:return PurchasePhoneCreditFragment.newIstance();
            case SUB_MENU_PURCHASE_ELECTRIC_TOKEN:return PurchaseElectricTokenFragment.newIstance();
            //payment
            case SUB_MENU_PAYMENT_CREDIT_CARD: return PaymentCreditCardFragment.newIstance();
            case SUB_MENU_PAYMENT_POST_PAID_PHONE:return PaymentPostPaidPhoneFragment.newIstance();
            case SUB_MENU_PAYMENT_ELECTRIC : return PaymentElectricFragment.newIstance();
            case SUB_MENU_PAYMENT_WATER : return PaymentWaterFragment.newIstance();
            case SUB_MENU_PAYMENT_INSURANCE: return PaymentInsuranceFragment.newIstance();
            case SUB_MENU_PAYMENT_TELEVISION: return PaymentTelevisionFragment.newIstance();
            case SUB_MENU_PAYMENT_BPJS : return PaymentBpjsFragment.newIstance();
            case SUB_MENU_INTERNET : return InternetFragment.newInstance();

            //transfer
            case SUB_MENU_TRANSFER_FELLOW_BANK:return FellowBankFragment.newInstance();
            case SUB_MENU_TRANSFER_INTER_BANK:return InterBankFragment.newInstance();

            //function
            case SUB_MENU_FUNCTION_VOID:return FunctionVoidFragment.newInstance();
            case SUB_MENU_FUNCTION_SETTELMENT:return FunctionSettelmentFragment.newInstance();

            default: return BalanceFragment.newInstance();

        }

    }

    //merchant
    public static final String NAME_MERCHANT = "Merchant Qubepay.com";
    public static final String ID_MERCHANT = "19283494";
    public static final String NO_REFRENCE = "1234-5678-90";


}
