package com.dipesan.miniatm.miniatm.view.transfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipesan.miniatm.miniatm.Activity.MainDetailActivity;
import com.dipesan.miniatm.miniatm.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dipesan.miniatm.miniatm.utils.AppConstant.MENU;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_TRANSFER_FELLOW_BANK;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_TRANSFER_INTER_BANK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment {


    public TransferFragment() {
        // Required empty public constructor
    }

    public static TransferFragment newInstance() {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({ R.id.fragment_transfer_inter_bank, R.id.fragment_transfer_fellow_bank})
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MainDetailActivity.class);
        switch (view.getId()) {
            case R.id.fragment_transfer_inter_bank:
                intent.putExtra(MENU, SUB_MENU_TRANSFER_INTER_BANK);
                break;
            case R.id.fragment_transfer_fellow_bank:
                intent.putExtra(MENU, SUB_MENU_TRANSFER_FELLOW_BANK);
                break;
        }
        startActivity(intent);
    }
}
