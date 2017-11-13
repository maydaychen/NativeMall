package com.example.nativeMall.ui.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativeMall.Bean.CodeMsgItemBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.ImageTools;
import com.example.nativeMall.R;
import com.example.nativeMall.Util;
import com.example.nativeMall.ui.widget.NewPatientPopWindow;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class FreeAskAct extends AppCompatActivity {


    @BindView(R.id.iv_up_img)
    ImageView ivUpImg;
    @BindView(R.id.iv_choose_doc_back)
    ImageView ivChooseDocBack;
    @BindView(R.id.tv_chose_patient)
    TextView tvChosePatient;
    @BindView(R.id.et_question_bref)
    EditText etQuestionBref;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_disease_describe)
    EditText etDiseaseDescribe;

    private NewPatientPopWindow newPatientPopWindow;
    private static final int PHOTO_WITH_DATA = 18;  //从SD卡中得到图片
    private static final int PHOTO_WITH_CAMERA = 37;// 拍摄照片
    private String imgName = "";
    private com.example.nativeMall.Bean.PatientBean PatientBean; // 确定询问的病人
    private static final String TAG = "FreeAskAct";
    private ProgressDialog  progressDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_ask);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        InitView();
        InitListener();
    }

    private void InitView() {
        newPatientPopWindow = new NewPatientPopWindow(FreeAskAct.this);
    }

    private void InitListener() {


        newPatientPopWindow.setNewAndCancelClickListener(new NewPatientPopWindow
                .NewAndCancelClickListener() {
            @Override
            public void newClick(Boolean click) {
                Intent intent = new Intent(FreeAskAct.this, AddPatientAct.class);
                startActivity(intent);
                newPatientPopWindow.dismiss();
            }

            @Override
            public void cancleClick(Boolean click) {
                newPatientPopWindow.dismiss();
            }

            @Override
            public void listClick(com.example.nativeMall.Bean.PatientBean patientBean) {

                tvChosePatient.setText(patientBean.getPatientName());
            }
        });
    }

    @OnClick({R.id.rl_chose_patient, R.id.rl_add_picture, R.id.iv_choose_doc_back, R.id.tv_submit})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_chose_patient:
                if (Config.IS_LOG) {
                    newPatientPopWindow = new NewPatientPopWindow(FreeAskAct.this);
                    newPatientPopWindow.showAtLocation(FreeAskAct.this.findViewById(R.id
                            .rl_mine_title), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    newPatientPopWindow.setNewAndCancelClickListener(new NewPatientPopWindow
                            .NewAndCancelClickListener() {


                        @Override
                        public void newClick(Boolean click) {
                            Intent intent = new Intent(FreeAskAct.this, AddPatientAct.class);
                            startActivity(intent);
                            newPatientPopWindow.dismiss();
                        }

                        @Override
                        public void cancleClick(Boolean click) {
                            newPatientPopWindow.dismiss();
                        }

                        @Override
                        public void listClick(com.example.nativeMall.Bean.PatientBean patientBean) {
                            tvChosePatient.setText(patientBean.getPatientName());
                            PatientBean = patientBean;
                            newPatientPopWindow.dismiss();
                        }
                    });
                } else {
                    Util.show(FreeAskAct.this);
                }
                break;
            case R.id.rl_add_picture:
                openPictureSelectDialog();
                break;
            case R.id.iv_choose_doc_back:
                finish();
                break;
            case R.id.tv_submit:
                if (tvChosePatient.getText().equals("请选择") | etQuestionBref.getText().equals
                        ("请描述病症名称") | etDiseaseDescribe.getText().equals("为您的咨询写一个醒目的标题")) {
                    Toast.makeText(FreeAskAct.this, "请补全预约信息", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = ProgressDialog.show(FreeAskAct.this, "提交咨询",
                            "正在提交，请稍等");
                    getSubmitByAsyncClient();

                }


                break;
        }

    }

    private void getSubmitByAsyncClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        String URL = Config.GLOBAL_URL1 + "inquisitionInfo/saveInquisition?inquisitionBref=" +
                etQuestionBref.getText().toString() + "&inquisitionRequest=" + etDiseaseDescribe
                .getText().toString() + "&userId=" + Config.userBean.getData().getUid() +
                "&patientInfoId=" + PatientBean.getId() + "&imgUrl1=" + "%5B%5D";
        Log.i(TAG, "getSubmitByAsyncClient: " + URL);
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                progressDialog.dismiss();
                String result = new String(bytes);
                Gson gson = new Gson();
                CodeMsgItemBean codeMsgItemBean = gson.fromJson(result, CodeMsgItemBean.class);
                String MSG = codeMsgItemBean.getMSG();
                if (MSG.equals("success")) {
                    Intent intent = new Intent(FreeAskAct.this, MyQuestionAct.class);
                    startActivity(intent);
                    Toast.makeText(FreeAskAct.this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(FreeAskAct.this, "问题提交未成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(FreeAskAct.this,"网络异常，请检查网络状态",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 拍照获取相片
     **/
    private void doTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用系统相机

        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image" +
                ".jpg"));
        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        //直接使用，没有缩小
        startActivityForResult(intent, PHOTO_WITH_CAMERA);  //用户点击了从相机获取
    }

    /**
     * 从相册获取图片
     **/
    private void doPickPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");  // 开启Pictures画面Type设定为image
        intent.setAction(Intent.ACTION_GET_CONTENT); //使用Intent.ACTION_GET_CONTENT这个Action
        startActivityForResult(intent, PHOTO_WITH_DATA); //取得相片后返回到本画面
    }

    private void openPictureSelectDialog() {
        //自定义Context,添加主题
        Context dialogContext = new ContextThemeWrapper(FreeAskAct.this, android.R.style
                .Theme_Light);
        String[] choiceItems = new String[2];
        choiceItems[0] = "相机拍摄";  //拍照
        choiceItems[1] = "本地相册";  //从相册中选择
        ListAdapter adapter = new ArrayAdapter<String>(dialogContext, android.R.layout
                .simple_list_item_1, choiceItems);
        //对话框建立在刚才定义好的上下文上
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogContext);

        builder.setTitle("添加图片");
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:  //相机
                        doTakePhoto();
                        break;
                    case 1:  //从图库相册中选取
                        doPickPhotoFromGallery();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 创建图片不同的文件名
     **/
    private String createPhotoFileName() {
        String fileName;
        Date date = new Date(System.currentTimeMillis());  //系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return fileName;
    }

    /**
     * 保存图片到本应用下
     **/
    private void savePicture(String fileName, Bitmap bitmap) {

        FileOutputStream fos = null;
        try {//直接写入名称即可，没有会被自动创建；私有：只有本应用才能访问，重新内容写入会被覆盖
            fos = FreeAskAct.this.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把图片写入指定文件夹中

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {  //返回成功
            switch (requestCode) {
                case PHOTO_WITH_CAMERA: {//拍照获取图片
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) { //是否有SD卡

                        Bitmap bitmap = BitmapFactory.decodeFile(Environment
                                .getExternalStorageDirectory() + "/image.jpg");

                        imgName = createPhotoFileName();
                        //写一个方法将此文件保存到本应用下面啦
                        savePicture(imgName, bitmap);

                        if (bitmap != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth()
                                    / 10, bitmap.getHeight() / 10);
                            ivUpImg.setImageBitmap(smallBitmap);

                            Toast.makeText(FreeAskAct.this, "已保存本应用的files文件夹下", Toast
                                    .LENGTH_LONG).show();
                        }
//                        Toast.makeText(MainActivity.this, "已保存本应用的files文件夹下", Toast
// .LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FreeAskAct.this, "没有SD卡", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case PHOTO_WITH_DATA: {//从图库中选择图片
                    ContentResolver resolver = getContentResolver();
                    //照片的原始资源地址
                    Uri originalUri = data.getData();
                    //System.out.println(originalUri.toString());  //"
                    // content://media/external/images/media/15838 "

//                  //将原始路径转换成图片的路径
//                  String selectedImagePath = uri2filePath(originalUri);
//                  System.out.println(selectedImagePath);  //"
// /mnt/sdcard/DCIM/Camera/IMG_20130603_185143.jpg "
                    try {
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                        imgName = createPhotoFileName();
                        //写一个方法将此文件保存到本应用下面啦
                        savePicture(imgName, photo);

                        if (photo != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() /
                                    5, photo.getHeight() / 5);

                            ivUpImg.setImageBitmap(smallBitmap);
                            Log.i(TAG, "onActivityResult: " + smallBitmap);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
