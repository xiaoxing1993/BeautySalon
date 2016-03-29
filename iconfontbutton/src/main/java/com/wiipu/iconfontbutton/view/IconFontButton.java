package com.wiipu.iconfontbutton.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wiipu.iconfontbutton.R;

/**
 * Created by Ken~Jc on 2016/3/24.
 */
public class IconFontButton extends LinearLayout{

    private TextView textViewOfUp;
    private TextView textViewOfDown;
    private int btnTrueColor;
    private int btnFalseColor;
    private boolean statusFlag=false;

    private LinearLayout linearLayoutOfIconFontButton;

//    监听点击事件
    private OnIconFontButtonClickListener listener;

//    设定监听器
    public void setIconFontButtonClickListener(OnIconFontButtonClickListener listener){
        this.listener=listener;
    }

    //    控件点击接口
    public interface OnIconFontButtonClickListener{
        public void onIconFontButtonClick(View view);
    }

//    为TextView制定矢量图文字，将Typeface传入
    public void setTypeface(Typeface iconfont){
        textViewOfUp.setTypeface(iconfont);
    }

//    设置两种状态的颜色
    public void setStatusChangedColor(int trueColor,int falseColor){
        this.btnTrueColor = getResources().getColor(trueColor);
        this.btnFalseColor = getResources().getColor(falseColor);
    }

//    返回当前状态
    public boolean getChecked(){
        return statusFlag;
    }

//    按照状态设置颜色,仅在普通状态下才可设置颜色
    public void setIconFontButtonStatus(boolean status){
            if (status){
                textViewOfUp.setTextColor(btnTrueColor);
                textViewOfDown.setTextColor(btnTrueColor);
            }else {
                textViewOfUp.setTextColor(btnFalseColor);
                textViewOfDown.setTextColor(btnFalseColor);
            }
        this.statusFlag=status;
    }
//    设置上面文字（IconFont）
    public void setUpText(String str){
        textViewOfUp.setText(str);
    }
//    设置下面文字（IconFont）
    public void setDownText(String str){
        textViewOfDown.setText(str);
    }

    public IconFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.font_button_view, this);

        textViewOfUp=(TextView)findViewById(R.id.tv_up_icon);
        textViewOfDown=(TextView)findViewById(R.id.tv_down_text);
        linearLayoutOfIconFontButton=(LinearLayout)findViewById(R.id.ll_font_button);

        linearLayoutOfIconFontButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
//                    点击回调
                    listener.onIconFontButtonClick(v);
                }
            }
        });

//        获得自定义属性并赋值
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.IconFontButton);
        String upText=typedArray.getString(R.styleable.IconFontButton_upButtonText);
        String downText=typedArray.getString(R.styleable.IconFontButton_downText);
        int colorOrigin=typedArray.getColor(R.styleable.IconFontButton_iconFontTextColor, 0x7e7979);
        float upSize=typedArray.getDimension(R.styleable.IconFontButton_iconFontUpTextSize, 15);
        float downSize=typedArray.getDimension(R.styleable.IconFontButton_iconFontDownTextSize, 15);

//        释放资源
        typedArray.recycle();

        textViewOfUp.setText(upText);
        textViewOfDown.setText(downText);

        textViewOfUp.setTextSize(upSize);
        textViewOfDown.setTextSize(downSize);

        textViewOfUp.setTextColor(colorOrigin);
        textViewOfDown.setTextColor(colorOrigin);
    }
}
