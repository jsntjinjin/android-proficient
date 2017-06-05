package com.fastaoe.framelibrary.skin.config;

/**
 * Created by jinjin on 17/6/5.
 */

public class SkinConfig {

    // skin sp名字
    public static final String SKIN_CONFIG_NAME = "SKIN_CONFIG_NAME";
    // skin 皮肤路径
    public static final String SKIN_PATH = "SKIN_PATH";

    // skin 状态 -> 不需要改变任何东西
    public static final int SKIN_STATE_NO_CHANGE = -1;
    // skin 状态 -> 换肤成功
    public static final int SKIN_STATE_SUCCESS = 1;
    // skin 状态 -> 换肤文件错误
    public static final int SKIN_STATE_FILE_NOEXSIST = -2;
    // skin 状态 -> 皮肤文件错误，不是apk
    public static final int SKIN_STATE_FILE_ERROR = -3;
}
