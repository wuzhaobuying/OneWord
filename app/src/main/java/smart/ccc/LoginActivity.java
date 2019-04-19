package smart.ccc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import smart.ccc.Bean.UserBean;
import smart.ccc.Interface.LoginService;

/*import com.hebin.mduse.entity.User;
import com.hebin.mduse.uitl.SPUtils;
import com.hebin.mduse.uitl.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;*/

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText login_username,login_password;
    private SharedPreferences sp;


       @Override
       protected void onCreate(@Nullable Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.content_login);

           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
           toolbar.setTitle("登录");
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onBackPressed();
               }
           });
           Button register= (Button) findViewById(R.id.btn_register);
           login_username= (EditText) findViewById(R.id.login_username);
           login_password= (EditText) findViewById(R.id.login_password);
           register.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                   startActivity(intent);
                   finish();
               }
           });
           final Button login = (Button) findViewById(R.id.btn_login);
           login.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (jugde()) {
                       UserBean userBean = new UserBean();
                       userBean.setUser(login_username.getText().toString());
                       userBean.setPwd(login_password.getText().toString());
                       List<UserBean> user = DataSupport.where("user=?", userBean.getUser()).where("pwd=?", userBean.getPwd()).find(UserBean.class);
                       if (user.isEmpty()) {
                           Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();

                       } else {
                           sp = getSharedPreferences("OneWorld", MODE_PRIVATE);
                           SharedPreferences.Editor editor = sp.edit();
                           editor.putString("user_name", user.get(0).getName());
                           editor.putLong("user_id", user.get(0).getId());
                           editor.commit();
                           Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(LoginActivity.this, main.class);
                           startActivity(intent);
                           finish();

                       }
                   }
                   else{
                       Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();

                   }
               }
           });


       }

    public Boolean jugde() {
        if (login_username.getText().toString().isEmpty()) {
            return false;
        }
        else if (login_password.getText().toString().isEmpty()){
            return false;
        }

        else return true;

    }

    public void login(){
       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://129.28.21.232:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        UserBean userBean=new UserBean();
        userBean.setUser(login_username.getText().toString());
        userBean.setPwd(login_password.getText().toString());
        final LoginService loginService = retrofit.create(LoginService.class);
        Log.d("TAG", "login: "+ JSONObject.toJSONString(userBean));
        loginService.login(userBean.getUser(),userBean.getPwd())
                .map(new Func1<UserBean, Object>() {

                    @Override
                    public Object call(UserBean userBean) {
                        SPUtils.put(LoginActivity.this,"name",userBean.getName());
                        SPUtils.put(LoginActivity.this,"user",userBean.getUser());
                        /*SPUtils.put(LoginActivity.this,"pwd",userBean.getPwd());*/
                        return null;
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this, main.class);
                        startActivity(intent);

                        Log.e("TAG","success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this, main.class);
                        startActivity(intent);
                        Log.e("TAG", "onError: "+e );

                    }

                    @Override
                    public void onNext(Object object) {

                    }
                });

    }


}
