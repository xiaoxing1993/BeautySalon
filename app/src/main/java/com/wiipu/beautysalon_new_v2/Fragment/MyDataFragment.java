package com.wiipu.beautysalon_new_v2.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wiipu.beautysalon_new_v2.network.NetworkManager;
import com.wiipu.beautysalon_new_v2.utils.DialogUtils;
import com.wiipu.beautysalon_new_v2.R;

/**
 * A simple {@link Fragment} subclass.
 * 个人中心的fragment
 */
public class MyDataFragment extends Fragment {


    private View m_fragment_my_data;
    private Button btn_ass_exit;
    private TextView text_career;

    public MyDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        m_fragment_my_data=inflater.inflate(R.layout.fragment_my_data, container, false);
        // Inflate the layout for this fragment
        initView();
        return m_fragment_my_data;
    }

    private void initView() {
        btn_ass_exit=(Button)m_fragment_my_data.findViewById(R.id.btn_exit_my_data);
        text_career=(TextView)m_fragment_my_data.findViewById(R.id.text_career_my_data);

        btn_ass_exit.setOnClickListener(login_out);
    }

    private View.OnClickListener login_out=new View.OnClickListener(){

        @Override
        public void onClick(View v){
            DialogUtils.showKnockOffDialog(getContext());
        }
    };

    public void onStart(){
        super.onStart();
    }

}
