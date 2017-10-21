package robin.com.wifisensor.Doc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import robin.com.wifisensor.LoginActivity;
import robin.com.wifisensor.MainActivity;
import robin.com.wifisensor.Utils.SharedPreference;
import robin.com.wifisensor.Utils.Utils;
import robin.com.wifisensor.model.PhotoManager;
import robin.com.wifisensor.model.QuizResult;
import robin.com.wifisensor.model.tbl.TblClient;
import robin.com.wifisensor.model.tbl.TblTrack;
import robin.com.wifisensor.util.database.ImageDatabaseHelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

/**
 * Created by hgc on 6/26/2015.
 */
public class CGlobal {

    public static final int maxBufferSize = 6000;
    public static final double pi = 3.1415926f;
    public static final double M_PI = 3.14159265358979323846;
    public static final double PIDiv180 = 0.017453292f;
//    public static GoogleApiClient googleApiClient = null;

    public static final String DIRECTORY = "intuitive";
    public static File photoFile;
    //    public static CameraPosition cameraPosition_togo;
//    public static CameraPosition cameraPosition_conq;
    //    public static CallbackManager g_callbackManager;
    public static DisplayMetrics displayMetrics;
    public static Display display;
    public static ImageDatabaseHelper dbManager;
    public static ArrayList<Object> listCountry = new ArrayList<>();
    public static ArrayList<Object> listState = new ArrayList<>();
    public static ArrayList<Object> listCity = new ArrayList<>();
    //    public static Address address;
//    public static LatLng mPos;
    //    public static ArrayList<Object> hotCountry  = new ArrayList<>();
//    public static ArrayList<Object> freshCountry  = new ArrayList<>();
    public static TabType tabType = TabType.main;
    public static Bitmap photo;
    public static List<String> boatList = new ArrayList<>();
    public static String _gsr = "2";
    public static MainActivity mainActivity;
    static String TAG = "CGlobal";

    public static void resetData() {
        photoFile = null;
    }

    public static String bluetoothStatus = "none";

    public static void initGlobal(Activity activity) {
        CGlobal.displayMetrics = activity.getResources().getDisplayMetrics();
        CGlobal.display = activity.getWindowManager().getDefaultDisplay();

        // init settings
        bluetoothStatus = "none";
        initDB(activity);
    }

    public static void initDB(Context context) {
        CGlobal.dbManager = new ImageDatabaseHelper(context);
        listCountry = CGlobal.dbManager.getCountries();
    }

//    public static void gotoMainWindow(Activity activity, LoginResponse loginResponse) {
//        SharedPreferences.Editor prefEditor =
//                PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).edit();
//
//        prefEditor.putInt(Constants.KEY_HASLOGIN, 1);
//        prefEditor.putString("tu_username", loginResponse.requestLogin.usrEmail);
//        prefEditor.putString("tu_password",loginResponse.requestLogin.usrPassword);
//        prefEditor.commit();
//
//        PhotoManager.setUserDefaultString("UserAccountID", loginResponse.d.UserAccountID, activity);
//        PhotoManager.setUserDefaultString("userEmail", loginResponse.d.userEmail, activity);
//        PhotoManager.setUserDefaultString("userFirstName", loginResponse.d.userFirstName, activity);
//        PhotoManager.setUserDefaultString("token", loginResponse.d.token, activity);
//
//        CGlobal.loginResponse = loginResponse;
//
//        Intent intent = new Intent(activity, MainActivity.class);
//        activity.startActivity(intent);
//        activity.finish();
//
//    }

    public static void initDefault(Activity activity) {
        String[] def = {"SOG", "AWA", "AWS"};
        for (int i = 0; i < def.length; i++) {
            PhotoManager.setUserDefaultString(Constants.KEY_DATAELEMENT + def[i], "1", activity);
        }

//        SOG, AWA, AWS
    }

    public static String getStringFromNumberForCurrency(String number) {
        String ret;
        DecimalFormat df = new DecimalFormat("#,##0");
        ret = df.format(Integer.valueOf(number));

        return ret;
    }

    public static Drawable getDrawableFromName(String prefix, String icon, Context context) {

        Resources res = context.getResources();
        int id = res.getIdentifier(prefix + icon, "drawable", context.getPackageName());
        if (id == 0) {
            return res.getDrawable(0);      /// error
        } else
            return res.getDrawable(id);
    }

    public static String getBaseIconPath(String prefix, String icon, Context context) {

        String ret = "";
        ret = Constants.url + "assets/base/" + prefix + icon + ".png";

        return ret;
    }

