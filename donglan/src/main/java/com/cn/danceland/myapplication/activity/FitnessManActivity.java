package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by feng on 2017/11/3.
 * 健身达人页
 */

public class FitnessManActivity extends BaseActivity {

    RecyclerView fitness_recycle;
    ImageView fitness_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        initView();
    }

    private void initView() {
        fitness_recycle = findViewById(R.id.fitness_recycle);

        fitness_back = findViewById(R.id.fitness_back);
        fitness_back.setOnClickListener(onClickListener);
        fitness_recycle.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> arr = new ArrayList<>();
        fitness_recycle.setAdapter(new MyRecycleAdapter(arr,FitnessManActivity.this));
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fitness_back:
                    finish();
                    break;
            }
        }
    };

    public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyRecycleViewHolder>{

        public ArrayList<String> arrayList;
        Context context;

        public MyRecycleAdapter(ArrayList<String> arrayList,Context context){
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fitness_recyle_item,parent,false);
            MyRecycleViewHolder myRecycleViewHolder = new MyRecycleViewHolder(v);
            return myRecycleViewHolder;
        }

        @Override
        public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
            Glide.with(FitnessManActivity.this).load("http://img06.tooopen.com/images/20160915/tooopen_sy_178926047887.jpg").into(holder.fitness_circleimage);
            //holder.fitness_circleimage.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.zhongdian_icon));
            holder.fitness_fans.setText("100w粉丝");
            holder.fitness_name.setText("小明同学");
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            Glide.with(FitnessManActivity.this).load("http://bpic.ooopic.com/15/50/63/15506336-428618c60ee298b4b17fe13668008109-4.jpg").
                    into(holder.im1);
            Glide.with(FitnessManActivity.this).load("http://pic35.photophoto.cn/20150527/0036036836605210_b.jpg").
                    into(holder.im2);
            Glide.with(FitnessManActivity.this).load("http://img1.juimg.com/160915/328468-16091509333851.jpg").
                    into(holder.im3);

        }


        @Override
        public int getItemCount() {
            return 20;
        }

        class MyRecycleViewHolder extends RecyclerView.ViewHolder{
            CircleImageView fitness_circleimage;
            TextView fitness_fans;
            TextView fitness_name;
            ImageView im1;
            ImageView im2;
            ImageView im3;

            public MyRecycleViewHolder(View itemView) {
                super(itemView);
                fitness_circleimage = itemView.findViewById(R.id.fitness_circleimage);
                fitness_fans = itemView.findViewById(R.id.fitness_fans);
                fitness_name = itemView.findViewById(R.id.fitness_name);
                im1 = itemView.findViewById(R.id.im1);
                im2 = itemView.findViewById(R.id.im2);
                im3 = itemView.findViewById(R.id.im3);
            }
        }
    }



}
