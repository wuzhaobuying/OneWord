package smart.ccc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import smart.ccc.Bean.ArticleBean;
import smart.ccc.Bean.CommentBean;
import smart.ccc.Interface.GetArticleByUserService;
import smart.ccc.Interface.SendCommentService;

/**
 * Created by Administrator on
 */

public class CommentActivity extends AppCompatActivity{

    private ArticleBean articleBean;
    private List<CommentBean> lists;
    private SimpleAdapter sim_adapter;
    private Button send_comment;
    private EditText comment_content;
    private TextView comnum;
    private ListView listview;
    private Map<String, Object> map;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        listview=(ListView)findViewById(R.id.comlist);
        send_comment= (Button) findViewById(R.id.send_comment);
        comment_content= (EditText) findViewById(R.id.comment_content);
        articleBean= (ArticleBean) getIntent().getExtras().getSerializable("article");
        lists=new ArrayList<>();
        initData();


       // comnum= (TextView) findViewById(R.id.comnum);
        //initData();
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendData();
                initData();
                sim_adapter.notifyDataSetChanged();


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData(){
        lists.clear();
        SharedPreferences sharedPreferences=getSharedPreferences("OneWorld",MODE_PRIVATE);
         List<Map<String, Object>> data_list=new ArrayList<>();
        Log.d("TAG", "initData: "+articleBean.getId());
            lists = DataSupport.where("article_i=?",String.valueOf(articleBean.getId())).find(CommentBean.class);

        for(int i=0;i<lists.size();i++){
             map = new HashMap<String, Object>();
            Log.d("...", "initData: "+lists.get(i).getUser());
            Log.d("...", "initData: "+lists.get(i).getContent());
            map.put("name",lists.get(i).getUser());
            map.put("comment",lists.get(i).getContent());
            Log.d("TAG", "initData: "+map);
            data_list.add(map);
        }
        sim_adapter=new SimpleAdapter(CommentActivity.this, data_list,R.layout.comitem,
                new String[] { "name","comment"},
                new int[] { R.id.comname, R.id.mycomment});
        sim_adapter.notifyDataSetChanged();
        listview.setAdapter(sim_adapter);



    }


    public void SendData(){
        SharedPreferences sharedPreferences=getSharedPreferences("OneWorld",MODE_PRIVATE);
        CommentBean comment = new CommentBean();
        Log.d("TAG", "SendData: "+articleBean.getId());
        comment.setArticle_i(articleBean.getId());
        comment.setUser(sharedPreferences.getString("user_name","未知"));
        Log.d("TAG", "SendData: "+articleBean.getUser());
        comment.setContent(comment_content.getText().toString());
        comment.save();
        Log.d("TAG", "SendData: "+comment.getArticle_i());
        Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
        comment_content.setText(null);

    }
}
