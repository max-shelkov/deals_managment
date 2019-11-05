package com.gecko.dealsmanagment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DealsKeeper extends AppCompatActivity {

    private static final String TAG = "myLog";
    private static final String SAVE_DEALS_FILE_NAME = "deals.save";


    private List<Deal> mDeals;

    public DealsKeeper() {
        Log.d(TAG, "DealsLoader constructor");
        mDeals = new ArrayList<>();
        deserializeDeals();
    }

    public List<Deal> getDeals(){
        return mDeals;
    }

    public void setDeals(List<Deal> deals) {
        mDeals = deals;
    }

    public void printDealsToLog(){
        for (Deal d : mDeals) {
            Log.d(TAG, "owner = " + d.getOwner());
        }
    }

    public List<Deal> dealsLoader(Uri uri) {
        Log.d(TAG, "deals loader from Uri started");

        String s = uri.getPath();
        int index = s.indexOf(":");
        String s1 = s.substring(index+1);
        String s2 = String.valueOf(Environment.getExternalStorageDirectory());
        String s3 = s2 +"/"+ s1;
        String s4 = "/storage/emulated/0/Download/201910.xls";

        File sdPath = Environment.getExternalStorageDirectory();
//        File sdFile = new File(sdPath.getAbsolutePath() + "/" + s1);
        File sdFile = new File(s1);

        Log.d(TAG, "dealLoader from Uri: sdFile = " + sdFile.getPath() );
        List<Deal> deals = new ArrayList<>();
//        File file = new File(s4);
        try {
            Workbook wb = new HSSFWorkbook(POIFSFileSystem.create(sdFile));
//            Workbook wb = WorkbookFactory.create(sdFile);
        } catch (IOException e) {
            Log.e(TAG, "error when try to create workbook " + e);
            e.printStackTrace();
        }

        return deals;
    }

    public List<Deal> dealsLoader(String fileName){
        Log.d(TAG, "deals loader from xls started");
        ArrayList<Deal> deals = new ArrayList<>();
        try {
            AssetManager am = MainActivity.getAppContext().getAssets();
            if (am == null){
                Log.d(TAG, "assets not found");
            } else {
                Log.d(TAG, "assets found");
            }
            Workbook wb = new HSSFWorkbook(am.open("201910.xls"));
//            Workbook wb = WorkbookFactory.create(am.open(fileName));
            Sheet sheet = wb.getSheet("текущие");
            Log.d(TAG, "after got sheet");
            for (int i = 1; i < (sheet.getPhysicalNumberOfRows()-1); i++) {
//            for (int i = 1; i < 20; i++) {
                Row row = sheet.getRow(i);
                Cell cellFirmName = row.getCell(1);
                if (cellFirmName.getStringCellValue().equals("")){
                    break;
                }
                Cell cellOwner = row.getCell(11);
                Cell cellContractor = row.getCell(2);
                Cell cellPriceVolume = row.getCell(4);
                Cell cellRealVolume = row.getCell(5);
                Cell cellToPay = row.getCell(7);
                Cell cellStartMonth = row.getCell(12);
                Log.d(TAG, "start month cell["+ i + "] value = " + cellStartMonth.getNumericCellValue());
                Calendar startDate = Calendar.getInstance();
                startDate.set(1900, 00,01);
                startDate.add(Calendar.DAY_OF_YEAR, (int)cellStartMonth.getNumericCellValue());
                startDate.set(Calendar.DAY_OF_MONTH, 1);
                Log.d(TAG, cellFirmName.getStringCellValue() +
                        ": startDate = " + startDate.get(Calendar.YEAR)+"."+(startDate.get(Calendar.MONTH)+1)+"."+startDate.get(Calendar.DAY_OF_MONTH));


                Cell cellDuration = row.getCell(14);
//                Log.d(TAG, "cellFirmName[" + i + "] = " + cellFirmName.getStringCellValue());
                Deal d = new Deal(cellOwner.getStringCellValue()
                        ,cellFirmName.getStringCellValue()
                        ,cellContractor.getStringCellValue()
                        ,Deal.DEAL_STATUS_CURRENT
                        ,(float)cellPriceVolume.getNumericCellValue()
                        ,(float)cellRealVolume.getNumericCellValue()
                        ,startDate
                        ,(short)cellDuration.getNumericCellValue());
                d.setToPay((float)cellToPay.getNumericCellValue());
                deals.add(d);

            }
            Log.d(TAG, "after cycle");
        } catch (IOException e) {
            Log.e(TAG, "can not open file: " + fileName);
            e.printStackTrace();
        }
        mDeals = deals;
        return mDeals;
    }


    public void serializeDeals(){
        Log.d(TAG, "start Serializing deals array");
        try {
            FileOutputStream fos = MainActivity.getAppContext().openFileOutput(SAVE_DEALS_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mDeals);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            Log.e(TAG, "error when serializing" + e);
            e.printStackTrace();
        }
        printDealsToLog();
        Log.d(TAG, "Serialing deals array finished");
    }

    public List<Deal> deserializeDeals(){
        try {
            FileInputStream fis = MainActivity.getAppContext().openFileInput(SAVE_DEALS_FILE_NAME);
            ObjectInputStream oin = new ObjectInputStream(fis);
            mDeals = (List<Deal>) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "error when deserializing" + e);
            e.printStackTrace();
        }
        printDealsToLog();
        Log.d(TAG, "Deserializing deals array finished");
        return mDeals;
    }



}
