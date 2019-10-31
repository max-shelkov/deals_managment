package com.gecko.dealsmanagment;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DealsKeeper extends AppCompatActivity {

    private static final String TAG = "myLog";
    private static final String SAVE_DEALS_FILE_NAME = "deals.save";

    private List<Deal> mDeals;

    public DealsKeeper() {
        Log.d(TAG, "before start dealsLoader");
        mDeals = new ArrayList<>();
    }

    public List<Deal> getDeals(){
        return mDeals;
    }

    public void setDeals(List<Deal> deals) {
        mDeals = deals;
    }

    /*
    private List<Deal> dealsGenerator(int number){
        ArrayList<Deal> deals = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Deal d = new Deal(i%2 == 0 ? "Manager1" : "Manager2", "VeryImportantClie" + i, "Ivanov", Deal.DEAL_STATUS_CURRENT,
                    4470, 10, (short)11, (short)6);
            d.setToPay(d.getPriceVolume()*4);
            deals.add(d);
        }
        return deals;
    }
*/



    public void printDealsToLog(){
        for (Deal d : mDeals) {
            Log.d(TAG, "owner = " + d.getOwner());
        }
    }

    public List<Deal> dealsLoader(String fileName){
        Log.d(TAG, "deals loader started");
        ArrayList<Deal> deals = new ArrayList<>();
        try {
            AssetManager am = MainActivity.getAppContext().getAssets();
            if (am == null){
                Log.d(TAG, "assets not found");
            } else {
                Log.d(TAG, "assets found");
            }
            Log.d(TAG, "before open file");
            Workbook wb = WorkbookFactory.create(am.open(fileName));
            Log.d(TAG, "after open file");
            Sheet sheet = wb.getSheet("текущие");
            Log.d(TAG, "after got sheet");
//            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            for (int i = 1; i < 10; i++) {
                Row row = sheet.getRow(i);
                Cell cellFirmName = row.getCell(1);
                Cell cellOwner = row.getCell(11);
                Cell cellContractor = row.getCell(2);
                Cell cellPriceVolume = row.getCell(4);
                Cell cellToPay = row.getCell(7);
                if (cellFirmName.getStringCellValue().equals("")){
                    break;
                }
//                Log.d(TAG, "cellFirmName[" + i + "] = " + cellFirmName.getStringCellValue());
                Deal d = new Deal(cellOwner.getStringCellValue()
                        ,cellFirmName.getStringCellValue()
                        ,cellContractor.getStringCellValue()
                        ,Deal.DEAL_STATUS_CURRENT
                        ,(float)cellPriceVolume.getNumericCellValue()
                        ,10, (short)11, (short)6);
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
