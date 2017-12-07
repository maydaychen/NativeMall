package com.example.nativeMall;

import com.example.nativeMall.Bean.PatientBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：JTR on 2016/8/29 13:56
 * 邮箱：2091320109@qq.com
 */
public class Config {
    public final static int line = 0xFFEEEEEE;


    public static boolean IS_LOG = false;

    public static String SIGN_TOKEN = "6357148a-43e9-45b0-81f1-f1b6a141ba2e";
    public static String LOG_IN_ID = "76c89e09-86d8-4cd5-aafa-af96bf1dca5f";
    public static String SEND_MSG_TOKEN = "521d23b9-c68e-40ff-a587-0e1859583224";
    public static String JIAOYAN_TOKEN = "ecc6ce4c-8eec-4bcd-aa39-938f568d47dc";

    public static String GLOBAL_URL3 = "http://101.231.124.9:56679/mcmall/phone/";
    public final static String GLOBAL_URL1 = "http://101.231.124.9:56679/mcmall/m/gyg/";
    public final static String PIC_URL = "http://101.231.124.9:56679/mcmall/";

    public static com.example.nativeMall.Bean.UserBean userBean = new com.example.nativeMall.Bean.UserBean();

    public static final int PASSWORD_MINGWEN = 0x90;
    public static final int PASSWORD_MIWEN = 0x81;

    public static int ToDoctListFlag; //为1进入DoctDateDetailAct,2进入OnlineAskDoctAct;

    public static List<PatientBean> patientBeanList = new ArrayList<>();

    public static String TAG = "chenyi";
}
