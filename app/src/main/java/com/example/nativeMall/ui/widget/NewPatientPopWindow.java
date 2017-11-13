package com.example.nativeMall.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.nativeMall.Adapter.AllPatientAdapter;
import com.example.nativeMall.Bean.PatientBean;
import com.example.nativeMall.Config;
import com.example.nativeMall.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Du on 2016/8/26.
 */
public class NewPatientPopWindow extends PopupWindow {

    private RecyclerView rvAllPatient;
    private Button btnNew, btnCancel;
    private RecyclerView.LayoutManager layoutManager;
    private View contentView;
    private NewAndCancelClickListener newAndCancelClickListener = null;
    private List listPatientName = new ArrayList<>();
    private AllPatientAdapter allPatientAdapter;
    private static final String TAG = "NewPatientPopWindow";

    public interface NewAndCancelClickListener {
        void newClick(Boolean click);

        void cancleClick(Boolean click);

        void listClick(PatientBean patientBean);
    }

    //    public NewPatientPopWindow(Activity context,View.OnClickListener itemsOnClick) {
    public NewPatientPopWindow(Context context) {
       super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popw_patient_choose, null);
        btnNew = (Button) contentView.findViewById(R.id.btn_new);
        btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
        rvAllPatient = (RecyclerView) contentView.findViewById(R.id.rv_all_patient);
        layoutManager = new LinearLayoutManager(context);
        rvAllPatient.setLayoutManager(layoutManager);


        if (Config.patientBeanList.size() > 0) {
            for (int i = 0; i < Config.patientBeanList.size(); i++) {

                listPatientName.add(Config.patientBeanList.get(i).getPatientName());
                //有缓存后将病人列表存入缓存，除去else的内容
            }
        }
//        else {
//            //LoginActivity.PatientInfoByAsyncHttpClientGet();
//
//            for (int i = 0; i < Config.patientBeanList.size();i++){
//                listPatientName.add(Config.patientBeanList.get(i).getPatientName());
//            }
//        }
        allPatientAdapter = new AllPatientAdapter(listPatientName);
        rvAllPatient.setAdapter(allPatientAdapter);
        allPatientAdapter.setOnItemClickListener(new AllPatientAdapter
                .OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                newAndCancelClickListener.listClick(Config.patientBeanList.get(position));

            }
        });


        //设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAndCancelClickListener.cancleClick(true);
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newAndCancelClickListener.newClick(true);
            }
        });
    }

    public void setNewAndCancelClickListener(NewAndCancelClickListener ClickListener) {

        this.newAndCancelClickListener = ClickListener;
    }


}
