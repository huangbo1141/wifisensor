package robin.com.wifisensor.Doc;


/**
 * Created by hgc on 4/16/2016.
 */
public class Constants {
    public static final String defaultCountryID = "ZZ1";
//    public static String url = "http://192.168.1.108/adminuser/";

    public static String url = "http://vps.inventech.co.za/";
    public static String key = "7ae0d73742609531dc77324e95e3b53d";

    public static int row_length = 8;
//    public static final String BOTTOM_TAB_FIRST     = "tab_a_identifier";
//    public static final String BOTTOM_TAB_SECOND    = "tab_b_identifier";
//    public static final String BOTTOM_TAB_THIRD     = "tab_c_identifier";
//    public static final String BOTTOM_TAB_FOURTH    = "tab_d_identifier";
//    public static final String BOTTOM_TAB_FIFTH     = "tab_e_identifier";
    public static final String[] tab_ids = {"0","1","2","3","4","5"};
    public static String curDevice = "default";
    public static String mAddressOutput = "";

    public static String head1 = "5,7,choice,10,12,15,random,result";
    public static String head2 = ",,,,,,,";
    public static final String PREFIX_TRACK = "GRIDTRACK_";

    public static final String APISERVICE_IP_URL = "http://ip-api.com/";
    public static final String APISERVICE_MAP_URL = "http://maps.googleapis.com/maps/";

    public static final String APISERVICE_FEATURE_URL = "https://api.mapbox.com/";

    public static final String mid_url = "Services/V3/Authentication.svc/";
    public static final String ACTION_LOGIN = mid_url +"AuthenticateUser";
    public static final String ACTION_REGISTER = "Services/V3/Account.svc/RegisterUser";
    public static final String ACTION_POSTDATA = "GridTrack/default/call/jsonrpc/upload";
    public static final String ACTION_LISTTIMELINE = "Services/V3/Account.svc/GetUserTimeline";
    public static final String ACTION_LISTFRIEND = "Services/V3/Session.svc/GetFriends";
    public static final String ACTION_SESSION = "Services/V3/Session.svc/RequestSession";
    public static final String ACTION_ADDPOINT = "Services/V3/Session.svc/AddPoint";

    public static final String ACTION_UPLOAD = "DivelogsDirectImport.php";
    public static final String ACTION_TRACKINGSESSION = "Services/V3/Session.svc/RequestSession";
    public static final String ACTION_TERMINATESESSION = "Services/V3/Session.svc/TerminateSession";
    public static final String ACTION_GETSEASTATE = "Services/V3/Analysis.svc/GetSeaVariance";
    public static final String ACTION_GETSPR = "Services/V3/Analysis.svc/SPR";
    public static final String ACTION_GETANALYSISSUMMARY = "Services/V3/Analysis.svc/GetAnalysisSummary";
    public static final String ACTION_FEATURESERVICE = "v4/mapbox.mapbox-terrain-v2/tilequery/[longt],[lat].json?radius=[rad]&access_token=pk.eyJ1IjoiYnJvZGllaiIsImEiOiJjaWtmeTU2Y3MwMDRkdHhrbHY2enQ0amZvIn0.QFENN8R6xYR_lzngoyWmyg";
    public static final String ACTION_WEATHERSERVICE = "Services/V3/Weather.svc/GetCurrentWind";
    public static final String ACTION_GETBOAD = "Services/V3/MobileApp.svc/GetBoats";
    public static final String ACTION_TOGO_IDS = "assets/rest/apns/loadtogo_ids.php";

    public static final String ACTION_TOGO_DATA = "assets/rest/apns/loadtogo_data.php";
    public static final String ACTION_CONQUERED_DATA = "assets/rest/apns/loadconquered_data.php";

    public static final String ACTION_DEFAULTPROFILE = "assets/uploads/user1.png";
    public static final String ACTION_LOADCOMMENT = "assets/rest/apns/load_comment.php";


    public static final String KEY_HASLOGIN = "hasLogin";
    public static final String KEY_INTROVIEWED = "introViewed";

