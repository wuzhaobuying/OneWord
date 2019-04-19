package smart.ccc;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import smart.ccc.Bean.ArticleBean;
import smart.ccc.Bean.ResultData;
import smart.ccc.Interface.SendArticleService;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class SendArticleActivity extends AppCompatActivity {

    private EditText et_title,et_content,et_author;
    private Button bt_send;
    private ImageView bt_addimg;
    private ArrayList<String> imagePaths=new ArrayList<>();
    private Spinner classify;
    private Switch isMe;
    private String imgPath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        initView();
        bt_addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  PhotoPickerIntent intent = new PhotoPickerIntent(SendArticleActivity.this);
                intent.setShowCarema(true); // 是否显示拍照， 默认false
                intent.setMaxTotal(1); // 最多选择照片数量，默认为9
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
// intent.setImageConfig(config);
                startActivityForResult(intent,10);*/

                Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);

            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jugde()){
                ArticleBean articleBean=new ArticleBean();
                /*articleBean.setUser(SPUtils.get(this,"user",null).toString());*/
                articleBean.setTitle(et_title.getText().toString());
                articleBean.setContent(et_content.getText().toString());
                articleBean.setAuthor(et_author.getText().toString());
                articleBean.setClassify(classify.getSelectedItem().toString());
                articleBean.setMe(isMe.getShowText());

                try {
                    byte[]  imageBytes = getBitmapByte(bitmap);
                    articleBean.setImagebyte(imageBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "onClick: "+articleBean.getImagebyte());
                Log.d("TAG", "onClick: "+imgPath);
                articleBean.save();
                Toast.makeText(SendArticleActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                finish();

            }

            else{
                    Toast.makeText(SendArticleActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();

                } }
        });


    }

    public Boolean jugde() {
        if (et_content.getText().toString().isEmpty()) {
            return false;
        }
        else if (et_title.getText().toString().isEmpty()){
            return false;
        }
        else if (et_author.getText().toString().isEmpty()){
            return false;
        }
        else return true;

    }

    private void initView() {
        bt_addimg= (ImageView) findViewById(R.id.bt_addimg);
        bt_send= (Button) findViewById(R.id.bt_send);
        et_author= (EditText) findViewById(R.id.et_author);
        et_content= (EditText) findViewById(R.id.et_content);
        et_title= (EditText) findViewById(R.id.et_title);
        isMe= (Switch) findViewById(R.id.isMe);
        classify= (Spinner) findViewById(R.id.classify);

    }


    private void sendArticle() throws Exception {
        ArticleBean articleBean=new ArticleBean();
        /*articleBean.setUser(SPUtils.get(this,"user",null).toString());*/
        articleBean.setTitle(et_title.getText().toString());
        articleBean.setContent(et_content.getText().toString());
        articleBean.setAuthor(et_author.getText().toString());
        articleBean.setClassify(classify.getSelectedItem().toString());
        articleBean.setMe(isMe.getShowText());
        byte[] imageBytes = readStream(imgPath);
        articleBean.setImagebyte(imageBytes);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://120.27.5.185:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();

        String zz= JSONObject.toJSONString(articleBean);
         Map map=new HashMap();
         File file=new File(imgPath);
         map.put("file"+"\";filename=\""+file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"),file));
        Log.d("cc","file"+"\";filename=\""+file.getName());
        final SendArticleService sendImageService1=retrofit.create(SendArticleService.class);
        sendImageService1.sendImage("publish",zz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResultData, Object>() {
                    @Override
                    public Object call(ResultData resultData) {
                        Log.d("TAG", "call: "+resultData.getStatus());
                        return null;
                    }
                })
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(SendArticleActivity.this,"发表成功", Toast.LENGTH_SHORT).show();
                        finish();
                        Log.d("TAG", "onCompleted: success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onError:"+e);

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });



    }


    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }
    public byte[] getBitmapByte(Bitmap bitmap) throws IOException {
ByteArrayOutputStream out=new ByteArrayOutputStream();
bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
try{
out.flush();
out.close();
 }
catch(IOException e){
        e.printStackTrace();
}
     return out.toByteArray();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case 1:
                    /*Glide.with(SendArticleActivity.this)
                            .load(imagePaths.get(0))
                            .placeholder(R.mipmap.default_error)
                            .error(R.mipmap.default_error)
                            .centerCrop()
                            .crossFade()
                            .into(bt_addimg);*/
                    imgPath=data.getData().getPath();
                    Uri uri = data.getData();
                    Log.d("TAG", "onActivityResult: "+data.getData().getPath());
                    ContentResolver cr = this.getContentResolver();
                    bitmap = null;
                        try {
                            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
        /* 将Bitmap设定到ImageView */
                        bt_addimg.setImageBitmap(bitmap);

                    break;
                    }

                        // 预览
              /*  case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdpater(ListExtra);
                    break;*/

            }
        super.onActivityResult(requestCode, resultCode, data);

        }





}
