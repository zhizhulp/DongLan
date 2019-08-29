package com.cn.danceland.myapplication.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.ImageFolder;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by feng on 2017/10/24.
 */

public class ImagesActivity extends BaseActivity {
    Uri uri;
    GridView photo_grid;
    ContentResolver contentResolver;
    Cursor cursor;
    HashSet<String> mDirPaths = new HashSet<String>();
    int mPicsSize,totalCount;
    List<ImageFolder> mImageFloders = new ArrayList<ImageFolder>();
    //图片数量最多的文件夹
    File mImgDir;
    ArrayList<String> arrPath;
    int order;
    String path;
    ImageView iv_back;
    TextView sure;
    ArrayList<String> integers;
    List<String> arrList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);
        Matisse.from(ImagesActivity.this)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(9) // 图片选择的最多数量
                //.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new PicassoEngine()) // 使用的图片加载引擎
                .forResult(100); // 设置作为标记的请求码
//        initHost();
//        initView();
//        setOnclick();
//        setData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void setOnclick() {
        sure.setOnClickListener(onClick);
        iv_back.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sure:
                    order=0;
                    Intent intent1 = new Intent();
                    intent1.putStringArrayListExtra("arrPath",arrPath);
                    setResult(0,intent1);
                    finish();
                    break;
                case R.id.iv_back:
                    finish();
                    break;

            }
        }
    };


    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if(mImgDir!=null){
                path = mImgDir.getAbsolutePath();
                List<String> arr = new ArrayList<String>();

                arr = Arrays.asList(mImgDir.list());
                if(arr!=null&&arr.size()>0){
                    for(int i = 0;i<arr.size();i++){
                        if(arr.get(i).contains(".jpg")||arr.get(i).contains(".png")){
                            arrList.add(arr.get(i));
                        }
                    }
                }
                MyAdapter myAdapter = new MyAdapter(ImagesActivity.this,arrList,path);
                photo_grid.setAdapter(myAdapter);
                //滚动到最后一行
                photo_grid.smoothScrollToPosition(arrList.size()-1);
            }



            //mProgressDialog.dismiss();
            // 为View绑定数据
            //data2View();
            // 初始化展示文件夹的popupWindw
            //initListDirPopupWindw();
        }
    };

    public void initHost() {
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        contentResolver =ImagesActivity.this.getContentResolver();
        arrPath = new ArrayList<String>();
        arrPath.clear();
        integers = new ArrayList<String>();
    }

    public void setData() {
        getImages();
    }



    public void getImages() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstPath=null;
                cursor = contentResolver.query(uri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                //cursor = contentResolver.query(uri, null, null, null, null);

                while (cursor.moveToNext()){
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if(firstPath==null){
                        firstPath = path;
                    }
                    File parentFile = new File(path).getParentFile();
                    if(parentFile==null){
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFolder imageFolder = null;
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFolder = new ImageFolder();
                        imageFolder.setDir(dirPath);
                        imageFolder.setFirstImagePath(path);
                    }
                    int picSize = 0;
                    try{
                        picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                if (filename.endsWith(".jpg")
                                        || filename.endsWith(".png")
                                        || filename.endsWith(".jpeg"))
                                    return true;
                                return false;
                            }
                        }).length;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    totalCount += picSize;
                    imageFolder.setCount(picSize);
                    mImageFloders.add(imageFolder);
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                cursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    public void initView(){
        photo_grid = findViewById(R.id.photo_grid);
        iv_back = findViewById(R.id.iv_back);

        sure = findViewById(R.id.sure);
        photo_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView item_select = view.findViewById(R.id.item_select);
                ImageView item_select_blue = view.findViewById(R.id.item_select_blue);
                if(order<9){
                    if(item_select_blue.getVisibility()==View.GONE){
                        item_select_blue.setVisibility(View.VISIBLE);
                        item_select.setVisibility(View.GONE);
                        arrPath.add(path+"/"+arrList.get(position));
                        order++;
                        integers.add(String.valueOf(position));
                    }else{
                        item_select_blue.setVisibility(View.GONE);
                        item_select.setVisibility(View.VISIBLE);
                        for (int i=arrPath.size()-1;i>0;i--){
                            if((path+"/"+arrList.get(position)).equals(arrPath.get(i))){
                                arrPath.remove(i);
                            }
                        }
                        order--;
                        integers.remove(String.valueOf(position));
                    }
                }else{
                    int m=0;
                    for (int i=arrPath.size()-1;i>0;i--){
                        if((path+"/"+arrList.get(position)).equals(arrPath.get(i))){
                            arrPath.remove(i);
                            item_select_blue.setVisibility(View.GONE);
                            item_select.setVisibility(View.VISIBLE);
                            m++;
                            order--;
                        }
                    }
                    if(m==0){
                        ToastUtils.showToastLong("最多可选9张");
                    }
                }
            }
        });
    }

    public class MyAdapter extends BaseAdapter{
        Context context;
        LayoutInflater mInflater;
        List<String> asList;
        String path;
        MyAdapter(Context context,List<String> asList,String mPath){
            this.context = context;
            this.asList = asList;
            path = mPath;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return asList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.images_item, null);
                viewHolder.img = convertView.findViewById(R.id.image_item);
                viewHolder.item_select = convertView.findViewById(R.id.item_select);
                viewHolder.item_select_blue = convertView.findViewById(R.id.item_select_blue);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

                if(integers.contains(String.valueOf(position))){
                    viewHolder.item_select_blue.setVisibility(View.VISIBLE);
                    viewHolder.item_select.setVisibility(View.GONE);
                }else{
                    viewHolder.item_select_blue.setVisibility(View.GONE);
                    viewHolder.item_select.setVisibility(View.VISIBLE);
                }



            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            viewHolder.img.setMaxHeight(100);
            viewHolder.img.setMaxWidth(windowManager.getDefaultDisplay().getWidth()/3);
            Glide.with(context).load(new File(path+"/"+asList.get(position))).into(viewHolder.img);
            //viewHolder.img.setImageBitmap(PictureUtil.getSmallBitmap(path+"/"+asList.get(position),windowManager.getDefaultDisplay().getWidth()/3,100));

            return convertView;
        }

    }
    public class ViewHolder{
        ImageView img,item_select,item_select_blue;
    }


}
