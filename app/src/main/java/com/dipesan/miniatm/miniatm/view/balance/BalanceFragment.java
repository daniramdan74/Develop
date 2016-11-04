package com.dipesan.miniatm.miniatm.view.balance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipesan.miniatm.miniatm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {


    public BalanceFragment() {
        // Required empty public constructor
    }
    public static BalanceFragment newInstance(){
        return new BalanceFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

}
