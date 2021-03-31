package com.razzaghi.testcorona.util.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.razzaghi.testcorona.R;

public class AnswerItem extends LinearLayout {

    public TextView txtQuestion ;
    public TextView txtFactor ;
    public LinearLayout linearOptions ;
    public AnswerOptionItem answerOptionItem;


    public AnswerItem(Context context) {
        super(context);
        //init(context);
    }

    public AnswerItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //  init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_answer_item,this,true);

        txtFactor=view.findViewById(R.id.txtFactor);
        txtQuestion=view.findViewById(R.id.txtQuestion);
        linearOptions=view.findViewById(R.id.linearOptions);
        answerOptionItem =new AnswerOptionItem(context);

    }
}
