package com.dipesan.miniatm.miniatm.view.transfer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipesan.miniatm.miniatm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterBankFragment extends Fragment {


    public InterBankFragment() {
        // Required empty public constructor
    }

    public static InterBankFragment newInstance() {
        InterBankFragment fragment = new InterBankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inter_bank, container, false);
    }

}