    public static String getNumberFromStringForCurrency(String string) {
        String ret;
        ret = string.replace(",", "");
        return ret;
    }

    public static String getCurrentTime(boolean isGmt) {
        Calendar calendar = Calendar.getInstance();
        Date sourceDate = calendar.getTime();

        SimpleDateFormat dateFormatUCT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (isGmt) {
            dateFormatUCT.setTimeZone(TimeZone.getTimeZone("gmt"));
        } else {
            dateFormatUCT.setTimeZone(TimeZone.getDefault());
        }
        String prettyVersion = dateFormatUCT.format(sourceDate);

        return prettyVersion;
    }

    public static String getCreationTime(Date date, boolean isGmt, int mode) {
        SimpleDateFormat dateFormatUCT = new SimpleDateFormat("HH:mm,dd/MM/yyyy");
        switch (mode) {
            case 1: {
                dateFormatUCT = new SimpleDateFormat("HH:mm,dd/MM/yyyy:");
                break;
            }
            case 2:{
                dateFormatUCT = new SimpleDateFormat("dd/MM/yyyy");
                break;
            }
            case 3:{
                dateFormatUCT = new SimpleDateFormat("HH:mm");
                break;
            }
            default: {
                break;
            }
        }
        if (isGmt) {
            dateFormatUCT.setTimeZone(TimeZone.getTimeZone("gmt"));
        } else {
            dateFormatUCT.setTimeZone(TimeZone.getDefault());
        }
        String prettyVersion = dateFormatUCT.format(date);

        return prettyVersion;
    }

