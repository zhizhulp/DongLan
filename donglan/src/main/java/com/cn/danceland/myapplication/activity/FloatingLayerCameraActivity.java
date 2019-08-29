package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.shouhuan.activity.WearFitClockSettingActivity;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.shouhuan.service.BluetoothLeService;
import com.cn.danceland.myapplication.shouhuan.utils.DataHandlerUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.PictureUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 公共浮层相机
 * 返回照片
 * isFloatLayer 是否有浮层 1有
 * floatLayerImgType 浮层图片 1男正面 2男反面 3男侧面 4女正面 5女反面 6女侧面
 * cameraPath 图片保存地址
 * Created by yxx on 2018/10/12.
 */

public class FloatingLayerCameraActivity extends Activity {
    private Context context;

    private TextureView textureView;
    private ImageButton takePhoto;
    private ImageButton backBtn;
    private ImageButton cameraSwap;
    private String mCameraId = "0";//摄像头id（通常0代表后置摄像头，1代表前置摄像头）
    private final int RESULT_CODE_CAMERA = 1;//判断是否有拍照权限的标识码
    private CameraDevice cameraDevice;
    private CameraCaptureSession mPreviewSession;
    private CaptureRequest.Builder mCaptureRequestBuilder, captureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private ImageReader imageReader;
    private int height = 0, width = 0;
    private Size previewSize;
    private ImageView imagev;

