package smart.ccc;

import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import smart.ccc.Bean.ArticleBean;

/**
 * Created by Administrator on
 */

public class DetailActivity extends AppCompatActivity {

    private TextView title,author,content,Title;
    private ArticleBean articleBean;
    private Button comment;
    private ImageView mainpic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        articleBean= (ArticleBean) Objects.requireNonNull(getIntent().getExtras()).getSerializable("article");
        initView();


        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("article",articleBean);
                Intent intent=new Intent(DetailActivity.this,CommentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    public void initView(){
        title= (TextView) findViewById(R.id.Title);
        author= (TextView) findViewById(R.id.author);
        content= (TextView) findViewById(R.id.content);
        Title= (TextView) findViewById(R.id.tv__title);
        comment= (Button) findViewById(R.id.comment);
        mainpic=(ImageView) findViewById(R.id.mainpic);
        title.setText("由"+articleBean.getAuthor()+"创建");
        content.setText(articleBean.getContent());
        if(articleBean.isMe()) {
            author.setText("#转载#");
        }
        else {
            author.setText("#原创#");
        }
        Title.setText(articleBean.getTitle());
        Glide.with(this).load(articleBean.getImagebyte()).into(mainpic);
    }
}
