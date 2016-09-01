package com.c9mj.platform.util.retrofit.bean;

/**
 * Created by Administrator on 2016/8/24.
 */
public class LoginBean{

    /**
     * code : 0
     * message : 成功
     * token_code : 415cd6f8dfc26f4ead46755fd6c8423e
     * redirect_url : http://localhost
     */

    int code;
    String message;
    String token_code;
    String redirect_url;

    public LoginBean(int code, String message, String token_code, String redirect_url) {
        this.code = code;
        this.message = message;
        this.token_code = token_code;
        this.redirect_url = redirect_url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken_code() {
        return token_code;
    }

    public void setToken_code(String token_code) {
        this.token_code = token_code;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }
}
