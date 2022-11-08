package com.damasingo.SQL_MANAGER;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.damasingo.CLASS_UTIL.account;
import com.damasingo.CLASS_UTIL.reqeust;
import com.damasingo.CLASS_UTIL.score;
import com.damasingo.CLASS_UTIL.setting;
import com.damasingo.SharedPreferences.sharedpreferences;
import com.damasingo.sign_in;

import java.util.ArrayList;

public class sql_manager extends SQLiteOpenHelper {


    public static final String DATABASE_NAME ="damas.db";
    Context context;

    public static final String TABLE_ACCOUNT ="ACCOUNT",
                               TABLE_REQUEST ="REQUEST",
                               TABLE_SCORE ="SCORE",
                               TABLE_SETTING ="setting";


    public static final String TABLE_SETTING_ID = "ID_SETTING",
                               TABLE_SETTING_NAME = "Name",
                               TABLE_SETTING_EXACT_CHOICE = "exact_choise";


    public static final String TABLE_REQUEST_ID   ="id",
                               TABLE_REQUEST_NAME   ="name",
                               TABLE_REQUEST_STATE   ="STATE",
                               TABLE_REQUEST_TYPE   ="TYPE",
                               TABLE_REQUEST_PHOTO   ="image";


    public static final String TABLE_ACCOUNT_ID   ="id",
                               TABLE_ACCOUNT_NAME   ="name",
                               TABLE_ACCOUNT_COUNTRY   ="country",
                               TABLE_ACCOUNT_IMAGE   ="image",
                               TABLE_ACCOUNT_LIEN_IMAGE   ="image_lien",
                               TABLE_ACCOUNT_POINT_EXPERIENCE   ="POINT_EXPERIENCE",
                               TABLE_ACCOUNT_MONEY   ="MONEY",
                               TABLE_ACCOUNT_VICTOIR_GAIN   ="VICTOIR_GAIN",
                               TABLE_ACCOUNT_VICTOIR_LOSS   ="VICTOIR_LOSS ",
                               TABLE_ACCOUNT_LEVEL   ="LEVEL",
                               TABLE_ACCOUNT_TOTAL_MONEY_WIN   ="MONEY_WIN";

    public static final String TABLE_SCORE_ID = "SCORE_ID",
                               TABLE_SCORE_SCORE = "SCORE_SCORE";

    public sql_manager(@Nullable Context context) {
        super(context,DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SETTING + "(" + TABLE_SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_SETTING_NAME + " TEXT," + TABLE_SETTING_EXACT_CHOICE + " text)");
        db.execSQL("create table " + TABLE_ACCOUNT + "(" + TABLE_ACCOUNT_ID + " Text PRIMARY KEY,"
                + TABLE_ACCOUNT_NAME + " TEXT,"
                + TABLE_ACCOUNT_IMAGE + " blob,"
                + TABLE_ACCOUNT_LIEN_IMAGE + " Text,"
                + TABLE_ACCOUNT_POINT_EXPERIENCE + " Integer,"
                + TABLE_ACCOUNT_MONEY + " DOUBLE,"
                + TABLE_ACCOUNT_LEVEL + " Integer,"
                + TABLE_ACCOUNT_VICTOIR_GAIN + " Integer,"
                + TABLE_ACCOUNT_VICTOIR_LOSS + " Integer,"
                + TABLE_ACCOUNT_TOTAL_MONEY_WIN + " DOUBLE,"
                + TABLE_ACCOUNT_COUNTRY + " TEXT)");


        db.execSQL("create table " + TABLE_REQUEST + "(" + TABLE_REQUEST_ID + " Text PRIMARY KEY,"
                + TABLE_REQUEST_NAME + " TEXT,"
                + TABLE_REQUEST_STATE + " Text,"
                + TABLE_REQUEST_TYPE + " Text,"
                + TABLE_REQUEST_PHOTO + " blob)");

        db.execSQL("create table " + TABLE_SCORE + "(" +
                TABLE_SCORE_ID + " Text PRIMARY KEY," +
                TABLE_SCORE_SCORE + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }



    //////////////////      TABLE SETTING     ///////////////////////////////////////////
    public void insert_data_into_Setting(String name, String exact_choix){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_SETTING_NAME,name);
        contantValues.put(TABLE_SETTING_EXACT_CHOICE,exact_choix);

        db.insert(TABLE_SETTING,null,contantValues);
    }
    public ArrayList<setting> get_all_setting(){
        ArrayList<setting> all_setting_object=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res=db.rawQuery("select * FROM "+TABLE_SETTING,null);
        while(res.moveToNext()){
            setting ob_setting=new setting();
            ob_setting.setId(res.getInt(0));
            ob_setting.setName(res.getString(1));
            ob_setting.setChoice(res.getString(2));
            all_setting_object.add(ob_setting);
        }
        return all_setting_object;
    }
    public void Update_data_setting(String id, String exact_choice){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_SETTING_ID,id);
        contantValues.put(TABLE_SETTING_EXACT_CHOICE,exact_choice);

