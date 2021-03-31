package com.razzaghi.testcorona.util.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.razzaghi.testcorona.R;

public class AnswerOptionItem extends LinearLayout {

    public TextView txtAnswerOption ;
    public LinearLayout linearAnswerOption ;
    public Long optionId=0L;


    public AnswerOptionItem(Context context) {
        super(context);
        //init(context);
    }

    public AnswerOptionItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //  init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_answer_option_item,this,true);

        txtAnswerOption=view.findViewById(R.id.txtAnswerOption);
        linearAnswerOption=view.findViewById(R.id.linearAnswerOption);



    }
}