    public static final String KEY_WINDDATA = "winddata";
    public static final String KEY_DATAELEMENT = "";
    public static final String KEY_HEARTRATE = "heartrate";
    public static final String KEY_BOATTYPE = "boattype";
    public static final String KEY_AUTOSTART = "autostart";
    public static final String kPitchGlobal = "kPitchGlobal";
    public static final String kRollGlobal = "kRollGlobal";




    public static String[] listGender = {"Male","Female"};
    public static String[] listEthnicity = {"White","Hispanic or Latino","Black or African","Native","Asian/Pacific Islander","Mixed race"};
    public static String[] listLookingfor = {"Man","Woman", "Man & Woman"};
    public static  String[] mPlanetTitles =
            {
                    "Browse",
                    "Search",
                    "Profile",
                    "My Chat",
                    "Create Bid",
                    "Bid Feeds",
                    "Buy Coins",
                    "Invite friends",
                    "Help",
                    "Sign Out",

            };
    public static final int TAB_BROWSE      = 0 ;
    public static final int TAB_SEARCH      = 1 ;
    public static final int TAB_PROFILE       = 2 ;
    public static final int TAB_MYCHAT          = 3 ;
    public static final int TAB_CREATEBID           = 4 ;
    public static final int TAB_BIDFEEDS           = 5 ;
    public static final int TAB_BUYCOINS           = 6 ;
    public static final int TAB_INVITEFRIENDS           = 7 ;
    public static final int TAB_HELP           = 8 ;
    public static final int TAB_SIGNOUT           = 9 ;

    public static final int REQUESTCODE_UPDATEPROFILE = 10;

    //broadcast
    public static final String BROADCAST_TRACKING_ON = "robin.com.wifisensor.Doc.BROADCAST_TRACKING_ON";
    public static final String BROADCAST_TRACKING_OFF = "robin.com.wifisensor.Doc.BROADCAST_TRACKING_OFF";
    public static final String BROADCAST_TRACKING_IQCONNECTED = "robin.com.wifisensor.Doc.BROADCAST_TRACKING_IQCONNECTED";
    public static final String BROADCAST_TRACKING_IQDISCONNECTED = "robin.com.wifisensor.Doc.BROADCAST_TRACKING_IQDISCONNECTED";

    public static final String BROADCAST_MAP_PICKLOCATION = "robin.com.wifisensor.Doc.BROADCAST_MAP_PICKLOCATION";

    public static final String BROADCAST_CONTENTCHANGED_HOT = "robin.com.wifisensor.Doc.BROADCAST_CONTENTCHANGED_HOT";
    public static final String BROADCAST_CONTENTCHANGED_OTHERS = "robin.com.wifisensor.Doc.BROADCAST_CONTENTCHANGED_OTHERS";

    public static final String BROADCAST_PHOTOCHANGED = "robin.com.wifisensor.Doc.BROADCAST_CONTENTCHANGED_OTHERS";

    public static final int REQUEST_CAMERA     =   10;
    public static final int SELECT_FILE         =   11;

    final public static String google_browserkey = "AIzaSyCmvC_H5S08MvkO-ixoQTpJQGXdu5qyVWg";

    public static final float MAP_ZOOM         =   4;

    public static final int SYNC_FIRSTTIME = 0;
    public static final int SYNC_INITIAL = 1;
    public static final int SYNC_UPLOADING = 2;
    public static final int SYNC_UPLOADING_DONE = 3;

    public static final String BROADCAST_SYNC_CHANGESTATUS = "beta.shareasuccess.doc.BROADCAST_SYNC_CHANGESTATUS";
    public static final String BROADCAST_MAP_ADDPOINT = "beta.shareasuccess.doc.BROADCAST_MAP_ADDPOINT";
    public static final String BROADCAST_STARTSYNC = "beta.shareasuccess.doc.BROADCAST_STARTSYNC";
    public static final String BROADCAST_NOTICE = "beta.shareasuccess.doc.BROADCAST_NOTICE";

    public static final int SETTING_MODE_TRACKING     =   1;
    public static final int SETTING_MODE_NONE     =   0;
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

}
