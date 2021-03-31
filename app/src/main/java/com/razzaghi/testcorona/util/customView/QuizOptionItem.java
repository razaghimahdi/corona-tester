package com.razzaghi.testcorona.util.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.razzaghi.testcorona.R;

public class QuizOptionItem extends LinearLayout {

    public MaterialRadioButton radioButtonOption ;
    public Long optionId=0L;


    public QuizOptionItem(Context context) {
        super(context);
        //init(context);
    }

    public QuizOptionItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //  init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_quiz_option_item,this,true);

        radioButtonOption=view.findViewById(R.id.radioButtonOption);



    }
}
