package com.tom.myapplication.dialogmanager;

/**
 * Created by Tomleisen on 2021/3/11.
 * Email : xy162162a@163.com
 * Operation :“窗口”约定规则
 * https://blog.csdn.net/qq_33866343/article/details/108441576?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-1&spm=1001.2101.3001.4242
 */
public interface OnDialogListener {
    /**
     * 展示
     */
    void show();

    /**
     * 关闭
     *
     * @param isCrowdOut 是否被挤出
     */
    void dismiss(boolean isCrowdOut);

    /**
     * 设置“窗口”关闭监听
     */
    void setOnDismissListener(DialogManager.OnDismissListener listener);

    /**
     * 设置“窗口”展示监听
     */
    void setOnShowListener(DialogManager.OnShowListener listener);

    /**
     * 是否满足show的条件（如处于某个tab，不关心时返回true就行）
     */
    boolean isCanShow();
}

