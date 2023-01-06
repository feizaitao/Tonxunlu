package com.example.tonxunlu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tonxunlu.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
/*
    Activity获取xml控件步骤
    1.写完xml文件，并且给要获得的控件添加id
    2.在LoginActivity定义成员变量
    3.在onCreate方法中，且必须在setContentView代码后面
 */

    private Button btnlogin;
    private Button btnreg;
    private Button btnforget;
    private EditText etaccount;
    private EditText etpassword;
    private CheckBox cbrem;
    //保存所有注册的用户
    public static List<User> list = new ArrayList<>();
    SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.butlogin);
        btnreg = findViewById(R.id.btnreg);
        btnforget = findViewById(R.id.btnforget);
        //添加点击事件
        btnlogin.setOnClickListener(this);
        btnreg.setOnClickListener(this);
        btnforget.setOnClickListener(this);

        etaccount =findViewById(R.id.etaccount);
        etpassword =findViewById(R.id.etpassword);

        cbrem =findViewById(R.id.cbrem);

        /*
        记住密码
        1.登录成功且记住密码被勾选，保存账号和密码
        2.下次打开登录界面，先从sp中获取账号和密码
        如果有，则将内容添加到输入框中
        3.下一次登录时，不想记住密码时，则清除原来的内容
         */
        sp = getSharedPreferences("loginrem.text",MODE_PRIVATE);
        if(sp != null){
            String account = sp.getString("account",null);
            String password = sp.getString("password",null);
            //将内容显示在输入框中
            etpassword.setText(password);
            etaccount.setText(account);
            //记住密码默认为勾选
            if(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)){
                cbrem.setChecked(true);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println(list);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id){
            case R.id.butlogin:
                //获取输入框中的内容
                String account = etaccount.getText().toString();
                String password = etpassword.getText().toString();
                //判断数据合法性
                if(account.trim().length() == 0) {
                    Toast.makeText(LoginActivity.this, "账号不能为空",Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                if(password.trim().length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空",Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                //进行登录检查
                User user =  doLogin(account,password);
                if(user == null){
                    //说明没有找到，登录失败
                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }else{
                    //登录成功

                    SharedPreferences.Editor edit = sp.edit();
                    if(cbrem.isChecked()){//被勾选
                        edit.putString("account",account);
                        edit.putString("password",password);
                    }else{
                    edit.clear();
                    }
                    edit.commit();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }

                break;
                case R.id.btnreg:
                    intent.setClass(LoginActivity.this,RegActivity.class);
                    startActivity(intent);
                break;
            case R.id.btnforget:
                intent.setClass(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 进行登录操作
     * @param account
     * @param password
     * @return
     */
    private User doLogin(String account,String password){
        for(User item:list){
            if (item.getAccount().equals(account) && item.getPassword().equals(password)){
                return item;
            }
        }
        return null;
    }
}