    public static String getAgoTime(String string) {
        String ret = "";
        SimpleDateFormat dateFormatUCT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUCT.setTimeZone(TimeZone.getTimeZone("gmt"));
        try {
            Date deadlineDate = dateFormatUCT.parse(string);
            Date currentDate = new Date();
            if (currentDate.before(deadlineDate)) {
                return "Just Now";
            }

            long timeDiff = currentDate.getTime() - deadlineDate.getTime();
            int hour = (int) (timeDiff / (60 * 60 * 1000));
            timeDiff = timeDiff % (60 * 60 * 1000);
            int min = (int) (timeDiff / (60 * 1000));
            timeDiff = timeDiff % (60 * 1000);
            int sec = (int) (timeDiff / 1000);

            if (hour == 0 && min == 0) {
                return "Just Now";
            }
            ret = String.format("%d hours %d minutes ago", hour, min);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static int ageFromBirthday(String birthday) {
        Date curDate = new Date();
        SimpleDateFormat dateFormatUCT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDate_str = dateFormatUCT.format(curDate);

        int age = Integer.valueOf(curDate_str.substring(0, 4)) - Integer.valueOf(birthday.substring(0, 4));

        return age;
    }

    public static String getGmtTime(Calendar calendar) {
        SimpleDateFormat dateFormatUCT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUCT.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = dateFormatUCT.format(calendar.getTime());

        return gmtTime;
    }

    public static String checkTimeForCreateBid(String string) {
        String ret = null;
        SimpleDateFormat dateFormatUCT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUCT.setTimeZone(TimeZone.getTimeZone("gmt"));
        try {
            Date suggestTime = dateFormatUCT.parse(string);
            Date currentDate = new Date();

            long timeDiff = currentDate.getTime() - suggestTime.getTime();
            int hour = (int) (timeDiff / (60 * 60 * 1000));
            timeDiff = timeDiff % (60 * 60 * 1000);
            int min = (int) (timeDiff / (60 * 1000));
            timeDiff = timeDiff % (60 * 1000);
            int sec = (int) (timeDiff / 1000);

            if (hour < 24) {
                long time1 = 24 * 60 * 60 * 1000;
                timeDiff = time1 - (currentDate.getTime() - suggestTime.getTime());
                hour = (int) (timeDiff / (60 * 60 * 1000));
                timeDiff = timeDiff % (60 * 60 * 1000);
                min = (int) (timeDiff / (60 * 1000));
                timeDiff = timeDiff % (60 * 1000);
                sec = (int) (timeDiff / 1000);
                ret = String.format("%02d:%02d:%02d", hour, min, sec);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getPrettyBirthday(String param) {
        String ret = "";
        try {
            SimpleDateFormat dateFormatUCT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormatUCT.parse(param);
//            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
            ret = sdf.format(date);
        } catch (Exception ex) {

        }
        return ret;
    }

    public static ArrayList<Object> getYearArray() {
        ArrayList<Object> array = new ArrayList<>();
        int lastyear = 1998;
        for (int i = lastyear; i >= 1900; i--) {
            String year = String.valueOf(i);
            array.add(year);
        }
        return array;
    }

    public static ArrayList<Object> getMonthArray() {
        ArrayList<Object> array = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String year = String.format("%02d", i);
            array.add(year);
        }
        return array;
    }

    public static ArrayList<Object> getDayArray(String year, String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        int limit = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        ArrayList<Object> array = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            String tmp = String.format("%02d", i);
            array.add(tmp);
        }
        return array;
    }

    public static String getThumbPhotoPath(String filename) {
        String ret = "";
        ret = Constants.url + "assets/uploads/thumbnail/" + filename;

        return ret;
    }

    public static String getPhotoPath(String filename) {
        String ret = "";
        ret = Constants.url + "assets/uploads/" + filename;

        return ret;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

    public static Bitmap getMarkerBitmap1(int number, Context context, Bitmap bm) {

        String gText = String.valueOf(number);
        int spellsize = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 14, displayMetrics));
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float scale = resources.getDisplayMetrics().density;
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(61, 61, 61));
        paint.setTextSize(spellsize);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);

        int bw = bounds.width();
        int bh = bounds.height();

        int radius;
        if (bw < bh)
            radius = bh / 2;
        else
            radius = bw / 2;
        radius += 5;

        int sm, cw, tw, th;
        sm = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 1, displayMetrics);
        ;
        cw = radius;
        tw = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 60, displayMetrics);
        th = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 60, displayMetrics);
        int dw = tw - 2 * sm - cw;
        int dh = tw - 2 * sm - cw;
        int thumb_width = bm.getWidth();
        int thumb_height = bm.getHeight();
        int realwidth, realheight;
        if (thumb_width > thumb_height) {
            realwidth = dw;
            realheight = (int) (((float) realwidth / (float) thumb_width) * thumb_height);
        } else {
            realheight = dh;
            realwidth = (int) (((float) realheight / (float) thumb_height) * thumb_width);
        }
        Bitmap bitmap = Bitmap.createBitmap(tw, th, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawRect(new Rect(0, cw, sm * 2 + dw, th), paint);
        int cx = (dw - realwidth) / 2 + sm, cy = (dh - realheight) / 2 + sm + cw;
        Rect dst = new Rect(cx, cy, realwidth, realheight);
        canvas.drawBitmap(bm, new Rect(0, 0, bm.getWidth(), bm.getHeight()), dst, null);

        if (number > 1) {
            RectF circle_rect = new RectF(tw - cw * 2, 0, 2 * cw, 2 * cw);
            paint.setStyle(Paint.Style.FILL);
            paint.setARGB(255, (int) (0.29 * 256), (int) (0.60 * 256), (int) (0.85 * 256));
            canvas.drawCircle(cx, cy, radius, paint);
            canvas.drawOval(circle_rect, paint);

            Paint plantxtpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            plantxtpaint.setColor(Color.rgb(255, 255, 255));
            paint.setTextSize(spellsize);

            Rect text_rect = new Rect((int) ((circle_rect.width() - bw) / 2 + circle_rect.left), (int) ((circle_rect.height() - bh) / 2 + circle_rect.top), bw, bh);
//            canvas.drawText(gText, cx - bw / 4, cy + bh / 4, plantxtpaint);
            canvas.drawText(gText, text_rect.left, text_rect.top, plantxtpaint);
        }

        return bitmap;
    }

    public static Bitmap getMarkerBitmap(String gText, Context context) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.rgb(61, 61, 61));
        paint.setTextSize((int) (14 * scale));

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);

        int bw = bounds.width();
        int bh = bounds.height();

        int radius;
        if (bw < bh)
            radius = bh / 2;
        else
            radius = bw / 2;
        radius += 5;
        int cx = radius;
        int cy = radius;
        int w = 2 * radius;
        int h = 2 * radius;

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Random rnd = new Random();

        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        canvas.drawCircle(cx, cy, radius, paint);

        Paint plantxtpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        plantxtpaint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize((int) (14 * scale));

        canvas.drawText(gText, cx - bw / 4, cy + bh / 4, plantxtpaint);

        return bitmap;
    }

    public static String getLastFileName(String param1) {
        int index = param1.lastIndexOf("/");
        String last = param1.substring(index + 1);
        return last;
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body, File futureStudioIconFile) {
        try {
            // todo change the file location/name according to your needs
//            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
    public static ArrayList<String> readFromDisk(File futureStudioIconFile) {
        ArrayList<String> data = new ArrayList<>();
        try {
            FileInputStream is;
            BufferedReader reader;
            final File file = futureStudioIconFile;

            if (file.exists()) {
                is = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while(line != null){
                    Log.d("StackOverflow", line);
                    String[] points = line.split(";");
                    if (points!=null && points.length>0){
                        for (int i=0; i<points.length; i++){
                            data.add(points[i]);
                        }
                    }

                    line = reader.readLine();
                }
            }

        } catch (IOException e) {

        }
        return data;
    }
    public static boolean writeToDisk(ArrayList<String> body, File futureStudioIconFile) {
        try {
            // todo change the file location/name according to your needs
//            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;
            OutputStreamWriter myOutWriter = null;
            try {
                outputStream = new FileOutputStream(futureStudioIconFile);
                myOutWriter = new OutputStreamWriter(outputStream);

                int step = 20;
                int cnt = 0;
                String temp = "";
                for (int i=0; i<body.size(); i++){
                    String row = body.get(i);
                    temp = temp + row + ";";
                    cnt++;
                    if (cnt>=step){
                        temp = temp.substring(0,temp.length()-1);
                        myOutWriter.append(temp);
                        myOutWriter.append("\n\r");
                        cnt = 0;
                        temp = "";
                    }
                }
                if (temp.length()>0){
                    temp = temp.substring(0,temp.length()-1);
                    myOutWriter.append(temp);
                    myOutWriter.append("\n\r");
                    cnt = 0;
                    temp = "";
                }

                myOutWriter.flush();
                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
                if (myOutWriter != null) {
                    myOutWriter.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static String escapeString(String string) {
        String ret = string;
        if (string != null) {
            ret = string.replaceAll("'", "''");
        }
        return ret;
    }

    public static boolean isNetworkAlive(Context context) {
        String tag = "CGlobal";
        try {
            ConnectivityManager nInfo = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            nInfo.getActiveNetworkInfo().isConnectedOrConnecting();
            Log.d(tag, "Net avail:"
                    + nInfo.getActiveNetworkInfo().isConnectedOrConnecting());
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                Log.d(tag, "Network available:true");
                return true;
            } else {
                Log.d(tag, "Network available:false");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void logout(Activity activity) {
        try {
//            PhotoManager.logout(activity);
//            CGlobal.resetData();
            new SharedPreference(activity).clearSharedPrefrence();
            new SharedPreference(activity).SaveValue(Utils.USERID, "");

            CGlobal.dbManager.logOut();

            if (mainActivity != null) {
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mainActivity.startActivity(intent);

                mainActivity.finish();
                mainActivity = null;
            } else {
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);

                activity.finish();
            }
            activity.finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static double degreesToRadians(double degree) {
        return degree * PIDiv180;
    }

    public static double truncatingRemainder(double val, double dividingBy) {
        double a1 = val / dividingBy;
        double remain = val - dividingBy * a1;
        return remain;
    }

    public static double radiansToDegrees(double radians) {
        double degrees = radians * (180 / pi);
        return degrees;
    }

    public static int getLastFileNumber(Context context) {
        String uname = new SharedPreference(context).RetriveValue(Utils.UserName);
        String prefix = "quiz_" + uname;

        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(downloads.getAbsolutePath() + File.separator + CGlobal.DIRECTORY);
        int max = 0;
        try {
            if (dir.exists()) {
                File[] files = dir.listFiles();
                List<Integer> tempList = new ArrayList<>();
                for (int i = 0; i < files.length; ++i) {
                    File file = files[i];
                    if (file.isDirectory()) {
                        //traverse(file);
                    } else {
                        // do something here with the file
                        try {
                            String name = file.getName();
                            if (name.startsWith(prefix)) {
                                String number = name.substring(prefix.length(), name.length() - 4);
                                tempList.add(Integer.valueOf(number));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                for (int i = 0; i < tempList.size(); i++) {
                    if (max < tempList.get(i)) {
                        max = tempList.get(i);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return max;
    }

    public static ArrayList getMyLogFiles(Context context) {
        // 0    data_f
        // 1    data_t
        // 2    Date
        // 3     filename
        // 4    number
        String uname = new SharedPreference(context).RetriveValue(Utils.UserName);
        String prefix = "quiz_" + uname;
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = new File(downloads.getAbsolutePath() + File.separator + CGlobal.DIRECTORY);
        int max = 0;
        ArrayList ret = new ArrayList();
        try {
            if (dir.exists()) {
                File[] files = dir.listFiles();
                ArrayList tempList = new ArrayList<>();
                for (int i = 0; i < files.length; ++i) {
                    File file = files[i];
                    if (file.isDirectory()) {
                        //traverse(file);
                    } else {
                        // do something here with the file
                        try {
                            String name = file.getName();
                            if (name.startsWith(prefix)) {
                                String number = name.substring(prefix.length(), name.length() - 4);
                                Map<String, Object> data = readExcelFile(context, name);
                                if (data != null) {
                                    data.put("number", number);
                                    tempList.add(data);
                                }

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                Collections.sort(tempList, new Comparator() {
                    @Override
                    public int compare(Object o, Object t1) {
                        Map<String, Object> first = (Map<String, Object>) o;
                        Map<String, Object> second = (Map<String, Object>) t1;
                        Integer num1 = Integer.valueOf((String) first.get("number"));
                        Integer num2 = Integer.valueOf((String) second.get("number"));
                        if (num1 > num2) {
                            return -1;
                        } else if (num1 < num2) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                ret = tempList;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    public static ArrayList getGraphRowData(List<QuizResult> resultList) {
        ArrayList arrayList = new ArrayList();
        try {
            float gsr5_f = 0, gsr7_f = 0, gsr10_f = 0, gsr12_f = 0, gsr15_f = 0;
            float gsr5_t = 0, gsr7_t = 0, gsr10_t = 0, gsr12_t = 0, gsr15_t = 0;
            float cnt_f = 0;
            float cnt_t = 0;
            for (int i = 0; i < resultList.size(); i++) {
                QuizResult idata = resultList.get(i);
                if (idata.isResult()) {
                    gsr5_t = gsr5_t + Float.valueOf(idata.getGSR5());
                    gsr7_t = gsr7_t + Float.valueOf(idata.getGSR7());
                    gsr10_t = gsr10_t + Float.valueOf(idata.getGAR10());
                    gsr12_t = gsr12_t + Float.valueOf(idata.getGSR12());
                    gsr15_t = gsr15_t + Float.valueOf(idata.getGSR15());
                    cnt_t = cnt_t + 1;
                } else {
                    gsr5_f = gsr5_f + Float.valueOf(idata.getGSR5());
                    gsr7_f = gsr7_f + Float.valueOf(idata.getGSR7());
                    gsr10_f = gsr10_f + Float.valueOf(idata.getGAR10());
                    gsr12_f = gsr12_f + Float.valueOf(idata.getGSR12());
                    gsr15_f = gsr15_f + Float.valueOf(idata.getGSR15());
                    cnt_f = cnt_f + 1;
                }
            }
            QuizResult data_f = new QuizResult();
            QuizResult data_t = new QuizResult();

            if (cnt_t > 0) {
                data_t.setGSR5(String.format("%.4f", gsr5_t / cnt_t));
                data_t.setGSR7(String.format("%.4f", gsr7_t / cnt_t));
                data_t.setGAR10(String.format("%.4f", gsr10_t / cnt_t));
                data_t.setGSR12(String.format("%.4f", gsr12_t / cnt_t));
                data_t.setGSR15(String.format("%.4f", gsr15_t / cnt_t));
            } else {
//                data_t = null;
            }

            if (cnt_f > 0) {
                data_f.setGSR5(String.format("%.4f", gsr5_f / cnt_f));
                data_f.setGSR7(String.format("%.4f", gsr7_f / cnt_f));
                data_f.setGAR10(String.format("%.4f", gsr10_f / cnt_f));
                data_f.setGSR12(String.format("%.4f", gsr12_f / cnt_f));
                data_f.setGSR15(String.format("%.4f", gsr15_f / cnt_f));
            } else {
//                data_f = null;
            }

            data_f.nCount = cnt_f;
            data_t.nCount = cnt_t;
            arrayList.add(data_f);
            arrayList.add(data_t);
            return arrayList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static QuizResult getGraphRowDataDic(List<QuizResult> resultList) {
        try {
            float gsr5_f = 0, gsr7_f = 0, gsr10_f = 0, gsr12_f = 0, gsr15_f = 0;
            float gsr5_t = 0, gsr7_t = 0, gsr10_t = 0, gsr12_t = 0, gsr15_t = 0;
            float cnt_f = 0;
            float cnt_t = 0;
            for (int i = 0; i < resultList.size(); i++) {
                QuizResult idata = resultList.get(i);
                gsr5_t = gsr5_t + Float.valueOf(idata.getGSR5());
                gsr7_t = gsr7_t + Float.valueOf(idata.getGSR7());
                gsr10_t = gsr10_t + Float.valueOf(idata.getGAR10());
                gsr12_t = gsr12_t + Float.valueOf(idata.getGSR12());
                gsr15_t = gsr15_t + Float.valueOf(idata.getGSR15());
                cnt_t = cnt_t + 1;
            }
            QuizResult data_t = new QuizResult();

            if (cnt_t > 0) {
                data_t.setGSR5(String.format("%.4f", gsr5_t / cnt_t));
                data_t.setGSR7(String.format("%.4f", gsr7_t / cnt_t));
                data_t.setGAR10(String.format("%.4f", gsr10_t / cnt_t));
                data_t.setGSR12(String.format("%.4f", gsr12_t / cnt_t));
                data_t.setGSR15(String.format("%.4f", gsr15_t / cnt_t));
            }

            data_t.nCount = cnt_t;
            return data_t;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> readExcelFile(Context context, String fileName) {

        // 0    data_f
        // 1    data_t
        // 2    Date
        // 3     filename
        // 4    number
        // check if available and not read only

        Map<String, Object> ret = null;
        try {
            ArrayList<QuizResult> resultList = new ArrayList<>();
            ArrayList<QuizResult> dicList = new ArrayList<>();
            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File dir = new File(downloads.getAbsolutePath() + File.separator + CGlobal.DIRECTORY);
            File logFile = new File(dir.getAbsolutePath() + File.separator + fileName);
            if (!logFile.exists()) {
                return null;
            }
            FileInputStream in = new FileInputStream(logFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            int curHead = 1;
            while ((line = reader.readLine()) != null) {
                // do something with the line you just read, e.g.
                try {
                    String[] pieces = line.split(",");
                    int head = checkHead(line);
                    if (head != 0) {
                        curHead = head;
                    }
                    switch (curHead) {
                        case 1: {
                            QuizResult result = new QuizResult();
                            result.setGSR5(pieces[0]);
                            result.setGSR7(pieces[1]);
                            result.setSelectedPos(Integer.valueOf(pieces[2]) - 1);
                            result.setGAR10(pieces[3]);
                            result.setGSR12(pieces[4]);
                            result.setGSR15(pieces[5]);
                            result.setRandomPos(Integer.valueOf(pieces[6]) - 1);
                            result.genResult();
                            resultList.add(result);
                            break;
                        }
                        case 2: {
                            QuizResult result = new QuizResult();
                            result.setGSR5(pieces[0]);
                            result.setGSR7(pieces[1]);
                            result.setSelectedPos(Integer.valueOf(pieces[2]) - 1);
                            result.setGAR10(pieces[3]);
//                            result.setGSR12(pieces[4]);
//                            result.setGSR15(pieces[5]);
//                            result.setRandomPos(Integer.valueOf(pieces[6])-1);
//                            result.genResult();
                            dicList.add(result);
                            break;
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Date lastModDate = new Date(logFile.lastModified());
            // analyse result
            ArrayList arrayList = getGraphRowData(resultList);
            if (arrayList != null) {
                arrayList.add(lastModDate);
                arrayList.add(fileName);

                ret = new HashMap<>();
                ret.put("incorrect", arrayList.get(0));
                ret.put("correct", arrayList.get(1));
                ret.put("date", lastModDate);
                ret.put("filename", fileName);

                if (dicList.size() > 0) {
                    QuizResult quizResult = getGraphRowDataDic(dicList);
                    ret.put("dics", quizResult);
                }
                QuizResult data_f = (QuizResult) arrayList.get(0);
                QuizResult data_t = (QuizResult) arrayList.get(1);
                if (quizValid(data_f,data_t)){
                    Log.i("VVVVVVVVVVVVVVVVVVVALID",lastModDate.toString());
                }else{
                    Log.i("IIIIIIIIIIIIIIIIIIIIIID",lastModDate.toString());
                }
            }

            reader.close();
            reader = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    //    public static int checkHead(String[] pieces){
//        // 0 invalid
//        // 1 decision head
//        // 2 first head
//        if (pieces.length == Constants.row_length){
//            boolean allempty = true;
//            for (String string:pieces){
//                String p = string.trim();
//                if (!p.isEmpty()){
//                    allempty = false;
//                    break;
//                }
//            }
//            if (allempty){
//                return 2;
//            }
//            String head = "5,7,choice,10,12,15,random,result";
//            String[] head_ps  = head.split(",");
//            boolean equalHead = true;
//            for (int i=0; i<head_ps.length; i++){
//                String h_s = head_ps[i].trim();
//                String c_s = pieces[i].trim();
//                if (!h_s.equals(c_s)){
//                    equalHead = false;
//                    break;
//                }
//            }
//            if (equalHead){
//                return 1;
//            }
//
//
//        }
//        return 0;
//    }
    public static int checkHead(String line) {
        // 0 invalid
        // 1 decision head
        // 2 first head
        if (line.equals(Constants.head1)) {
            return 1;
        } else if (line.equals(Constants.head2)) {
            return 2;
        }
        return 0;
    }

    public static String getFileName(int max, int mode, Context context) {
        String uname = new SharedPreference(context).RetriveValue(Utils.UserName);

        if (mode == 1) {
            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File folder = new File(downloads.getAbsolutePath() + File.separator + CGlobal.DIRECTORY);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String name = "quiz_" + uname + String.format("%06d", max);
            String zipfile = folder.getAbsolutePath() + File.separator + name + ".csv";
            return zipfile;
        } else {
            String name = "quiz_" + uname + String.format("%06d", max) + ".csv";
            return name;
        }

    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static Random random = new Random(100);

    public static LineDataSet getDataSetSample(){
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i=0; i<100; i+=10){

            float val = random.nextFloat();
            yVals1.add(new BarEntry(i, val));
        }

        LineDataSet set1;

        int clr_t = Color.parseColor("#4472c4");
        int clr_f = Color.parseColor("#ed7d31");
        int clr_d = Color.parseColor("#ff0000");
        set1 = new LineDataSet(yVals1, "Track Data");
        set1.setCircleColor(clr_t);
        set1.setColor(clr_t);

        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setLineWidth(1f);
        set1.setFillAlpha(100);
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(true);
//            set1.setDrawFilled(false);
        return set1;
    }

    public static LineDataSet getDataSet(ArrayList<String> listdata, int mode) {
        if (listdata != null) {
            ArrayList<Entry> yVals1 = new ArrayList<>();
            for (int i=0;i<listdata.size();i++){
                String jsonArray = listdata.get(i);
                String[] pieces =  jsonArray.split("_");
                try{

                    float x = Float.parseFloat(pieces[0]);
                    float y = Float.parseFloat(pieces[1]);
                    yVals1.add(new BarEntry(x, y));
                }catch (Exception ex){

                }

            }
            LineDataSet set1;

            int clr_t = Color.parseColor("#4472c4");
            int clr_f = Color.parseColor("#ed7d31");
            int clr_d = Color.parseColor("#ff0000");
            if (mode == 1) {
                set1 = new LineDataSet(yVals1, "");
                set1.setCircleColor(clr_t);
                set1.setColor(clr_t);
            } else if (mode == 2) {
                set1 = new LineDataSet(yVals1, "");
                set1.setCircleColor(clr_d);
                set1.setColor(clr_d);
            } else {
                set1 = new LineDataSet(yVals1, "");
                set1.setCircleColor(clr_f);
                set1.setColor(clr_f);
            }

            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setLineWidth(1f);
            set1.setFillAlpha(100);
            set1.setDrawCircleHole(false);
            set1.setDrawCircles(true);
//            set1.setDrawFilled(false);
            return set1;
        }
        return null;
    }

    public static void drawGraphSample(LineChart mChart, Context context) {
        List<ILineDataSet> dataList = new ArrayList<>();
        LineDataSet set1 = getDataSetSample();

        if (set1 != null) dataList.add(set1);

        // create a data object with the datasets
        LineData data = new LineData(dataList);
        data.setValueTextSize(9f);
        //008ab0
        mChart.setData(data);
        mChart.setBackgroundColor(Color.WHITE);
//        mChart.getAxisLeft().setInverted(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawAxisLine(false);


        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.getAxisLeft().setDrawLabels(true);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawLabels(true);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setEnabled(true);

        mChart.setClickable(false);
        mChart.setFocusable(false);
        mChart.getData().setHighlightEnabled(false);

        mChart.setTouchEnabled(false);
        mChart.setBackgroundColor(Color.TRANSPARENT);

//        mChart.setVisibleXRangeMinimum(0);
//        mChart.setVisibleXRangeMaximum(20);
//        mChart.animate();
        mChart.moveViewToX(0);
        mChart.getXAxis().setLabelCount(10, false);

        mChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        mChart.getLegend().setEnabled(false);
        // view port
//        mChart.setViewPortOffsets(-50,50,50,-50);
//        mChart.zoom(0.5f,1,10,10);

        // limit line
//        LimitLine LimitLine = new LimitLine(0,"");
//        LimitLine.setLineColor (Color.GREEN);
//        LimitLine.setTextColor (Color.GREEN);
//        mChart.getXAxis().addLimitLine(LimitLine);


//        mChart.getAxisLeft().setStartAtZero(true);
//        mChart.getXAxis().

    }

    public static void drawGraph(ArrayList<String> datalist, LineChart mChart, Context context) {
        List<ILineDataSet> dataList = new ArrayList<>();
        LineDataSet set1 = getDataSet(datalist, 1);

        if (set1 != null) dataList.add(set1);

        // create a data object with the datasets
        LineData data = new LineData(dataList);
        data.setValueTextSize(9f);

        //008ab0
        mChart.setData(data);
        mChart.setBackgroundColor(Color.WHITE);
//        mChart.getAxisLeft().setInverted(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawAxisLine(false);


        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawLabels(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setEnabled(false);

        mChart.setClickable(false);
        mChart.setFocusable(false);
        mChart.getData().setHighlightEnabled(false);

        mChart.setTouchEnabled(false);
        mChart.setBackgroundColor(Color.TRANSPARENT);

//        mChart.setVisibleXRangeMinimum(0);
//        mChart.setVisibleXRangeMaximum(20);
//        mChart.animate();
        mChart.moveViewToX(0);
        mChart.getXAxis().setLabelCount(10, false);

        mChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        mChart.getLegend().setEnabled(false);
        // view port
//        mChart.setViewPortOffsets(-50,50,50,-50);
//        mChart.zoom(0.5f,1,10,10);

        // limit line
//        LimitLine LimitLine = new LimitLine(0,"");
//        LimitLine.setLineColor (Color.GREEN);
//        LimitLine.setTextColor (Color.GREEN);
//        mChart.getXAxis().addLimitLine(LimitLine);


//        mChart.getAxisLeft().setStartAtZero(true);
//        mChart.getXAxis().

    }

    public static boolean  quizValid(QuizResult data_f,QuizResult data_t){
        boolean direction = true; //
        boolean ret = true;
        //  true    f_value < t_value
        //  false
        String[] f_str = {data_f.getGSR5(),data_f.getGSR7(),data_f.getGAR10(),data_f.getGSR7(),data_f.getGSR7()};
        String[] t_str = {data_t.getGSR5(),data_t.getGSR7(),data_t.getGAR10(),data_t.getGSR7(),data_t.getGSR7()};

        for (int i=0; i<f_str.length; i++){
            double f_value = Double.valueOf(f_str[i]);
            double t_value = Double.valueOf(t_str[i]);
            if (i == 0){
                if (f_value < t_value){
                    direction = true;
                }else{
                    direction = false;
                }
            }else{
                if (direction){
                    if (f_value > t_value){
                        ret = false;
                        break;
                    }
                }else{
                    if (f_value < t_value){
                        ret = false;
                        break;
                    }
                }
            }
        }

        return ret;
    }

    public static enum TabType {
        main,
        detail,
        test
    }
    public static File dirChecker(String dir)
    {
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloads.getAbsolutePath() + File.separator + dir);
        if(!file.exists())
        {
            file.mkdirs();
        }
        return file;
    }
    public static String getPostData(TblTrack tblTrack){
        String ret=null;
        File wifisensor = dirChecker("wifisensor");
        File file = new File(wifisensor.getAbsolutePath() + File.separator + tblTrack.tt_id+".txt");
        ArrayList<String> received_data = CGlobal.readFromDisk(file);
        TblClient client = CGlobal.dbManager.getClient(tblTrack);
        JSONObject jsonObject = client.getJsonObject();
        jsonObject = tblTrack.getJsonObject(jsonObject);
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<received_data.size(); i++){
            jsonArray.put(received_data.get(i));
        }
        try{
            jsonObject.put("points",jsonArray);
        }catch (Exception ex){

        }
        ret = jsonObject.toString();

        return ret;
    }
}
