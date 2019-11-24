package com.gecko.dealsmanagment;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.gecko.dealsmanagment.GeckoUtils.formattedInt;
import static com.gecko.dealsmanagment.GeckoUtils.monthsBetween;
import static com.gecko.dealsmanagment.GeckoUtils.msXlsCellToInt;
import static com.gecko.dealsmanagment.GeckoUtils.msXlsDateToCalendar;

public class DealsKeeper{

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

    public void addDeal(Deal d){
        mDeals.add(d);
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
        String s4 = "/storage/emulated/0/Download/201911.xls";

        File sdPath = Environment.getExternalStorageDirectory();
//        File sdFile = new File(sdPath.getAbsolutePath() + "/" + s1);
        File sdFile = new File(s1);

        Log.d(TAG, "dealLoader from Uri: sdFile = " + sdFile.getPath() );
        List<Deal> deals = new ArrayList<>();
//        File file = new File(s4);
        try {
            //
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
            assert am != null;
            Workbook wb = new HSSFWorkbook(am.open("201911.xls"));
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
                Cell cellStatus = row.getCell(3);
                Cell cellPriceVolume = row.getCell(4);
                cellPriceVolume.setCellType(CellType.STRING);
                Cell cellRealVolume = row.getCell(5);
                cellRealVolume.setCellType(CellType.STRING);
                Cell cellToPay = row.getCell(7);
                Cell cellStartMonth = row.getCell(12);
                Cell cellBalance = row.getCell(6);
                Calendar startDate = msXlsDateToCalendar((int)cellStartMonth.getNumericCellValue());
                Log.d(TAG, cellFirmName.getStringCellValue() +
                        ": startDate = " + startDate.get(Calendar.YEAR)+"."+(startDate.get(Calendar.MONTH)+1)+"."+startDate.get(Calendar.DAY_OF_MONTH));
                Cell cellDuration = row.getCell(14);
                Cell cellPayPlanDate = row.getCell(8);

                Calendar payPlanDate = null;
                if (cellPayPlanDate.getNumericCellValue() > 0){
                    payPlanDate = msXlsDateToCalendar((int)cellPayPlanDate.getNumericCellValue());
                }
                Cell cellPaid = row.getCell(9);

                Cell cellPayRealDate = row.getCell(10);
                Calendar payRealDate = null;
                if (cellPayRealDate.getNumericCellValue()>0){
                    payRealDate = msXlsDateToCalendar((int)cellPayRealDate.getNumericCellValue());
                }

//                Log.d(TAG, "cellFirmName[" + i + "] = " + cellFirmName.getStringCellValue());
                Deal d = new Deal(cellOwner.getStringCellValue()
                        ,cellFirmName.getStringCellValue()
                        ,cellContractor.getStringCellValue()
                        ,defineStatus(cellStatus.getStringCellValue())
                        ,msXlsCellToInt(cellPriceVolume.getStringCellValue())
                        ,msXlsCellToInt(cellRealVolume.getStringCellValue())
                        ,startDate
                        ,(int)cellDuration.getNumericCellValue());
                d.setType(defineType(cellStatus.getStringCellValue()));
                d.setToPay((int)(cellToPay.getNumericCellValue()*100));
                d.setBalance((int)(cellBalance.getNumericCellValue())*100);
                d.setPayPlanDate(payPlanDate);
                d.setPaid((int)(cellPaid.getNumericCellValue())*100);
                d.setPayRealDate(payRealDate);
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

    public String[] findMppFromDeals(){
        Set<String> mppSet = new HashSet<>();
        for (int i = 0; i < mDeals.size(); i++) {
            mppSet.add(mDeals.get(i).getOwner());
        }
        return mppSet.toArray(new String[0]);
    }

    public String[] findProlongations(){
        List<String> prolongationsList = new ArrayList<>();
        for (int i = 0; i < mDeals.size(); i++) {

            if (monthsBetween(mDeals.get(i).getStartMonth(), Calendar.getInstance())==mDeals.get(i).getDuration()){
                prolongationsList.add(mDeals.get(i).getName()+": "+formattedInt(mDeals.get(i).getPriceVolume()));
            }
        }
        return prolongationsList.toArray(new String[0]);
    }

    public String[] findDealsForOversell(){
        Set<String> dealsNames = new HashSet<>();
        for (int i = 0; i < mDeals.size(); i++) {
            dealsNames.add(mDeals.get(i).getName());
        }
        return dealsNames.toArray(new String[0]);
    }

    public Deal findDealByNameAndVolume(String name, int volume){
        for (int i = 0; i < mDeals.size(); i++) {
            if (mDeals.get(i).getName().equals(name)&&mDeals.get(i).getPriceVolume()==volume){
                return mDeals.get(i);
            }
        }
        return null;
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
//        printDealsToLog();
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
//        printDealsToLog();
        Log.d(TAG, "Deserializing deals array finished");
        return mDeals;
    }

    public void replaceDeal(Deal d) {
        for (int i = 0; i < mDeals.size(); i++) {
            if(d.getId().equals(mDeals.get(i).getId())){
                mDeals.set(i, d);
            }
        }
    }

    public int getCurrentVolumePrice(){
        int vol = 0;
        for (int i = 0; i < mDeals.size(); i++) {
            if (mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_CURRENT)
                ||mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_PROLONGATION)
                ||mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_BREAK)
                ||mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_PREPROLONGATION)){
                vol = vol + mDeals.get(i).getPriceVolume();
            }
        }
        return vol;
    }

    public int getCurrentVolumeReal(){
        int vol = 0;
        int x = 0;
        for (int i = 0; i < mDeals.size(); i++) {
            if (mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_CURRENT)
                    ||mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_PROLONGATION)
                    ||mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_BREAK)
                    ||mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_PREPROLONGATION)){
                x = mDeals.get(i).getRealVolume();

            }
            vol = vol + x;

            Log.d(TAG, "company = " + mDeals.get(i).getName() +" Vf = " + mDeals.get(i).getRealVolume()+" vol real = " + vol);
        }
        return vol;

    }

    public int getProlongationVolumePrice(){
        int vol = 0;
        for (int i = 0; i < mDeals.size(); i++) {
            if (mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_PROLONGATION)){
                vol+=mDeals.get(i).getPriceVolume();
            }
        }
        return vol;
    }

    public int getProlongationVolumeReal(){
        int vol = 0;
        for (int i = 0; i < mDeals.size(); i++) {
            if (mDeals.get(i).getStatus().equals(Deal.DEAL_STATUS_PROLONGATION)){
                vol+=mDeals.get(i).getRealVolume();
            }
        }
        return vol;
    }

    public int getUniqueClientsCount(){
        Set<String> uniqueNames = new HashSet();
        for (int i = 0; i < mDeals.size(); i++) {
            uniqueNames.add(mDeals.get(i).getName());
        }
        return uniqueNames.size();
    }

    private String defineStatus(String status){
        if (status.equals("Продажи")){
            return Deal.DEAL_STATUS_CURRENT;
        } else if (status.equals("Расторжение")){
            return Deal.DEAL_STATUS_BREAK;
        } else if (status.equals("Новый")){
            return Deal.DEAL_STATUS_NEW;
        } else if (status.equals("Допродажа")){
            return Deal.DEAL_STATUS_OVERSELL;
        }else if (status.equals("Продление")){
            return Deal.DEAL_STATUS_PROLONGATION;
        } else if (status.equals("Регионалка")){
            return Deal.DEAL_STATUS_CURRENT;
        } else {
            return "";
        }
    }

    private String defineType(String type){
        if (type.equals("Продажи")
                ||type.equals("Расторжение")
                ||type.equals("Новый")
                ||type.equals("Допродажа")
                ||type.equals("Продление")){
            return Deal.DEAL_TYPE_SELL;
        } else if(type.equals("Рекламный бартер")){
            return Deal.DEAL_TYPE_EXCHANGE;
        } else if(type.equals("Товарный бартер")){
            return Deal.DEAL_TYPE_EXCHANGE;
        } else if(type.equals("Входящая регионалка")) {
            return Deal.DEAL_TYPE_REGIONAL_IN;
        } else if(type.equals("Исходящая регионалка")){
            return Deal.DEAL_TYPE_REGIONAL_OUT;
        } else {
            return "";
        }
    }


    public String[] getNames(){
        Set<String> nSet = new HashSet<>();
        for (int i = 0; i < mDeals.size(); i++) {
            nSet.add(mDeals.get(i).getName());
        }
        return nSet.toArray(new String[0]);
    }

    public String[] getPps3Names(){
        Set<String> pps3 = new HashSet<>();
        for (int i = 0; i < mDeals.size() ; i++) {
            if (monthsBetween(Calendar.getInstance(), mDeals.get(i).getFinishMonth())==2){
                pps3.add(mDeals.get(i).getName());
            }
        }
        return pps3.toArray(new String[0]);
    }

    public String findContractorByFirmName(String name){
        for (Deal d: mDeals) {
            if (d.getName().equals(name)){
                return d.getContractor();
            }
        }
        return null;
    }

    public String findOwnerByFirmName(String name){
        for (Deal d: mDeals) {
            if (d.getName().equals(name)){
                return d.getOwner();
            }
        }
        return null;
    }

    public void sortDeals() {
        Collections.sort(mDeals);
    }
}
