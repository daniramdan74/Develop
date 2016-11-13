package com.dipesan.miniatm.miniatm.view.merchant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dipesan.miniatm.miniatm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantFragment extends Fragment {


    public MerchantFragment() {
        // Required empty public constructor
    }

    public static MerchantFragment newIstance() {
        return new MerchantFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_merchant, container, false);
    }

}