        db.update(TABLE_SETTING,contantValues,"ID_SETTING = ?",new String[] { id });

    }
    /////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////       TABLE ACCOUNT        ///////////////////////////////////////////////////////
    public void insert_data_into_account(account account){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_ACCOUNT_ID,account.getId());
        contantValues.put(TABLE_ACCOUNT_NAME,account.getName());
        contantValues.put(TABLE_ACCOUNT_IMAGE,account.getPhoto_saved());
        contantValues.put(TABLE_ACCOUNT_LIEN_IMAGE,account.getImage());

        contantValues.put(TABLE_ACCOUNT_POINT_EXPERIENCE,account.getPoint_experience());
        contantValues.put(TABLE_ACCOUNT_MONEY,account.getMoney());
        contantValues.put(TABLE_ACCOUNT_LEVEL,account.getLevel());
        contantValues.put(TABLE_ACCOUNT_VICTOIR_GAIN,account.getVictoir_gain());
        contantValues.put(TABLE_ACCOUNT_VICTOIR_LOSS,account.getVictoir_loss());
        contantValues.put(TABLE_ACCOUNT_TOTAL_MONEY_WIN,account.getTotal_money_win());
        contantValues.put(TABLE_ACCOUNT_COUNTRY,account.getCountry());

        db.insert(TABLE_ACCOUNT,null,contantValues);

    }

    public void delet_all_data_account() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_ACCOUNT);
        //db.close();
    }

    public void Update_V_A_acoount(Context context,account account){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();

        if(account.getName()!=null)
            contantValues.put(TABLE_ACCOUNT_NAME,account.getName());
        if(account.getImage()!=null)
            contantValues.put(TABLE_ACCOUNT_LIEN_IMAGE,account.getImage());
        if(account.getPhoto_saved()!=null)
            contantValues.put(TABLE_ACCOUNT_IMAGE,account.getPhoto_saved());


        contantValues.put(TABLE_ACCOUNT_POINT_EXPERIENCE,account.getPoint_experience());
        contantValues.put(TABLE_ACCOUNT_MONEY,account.getMoney());
        contantValues.put(TABLE_ACCOUNT_LEVEL,account.getLevel());
        contantValues.put(TABLE_ACCOUNT_VICTOIR_GAIN,account.getVictoir_gain());
        contantValues.put(TABLE_ACCOUNT_VICTOIR_LOSS,account.getVictoir_loss());
        contantValues.put(TABLE_ACCOUNT_TOTAL_MONEY_WIN,account.getTotal_money_win());
        contantValues.put(TABLE_ACCOUNT_COUNTRY,account.getCountry());

        db.update(TABLE_ACCOUNT,contantValues,"id = ?",new String[] { new sql_manager(context).get__account().getId() });
    }

    public account get__account() {
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("select * FROM " + TABLE_ACCOUNT, null);
        account account = new account();
        while (res.moveToNext()) {
            account.setId(res.getString(0));
            account.setName(res.getString(1));
            account.setPhoto_saved(res.getBlob(2));
            account.setImage(res.getString(3));

            account.setPoint_experience(res.getInt(4));
            account.setMoney(res.getDouble(5));
            account.setLevel(res.getInt(6));
            account.setVictoir_gain(res.getInt(7));
            account.setVictoir_loss(res.getInt(8));
            account.setTotal_money_win(res.getDouble(9));
            account.setCountry(res.getString(10));
        }
        //db.close();
        return account;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////// TABLE REQUEST ////////////////////////////////////////////////////////

    public void insert_data_into_request(reqeust account){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_REQUEST_ID,account.getId());
        contantValues.put(TABLE_REQUEST_NAME,account.getName());
        contantValues.put(TABLE_REQUEST_STATE,account.getState());
        contantValues.put(TABLE_REQUEST_TYPE,account.getType());
        contantValues.put(TABLE_REQUEST_PHOTO,account.getPhoto_saved());

        if(get_this_request(account.getId()).getName()==null) {
            db.insert(TABLE_REQUEST, null, contantValues);
        }
        else {
            Update_Request(account);
        }


    }

    public void Update_Request(reqeust reqeust){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_REQUEST_STATE,reqeust.getState());
        contantValues.put(TABLE_REQUEST_TYPE,reqeust.getType());

        db.update(TABLE_REQUEST,contantValues,TABLE_REQUEST_ID+" = ?",new String[] { reqeust.getId() });
    }

    public void delet_all_data_request() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_REQUEST);
        //db.close();
    }

    public void delete_this_request(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_REQUEST+" where "+TABLE_REQUEST_ID+" like '"+id+"'");
        //db.close();
    }

    public void hide_this_request(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update  "+TABLE_REQUEST+" set "+TABLE_REQUEST_TYPE+" ='accept'  where "+TABLE_REQUEST_ID+" like '"+id+"'");
        //db.close();
    }



    public reqeust get_this_request(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res=db.rawQuery("select * FROM "+TABLE_REQUEST+" WHERE "+TABLE_REQUEST_ID+" like '"+id+"'",null);

        reqeust request=new reqeust();

        while (res.moveToNext()){
            request.setId(res.getString(0));
            request.setName(res.getString(1));
            request.setState(res.getString(2));
            request.setType(res.getString(3));
            request.setPhoto_saved(res.getBlob(4));
        }

        return request;
    }

    public ArrayList<reqeust> get__request_sent(){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("select * FROM " + TABLE_REQUEST , null);

        ArrayList<reqeust> list=new ArrayList<>();
        while (res.moveToNext()) {
            reqeust request = new reqeust();
            request.setId(res.getString(0));
            request.setName(res.getString(1));
            request.setState(res.getString(2));
            request.setType(res.getString(3));
            request.setPhoto_saved(res.getBlob(4));

            if(request.getType().equals("sent"))
               list.add(request);
        }
        //db.close();
        return list;
    }

    public void delete_request_sent() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_REQUEST+" where "+TABLE_REQUEST_TYPE+" like 'sent'");
    }

    public ArrayList<reqeust> get__request_receive(){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("select * FROM " + TABLE_REQUEST , null);

        ArrayList<reqeust> list=new ArrayList<>();
        while (res.moveToNext()) {
            reqeust request = new reqeust();
            request.setId(res.getString(0));
            request.setName(res.getString(1));
            request.setState(res.getString(2));
            request.setType(res.getString(3));
            request.setPhoto_saved(res.getBlob(4));

            if(request.getType().equals("receive"))
                list.add(request);
        }
        //db.close();
        return list;
    }

    /*public void delete_request_receive() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_REQUEST+" where "+TABLE_REQUEST_TYPE+" like 'receive'");
    }
     */

    public ArrayList<reqeust> get__freinds(){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res = db.rawQuery("select * FROM " + TABLE_REQUEST , null);

        ArrayList<reqeust> list=new ArrayList<>();
        while (res.moveToNext()) {
            reqeust request = new reqeust();
            request.setId(res.getString(0));
            request.setName(res.getString(1));
            request.setState(res.getString(2));
            request.setType(res.getString(3));
            request.setPhoto_saved(res.getBlob(4));

            if(request.getState().equals("accept"))
                list.add(request);
        }
        //db.close();
        return list;
    }

   /* public void delete_freinds() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_REQUEST+" where "+TABLE_REQUEST_STATE+" like 'accept'");
    }

    */
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////// TABLE SCORE //////////////////////////////////////////

    public void insert_data_into_score(score score){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_SCORE_ID,score.getId());
        contantValues.put(TABLE_SCORE_SCORE,score.getScore());


        if(get_score_of(score.getId()).equals(""))
            db.insert(TABLE_SCORE,null,contantValues);



    }

    public void delet_all_data_score() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  FROM "+TABLE_SCORE);
        //db.close();
    }

    public String get_score_of(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor res=db.rawQuery("select * FROM "+TABLE_SCORE+" WHERE "+TABLE_SCORE_ID+" like '"+id+"'",null);

        String score="";

        while (res.moveToNext()){
            score=res.getString(1);
        }

        //db.close();
        return score;
    }

    public void Update_score_of(score score){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contantValues=new ContentValues();
        contantValues.put(TABLE_SCORE_SCORE,score.getScore());

        db.update(TABLE_SCORE,contantValues,TABLE_SCORE_ID+" = ?",new String[] { score.getId() });
    }


    ///////////////////////////////////////////////////////////////////////////////////////

    public void clear(){
        delet_all_data_account();
        delet_all_data_request();
        delet_all_data_score();

        SharedPreferences settings = context.getSharedPreferences("image", Context.MODE_PRIVATE);
        SharedPreferences info = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences tabbed = context.getSharedPreferences("tabbed", Context.MODE_PRIVATE);


        settings.edit().clear().apply();
        info.edit().clear().apply();
        tabbed.edit().clear().apply();

        new sharedpreferences(context).
                put_setting_type("user_is_not_sign_in",true);
    }
}
