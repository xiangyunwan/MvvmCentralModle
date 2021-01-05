package com.mobile.centaur.network;

/**
 * created by mengbenming on 2018/9/15
 *
 * @class describe
 */
public class ApiException extends RuntimeException {

    private int errCode = 0;
    private boolean isPost;

    public ApiException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        if (errCode == 15) {
//            EventBus.getDefault().post(new ExceptionEventBean(errCode));
//            EventBus.getDefault().post(new ExceptionLogoutEventBean(errCode));
        }
    }

    public ApiException(BaseResponse baseResponse) {

    }

    public ApiException(int errCode, String msg, boolean isPost) {
        super(msg);
        this.errCode = errCode;
        this.isPost = isPost;
    }

    public int getErrCode() {
        return errCode;
    }

    public boolean isPost() {
        return isPost;
    }

}
