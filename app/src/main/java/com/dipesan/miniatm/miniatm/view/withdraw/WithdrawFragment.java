package com.dipesan.miniatm.miniatm.view.withdraw;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.dipesan.miniatm.miniatm.R;
import com.dipesan.miniatm.miniatm.utils.MoneyTextWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFragment extends Fragment {

    @BindView(R.id.withdraw_fragment_amount_edit_text) EditText withdrawFragmentAmountEditText;
    @BindView(R.id.withdraw_fragment_amount_others_edit_text) EditText withdrawFragmentAmountOthersEditText;
    @BindView(R.id.withdraw_fragment_process_button) Button withdrawFragmentProcessButton;
    @BindView(R.id.withdraw_fragment_amount_others_text_input_layout) TextInputLayout withdrawFragmentAmountOthersTextInputLayout;
    @BindView(R.id.withdraw_fragment_amount_others_text_relative_layout) RelativeLayout withdrawFragmentAmountOthersTextRelativeLayout;
    @BindView(R.id.withdraw_fragment_amount_text_input_layout) TextInputLayout withdrawFragmentAmountTextInputLayout;
    private int whichItemsNominal = 0;

    public WithdrawFragment() {
        // Required empty public constructor
    }

    public static WithdrawFragment newInstance() {
        WithdrawFragment fragment = new WithdrawFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        ButterKnife.bind(this, view);
        withdrawFragmentAmountOthersTextInputLayout.setVisibility(View.GONE);
        withdrawFragmentAmountEditText.setInputType(InputType.TYPE_NULL);
        withdrawFragmentAmountOthersEditText.addTextChangedListener(new MoneyTextWatcher(withdrawFragmentAmountOthersEditText));
        visibleKeyboardAmount();
        return view;
    }

    private void visibleKeyboardAmount() {
        withdrawFragmentAmountEditText.setCursorVisible(false);
        withdrawFragmentAmountEditText.setFocusableInTouchMode(false);
        withdrawFragmentAmountEditText.setFocusable(false);
    }


    @OnClick({R.id.withdraw_fragment_amount_edit_text, R.id.withdraw_fragment_process_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_fragment_amount_edit_text:
                hideKeyboard(getView());
                showNominal();
                break;
            case R.id.withdraw_fragment_process_button:
                showProcess();
                break;
        }
    }

    private void showProcess() {
        if (withdrawFragmentAmountEditText.getText().toString().isEmpty()) {
            withdrawFragmentAmountTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            withdrawFragmentAmountTextInputLayout.setErrorEnabled(false);
        }
        if (withdrawFragmentAmountEditText.getText().toString().equals("Lainnya")&&
                withdrawFragmentAmountOthersEditText.getText().toString().isEmpty()){
            withdrawFragmentAmountOthersTextInputLayout.setError("Tidak Boleh Kosong");
        }else {
            withdrawFragmentAmountOthersTextInputLayout.setErrorEnabled(false);
        }
        if (withdrawFragmentAmountEditText.getText().toString().equals("Lainnya")&&
                withdrawFragmentAmountOthersEditText.getText().toString().length()>0) {
            withdrawFragmentAmountEditText.setEnabled(false);
            withdrawFragmentAmountOthersEditText.setEnabled(false);
        }else {
            withdrawFragmentAmountEditText.setEnabled(true);
            withdrawFragmentAmountOthersEditText.setEnabled(true);
        }
        if (withdrawFragmentAmountEditText.getText().toString().length()>0){
            withdrawFragmentAmountEditText.setEnabled(false);
        }else {
            withdrawFragmentAmountEditText.setEnabled(true);
        }
    }

    private void showNominal() {
        List<String> listItems = new ArrayList<String>();
        listItems.add("50,000");
        listItems.add("100,000");
        listItems.add("200,000");
        listItems.add("300,000");
        listItems.add("500,000");
        listItems.add("100,000");
        listItems.add("Lainnya");
        final CharSequence[] itemsNominal = listItems.toArray(new CharSequence[listItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(itemsNominal, whichItemsNominal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                whichItemsNominal = which;
                if (itemsNominal[which] == "Lainnya") {
                    withdrawFragmentAmountOthersTextInputLayout.setVisibility(View.VISIBLE);
                }
                else {
                    withdrawFragmentAmountOthersTextInputLayout.setVisibility(View.GONE);
                    withdrawFragmentAmountOthersEditText.setText(null);
                }
                withdrawFragmentAmountEditText.setText(itemsNominal[which]);
                dialogInterface.dismiss();
            }
        });
        builder.setTitle(R.string.phonecreditAmount);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