    private ImageReader saveImageReader;
    byte[] saveImageData;
    private ImageButton reTakePhoto;
    private ImageButton surePhoto;
    private ImageButton closePhoto;
    private ImageView confirm_iv;
    private ImageView finess_floating_layer_im;
    private RelativeLayout confirm_layout;
    private RelativeLayout camera_layout;
    private ProgressDialog dialog;
    private String isFloatLayer;
    private String cameraPath;
    private String floatLayerImgType;//浮层图片 1男正面 2男反面 3男侧面 4女正面 5女反面 6女侧面

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfit_camera);
        context = this;
        isFloatLayer = getIntent().getStringExtra("isFloatLayer");//是否有浮层 1有
        cameraPath = getIntent().getStringExtra("cameraPath");//图片保存地址
        if (isFloatLayer != null && isFloatLayer.equals("1")) {
            floatLayerImgType = getIntent().getStringExtra("floatLayerImgType");//浮层图片 1男正面 2男反面 3男侧面 4女正面 5女反面 6女侧面
        }
        LogUtil.i("isFloatLayer--" + isFloatLayer);
        LogUtil.i("cameraPath--" + cameraPath);
        LogUtil.i("floatLayerImgType--" + floatLayerImgType);
        initView();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        textureView = findViewById(R.id.textureView);
        takePhoto = findViewById(R.id.takePhoto);
        imagev = findViewById(R.id.iv);
        backBtn = findViewById(R.id.backBtn);
        cameraSwap = findViewById(R.id.cameraSwap);

        reTakePhoto = findViewById(R.id.reTakePhoto);
        surePhoto = findViewById(R.id.surePhoto);
        closePhoto = findViewById(R.id.closePhoto);
        confirm_iv = findViewById(R.id.confirm_iv);
        confirm_layout = findViewById(R.id.confirm_layout);
        camera_layout = findViewById(R.id.camera_layout);
        finess_floating_layer_im = findViewById(R.id.finess_floating_layer_im);
        if (isFloatLayer != null && isFloatLayer.equals("1")) {
            finess_floating_layer_im.setVisibility(View.VISIBLE);
            switch (floatLayerImgType) {
                case "1":
                    finess_floating_layer_im.setBackground(getResources().getDrawable(R.drawable.finess_camera_man_positive));
                    break;
                case "2":
                    finess_floating_layer_im.setBackground(getResources().getDrawable(R.drawable.finess_camera_man_reverse));
                    break;
                case "3":
                    finess_floating_layer_im.setBackground(getResources().getDrawable(R.drawable.finess_camera_man_side));
                    break;
                case "4":
                    finess_floating_layer_im.setBackground(getResources().getDrawable(R.drawable.finess_camera_woman_positive));
                    break;
                case "5":
                    finess_floating_layer_im.setBackground(getResources().getDrawable(R.drawable.finess_camera_woman_reverse));
                    break;
                case "6":
                    finess_floating_layer_im.setBackground(getResources().getDrawable(R.drawable.finess_camera_woman_side));
                    break;
            }
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("保存中……");

        //重新拍照
        reTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_layout.setVisibility(View.GONE);
                camera_layout.setVisibility(View.VISIBLE);
                if (cameraDevice != null) {
                    stopCamera();
                }
                startCamera();
            }
        });

        //确定保存拍照
        surePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveImageData != null) {
                    dialog.show();
                    reTakePhoto.setFocusable(false);
                    surePhoto.setFocusable(false);
                    closePhoto.setFocusable(false);
                    saveImage();

                }
            }
        });

        //关闭此页面
        closePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(-1, new Intent(context, BodyZongHeActivity.class));
                finish();
            }
        });

        //拍照
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        cameraSwap.setOnClickListener(new View.OnClickListener() {//摄像头id（通常0代表后置摄像头，1代表前置摄像头）
            @Override
            public void onClick(View v) {
                if (mCameraId.equals("0")) {
                    mCameraId = "1";
                } else {
                    mCameraId = "0";
                }
                if (cameraDevice != null) {
                    stopCamera();
                }
                startCamera();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(-1, new Intent(context, BodyZongHeActivity.class));
                finish();
            }
        });
        //设置TextureView监听
        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(-1, new Intent(context, BodyZongHeActivity.class));
        finish();
        return super.onKeyDown(keyCode, event);
    }

    //-------------------------------------------------------------------------------------
    int mDisplayWidth = 0;
    int mDisplayHeight = 0;
    int mPreviewWidth = 640;
    int mPreviewHeight = 480;
    /**
     * TextureView的监听
     */
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        //可用
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            FloatingLayerCameraActivity.this.width = width;
            FloatingLayerCameraActivity.this.height = height;
            int mWidth = FloatingLayerCameraActivity.this.width;
            int mHeight = FloatingLayerCameraActivity.this.height;
            RectF previewRect = new RectF(0, 0, mWidth, mHeight);
            double aspect = (double) mPreviewWidth / mPreviewHeight;

            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT) {
                aspect = 1 / aspect;
            }

            if (mWidth < (mHeight * aspect)) {
                mDisplayWidth = mWidth;
                mDisplayHeight = (int) (mHeight * aspect + .5);
            } else {
                mDisplayWidth = (int) (mWidth / aspect + .5);
                mDisplayHeight = mHeight;
            }
            LogUtil.i("mHeight--" + mHeight);
            if (mWidth == 1080 && mHeight == 1857) {//1080*1920 如此
                mDisplayWidth = (int) (mWidth / aspect + .5);
                mDisplayHeight = 1920;
            }

            RectF surfaceDimensions = new RectF(0, 0, mDisplayWidth, mDisplayHeight);
            Matrix matrix = new Matrix();
            matrix.setRectToRect(previewRect, surfaceDimensions, Matrix.ScaleToFit.FILL);
            textureView.setTransform(matrix);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        //释放
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            stopCamera();
            return true;
        }

        //更新
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };


    /**
     * 打开摄像头
     */
    @SuppressLint("NewApi")
    private void openCamera() {
        @SuppressLint("WrongConstant") CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //设置摄像头特性
        setCameraCharacteristics(manager);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //提示用户开户权限
                String[] perms = {"android.permission.CAMERA"};
                ActivityCompat.requestPermissions(FloatingLayerCameraActivity.this, perms, RESULT_CODE_CAMERA);

            } else {
                manager.openCamera(mCameraId, stateCallback, null);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置摄像头的参数
     */
    @SuppressLint("NewApi")
    private void setCameraCharacteristics(CameraManager manager) {
        try {
            // 获取指定摄像头的特性
            CameraCharacteristics characteristics
                    = manager.getCameraCharacteristics(mCameraId);
            // 获取摄像头支持的配置属性
            StreamConfigurationMap map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            // 获取摄像头支持的最大尺寸
            Size largest = Collections.max(
                    Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
            // 创建一个ImageReader对象，用于获取摄像头的图像数据
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                    ImageFormat.JPEG, 2);
            //设置获取图片的监听
            imageReader.setOnImageAvailableListener(imageAvailableListener, null);
            // 获取最佳的预览尺寸
            previewSize = chooseOptimalSize(map.getOutputSizes(
                    SurfaceTexture.class), width, height, largest);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
    }

    @SuppressLint("NewApi")
    private static Size chooseOptimalSize(Size[] choices
            , int width, int height, Size aspectRatio) {
        // 收集摄像头支持的大过预览Surface的分辨率
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        // 如果找到多个预览尺寸，获取其中面积最小的
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            //没有合适的预览尺寸
            return choices[0];
        }
    }


    // 为Size定义一个比较器Comparator
    @SuppressLint("NewApi")
    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            // 强转为long保证不会发生溢出
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    /**
     * 摄像头状态的监听
     */
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        // 摄像头被打开时触发该方法
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            FloatingLayerCameraActivity.this.cameraDevice = cameraDevice;
            // 开始预览
            takePreview();
        }

        // 摄像头断开连接时触发该方法
        @SuppressLint("NewApi")
        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            FloatingLayerCameraActivity.this.cameraDevice.close();
            FloatingLayerCameraActivity.this.cameraDevice = null;
        }

        // 打开摄像头出现错误时触发该方法
        @SuppressLint("NewApi")
        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            cameraDevice.close();
        }
    };

    /**
     * 开始预览
     */
    @SuppressLint("NewApi")
    private void takePreview() {
        SurfaceTexture mSurfaceTexture = textureView.getSurfaceTexture();
        //设置TextureView的缓冲区大小
        mSurfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        //获取Surface显示预览数据
        Surface mSurface = new Surface(mSurfaceTexture);
        try {
            //创建预览请求
            mCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 设置自动对焦模式
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //设置Surface作为预览数据的显示界面
            mCaptureRequestBuilder.addTarget(mSurface);
            //创建相机捕获会话，第一个参数是捕获数据的输出Surface列表，第二个参数是CameraCaptureSession的状态回调接口，当它创建好后会回调onConfigured方法，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            cameraDevice.createCaptureSession(Arrays.asList(mSurface, imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        //开始预览
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        mPreviewSession = session;
                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                        mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     */
    @SuppressLint("NewApi")
    private void takePicture() {
        try {
            if (cameraDevice == null) {
                return;
            }
            // 创建拍照请求
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 设置自动对焦模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 将imageReader的surface设为目标
            captureRequestBuilder.addTarget(imageReader.getSurface());
            // 获取设备方向
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION
                    , ORIENTATIONS.get(rotation));
            // 停止连续取景
            mPreviewSession.stopRepeating();
            //拍照
            CaptureRequest captureRequest = captureRequestBuilder.build();
            //设置拍照监听
            mPreviewSession.capture(captureRequest, captureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听拍照结果
     */
    @SuppressLint("NewApi")
    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {
        // 拍照成功
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            // 重设自动对焦模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            // 设置自动曝光模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            try {
                //重新进行预览
                mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }
    };

    /**
     * 监听拍照的图片
     */
    @SuppressLint("NewApi")
    private ImageReader.OnImageAvailableListener imageAvailableListener = new ImageReader.OnImageAvailableListener() {
        // 当照片数据可用时激发该方法
        @Override
        public void onImageAvailable(ImageReader reader) {
            saveImageReader = reader;
            // 获取捕获的照片数据
            Image image = reader.acquireNextImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            saveImageData = data;
            //显示图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            confirm_iv.setImageBitmap(UIUtils.rotateBitmap(bitmap, 90));//旋转90并且赋值
            confirm_layout.setVisibility(View.VISIBLE);
            camera_layout.setVisibility(View.GONE);
        }
    };

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case RESULT_CODE_CAMERA:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    openCamera();
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    Toast.makeText(FloatingLayerCameraActivity.this, "请开启应用拍照权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 启动拍照
     */
    private void startCamera() {
        if (textureView.isAvailable()) {
            if (cameraDevice == null) {
                openCamera();
            }
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    /**
     * 停止拍照释放资源
     */
    @SuppressLint("NewApi")
    private void stopCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void saveImage() {
        //先验证手机是否有sdcard
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "你的sd卡不可用。", Toast.LENGTH_SHORT).show();
            return;
        }
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        String picturePath = System.currentTimeMillis() + ".jpg";
        cameraPath=picturePath;
        LogUtil.i("cameraPath--"+cameraPath);
//        String picturePath = cameraPath;
        File file = new File(galleryPath, picturePath);
        try {
            //存到本地相册
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(saveImageData);
            fileOutputStream.close();

            //显示图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeByteArray(saveImageData, 0, saveImageData.length, options);
            //通知相册更新
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    UIUtils.rotateBitmap(bitmap, 90), picturePath, null);
//            //通知相册更新
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    bitmap, picturePath, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            reTakePhoto.setFocusable(true);
            surePhoto.setFocusable(true);
            closePhoto.setFocusable(true);
            dialog.dismiss();
//            ToastUtils.showToastShort("图片保存成功");
            LogUtil.i("cameraPath--"+galleryPath+picturePath);
            setResult(1, new Intent(context, BodyZongHeActivity.class).putExtra("picturePath",galleryPath+picturePath));
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraDevice != null) {
            stopCamera();
        }
    }
}
