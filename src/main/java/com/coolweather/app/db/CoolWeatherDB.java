package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZZH on 2016/08/25.
 */
public class CoolWeatherDB {
    public static final String DB_NAME = "cool_weather";
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase sqLiteDatabase;

    /**
     *构造方法私有化
     */
    public CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    /**
     * 获取CoolWeatherDB实例
     */
    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB ==null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储到数据库
     */
    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            sqLiteDatabase.insert("province",null,values);
        }
    }
    /**
     *从数据库获取全国省份信息
     */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = sqLiteDatabase.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
    /**
     * 将City实例保存到数据库
     */
    public void saveCity(City city){
        if (city!=null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            sqLiteDatabase.insert("City",null,values);
        }
    }
    /**
     * 从数据库获取默省下的所有城市列表
     */
    public List<City> loadCity(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = sqLiteDatabase.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while(cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
    /**
     * 将County实例存储到数据库
     */
    public void saveCounty(County county){
        if (county!=null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            sqLiteDatabase.insert("County",null,values);
        }
    }
    /**
     * 从何数据库读取某城市下的县信息
     */
    public List<County> loadCounty(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = sqLiteDatabase.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
}