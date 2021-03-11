package com.tom.myapplication.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;

import com.tom.myapplication.dialogmanager.DialogManager;
import com.tom.myapplication.dialogmanager.OnDialogListener;

import io.reactivex.annotations.NonNull;

/**
 * Created by Tomleisen on 2021/3/11.
 * Email : xy162162a@163.com
 * Operation :
 */
public class TestDialog extends AlertDialog implements OnDialogListener {

    /**
     * 是否被挤出（每个实现DialogManager.Dialog的窗口类都需要新建该变量）
     */
    private boolean isCrowdOut;

    public TestDialog(Context context) {
        super(context);
    }
//
//    protected TestDialog(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
//    }
//
//    protected TestDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss(boolean isCrowdOut) {
        /*isCrowdOut在super.dismiss()之前赋值*/
        this.isCrowdOut = isCrowdOut;
        super.dismiss();
    }

    @Override
    public void setOnDismissListener(DialogManager.OnDismissListener listener) {
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss(isCrowdOut);
            }
        });
    }

    @Override
    public void setOnShowListener(DialogManager.OnShowListener listener) {
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                listener.onShow();
            }
        });
    }

    /**
     * 每个实现DialogManager.Dialog的窗口类都需要实现该
     * 方法告诉DialogManager是否可展示此窗口（比如有些窗
     * 口只在页面的某个tab下才能展示）
     */
    @Override
    public boolean isCanShow() {
        return true;
    }

}
