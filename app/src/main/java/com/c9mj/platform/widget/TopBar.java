package com.c9mj.platform.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.c9mj.platform.R;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class TopBar extends LinearLayout implements View.OnClickListener{

    private Context context;

    private int theme;//0:light 1:dark
    private int wholeRLayoutBackground;
    private int titleTvTextColor;
    private int btnChooseTextColor;
    private int btnNormalTextColor;
    private Drawable leftBtnChooseBackground;
    private Drawable leftBtnNormalBackground;
    private Drawable middleBtnChooseBackground;
    private Drawable middleBtnNormalBackground;
    private Drawable rightBtnChooseBackground;
    private Drawable rightBtnNormalBackground;


    private View v;
    private ImageView leftIv, rightIv;
    private TextView titleTv, rightTv;
    private LinearLayout clickLLayout;
    private RelativeLayout wholeRLayout;
    private Button leftBtn, middleBtn, rightBtn;

    OnTopBarClickListener listener;
    public void setOnTopBarClickListener(OnTopBarClickListener listener) {
        this.listener = listener;
    }
    public interface OnTopBarClickListener {
        void leftIvClick();
        void rightIvClick();
        void leftBtnClick();
        void middleBtnClick();
        void rightBtnClick();
        void rightTvClick();
    }

    public TopBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initView();
    }


    private void initView() {
        v = LayoutInflater.from(context).inflate(R.layout.top_bar_layout , this);
        leftIv = (ImageView) v.findViewById(R.id.left_iv);
        rightIv = (ImageView) v.findViewById(R.id.right_iv);
        rightTv = (TextView) v.findViewById(R.id.right_tv);
        titleTv = (TextView) v.findViewById(R.id.title_tv);
        clickLLayout = (LinearLayout) v.findViewById(R.id.click_layout);
        wholeRLayout = (RelativeLayout) findViewById(R.id.whole_layout);
        leftBtn = (Button) v.findViewById(R.id.left_btn);
        middleBtn = (Button) v.findViewById(R.id.middle_btn);
        rightBtn = (Button) v.findViewById(R.id.right_btn);

        leftIv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        rightIv.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        middleBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_iv:{//左按钮(back)
                listener.leftIvClick();
            }break;

            case R.id.right_iv:{//右按钮
                listener.rightIvClick();
            }break;

            case R.id.right_tv:{//右文本
                listener.rightTvClick();
            }break;

            case R.id.left_btn:{//中间三按钮之left
                listener.leftBtnClick();
                leftBtn.setTextColor(btnChooseTextColor);
                leftBtn.setBackgroundDrawable(leftBtnChooseBackground);
                middleBtn.setTextColor(btnNormalTextColor);
                middleBtn.setBackgroundDrawable(middleBtnNormalBackground);
                rightBtn.setTextColor(btnNormalTextColor);
                rightBtn.setBackgroundDrawable(rightBtnNormalBackground);
            }break;

            case R.id.middle_btn:{//中间三按钮之middle
                listener.middleBtnClick();
                leftBtn.setTextColor(btnNormalTextColor);
                leftBtn.setBackgroundDrawable(leftBtnNormalBackground);
                middleBtn.setTextColor(btnChooseTextColor);
                middleBtn.setBackgroundDrawable(middleBtnChooseBackground);
                rightBtn.setTextColor(btnNormalTextColor);
                rightBtn.setBackgroundDrawable(rightBtnNormalBackground);
            }break;

            case R.id.right_btn:{//中间三按钮之right
                listener.rightBtnClick();
                leftBtn.setTextColor(btnNormalTextColor);
                leftBtn.setBackgroundDrawable(leftBtnNormalBackground);
                middleBtn.setTextColor(btnNormalTextColor);
                middleBtn.setBackgroundDrawable(middleBtnNormalBackground);
                rightBtn.setTextColor(btnChooseTextColor);
                rightBtn.setBackgroundDrawable(rightBtnChooseBackground);
            }break;
        }
    }

    /**
     * 根据cpnfig配置改变TopBar
     * @param config
     */
    public void init(TopBarConfig config){
        if (config.leftIvResId != 0) {
            leftIv.setImageResource(config.leftIvResId);
        }
        leftIv.setVisibility(config.leftIvVisible ? VISIBLE : GONE);

        if (config.rightIvResId != 0) {
            rightIv.setImageResource(config.rightIvResId);
        }
        rightIv.setVisibility(config.rightIvVisible ? VISIBLE : GONE);

        if (config.leftBtnResId != 0) {
            leftBtn.setBackgroundResource(config.leftBtnResId);
        }
        leftBtn.setText(config.leftBtnText);

        if (config.middleBtnResId != 0) {
            middleBtn.setBackgroundResource(config.middleBtnResId);
        }
        middleBtn.setText(config.middleBtnText);
        middleBtn.setVisibility(config.middleBtnVisible ? VISIBLE : GONE);

        if (config.rightBtnResId != 0) {
            rightBtn.setBackgroundResource(config.rightBtnResId);
        }
        rightBtn.setText(config.rightBtnText);

        clickLLayout.setVisibility(config.clickLLayoutVisible ? VISIBLE : GONE);

        if (config.wholeRLayoutResId != 0) {
            wholeRLayout.setBackgroundResource(config.wholeRLayoutResId);
        }

        titleTv.setText(config.titleText);
        titleTv.setVisibility(config.titleTvVisible ? VISIBLE : GONE);

        rightTv.setText(config.rightText);
        rightTv.setVisibility(config.rightTvVisible ? VISIBLE : GONE);

        /***************设置TopBar主题********************/
        //0:light 1:dark
        if (config.theme == 0) {
            setWholeRLayoutBackground(context.getResources().getColor(R.color.colorTopBarBackgroundLight));
            setTitleTvTextColor(context.getResources().getColor(R.color.colorTopBarTextLight));
            setBtnChooseTextColor(context.getResources().getColor(R.color.colorTopBarBtnTextChooseLight));
            setBtnNormalTextColor(context.getResources().getColor(R.color.colorTopBarBtnTextNormalLight));
            setLeftBtnChooseBackground(context.getResources().getDrawable(R.drawable.tab2_left_pre));
            setLeftBtnNormalBackground(context.getResources().getDrawable(R.drawable.tab2_left));
            setMiddleBtnChooseBackground(context.getResources().getDrawable(R.drawable.tab2_middle_pre));
            setMiddleBtnNormalBackground(context.getResources().getDrawable(R.drawable.tab2_middle));
            setRightBtnChooseBackground(context.getResources().getDrawable(R.drawable.tab2_right_pre));
            setRightBtnNormalBackground(context.getResources().getDrawable(R.drawable.tab2_right));
        }else if (config.theme == 1){
            setWholeRLayoutBackground(context.getResources().getColor(R.color.colorTopBarBackgroundDark));
            setTitleTvTextColor(context.getResources().getColor(R.color.colorTopBarTextDark));
            setBtnChooseTextColor(context.getResources().getColor(R.color.colorTopBarBtnTextChooseDark));
            setBtnNormalTextColor(context.getResources().getColor(R.color.colorTopBarBtnTextNormalDark));
            setLeftBtnChooseBackground(context.getResources().getDrawable(R.drawable.tab_left_pre));
            setLeftBtnNormalBackground(context.getResources().getDrawable(R.drawable.tab_left));
            setMiddleBtnChooseBackground(context.getResources().getDrawable(R.drawable.tab_middle_pre));
            setMiddleBtnNormalBackground(context.getResources().getDrawable(R.drawable.tab_middle));
            setRightBtnChooseBackground(context.getResources().getDrawable(R.drawable.tab_right_pre));
            setRightBtnNormalBackground(context.getResources().getDrawable(R.drawable.tab_right));
        }
        wholeRLayout.setBackgroundColor(wholeRLayoutBackground);
        titleTv.setTextColor(titleTvTextColor);
        rightTv.setTextColor(titleTvTextColor);
        //默认选中左按钮 Choose:选中 Normal:未选中
        leftBtn.setTextColor(btnChooseTextColor);
        leftBtn.setBackgroundDrawable(leftBtnChooseBackground);
        middleBtn.setTextColor(btnNormalTextColor);
        middleBtn.setBackgroundDrawable(middleBtnNormalBackground);
        rightBtn.setTextColor(btnNormalTextColor);
        rightBtn.setBackgroundDrawable(rightBtnNormalBackground);
    }

    public void setBtnChooseTextColor(int btnChooseTextColor) {
        this.btnChooseTextColor = btnChooseTextColor;
    }

    public void setBtnNormalTextColor(int btnNormalTextColor) {
        this.btnNormalTextColor = btnNormalTextColor;
    }

    public void setWholeRLayoutBackground(int wholeRLayoutBackground) {
        this.wholeRLayoutBackground = wholeRLayoutBackground;
    }

    public void setTitleTvTextColor(int titleTvTextColor) {
        this.titleTvTextColor = titleTvTextColor;
    }

    public void setLeftBtnChooseBackground(Drawable leftBtnChooseBackground) {
        this.leftBtnChooseBackground = leftBtnChooseBackground;
    }

    public void setLeftBtnNormalBackground(Drawable leftBtnNormalBackground) {
        this.leftBtnNormalBackground = leftBtnNormalBackground;
    }

    public void setMiddleBtnChooseBackground(Drawable middleBtnChooseBackground) {
        this.middleBtnChooseBackground = middleBtnChooseBackground;
    }

    public void setMiddleBtnNormalBackground(Drawable middleBtnNormalBackground) {
        this.middleBtnNormalBackground = middleBtnNormalBackground;
    }

    public void setRightBtnChooseBackground(Drawable rightBtnChooseBackground) {
        this.rightBtnChooseBackground = rightBtnChooseBackground;
    }

    public void setRightBtnNormalBackground(Drawable rightBtnNormalBackground) {
        this.rightBtnNormalBackground = rightBtnNormalBackground;
    }

    public Button getLeftBtn() {
        return leftBtn;
    }

    public Button getMiddleBtn() {
        return middleBtn;
    }

    public Button getRightBtn() {
        return rightBtn;
    }

    public Drawable getLeftBtnChooseBackground() {
        return leftBtnChooseBackground;
    }

    public Drawable getLeftBtnNormalBackground() {
        return leftBtnNormalBackground;
    }

    public Drawable getRightBtnChooseBackground() {
        return rightBtnChooseBackground;
    }

    public Drawable getRightBtnNormalBackground() {
        return rightBtnNormalBackground;
    }

    public int getBtnChooseTextColor() {
        return btnChooseTextColor;
    }

    public int getBtnNormalTextColor() {
        return btnNormalTextColor;
    }
}
