package com.dipesan.miniatm.miniatm.view.function;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dipesan.miniatm.miniatm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunctionSettelmentFragment extends Fragment {


    @BindView(R.id.fragfunction_settelment_password_edit_text) EditText fragfunctionSettelmentPasswordEditText;

    public FunctionSettelmentFragment() {
        // Required empty public constructor
    }

    public static FunctionSettelmentFragment newInstance() {
        FunctionSettelmentFragment fragment = new FunctionSettelmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_function_settelment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragfunction_settelment_password_button)
    public void onClick() {
    }
}
