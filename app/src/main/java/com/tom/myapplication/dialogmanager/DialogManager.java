package com.tom.myapplication.dialogmanager;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomleisen on 2021/3/11.
 * Email : xy162162a@163.com
 * Operation :弹窗管理（支持设置弹窗优先级）
 */

public class DialogManager {
    private List<DialogParam> mDialogs;
    private static DialogManager mDefaultInstance;

    private DialogManager() {
    }


    /**
     * 获取弹窗管理者
     */
    public static DialogManager getInstance() {
        if (mDefaultInstance == null) {
            synchronized (DialogManager.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new DialogManager();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 添加弹窗
     *
     * @param dialogParam 待添加的弹窗
     */
    public synchronized void add(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getOnDialogListener() != null) {
            if (mDialogs == null) {
                mDialogs = new ArrayList<>();
            }
            dialogParam.getOnDialogListener().setOnShowListener(new OnShowListener() {
                @Override
                public void onShow() {
                    dialogParam.setShowing(true);
                    dialogParam.setPrepareShow(false);
                }
            });

            dialogParam.getOnDialogListener().setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(boolean isCrowdOut) {
                    dialogParam.setShowing(false);
                    if (isCrowdOut) {
                        dialogParam.setPrepareShow(true);
                    } else {
                        mDialogs.remove(dialogParam);
                        showNext();
                    }
                }
            });
            mDialogs.add(dialogParam);
        }
    }

    /**
     * 展示弹窗
     *
     * @param dialogParam 待展示的弹窗
     */
    public synchronized void show(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getOnDialogListener() != null) {
            if (mDialogs == null) {
                if (dialogParam.getOnDialogListener().isCanShow()) {
                    dialogParam.getOnDialogListener().show();
                }
            } else {
                /*判断优先级及是否可展示*/
                maybeShow(dialogParam);
            }
        }
    }

    /**
     * 展示弹窗（优先级最高的Dialog）
     */
    public synchronized void show() {
        DialogParam dialogParam = getMaxPriorityDialog();
        if (dialogParam != null) {
            OnDialogListener OnDialogListener = dialogParam.getOnDialogListener();
            if (OnDialogListener != null && OnDialogListener.isCanShow()) {
                OnDialogListener.show();
            }
        }
    }

    /**
     * 清除弹窗管理者
     */
    public synchronized void clear() {
        if (mDialogs != null) {
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                if (mDialogs.get(i) != null) {
                    mDialogs.get(i).setPrepareShow(false);
                }
            }
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                if (mDialogs.get(i) != null) {
                    OnDialogListener OnDialogListener = mDialogs.get(i).getOnDialogListener();
                    if (OnDialogListener != null) {
                        OnDialogListener.dismiss(false);
                    }
                }
            }
            mDialogs.clear();
        }
    }

    /**
     * 清除弹窗管理者
     *
     * @param dismiss 是否同时dismiss掉弹窗管理者维护的弹窗
     */
    public synchronized void clear(boolean dismiss) {
        if (mDialogs != null) {
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                if (mDialogs.get(i) != null) {
                    mDialogs.get(i).setPrepareShow(false);
                }
            }
            if (dismiss) {
                for (int i = 0, size = mDialogs.size(); i < size; i++) {
                    if (mDialogs.get(i) != null) {
                        OnDialogListener OnDialogListener = mDialogs.get(i).getOnDialogListener();
                        if (OnDialogListener != null) {
                            OnDialogListener.dismiss(false);
                        }
                    }
                }
            }
            mDialogs.clear();
        }
    }

    /**
     * 展示下一个优先级最大的Dialog（非自行调用dismiss而是被优先级高的弹窗show后挤掉）
     */
    private synchronized void showNext() {
        DialogParam dialog = getMaxPriorityDialog();
        if (dialog != null) {
            if (dialog.isPrepareShow() && dialog.getOnDialogListener().isCanShow()) {
                dialog.getOnDialogListener().show();
            }
        }

    }

    /**
     * 展示弹窗（满足条件可展示）
     *
     * @param dialogParam 待展示的弹窗
     */
    private void maybeShow(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getOnDialogListener() != null) {
            DialogParam topShowDialog = getShowingDialog();
            if (topShowDialog == null) {
                if (dialogParam.getOnDialogListener().isCanShow()) {
                    dialogParam.getOnDialogListener().show();
                }
            } else {
                /*获取优先级*/
                int priority = dialogParam.getPriority();
                if (priority >= topShowDialog.getPriority()) {
                    if (dialogParam.getOnDialogListener().isCanShow()) {
                        dialogParam.getOnDialogListener().show();
                        topShowDialog.getOnDialogListener().dismiss(true);
                        /*设置参数支持当前show关闭后自动show带该参数的优先级最高的弹窗*/
                        topShowDialog.setPrepareShow(true);
                    }
                }
            }
        }
    }

    /**
     * 获取当前栈中优先级最高的Dialog（优先级相同则返回后添加的弹窗）
     */
    private synchronized DialogParam getMaxPriorityDialog() {
        if (mDialogs != null) {
            int maxPriority = -1;
            int position = -1;
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                DialogParam dialog = mDialogs.get(i);
                if (i == 0) {
                    position = 0;
                    maxPriority = dialog.getPriority();
                } else {
                    if (dialog.getPriority() >= maxPriority) {
                        position = i;
                        maxPriority = dialog.getPriority();
                    }
                }
            }
            if (position != -1) {
                return mDialogs.get(position);
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取当前处于show状态的弹窗
     */
    private synchronized DialogParam getShowingDialog() {
        if (mDialogs != null) {
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                DialogParam dialogParam = mDialogs.get(i);
                if (dialogParam != null && dialogParam.getOnDialogListener() != null && dialogParam.isShowing()) {
                    return dialogParam;
                }
            }
        }
        return null;
    }

    /**
     * “窗口”展示监听
     */
    public interface OnShowListener {
        void onShow();
    }

    /**
     * “窗口”关闭监听
     */
    public interface OnDismissListener {
        /**
         * @param isCrowdOut 是否被挤出
         */
        void onDismiss(boolean isCrowdOut);
    }



}

