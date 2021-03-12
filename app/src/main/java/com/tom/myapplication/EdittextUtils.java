package com.tom.myapplication;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Tomleisen on 2021/3/10.
 * Email : xy162162a@163.com
 * Operation :
 */
public class EdittextUtils {


    /**
     * 禁止输入框复制粘贴菜单
     */
    public static void disableCopyAndPaste(final EditText editText) {
        try {
            if (editText == null) {
                return ;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            editText.setLongClickable(false);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // setInsertionDisabled when user touches the view
                        setInsertionDisabled(editText);
                    }

                    return false;
                }
            });
            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * @param editText 控制的输入框
     * @param num      保留几位小数
     */
    public static void format(final EditText editText, final int num) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    return;
                }
                String price = editText.getText().toString();
                if(price.equals("0")){
                    return;
                }
                if (!TextUtils.isEmpty(price)) {
                    if (price.startsWith(".")) {
                        editText.setText("");
                    } else {
                        String content = s == null ? null : s.toString();
                        int size = content.length();
                        //删除两个重复点
                        if (size >= 2 && content.startsWith("0") && content.substring(1, size).contains(".")) { //判断之前有没有输入过.
                            int dex = content.indexOf(".");
                            if(size - dex > 1){
                                if(String.valueOf(content.charAt(dex+1)).equals(".")){
                                    s.delete(dex+1, size);//删除重复输入的.
                                    return;
                                }
                            }
                        }
                        //删除重复0
                        if (Float.parseFloat(price) > 0) {
                            if (size >= 2 && content.startsWith("0") && !String.valueOf(content.charAt(1)).equals("0") && !String.valueOf(content.charAt(1)).equals(".")) { //判断之前有没有输入过0
                                s.delete(0, 1);//删除重复输入的0
                            }
                        } else if (Float.parseFloat(price) == 0) {
                            if (size >= 2 && content.startsWith("0") && String.valueOf(content.charAt(1)).equals("0")) { //判断之前有没有输入过0
                                s.delete(size - 1, size);//删除重复输入的0
                            }
                        }
                        //删除多余数字
                        if (size >= 2 && content.contains(".")) { //判断之前有没有输入过.
                            int dex = content.indexOf(".");
                            if(size - dex - 1 > num){
                                s.delete(size - 1, size);
                                return;
                            }

                        }
                    }
                }
            }
        });
    }


    /**
     * 设置EditText为价钱输入模式
     *
     * @param editText 相应的EditText
     * @param digits   限制的小数位数
     */
    public static void setPriceMode(final EditText editText, final int digits) {
//        设置输入类型为小数数字，允许十进制小数点提供分数值。
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        给EditText设置文本变动监听事件
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                如果文本包括"."，删除“.”后面超过2位后的数据
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + digits + 1);
                        editText.setText(s);
                        editText.setSelection(s.length()); //光标移到最后
                    }
                }
//                未输入数字的情况下输入小数点时，个位数字自动补零
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
//                如果文本以"0"开头并且第二个字符不是"."，不允许继续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }


}
