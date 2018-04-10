package cn.sdt.ottadvert.db;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.sdt.ottadvert.db.bean.ErrorMD5Material;
import cn.sdt.ottadvert.db.bean.LastPlayDate;
import cn.sdt.ottadvert.db.bean.Material;
import cn.sdt.ottadvert.db.dao.DefaultMaterialDao;
import cn.sdt.ottadvert.db.dao.ErrorMD5MaterialDao;
import cn.sdt.ottadvert.db.dao.LastPlayDateDao;
import cn.sdt.ottadvert.db.dao.MaterialDao;
import cn.sdt.ottadvert.helper.R;

public class DbOperationActivity extends AppCompatActivity {
    public final static String TAG = "DbActivity";
    private final static int INSERT = 0;
    private final static int UPDATE = 1;
    private final static int QUERY_ONE = 2;
    private final static int QUERY_ALL = 3;
    private final static int DELETE = 4;
    Handler mHandler;
    HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_operation);
        findViewById(R.id.insert).requestFocus();

        handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "thread::" + Thread.currentThread().getName());//子线程
                switch (msg.what) {
                    case INSERT:
                        int id = (int) insert();
                        break;
                    case UPDATE:
                        int result = update(123);
                        break;
                    case DELETE:
                        result = delete(1);
                        break;
                    case QUERY_ONE:
                        queryById(123);
                        break;
                    case QUERY_ALL:
                        queryAll();
                        break;
                }
            }
        };

        muiltThreadOpt();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        handlerThread.quit();
    }

    public void btnClcik(View view) {
        switch (view.getId()) {
            case R.id.insert:
                mHandler.sendEmptyMessage(INSERT);
                break;
            case R.id.update:
                mHandler.sendEmptyMessage(UPDATE);
                break;
            case R.id.deleteById:
                mHandler.sendEmptyMessage(DELETE);
                break;
            case R.id.queryById:
                mHandler.sendEmptyMessage(QUERY_ONE);
                break;
            case R.id.queryAll:
                mHandler.sendEmptyMessage(QUERY_ALL);
                break;

        }
    }


    private long insert() {
        Material material = new Material();
        material.setWidth(1080);
        material.setHeight(1920);
        material.setDuration(20);
        material.setMaterialType(0);
        material.setSpaceCode("ottBoot");
        material.setMaterialPath("data/data/pkgname/advert/adsys/video/4/96b5dd6e49734b1b8fe651fb7d9ed85a.mp4");
        material.setMaterialUrl("http://mifeng.skyworthbox.com:12000/adsys/video/4/96b5dd6e49734b1b8fe651fb7d9ed85a.mp4");
        material.setMd5("20c360e444895a94e79999054dfb058b");
        material.setMonitorUrl("http://www.baidu.com?test=222&aa=leu");
        material.setScheduleId(122);
        material.setMaterialId(255);
        material.setActionType(0);
        material.setAction("com.test.midea.PlayActivity");
        material.setOrderNo("2233");
        material.setStartTime(System.currentTimeMillis());
        material.setEndTime(System.currentTimeMillis());
        material.setDeliveryLimit(8);
        material.setDeliverySpeed(0);
        material.setIsCurrent("Y");
        material.setDuration(0);
        material.setPlayCount(0);
        MaterialDao dao = new MaterialDao(this);
        long rowId = dao.insert(material);
        return rowId;
    }

    private int update(int id) {
        MaterialDao dao = new MaterialDao(this);
        Material material = dao.query((int) id);
        if (material != null) {
            Log.d(TAG, "material:" + material.toString());
            material.setWidth(720);
            material.setHeight(1280);
            int result = dao.update(material);
            if (result > 0) {
                Log.d(TAG, "update:成功");
            } else {
                Log.d(TAG, "update 失败");
            }
            return result;
        } else {
            return -99;
        }
    }

    private int delete(int id) {
        MaterialDao dao = new MaterialDao(this);
        int result = dao.delete(id);

        if (result > 0) {
            Log.d(TAG, "delete:" + result + "成功");
        } else {
            Log.d(TAG, "delete:" + result + "失败,无此记录");
        }
        return result;
    }

    private void deleteAll() {
        DefaultMaterialDao dao = new DefaultMaterialDao(this);
        int result = dao.deleteAll();
        if (result > 0) {
            Log.d(TAG, "delete成功");
        } else {
            Log.d(TAG, "delete失败,无此记录");
        }
    }


    private void queryAll() {
        MaterialDao dao = new MaterialDao(this);
        List<Material> materialList = dao.queryAll();
        if (materialList != null) {
            for (Material material : materialList) {
                Log.d(TAG, "materal:" + material);
            }
        } else {
            Log.d(TAG, "表数据为空");
        }
    }


    private void queryById(int id) {
        MaterialDao dao = new MaterialDao(this);
        Material material = dao.query(id);
        if (material != null) {
            Log.d(TAG, "material:" + material);
        } else {
            Log.d(TAG, "查无此数据:" + id);
        }
    }


    private void muiltThreadOpt() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                insertErrorMaterial();
            }
        }, 5000, 4 * 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateErrorMaterial1();
            }
        }, 6000, 6 * 1000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateErrorMaterial2();
            }
        }, 6000, 8 * 1000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deleteErrorMaterial();
            }
        }, 10000, 60 * 1000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                insertLastPlayDate();
            }
        }, 10000, 60 * 1000);

    }


    private void insertErrorMaterial() {
        ErrorMD5MaterialDao dao = new ErrorMD5MaterialDao(this);
        ErrorMD5Material errorMaterial = new ErrorMD5Material();
        errorMaterial.setCreateTimestamp(System.currentTimeMillis());
        errorMaterial.setScheduleId(123);
        errorMaterial.setMaterialId(334);
        errorMaterial.setMd5("XF2XF3XF4");
        errorMaterial.setMaterialUrl("http://test.cccc.com");
        long rowid = dao.insert(errorMaterial);
        Log.d(TAG, "errormaterial rowid:" + rowid);
    }


    private void updateErrorMaterial1() {
        ErrorMD5MaterialDao dao = new ErrorMD5MaterialDao(this);
        List<ErrorMD5Material> errorMD5Materials = dao.queryAll();
        if (errorMD5Materials != null) {
            for (ErrorMD5Material md5Material : errorMD5Materials) {
                md5Material.setMd5("7X7F7C7E7D7A7B");
                int result = dao.update(md5Material);
                Log.d(TAG, "result:" + result);
            }
        }
    }

    private void updateErrorMaterial2() {
        ErrorMD5MaterialDao dao = new ErrorMD5MaterialDao(this);
        List<ErrorMD5Material> errorMD5Materials = dao.queryAll();
        if (errorMD5Materials != null) {
            for (ErrorMD5Material md5Material : errorMD5Materials) {
                md5Material.setMaterialUrl("http://www.mibii.com?user=kan&level=2");
                int result = dao.update(md5Material);
                Log.d(TAG, "result:" + result);
            }
        }
    }

    private void deleteErrorMaterial() {
        ErrorMD5MaterialDao dao = new ErrorMD5MaterialDao(this);
        dao.deleteAll();
    }


    private void insertLastPlayDate() {
        LastPlayDateDao dao = new LastPlayDateDao(this);
        LastPlayDate lastPlayDate = new LastPlayDate();
        lastPlayDate.setScheduleId(133);
        lastPlayDate.setLastPlayTimestamp(System.currentTimeMillis());
        long rowid = dao.insert(lastPlayDate);
        Log.d(TAG, "lastplaydate rowid:" + rowid);
    }


    private void updateLastPlayDate() {
        LastPlayDateDao dao = new LastPlayDateDao(this);
        List<LastPlayDate> list = dao.queryAll();

        for (LastPlayDate lastPlayDate : list) {
            lastPlayDate.setScheduleId(266);
            dao.update(lastPlayDate);
        }
    }


}
