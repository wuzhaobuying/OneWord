package smart.ccc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import smart.ccc.Bean.UserBean;
import smart.ccc.Interface.RegisterService;

/*import com.hebin.mduse.entity.ResultData;
import com.hebin.mduse.entity.User;
import com.hebin.mduse.uitl.RegisterService;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_username,edit_password,edit_name;
    private String resultdata;



    @Override
       protected void onCreate(@Nullable Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.content_register);

           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
           toolbar.setTitle("注册");
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           toolbar.setNavigationOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onBackPressed();
               }
           });

        LitePal.getDatabase();
        Button register = (Button) findViewById(R.id.register);
        edit_username= (EditText) findViewById(R.id.edit_username);
        edit_password= (EditText) findViewById(R.id.edit_password);
        edit_name= (EditText) findViewById(R.id.edit_name);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jugde()) {
                    final UserBean user = new UserBean();
                    user.setName(edit_name.getText().toString());
                    user.setUser(edit_username.getText().toString());
                    user.setPwd(edit_password.getText().toString());
                    List<UserBean> users = DataSupport.where("user=?", user.getUser()).find(UserBean.class);
                    if (users.isEmpty()) {
                        user.save();
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "账户已存在", Toast.LENGTH_SHORT).show();
                        Log.d("ss", DataSupport.findAll(UserBean.class).toString());
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "账号、密码或用户名不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


       }
    public Boolean jugde() {
        if (edit_name.getText().toString().isEmpty()) {
            return false;
        }
        else if (edit_username.getText().toString().isEmpty()){
            return false;
        }
        else if (edit_password.getText().toString().isEmpty()){
            return false;
        }
        else return true;

    }


       public void register() throws UnsupportedEncodingException {

           Retrofit retrofit=new Retrofit.Builder()
                   .baseUrl("http://129.28.21.232:8080/")
                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(new OkHttpClient())
                   .build();

           final RegisterService registerService=retrofit.create(RegisterService.class);
          UserBean user=new UserBean();
           user.setName(edit_name.getText().toString());
           user.setUser(edit_username.getText().toString());
           user.setPwd(edit_password.getText().toString());
          registerService.register(user.getName(),user.getUser())
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Subscriber<Object>() {
                       @Override
                       public void onCompleted() {
                               Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                               finish();


                           Log.e("TAG","success");

                       }
                       @Override
                       public void onError(Throwable e) {
                           Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                       }

                       @Override
                       public void onNext(Object object) {

                       }
                   });
       }


    /*public  void register(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.5.185:8080/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        final RegisterService registerService=retrofit.create(RegisterService.class);
        User user=new User();
        user.setUserName(edit_name.getText().toString());
        user.setUserNum(edit_username.getText().toString());
        user.setUserPwd(edit_password.getText().toString());
        Log.d("TAG", "register: "+edit_name.getText().toString());
        String zz= com.alibaba.fastjson.JSONObject.toJSONString(user);
        Log.d("TAG", "register: "+zz);

        registerService.register(zz)
                .map(new Func1<ResultData, Object>() {

                    @Override
                    public Object call(ResultData resultData) {
                        Log.d("TAG", "call: "+resultData.getStatus());
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                        Log.e("TAG","success");
                        Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: "+e );

                    }

                    @Override
                    public void onNext(Object object) {

                    }
                });


    }*/


}
