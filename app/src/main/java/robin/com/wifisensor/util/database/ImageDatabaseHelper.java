package robin.com.wifisensor.util.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import robin.com.wifisensor.model.BaseModel;
import robin.com.wifisensor.model.Country;
import robin.com.wifisensor.model.tbl.TblClient;
import robin.com.wifisensor.model.tbl.TblPoint;
import robin.com.wifisensor.model.tbl.TblTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImageDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "networkusage.db";
    public static final int DATABASE_VERSION = 2;

    public ImageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        ImageTable.onCreate(database);

    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        ImageTable.onUpgrade(database, oldVersion, newVersion);
    }
    public void logOut(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "delete from "+ImageTable.CLIENT_TABLE+" where 1;";
            db.execSQL(sql);

            sql = "delete from "+ImageTable.TRACK_TABLE+" where 1;";
            db.execSQL(sql);

            sql = "delete from "+ImageTable.DATA_TABLE+" where 1;";
            db.execSQL(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }
    public boolean insertAddPointRequest(TblPoint data){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql;
        try{
            String[] strings = {"tp_id","$change","serialVersionUID"};
            ArrayList<String> exceps = new ArrayList<>(Arrays.asList(strings));

            sql = BaseModel.getInsertSql(data,"tbl_point",exceps);
            db.execSQL(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    public List<TblPoint> getFailList() {
        ArrayList<TblPoint> retList = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "delete from tbl_point where tp_d = 1";
            db.execSQL(sql);

            sql = "select * from tbl_point where tp_d = 0";

            Cursor c = db.rawQuery(sql, null);
            int count = 0;
            if (c.moveToFirst()) {
                int cid = c.getColumnIndex("tp_id");
                int cjson = c.getColumnIndex("tp_json");
                int cd = c.getColumnIndex("tp_d");
                while (true) {
                    String tid = c.getString(cid);
                    String tjson = c.getString(cjson);
                    String td = c.getString(cd);


                    TblPoint data = new TblPoint();
                    data.tp_id = tid;
                    data.tp_json = tjson;
                    data.tp_d = td;
                    retList.add(data);
                    if (c.isLast())
                        break;
                    c.moveToNext();
                }
            }
            c.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return retList;
    }
    public boolean updateAddPointRequest(TblPoint data){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql;
        try{
            String[] strings = {"tp_id"};
            ArrayList<String> exceps = new ArrayList<>(Arrays.asList(strings));
            sql = BaseModel.getUpdateSql(data,"tbl_point","tp_id",data.tp_id);
            db.execSQL(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    public Country getCountryFromName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        Country country = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.TABLE_IMAGE + " where countryName = '" + name + "';";
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.IMAGE_ID);
            int cname = c.getColumnIndex(ImageTable.IMAGE_CNAME);
            int clat = c.getColumnIndex(ImageTable.IMAGE_LAT);
            int clon = c.getColumnIndex(ImageTable.IMAGE_LON);

            String countryId = c.getString(cid);
            String countrynane = c.getString(cname);
            String lat = c.getString(clat);
            String lon = c.getString(clon);

            country = new Country();
            country.countryID = countryId;
            country.countryName = countrynane;
            country.latitude = lat;
            country.longitude = lon;
        }
        if (country==null){
            country=  getCountryFromLocalName(name);
        }
        return country;
    }

    public Country getCountryFromLocalName(String name){
        if (name == null || name.isEmpty()) {
            return null;
        }
        Country country = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.TABLE_IMAGE + " where localName like '%" + name + "%';";
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.IMAGE_ID);
            int cname = c.getColumnIndex(ImageTable.IMAGE_CNAME);
            int clat = c.getColumnIndex(ImageTable.IMAGE_LAT);
            int clon = c.getColumnIndex(ImageTable.IMAGE_LON);

            String countryId = c.getString(cid);
            String countrynane = c.getString(cname);
            String lat = c.getString(clat);
            String lon = c.getString(clon);

            country = new Country();
            country.countryID = countryId;
            country.countryName = countrynane;
            country.latitude = lat;
            country.longitude = lon;
        }

        return country;
    }
    public Country getCountryFromWebCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        Country country = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.TABLE_IMAGE + " where webCode = '" + code + "';";
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.IMAGE_ID);
            int cname = c.getColumnIndex(ImageTable.IMAGE_CNAME);
            int clat = c.getColumnIndex(ImageTable.IMAGE_LAT);
            int clon = c.getColumnIndex(ImageTable.IMAGE_LON);

            String countryId = c.getString(cid);
            String countrynane = c.getString(cname);
            String lat = c.getString(clat);
            String lon = c.getString(clon);

            country = new Country();
            country.countryID = countryId;
            country.countryName = countrynane;
            country.latitude = lat;
            country.longitude = lon;
        }
        return country;
    }

    public ArrayList<Object> getCountries() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.TABLE_IMAGE + " where 1;";

        ArrayList<Object> countries = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.IMAGE_ID);
            int cname = c.getColumnIndex(ImageTable.IMAGE_CNAME);
            int clat = c.getColumnIndex(ImageTable.IMAGE_LAT);
            int clon = c.getColumnIndex(ImageTable.IMAGE_LON);

            while (true) {
                String countryId = c.getString(cid);
                String countrynane = c.getString(cname);
                String lat = c.getString(clat);
                String lon = c.getString(clon);


                Country country = new Country();
                country.countryID = countryId;
                country.countryName = countrynane;
                country.latitude = lat;
                country.longitude = lon;
                countries.add(country);


                if (c.isLast())
                    break;
                c.moveToNext();
            }

        }
        c.close();
        return countries;
    }
    public boolean insertClient(TblClient data){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql;
        try{
            String[] strings = {ImageTable.CLIENT_ID,"$change","serialVersionUID"};
            ArrayList<String> exceps = new ArrayList<>(Arrays.asList(strings));

            sql = BaseModel.getInsertSql(data,ImageTable.CLIENT_TABLE,exceps);
            db.execSQL(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    public boolean insertTrack(TblTrack data){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql;
        try{
            String[] strings = {ImageTable.TRACK_ID,"$change","serialVersionUID"};
            ArrayList<String> exceps = new ArrayList<>(Arrays.asList(strings));

            sql = BaseModel.getInsertSql(data,ImageTable.TRACK_TABLE,exceps);
            db.execSQL(sql);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return true;
    }
    public TblClient checkDuplicate(TblClient tblClient){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.CLIENT_TABLE
                + " where tc_name = '"+tblClient.tc_name+"' "
                + " and tc_location = '"+tblClient.tc_location+"' "
                + " and tc_job = '"+tblClient.tc_job+"' "
                + " and tc_jobnum = '"+tblClient.tc_jobnum+"' ";
        Cursor c = db.rawQuery(sql, null);
        TblClient country = null;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.CLIENT_ID);
            int cname = c.getColumnIndex(ImageTable.CLIENT_NAME);
            int clocation = c.getColumnIndex(ImageTable.CLIENT_LOCATION);
            int cjob = c.getColumnIndex(ImageTable.CLIENT_JOB);
            int cjobnum = c.getColumnIndex(ImageTable.CLIENT_JOBNUM);
            int cdate = c.getColumnIndex(ImageTable.CLIENT_DATE);
            int cinterval = c.getColumnIndex(ImageTable.CLIENT_INTERVAL);

            String id = c.getString(cid);
            String name = c.getString(cname);
            String location = c.getString(clocation);
            String job = c.getString(cjob);
            String jobnum = c.getString(cjobnum);
            String date = c.getString(cdate);
            String interval = c.getString(cinterval);

            country = new TblClient();
            country.tc_id = id;
            country.tc_name = name;
            country.tc_location = location;
            country.tc_job = job;
            country.tc_jobnum = jobnum;
            country.tc_date = date;
            country.tc_interval = interval;
        }
        return country;
    }
    public ArrayList<Object> getClientList(Map<String,Object> input){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.CLIENT_TABLE + " order by tc_name asc;";
        if (input!=null){
            Object name = input.get("name");
            Object location =  input.get("location");
            String mode = (String) input.get("mode");
            Object job = input.get("job");
            if (mode.equals("locations")){
                if (name== null){
                    sql = "select * from " + ImageTable.CLIENT_TABLE + " group by tc_location order by tc_location asc";
                }else{
                    sql = "select * from " + ImageTable.CLIENT_TABLE + " where tc_name = '"+name+"' group by tc_location order by tc_location asc";
                }
            }else if (mode.equals("job")){
                sql = "select * from " + ImageTable.CLIENT_TABLE + " where tc_name = '"+name+"' " +
                        " and tc_location = '"+location+"' " +
                        " group by tc_job order by tc_job asc";
            }else if (mode.equals("jobnumber")){
                sql = "select * from " + ImageTable.CLIENT_TABLE + " where tc_name = '"+name+"' " +
                        " and tc_location = '"+location+"' " +
                        " and tc_job = '"+job+"'" +
                        "group by tc_jobnum order by tc_jobnum asc";
            }else if(mode.equals("name")){
                sql = "select * from " + ImageTable.CLIENT_TABLE + " group by tc_name order by tc_name asc;";
            }
        }

        ArrayList<Object> countries = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.CLIENT_ID);
            int cname = c.getColumnIndex(ImageTable.CLIENT_NAME);
            int clocation = c.getColumnIndex(ImageTable.CLIENT_LOCATION);
            int cjob = c.getColumnIndex(ImageTable.CLIENT_JOB);
            int cjobnum = c.getColumnIndex(ImageTable.CLIENT_JOBNUM);
            int cdate = c.getColumnIndex(ImageTable.CLIENT_DATE);
            int cinterval = c.getColumnIndex(ImageTable.CLIENT_INTERVAL);

            while (true) {
                String id = c.getString(cid);
                String name = c.getString(cname);
                String location = c.getString(clocation);
                String job = c.getString(cjob);
                String jobnum = c.getString(cjobnum);
                String date = c.getString(cdate);
                String interval = c.getString(cinterval);


                TblClient country = new TblClient();
                country.tc_id = id;
                country.tc_name = name;
                country.tc_location = location;
                country.tc_job = job;
                country.tc_jobnum = jobnum;
                country.tc_date = date;
                country.tc_interval = interval;
                countries.add(country);


                if (c.isLast())
                    break;
                c.moveToNext();
            }

        }
        c.close();
        return countries;
    }

    public String getUniqueJobNumber(){
        TblClient tblClient = getLastJobNumberRow();
        String jobNumber = "1";
        if (tblClient != null){
            jobNumber = tblClient.tc_jobnum;
        }
        return jobNumber;
    }
    public TblClient getLastJobNumberRow(){

        TblClient country = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.CLIENT_TABLE + " order by tc_jobnum desc limit 1";
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.CLIENT_ID);
            int cname = c.getColumnIndex(ImageTable.CLIENT_NAME);
            int clocation = c.getColumnIndex(ImageTable.CLIENT_LOCATION);
            int cjob = c.getColumnIndex(ImageTable.CLIENT_JOB);
            int cjobnum = c.getColumnIndex(ImageTable.CLIENT_JOBNUM);
            int cdate = c.getColumnIndex(ImageTable.CLIENT_DATE);
            int cinterval = c.getColumnIndex(ImageTable.CLIENT_INTERVAL);

            String id = c.getString(cid);
            String name = c.getString(cname);
            String location = c.getString(clocation);
            String job = c.getString(cjob);
            String jobnum = c.getString(cjobnum);
            String date = c.getString(cdate);
            String interval = c.getString(cinterval);

            country = new TblClient();
            country.tc_id = id;
            country.tc_name = name;
            country.tc_location = location;
            country.tc_job = job;
            country.tc_jobnum = jobnum;
            country.tc_date = date;
            country.tc_interval = interval;
        }
        return country;
    }

    public String getUniqueTrackNumber(){
        TblTrack tblTrack = getLastTrackNumberRow();
        String number = "1";
        if (tblTrack != null){
            number = tblTrack.tt_track;
        }
        return number;
    }
    public TblTrack getLastTrackNumberRow(){

        TblTrack country = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.TRACK_TABLE + " order by tt_track desc limit 1";
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.TRACK_ID);
            int cclientid = c.getColumnIndex(ImageTable.TRACK_CLIENTID);
            int ctrack = c.getColumnIndex(ImageTable.TRACK_TRACK);
            int corigin = c.getColumnIndex(ImageTable.TRACK_ORIGIN);
            int cdevice = c.getColumnIndex(ImageTable.TRACK_DEVICE);

            String id = c.getString(cid);
            String clientid = c.getString(cclientid);
            String track = c.getString(ctrack);
            String origin = c.getString(corigin);
            String device = c.getString(cdevice);

            country = new TblTrack();
            country.tt_id = id;
            country.tc_id = clientid;
            country.tt_track = track;
            country.tt_origin = origin;
            country.tt_device = device;
        }
        return country;
    }
    public String getUniqueOriginNumber(){
        TblTrack tblTrack = getLastTrackNumberRow();
        String number = "1";
        if (tblTrack != null){
            number = tblTrack.tt_origin;
        }
        return number;
    }
    public TblTrack getLastOriginNumberRow(){

        TblTrack country = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from " + ImageTable.TRACK_TABLE + " order by tt_origin desc limit 1";
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.TRACK_ID);
            int cclientid = c.getColumnIndex(ImageTable.TRACK_CLIENTID);
            int ctrack = c.getColumnIndex(ImageTable.TRACK_TRACK);
            int corigin = c.getColumnIndex(ImageTable.TRACK_ORIGIN);
            int cdevice = c.getColumnIndex(ImageTable.TRACK_DEVICE);

            String id = c.getString(cid);
            String clientid = c.getString(cclientid);
            String track = c.getString(ctrack);
            String origin = c.getString(corigin);
            String device = c.getString(cdevice);

            country = new TblTrack();
            country.tt_id = id;
            country.tc_id = clientid;
            country.tt_track = track;
            country.tt_origin = origin;
            country.tt_device = device;
        }
        return country;
    }
    public ArrayList<Object> getTrackList(Map<String,Object> input){

        TblClient tblClient = (TblClient) input.get("client");
        String tc_id = tblClient.tc_id;

        String sql = "select * from " + ImageTable.TRACK_TABLE
                + " where tc_id = '" + tc_id + "' order by tt_id asc";

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Object> countries = new ArrayList<>();
        Cursor c = db.rawQuery(sql, null);
        int count = 0;
        if (c.moveToFirst()) {
            int cid = c.getColumnIndex(ImageTable.TRACK_ID);
            int cclientid = c.getColumnIndex(ImageTable.TRACK_CLIENTID);
            int ctrack = c.getColumnIndex(ImageTable.TRACK_TRACK);
            int corigin = c.getColumnIndex(ImageTable.TRACK_ORIGIN);
            int cdevice = c.getColumnIndex(ImageTable.TRACK_DEVICE);

            while (true) {
                String id = c.getString(cid);
                String clientid = c.getString(cclientid);
                String track = c.getString(ctrack);
                String origin = c.getString(corigin);
                String device = c.getString(cdevice);

                TblTrack country = new TblTrack();
                country.tt_id = id;
                country.tc_id = clientid;
                country.tt_track = track;
                country.tt_origin = origin;
                country.tt_device = device;
                countries.add(country);


                if (c.isLast())
                    break;
                c.moveToNext();
            }

        }
        c.close();
        return countries;
    }
}
 
 