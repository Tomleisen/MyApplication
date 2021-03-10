package com.tom.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvMoney, tvMoneyCode, tvMoneyCode2;//余额，币种代码
    private EditText editText;
    private LinearLayout lyScan;

    int[] buttons; // 数字按钮数组
    float result;
    float result0 = 0.00f;
    float result1;

    ImageButton buttonDelete, buttonClean; // 按钮对象声明
    Button buttonJia;
    //    Button buttonJian;
//    Button buttonCheng;
//    Button buttonChu;
    Button buttonDengyu;
    Button buttonPoint;//点

    String str1; // 旧输入的值
    String str2; // 新输入的值

    int flag = 0; // 计算标志位,0第一次输入；1加；2减；3乘；4除；5等于
    Button temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        initView();
        addEvents();
    }


    private void initView() {
        tvMoney = findViewById(R.id.tvMoney);
        tvMoneyCode = findViewById(R.id.tvMoneyCode);
        tvMoneyCode2 = findViewById(R.id.tvMoneyCode2);
        editText = findViewById(R.id.editText);
        lyScan = findViewById(R.id.lyScan);

        str1 = String.valueOf(editText.getText());
        str2 = ""; // 初始化运算输入数值

        buttonDelete = findViewById(R.id.ButtonDelete); // 获得计算按钮的按钮对象
        buttonClean = findViewById(R.id.ButtonClean); // 获得计算按钮的按钮对象
        buttonJia = findViewById(R.id.ButtonJia);
//        buttonJian = findViewById(R.id.ButtonJian);
//        buttonCheng = findViewById(R.id.ButtonCheng);
//        buttonChu = findViewById(R.id.ButtonChu);
        buttonDengyu = findViewById(R.id.ButtonDengyu);
        buttonPoint = findViewById(R.id.ButtonPoint);

        buttons = new int[]{ // 记录数值按钮的id
                R.id.Button00, R.id.Button01, R.id.Button02, R.id.Button03,
                R.id.Button04, R.id.Button05, R.id.Button06, R.id.Button07,
                R.id.Button08, R.id.Button09, R.id.ButtonPoint, R.id.ButtonJia};

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                //https://blog.csdn.net/xiayiye5/article/details/90412877?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control

                String total = s.toString();
                if (total.startsWith("0")) {
                    if (total.length() >= 2) {
                        if (!".".equals(String.valueOf(total.charAt(1)))) {
                            total = total.substring(1, total.length());
                            editText.setText(total);
                            editText.setSelection(total.length());
                        }
                    }
                }
                if (total.contains("..")) {
                    editText.setText(getEditText().substring(0, getEditText().length() - 1));
                }
                if (total.contains("++")) {
                    editText.setText(getEditText().substring(0, getEditText().length() - 1));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }


    /**
     * 保留两位小数正则
     *
     * @param number
     * @return
     */
    public static boolean isOnlyPointNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


    private void addEvents() {
        EdittextUtils.disableCopyAndPaste(editText);
        editText.setFocusable(false);

        lyScan.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonClean.setOnClickListener(this);
        buttonJia.setOnClickListener(this);
        buttonDengyu.setOnClickListener(this);

//        buttonListener(buttonJia, 1);
//        buttonListener(buttonJian, 2);
//        buttonListener(buttonCheng, 3);
//        buttonListener(buttonChu, 4);

        // 监听
        for (int i = 0; i < buttons.length; i++) {
            temp = findViewById(buttons[i]);
            temp.setOnClickListener( // 为Button添加监听器
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str1 = editText.getText().toString().trim();
                            str1 = str1 + (((Button) v).getText());// 获得新输入的值
                            System.out.println("str1" + ":::" + str1);
                            editText.setText(str1);
                        }
                    });
        }
    }


    @Override
    public void onClick(View view) {
        if (view == lyScan) {
            if (!TextUtils.isEmpty(getEditText()) && !TextUtils.equals(getEditText(),"0")) {
                MyToast("开始扫码");
            }
        } else if (view == buttonClean) {//清空按钮
            str1 = "";
            str2 = ""; // 清空记录
            editText.setText("0");
            flag = 0;
        } else if (view == buttonDelete) {//删除按钮
            if (TextUtils.isEmpty(getEditText()) || TextUtils.equals(getEditText(),"0")) {
                str1 = "";
                str2 = ""; // 清空记录
                editText.setText("0");
                flag = 0;
            } else {
                String str = getEditText();
                editText.setText(str.substring(0, str.length() - 1));
            }
        } else if (view == buttonDengyu) {
//            result1 = Integer.parseInt(str1);
//            if (flag == 1) {
//                result = result0 + result1;
//                System.out.println(result0 + ":" + result1);
//            } else if (flag == 2) {
//                result = result0 - result1;
//            } else if (flag == 3) {
//                result = result0 * result1;
//            } else if (flag == 4) {
//                result = (int) (result0 / result1);
//            }
//            String str = (result + "").trim();
//            System.out.println(str);
//            editText.setText(str);
            if (!TextUtils.isEmpty(getEditText()) && !TextUtils.equals(getEditText(),"0")) {
                editText.setText(getCalculateNum());
//                editText.setText(getCalculateNum2());
            }
        }
    }

    // 按钮监听
    public void buttonListener(Button button, final int id) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result0 = result0 + Float.valueOf(getEditText());
                editText.setText(getCalculateNum2());
                flag = id;
            }
        });
    }


    private String getCalculateNum2(){
        String calculateNum = "0";
        result0 = result0 + Float.valueOf(getEditText());
        calculateNum = formatNumberOne(result0);
        return calculateNum;
    }

    /**
     * 返回计算好的数值
     */
    private String getCalculateNum() {
        String calculateNum = "0";
        float numFloat = 0.00f;
        String[] numArray = getSelectTime(getEditText());
        for (int i = 0; i < numArray.length; i++) {
            numFloat = numFloat + Float.valueOf(numArray[i]);
        }
        calculateNum = formatNumberOne(numFloat);
        if (calculateNum.contains(".00")){
            calculateNum = formatNumber(numFloat);
        }
        return calculateNum;
    }

    /**
     * 取整数
     *
     * @param value
     */
    public static String formatNumber(float value) {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(value);
    }

    /**
     * 保留两位小数点
     *
     * @param value
     */
    public static String formatNumberOne(float value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /**
     * 保留两位小数点
     */
    public static double formatDoubleTwo(float value) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public String getEditText() {
        return editText.getText().toString();
    }

    private void MyToast(String msg) {
        Toast.makeText(CalculatorActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 拆分数组以空格组装的数据
     *
     * @param time 字符串数据  如："2021-03-01+13:00~15:00"
     * @return 字符串数组 如：["2021-03-01","13:00~15:00"]
     * String IP4 = "[\\+\\-\\*\\/]";
     */
    private String[] getSelectTime(String time) {
        return time.split("\\++");
    }


}
