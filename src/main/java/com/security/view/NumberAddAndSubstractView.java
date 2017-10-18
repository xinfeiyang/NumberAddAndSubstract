package com.security.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义的数字增加/减少的View;
 */
public class NumberAddAndSubstractView extends LinearLayout implements View.OnClickListener {

    private Button btnSub;
    private TextView tvValue;
    private Button btnAdd;
    private Integer minValue;
    private Integer maxValue;
    private Integer value;
    private Drawable substractBackground;
    private Drawable addBackground;
    private Drawable addAndSubBackground;

    public NumberAddAndSubstractView(Context context) {
        this(context, null);
    }

    public NumberAddAndSubstractView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddAndSubstractView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_number_add_sub, this);
        btnSub = (Button) findViewById(R.id.btn_sub);
        tvValue = (TextView) findViewById(R.id.tv_value);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);

        //获取自定义属性的值;
        if(attrs!=null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberAddAndSubstractView);
            minValue = array.getInteger(R.styleable.NumberAddAndSubstractView_minValue,1);
            maxValue = array.getInteger(R.styleable.NumberAddAndSubstractView_maxValue,10);
            value = array.getInteger(R.styleable.NumberAddAndSubstractView_value,1);
            substractBackground = array.getDrawable(R.styleable.NumberAddAndSubstractView_numberSubstractBtnBackground);
            addBackground = array.getDrawable(R.styleable.NumberAddAndSubstractView_numberAddBtnBackground);
            addAndSubBackground = array.getDrawable(R.styleable.NumberAddAndSubstractView_numberAddAndSubstracBackground);
            array.recycle();
            if(value>=1){
                setValue(value);
            }
            if(substractBackground!=null){
                btnSub.setBackgroundDrawable(substractBackground);
            }

            if(addBackground!=null){
                btnSub.setBackgroundDrawable(addBackground);
            }

            if(addAndSubBackground!=null){
                this.setBackgroundDrawable(addAndSubBackground);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_add) {
            addNumber();
            if (listener != null) {
                listener.onNumberAdd(v, value);
            }
        } else if (i == R.id.btn_sub) {
            subNumber();
            if (listener != null) {
                listener.onNumberSubstract(v, value);
            }
        }
    }

    /**
     * 增加数字;
     */
    private void addNumber() {
        if(value<maxValue){
            value+=1;
        }
        setValue(value);
    }

    /**
     * 减少数字；
     */
    private void subNumber(){
        if(value>minValue){
            value-=1;
        }
        setValue(value);
    }


    //自定义接口回调;

    private OnNumberAddAndSubstractListener listener;

    public void setOnNumberAddAndSubstractListener(OnNumberAddAndSubstractListener listener){
        this.listener=listener;
    }

    /**
     * 定义接口;
     */
    public interface OnNumberAddAndSubstractListener{

        /**
         * 数字增加的时候回调;
         * @param view :当前触发的View;
         * @param value :当前的数字;
         */
        public void onNumberAdd(View view, Integer value);


        /**
         * 数字减少的时候回调;
         * @param view :当前触发的View;
         * @param value :当前的数字;
         */
        public void onNumberSubstract(View view, Integer value);
    }



    //get、set
    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getValue() {
        String v=tvValue.getText().toString().trim();
        if(!TextUtils.isEmpty(v)){
            value=Integer.valueOf(v);
        }
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        tvValue.setText(value+"");
    }

}
