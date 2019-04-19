package smart.ccc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import smart.ccc.Bean.ArticleBean;
import smart.ccc.Interface.GetAllArticleService;
import smart.ccc.Interface.GetArticleByClassifyService;

/**
 * Created by Administrator on
 */

public class ClassifyFrament extends android.support.v4.app.Fragment{

    private Toolbar toolbar1;
    private RecyclerView recycler_view;
    private TextView tv1,tv2;
    private View view;
    private List<ArticleBean> lists;
    private TextView title;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
         view = inflater.inflate(R.layout.classifyfragment, container, false);

        toolbar1= (Toolbar) getActivity().findViewById(R.id.toolbar_main);
      /*  title= (TextView) getActivity().findViewById(R.id.Title);
        title.setText("主页");
        toolbar1.setTitle("");*/

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar1);
       /* ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar)(view.findViewById(R.id.toolbar1)));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("主页");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
      /*   toolbar1= (Toolbar) view.findViewById(R.id.toolbar1);

        toolbar1.setTitle("主页");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar1);


*/
        initView();
        LinearLayoutManager m=new LinearLayoutManager(getContext());
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(m);
        initData();




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initData() {
        lists=new ArrayList<>();
        String classify = null;
       int classyNum= getActivity().getIntent().getExtras().getInt("classyNum");
       if(classyNum==1){
           classify="文字";
       }
        if(classyNum==2){
            classify="语录";
        }

        if(classyNum==3){
            classify="歌词";
        }

        if(classyNum==4){
            classify="电影";
        }

        if(classyNum==5){
            classify="诗词";
        }


        lists.addAll(DataSupport.where("classify=?",classify).find(ArticleBean.class));

        recyclerViewadapter adapter=new recyclerViewadapter(lists,getContext());
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("article",lists.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        }));
          recycler_view.setAdapter(adapter);



    }

    private void initView() {
        recycler_view= (RecyclerView) view.findViewById(R.id.recycler_View);
        tv1= (TextView) view.findViewById(R.id.tv1);
        tv2= (TextView) view.findViewById(R.id.tv2);


    }
}
