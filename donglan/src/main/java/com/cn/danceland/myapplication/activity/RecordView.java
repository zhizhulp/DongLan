package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by feng on 2017/11/17.
 */

public class RecordView extends BaseActivity implements SurfaceHolder.Callback{
    SurfaceHolder mSurfaceHolder;
    SurfaceView sView;
    Camera camera;
    MediaRecorder mRecorder;
    File videoFile;
    Button record;
    Button stop;
    long startTime,lastTime;
    Handler handler;
    Timer timer = new Timer(true);
    String videoPath;
    boolean isPreview;
    SurfaceHolder holder;
    ProgressBar progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordview);
        initView();
        //startPre();
        //init();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    stop();
                    Intent intent = new Intent();
                    intent.putExtra("videoPath",videoPath);
                    setResult(111,intent);
                    finish();
                }
            }
        };
    }

    public void showProgress(){
        new Thread(new Runnable() {
            int i = -1;
            int current = 0;

            @Override
            public void run() {

                try {
                    while (11 != progress.getProgress()) {
                        i++;
                        progress.setProgress(i);
                        //current = progress.getProgress();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    };

    private void initView() {

        sView = findViewById(R.id.sView);

        holder = sView.getHolder();
        //sView.getHolder().setKeepScreenOn(true);
        progress = findViewById(R.id.progress);
        progress.setMax(10);
        progress.setProgress(0);
        record = findViewById(R.id.record);
        stop = findViewById(R.id.stop);
        record.setOnClickListener(onClickListener);
        stop.setOnClickListener(onClickListener);
        holder.addCallback(RecordView.this);
        ((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setStreamMute(AudioManager.STREAM_SYSTEM,true);
//        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        mAudioManager.setStreamVolume();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
               switch(v.getId()){
                   case R.id.record:
                       start();
                       showProgress();
                       timer.schedule(task,11000);
                       break;
                   case R.id.stop:
                       stop();
                       Intent intent = new Intent();
                       if(videoPath!=null){
                           intent.putExtra("videoPath",videoPath);
                       }
                       setResult(111,intent);
                       timer.cancel();
                       finish();
                       break;

               }
        }
    };

    public void init(){
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        // 创建保存录制视频的视频文件
        videoPath = Environment.getExternalStorageDirectory().getPath()
                + "/DCIM/Camera/"+"VID_"+year+month+date+"_"+hour+minute+second+".mp4";
        File dir = new File(Environment.getExternalStorageDirectory().getPath()
                + "/donglan/camera/vedio/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        videoFile = new File(videoPath);
        // 创建MediaPlayer对象
        mRecorder = new MediaRecorder();
        mRecorder.reset();

        //startPre();
        camera.setDisplayOrientation(90);

//
//        //Camera.Parameters parameters = camera.getParameters();
//
        camera.unlock();
        mRecorder.setCamera(camera);

        // 设置从麦克风采集声音
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置从摄像头采集图像
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        //构造CamcorderProfile，使用高质量视频录制
//        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//        mRecorder.setProfile(camcorderProfile);
        mRecorder.setOrientationHint(90);
        // 设置视频文件的输出格式
        // 必须在设置声音编码格式、图像编码格式之前设置
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        // 设置声音编码的格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        // 设置图像编码的格式
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

        // 每秒 4帧。。设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
        //mRecorder.setVideoFrameRate(30);
        mRecorder.setVideoEncodingBitRate(5*1280*720);
        mRecorder.setVideoSize(640,480);
        mRecorder.setMaxDuration(10000);

        mRecorder.setOutputFile(videoFile.getAbsolutePath());
        // 指定使用SurfaceView来预览视频
        mRecorder.setPreviewDisplay(sView.getHolder().getSurface());
    }

    /**
     * 开始录制视频
     * @throws IOException
     * @throws IllegalStateException
     */
    private void start(){

        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        camera = Camera.open();
        init();
        try {
            mRecorder.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
            // 开始录制
            mRecorder.start();
            // 让record按钮不可用。
            record.setEnabled(false);
            // 让stop按钮可用。
            //stop.setEnabled(true);
            //isRecording = true;
    }

    public void stop(){
        if(mRecorder!=null){
            // 停止录制
            mRecorder.stop();
            // 释放资源
            mRecorder.release();
            mRecorder = null;

        }

        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }

//            // 让record按钮可用。
//            record.setEnabled(true);
//            // 让stop按钮不可用。
//            stop.setEnabled(false);
//            isRecording = false;
    }

    public void startPre(){
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPre();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        camera.startPreview();
        camera.setDisplayOrientation(90);//将预览旋转90度

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
