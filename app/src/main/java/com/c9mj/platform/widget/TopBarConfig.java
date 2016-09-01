package com.c9mj.platform.widget;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class TopBarConfig {

    int leftIvResId = 0;
    boolean leftIvVisible = false;

    int rightIvResId = 0;
    boolean rightIvVisible = false;

    int leftBtnResId = 0;
    String leftBtnText = "";

    int middleBtnResId = 0;
    boolean middleBtnVisible = false;
    String middleBtnText = "";

    int rightBtnResId = 0;
    String rightBtnText = "";

    boolean clickLLayoutVisible = false;

    int wholeRLayoutResId = 0;

    String titleText = "";
    boolean titleTvVisible = false;

    String rightText = "";
    boolean rightTvVisible = false;

    int theme = 0;//0:light 1:dark

    public static class Buider{

        int leftIvResId = 0;
        boolean leftIvVisible = false;

        int rightIvResId = 0;
        boolean rightIvVisible = false;

        int leftBtnResId = 0;
        String leftBtnText = "";

        int middleBtnResId = 0;
        boolean middleBtnVisible = false;
        String middleBtnText = "";

        int rightBtnResId = 0;
        String rightBtnText = "";

        boolean clickLLayoutVisible = false;

        String titleText = "";
        boolean titleTvVisible = false;

        String rightText = "";
        boolean rightTvVisible = false;

        int theme = 1;//0:light 1:dark

        /******************左边ImageView*********************/
        public Buider setLeftImageViewVisible(boolean isVisible){
            leftIvVisible = isVisible;
            return this;
        }
        public Buider setLeftImageViewResource(int resId){
            leftIvResId = resId;
            return this;
        }

        /******************右边ImageView*********************/
        public Buider setRightImageViewVisible(boolean isVisible){
            rightIvVisible = isVisible;
            return this;
        }
        public Buider setRightImageViewResource(int resId){
            rightIvResId = resId;
            return this;
        }

        /******************左Button*********************/
        public Buider setLeftButtonResource(int resId){
            leftBtnResId = resId;
            return this;
        }
        public Buider setLeftButtonText(String text){
            leftBtnText = text;
            return this;
        }

        /******************中间Button*********************/
        public Buider setMiddleButtonResource(int resId){
            middleBtnResId = resId;
            return this;
        }
        public Buider setMiddleButtonVisible(boolean isVisible){
            middleBtnVisible = isVisible;
            return this;
        }
        public Buider setMiddleButtonText(String text){
            middleBtnText = text;
            return this;
        }


        /******************右Button*********************/
        public Buider setRightButtonResource(int resId){
            rightBtnResId = resId;
            return this;
        }
        public Buider setRightButtonText(String text){
            rightBtnText = text;
            return this;
        }

        /******************Click Layout*********************/
        public Buider setClickLLayoutVisible(boolean isVisible){
            clickLLayoutVisible = isVisible;
            return this;
        }


        /******************右TextView*********************/
        public Buider setRightTextViewText(String text){
            rightText = text;
            return this;
        }
        public Buider setRightTextViewVisible(boolean isVisible){
            rightTvVisible = isVisible;
            return this;
        }

        /******************标题Title TextView*********************/
        public Buider setTitleTextViewText(String text){
            titleText = text;
            return this;
        }
        public Buider setTitleTextViewVisible(boolean isVisible){
            titleTvVisible = isVisible;
            return this;
        }

        /******************TopBar主题*********************/
        public Buider setTheme(int theme){
            this.theme = theme;//0:light 1:dark
            return this;
        }

        private void applyConfig(TopBarConfig config) {

            config.leftIvResId = this.leftIvResId;
            config.leftIvVisible = this.leftIvVisible;

            config.rightIvResId = this.rightIvResId;
            config.rightIvVisible = this.rightIvVisible;

            config.leftBtnResId = this.leftBtnResId;
            config.leftBtnText = this.leftBtnText;

            config.middleBtnResId = this.middleBtnResId;
            config.middleBtnText = this.middleBtnText;
            config.middleBtnVisible = this.middleBtnVisible;

            config.rightBtnResId = this.rightBtnResId;
            config.rightBtnText = this.rightBtnText;

            config.clickLLayoutVisible = this.clickLLayoutVisible;

            config.titleText = this.titleText;
            config.titleTvVisible = this.titleTvVisible;

            config.rightText = this.rightText;
            config.rightTvVisible = this.rightTvVisible;

            config.theme = this.theme;
        }

        public TopBarConfig create(){
            TopBarConfig config = new TopBarConfig();
            applyConfig(config);
            return config;
        }


    }


}
