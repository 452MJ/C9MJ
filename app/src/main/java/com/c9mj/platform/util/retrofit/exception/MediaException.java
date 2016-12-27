package com.c9mj.platform.util.retrofit.exception;

import com.pili.pldroid.player.PLMediaPlayer;

/**
 * author: LMJ
 * date: 2016/9/1
 * 统一处理网络异常errorCode
 */
public class MediaException extends RuntimeException {

    public MediaException(int code) {
        this(getErrorMessage(code));
    }

    public MediaException(String msg) {
        super(msg);
    }

    private static String getErrorMessage(int code) {
        String msg;
        switch (code) {
            case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                msg = "Invalid URL !";
                break;
            case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                msg = "404 resource not found !";
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                msg = "Connection refused !";
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                msg = "Connection timeout !";
                break;
            case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                msg = "Empty playlist !";
                break;
            case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                msg = "Stream disconnected !";
                break;
            case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                msg = "Network IO Error !";
                break;
            case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                msg = "Unauthorized Error !";
                break;
            case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                msg = "Prepare timeout !";
                break;
            case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                msg = "Read frame timeout !";
                break;
            case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
            default:
                msg = "unknown error !";
                break;
        }
        return msg;
    }

}
