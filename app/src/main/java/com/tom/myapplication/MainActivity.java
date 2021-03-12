package com.tom.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lkn.net.creator.ServiceGenerator;
import com.tom.myapplication.calcu.CalculatorActivity3;
import com.tom.myapplication.dialog.TestDialog;
import com.tom.myapplication.dialogmanager.DialogManager;
import com.tom.myapplication.dialogmanager.DialogParam;


public class MainActivity extends AppCompatActivity {

    AppCompatTextView textView,textView2,textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this::onClicks);
        textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(this::onClicks2);
        textView3 = findViewById(R.id.textView3);
        textView3.setOnClickListener(this::onClicks3);
//        initDialog(this);
    }



    public void onClicks(View view) {
//        MyToast.show(this, JsonUtils.toJson("123456"));
//        ServiceGenerator serviceGenerator = new ServiceGenerator();
        startActivity(new Intent(MainActivity.this,CalculatorActivity.class));
    }

    public void onClicks3(View view) {
        startActivity(new Intent(MainActivity.this, CalculatorActivity2.class));
    }

    public void onClicks2(View view) {
        initDialog(this);
        DialogManager.getInstance().show();
    }

    private void initDialog(Context context) {

        TestDialog alertDialog1;
        TestDialog alertDialog2;
        TestDialog alertDialog3;
        int[] prioritys = new int[]{3, 4, 2};
        alertDialog1 = new TestDialog(context);
        alertDialog1.setTitle("温馨提示");
        alertDialog1.setMessage("第一个弹窗,优先级：" + prioritys[0]);
        alertDialog1.setCancelable(false);
        alertDialog1.setButton(DialogInterface.BUTTON_POSITIVE, "关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              /*调用此方法传false告诉DialogManager此窗口
                dismiss是用户自己关闭的，而非被优先级更高的弹
                窗show后被挤出，这种情况优先级更高的弹窗dismiss
                后DialogManager不会重新show此弹窗*/
                alertDialog1.dismiss(false);
            }
        });
        alertDialog2 = new TestDialog(context);
        alertDialog2.setTitle("温馨提示");
        alertDialog2.setMessage("第二个弹窗,优先级：" + prioritys[1]);
        alertDialog2.setCancelable(false);
        alertDialog2.setButton(DialogInterface.BUTTON_POSITIVE, "关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              /*调用此方法传false告诉DialogManager此窗口
                dismiss是用户自己关闭的，而非被优先级更高的弹
                窗show后被挤出，这种情况优先级更高的弹窗dismiss
                后DialogManager不会重新show此弹窗*/
                alertDialog2.dismiss(false);
            }
        });
        alertDialog3 = new TestDialog(context);
        alertDialog3.setTitle("温馨提示");
        alertDialog3.setMessage("第三个弹窗,优先级：" + prioritys[2]);
        alertDialog3.setCancelable(false);
        alertDialog3.setButton(DialogInterface.BUTTON_POSITIVE, "关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              /*调用此方法传false告诉DialogManager此窗口
                dismiss是用户自己关闭的，而非被优先级更高的弹
                窗show后被挤出，这种情况优先级更高的弹窗dismiss
                后DialogManager不会重新show此弹窗*/
                alertDialog3.dismiss(false);
            }
        });
        DialogManager.getInstance().add(new DialogParam.Builder().dialog(alertDialog1).priority(prioritys[0]).build());
        DialogManager.getInstance().add(new DialogParam.Builder().dialog(alertDialog2).priority(prioritys[1]).build());
        DialogManager.getInstance().add(new DialogParam.Builder().dialog(alertDialog3).priority(prioritys[2]).build());



    }

}
