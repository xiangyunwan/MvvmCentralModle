package com.mobile.centaur.widget.versionmanager;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
public interface UpdateListener {
    /**
     * @description 开始升级
     * @param totalSize 安装包体积
     */
    void onStart(long totalSize);

    /**
     * @description 正在下载
     */
    void onLoading(long currentSize, float percent, float speed);

    /**
     * @description 下载完成
     * @param totalSize 安装包体积
     */
    void onLoadingFinish(long totalSize);

    /**
     * @description md5校验成功
     */
    void onMd5Checked(String path);

    /**
     * @description 升级失败
     * @param error
     */
    void onError(String error);
}
