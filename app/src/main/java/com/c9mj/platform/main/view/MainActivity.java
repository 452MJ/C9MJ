package com.c9mj.platform.main.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.c9mj.platform.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.SimpleViewPagerDelegate;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    final String[] channels = new String[]{"随便看看", "直播", "我的"};
    private List<String> dataList = Arrays.asList(channels);

    final int[] normalResId = new int[]{
            R.drawable.ic_news_normal,
            R.drawable.ic_live_normal,
            R.drawable.ic_user_normal
    };
    final int[] pressedResId = new int[]{
            R.drawable.ic_news_pressed,
            R.drawable.ic_live_pressed,
            R.drawable.ic_user_pressed
    };

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPager.setAdapter(adapter);

        initIndicator();
    }


    private void initIndicator() {
        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
//        commonNavigator.setFollowTouch(true);
        commonNavigator.setIndicatorOnTop(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataList == null ? 0 : dataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);
                titleView.setContentView(R.layout.item_tab_layout);

                final Button tab_btn = (Button) titleView.findViewById(R.id.tab_btn);
                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {
                        tab_btn.setBackgroundResource(pressedResId[i]);
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
                        tab_btn.setBackgroundResource(normalResId[i]);
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setMaxCircleRadius(UIUtil.dip2px(context, 2.5));
                indicator.setColors(Color.parseColor("#cf449a"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        SimpleViewPagerDelegate.with(magicIndicator, viewPager).delegate();
    }

    private PagerAdapter adapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(container.getContext());
            textView.setText(dataList.get(position));
            textView.setGravity(Gravity.CENTER);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            TextView textView = (TextView) object;
            String text = textView.getText().toString();
            int index = dataList.indexOf(text);
            if (index >= 0) {
                return index;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return dataList.get(position);
        }
    };
}
