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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DealsKeeper extends AppCompatActivity {

    public static final String TAG = "myLog";

    private List<Deal> mDeals;

    public DealsKeeper() {
//        mDeals = dealsGenerator(20);
        Log.d(TAG, "before start dealsLoader");
//        mDeals = dealsLoader("201910.xls");
        mDeals = new ArrayList<>();
    }

    public List<Deal> getDeals(){

        return mDeals;
    }

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



    public void printDealsToLog(){
        for (Deal d : mDeals) {
            Log.d(TAG, d.toString());
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
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
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


}
