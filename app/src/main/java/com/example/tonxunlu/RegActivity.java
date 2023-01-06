package com.example.tonxunlu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tonxunlu.model.User;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnsave;
    private EditText etaccount;
    private EditText etpassword;
    private EditText etpasswordcfm;
    private EditText etphone;
    private Button btnreset;
    private Button btnback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        btnsave = findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
        btnreset = findViewById(R.id.btnreset);
        btnreset.setOnClickListener(this);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(this);

        etaccount =findViewById(R.id.etaccount);
        etpassword =findViewById(R.id.etpassword);
        etpasswordcfm =findViewById(R.id.etpasswordcfm);
        etphone =findViewById(R.id.etphone);
    }

    @Override
    public void onClick(View v) {
    int id = v.getId();
    switch (id){
        case R.id.btnsave:
            //1.获取输入框中内容
            String account = etaccount.getText().toString();
            String password = etpassword.getText().toString();
            String passwordcfm = etpasswordcfm.getText().toString();
            String phone = etphone.getText().toString();
            //判断内容是否合法
            //account.trim()表示去除左右空格
            if(account.trim().length() == 0) {
                Toast.makeText(RegActivity.this, "账号不能为空",Toast.LENGTH_SHORT).show();
                return;//中断方法
            }
            if(password.trim().length() == 0) {
                Toast.makeText(RegActivity.this, "密码不能为空",Toast.LENGTH_SHORT).show();
                return;//中断方法
            }
            if(!password.equals(passwordcfm)) {
                Toast.makeText(RegActivity.this, "两次输入不一致",Toast.LENGTH_SHORT).show();
                return;//中断方法
            }
            if(phone.trim().length() == 0) {
                Toast.makeText(RegActivity.this, "手机不能为空",Toast.LENGTH_SHORT).show();
                return;//中断方法
            }
            //判断账号密码是否重复
            if(isExistByAccountAndPhone(account,phone)){
                Toast.makeText(RegActivity.this, "账号或手机已经存在，请重新输入",Toast.LENGTH_SHORT).show();
                return;
            }
            //将数据打包到User对象中
            User bean = new User(account,password,phone);
            //添加到集合中
            LoginActivity.list.add(bean);
            //返回到登录界面
            finish();
            break;
        case R.id.btnback:
            finish();
            break;
        case R.id.btnreset:
            //清空输入框内容
            etaccount.setText("");
            etpassword.setText("");
            etpasswordcfm.setText("");
            etphone.setText("");
            break;
    }
    }

    /**
     * 根据账号和密码查询是否存在用户
     * @param account
     * @param phone
     * @return
     */
    public boolean isExistByAccountAndPhone(String account,String phone){
    /*
    如下是Java中for-each循环写法
    item表示几何中的元素
     */
       for(User item:LoginActivity.list){
           if(item.getAccount().equals(account) || item.getPhone().equals(phone)){
               return true;
           }
       }
       return false;
    }

}