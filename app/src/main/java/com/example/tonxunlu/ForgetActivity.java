package com.example.tonxunlu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tonxunlu.model.User;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnback;
    private Button btnquery;
    private EditText etaccount;
    private EditText etphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(this);
        btnquery = findViewById(R.id.btnquery);
        btnquery.setOnClickListener(this);
        etaccount = findViewById(R.id.etaccount);
        etphone = findViewById(R.id.etphone);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.btnback:
                finish();
                break;
            case R.id.btnquery:
                //获取输入框中的内容
                String account = etaccount.getText().toString();
                String phone = etphone.getText().toString();
                //判断内容是否合法
                //account.trim()表示去除左右空格
                if(account.trim().length() == 0) {
                    Toast.makeText(ForgetActivity.this, "账号不能为空",Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                if(phone.trim().length() == 0) {
                    Toast.makeText(ForgetActivity.this, "手机不能为空",Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                String password = doFindPassword(account,phone);
                if(password == null){
                    //没有找到
                    Toast.makeText(ForgetActivity.this, "该账号和手机无法查找到相应密码",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgetActivity.this, "您的密码为："+password,Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
    /**
     * 根据账号和手机查找密码
     * @param account
     * @param phone
     * @return
     */
    private String doFindPassword(String account, String phone){
        for(User item:LoginActivity.list){
            if (item.getAccount().equals(account) && item.getPhone().equals(phone)){
                return item.getPassword();
            }
        }
        return null;
    }
}