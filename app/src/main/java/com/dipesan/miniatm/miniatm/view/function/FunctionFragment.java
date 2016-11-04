package com.dipesan.miniatm.miniatm.view.function;


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
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_FUNCTION_SETTELMENT;
import static com.dipesan.miniatm.miniatm.utils.AppConstant.SUB_MENU_FUNCTION_VOID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionFragment extends Fragment {


    public FunctionFragment() {
        // Required empty public constructor
    }

    public static FunctionFragment newInstance() {
        FunctionFragment fragment = new FunctionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_function, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.fragment_function_void_button, R.id.fragment_function_settelment_button})
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MainDetailActivity.class);
        switch (view.getId()) {
            case R.id.fragment_function_void_button:
                intent.putExtra(MENU, SUB_MENU_FUNCTION_VOID);
                break;
            case R.id.fragment_function_settelment_button:
                intent.putExtra(MENU, SUB_MENU_FUNCTION_SETTELMENT);
                break;
        }
        startActivity(intent);
    }
}
