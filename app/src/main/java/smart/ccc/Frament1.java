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

import smart.ccc.Bean.ArticleBean;
import smart.ccc.Bean.DataBean;

/**
 * Created by Administrator on
 */

public class Frament1 extends android.support.v4.app.Fragment{

    private Toolbar toolbar1;
    private RecyclerView recycler_view;
    private TextView tv1,tv2,classify;
    private View view;
    private ArrayList<ArticleBean> lists;
    private TextView title;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
         view = inflater.inflate(R.layout.fragment1, container, false);

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
        m.setOrientation(LinearLayoutManager.HORIZONTAL);//将layout设置为水平滑动
        recycler_view.setLayoutManager(m);
        initData();




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();

    }

    private void initData() {
        lists=new ArrayList<ArticleBean>();
      /*  lists.add(new ArticleBean("《赋得古原草送别》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","青青原上草，一岁一枯荣","Smart","1",true,"scc"));
        lists.add(new ArticleBean("《将进酒》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","君不见，黄河之水天上来，奔流到海不复回","Terry","1",true,"scc"));
        lists.add(new ArticleBean("《蝶恋花》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","衣带渐宽终不悔，为伊消得人憔悴","Leo","1",true,"scc"));
        lists.add(new ArticleBean("《诗经·邶风·击鼓》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","死生契阔，与子成说.执子之手，与子偕老","Oliver","1",true,"scc"));
        lists.add(new ArticleBean("《暮秋独游曲江》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","深知身在情长在，怅望江头江水声","Biscuit","1",true,"scc"));
        lists.add(new ArticleBean("《锦瑟》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","此情可待成追忆，只是当时已惘然","Smart","1",true,"scc"));
        lists.add(new ArticleBean("《江城子》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","十年生死两茫茫，不思量，自难忘，千里孤坟，无处话凄凉","Smart","1",true,"scc"));
        lists.add(new ArticleBean("《枉凝眉》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","一个是阆苑仙葩，一个是美玉无瑕.若说没奇缘，今生偏又遇著他；若说有奇缘，如何心事终虚话？","Smart","1",true,"scc"));
        lists.add(new ArticleBean("《八六子·倚危亭》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","夜月一帘幽梦，春风十里柔情","苹果","1",true,"scc"));
        lists.add(new ArticleBean("《菩提偈》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","菩提本无树，明镜亦非台","萌萌","1",true,"scc"));
        lists.add(new ArticleBean("《卜算子·答施》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","相思似海深，旧事如天远","饼饼有礼","1",true,"scc"));
        lists.add(new ArticleBean("《鹤冲天·黄金榜上》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","青春都一晌，忍把浮名，换了浅斟低唱","Smart","1",true,"scc"));
        lists.add(new ArticleBean("《阿甘正传》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","生活就像巧克力,你永远不会知道你会得到什么","Smart","1",true,"scc"));
        lists.add(new ArticleBean("《东邪西毒》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","多年以后我有个绰号叫西毒,任何人都可以变得狠毒,只要你尝试过什么叫妒嫉.我不介意其他人怎么看我,我只不过不想别人比我更开心.我以为有一些人永远不会妒嫉,因为他太骄傲 . 在我出道的时候,我认识了一个人,因为他喜欢在东边出没,所以很多年以后,他有个绰号叫东邪.","苹果","1",true,"scc"));
        lists.add(new ArticleBean("《肖申克的救赎》","http://pic11.nipic.com/20101214/213291_155243023914_2.jpg","恐惧让你沦为囚犯,希望让你重获自由,坚强的人只能救赎自己,伟大的人才能拯救别人.记着,希望是件好东西,而且从没有一样好东西会消逝.忙活,或者等死.","萌萌","1",true,"scc"));
*/
        lists.addAll(DataSupport.findAll(ArticleBean.class));
        recyclerViewadapter adapter=new recyclerViewadapter(lists,getContext());

        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                Bundle bundle=new Bundle();
                Log.d("TAG", "onItemClick: "+lists.get(position).getId());
                bundle.putSerializable("article",lists.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);


            }
        }));
        recycler_view.setAdapter(adapter);

  /*    String test="[{\"title\":\"lalala\",\"content\":\"nihaonihao\",\"author\":\"2222\",\"isMe\":true,\"classify\":\"1\",\"user\":\"555\"},{\"title\":\"诗词\",\"content\":\"白日依山尽\",\"author\":\"啊哈\",\"isMe\":false,\"classify\":\"1\",\"user\":\"555\"},{\"title\":\"诗词\",\"content\":\"举头望明月\",\"author\":\"试试\",\"isMe\":false,\"classify\":\"1\",\"user\":\"555\"},{\"title\":\"语录\",\"content\":\"哈哈哈哈\",\"author\":\"的\",\"isMe\":true,\"classify\":\"2\",\"user\":\"555\"}]";
        lists=JSONObject.parseArray(test,ArticleBean.class);
        Log.d("TAG", "initData: "+lists.get(0).getAuthor());
*/
    /*    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://121.42.150.20:8080/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        final GetAllArticleService getAllArticleService=retrofit.create(GetAllArticleService.class);

            getAllArticleService.GetAllArticleService("articleAll")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<List<ArticleBean>, Object>() {

                        @Override
                        public Object call(List<ArticleBean> articleBeen) {
                            lists.clear();
                            lists.addAll(articleBeen);
                            return null;
                        }
                    })
                    .subscribe(new Subscriber() {
                        @Override
                        public void onCompleted() {


                            Log.d("TAG", "onCompleted: success");
                            Log.d("TAG", "onCompleted: "+lists);
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

                        @Override
                        public void onError(Throwable e) {
                            Log.d("TAG", "onError:"+e);
                            Log.d("TAG", "onError: "+lists);

                        }

                        @Override
                        public void onNext(Object o) {

                        }
                    });

*/

    }

    private void initView() {
        recycler_view= (RecyclerView) view.findViewById(R.id.recycler_View);
        tv1= (TextView) view.findViewById(R.id.tv1);
        tv2= (TextView) view.findViewById(R.id.tv2);
        title= (TextView) view.findViewById(R.id.title);
        classify= (TextView) view.findViewById(R.id.classify);


    }
}
