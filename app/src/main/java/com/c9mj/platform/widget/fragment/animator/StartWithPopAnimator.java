package com.c9mj.platform.widget.fragment.animator;

import android.os.Parcel;
import android.os.Parcelable;

import com.c9mj.platform.R;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * author: liminjie
 * date: 2017/3/1
 * desc: startWithPop的过场动画
 */

public class StartWithPopAnimator extends FragmentAnimator implements Parcelable {

    public StartWithPopAnimator() {
        enter = R.anim.fragment_enter;
        exit = R.anim.fragment_exit;
        popEnter = R.anim.fragment_pop_enter;
        popExit = R.anim.fragment_pop_exit;
    }

    protected StartWithPopAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StartWithPopAnimator> CREATOR = new Creator<StartWithPopAnimator>() {
        @Override
        public StartWithPopAnimator createFromParcel(Parcel in) {
            return new StartWithPopAnimator(in);
        }

        @Override
        public StartWithPopAnimator[] newArray(int size) {
            return new StartWithPopAnimator[size];
        }
    };
}
