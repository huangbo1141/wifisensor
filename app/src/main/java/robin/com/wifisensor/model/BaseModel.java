package robin.com.wifisensor.model;


import robin.com.wifisensor.Doc.CGlobal;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hgc on 4/16/2016.
 */
public class BaseModel implements Serializable {
    public String create_datetime;
    public String modify_datetime;
    public String sqlmode;
    public String sql;

    public ImageDimen imginfo;

    public int response;
    public String error;


    public class ImageDimen{
        public String tp_width;
        public String tp_height;
    }

    public static Map<String, String> getQueryMap(Object obj){
        Map<String, String> ret = new HashMap<>();
        for (Field f: obj.getClass().getDeclaredFields()){
            try {
                String name = f.getName();
                String value = (String) f.get(obj);
                if (value!=null)
                    ret.put(name,value);
            }catch (Exception ex){

            }

        }
        return ret;
    }

    public static String buildJson(Object obj){
        JSONObject jsonObject = new JSONObject();
        for (Field f: obj.getClass().getDeclaredFields()){
            try {
                String name = f.getName();
                String value = (String) f.get(obj);
                if (value!=null) {
                    jsonObject.put(name,value);
                }
            }catch (Exception ex){

            }

        }
        return jsonObject.toString();
    }

    public static String getInsertSql(Object targetClass, String tableName, ArrayList<String> exceptions){
        String sql = null;
        String part1 = "";
        String part2 = "";

        for (Field f:targetClass.getClass().getFields()){
            String name = f.getName();
            if (exceptions!=null && exceptions.indexOf(name)>=0 ){
                continue;
            }
            try{
                Object obj = f.get(targetClass);
                if (obj == null){
                    String item1 = String.format("`%s`,",name);
                    String item2 = "'',";
                    part1 = part1 + item1;
                    part2 = part2 + item2;
                }else{
                    if (obj instanceof String){
                        String value = CGlobal.escapeString((String) obj);

                        String item1 = String.format("`%s`,",name);
                        String item2 = String.format("'%s',",value);
                        part1 = part1 + item1;
                        part2 = part2 + item2;
                    }else if(obj instanceof Integer){
                        Integer value = (Integer) obj;
                        String item1 = String.format("`%s`,",name);
                        String item2 = String.format("'%d',",value);
                        part1 = part1 + item1;
                        part2 = part2 + item2;
                    }
                }


            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        if (part1.length()>0){
            part1 = part1.substring(0,part1.length()-1);
            part2 = part2.substring(0,part2.length()-1);

            sql = String.format("insert into %s (%s) values (%s)",tableName,part1,part2);
        }

        return sql;
    }
    public static String getUpdateSql(Object targetClass, String tableName, String wField, String wValue){
        String sql = null;
        String part1 = "";
        for (Field f: targetClass.getClass().getFields()){
            try{
                String name = f.getName();
                if (name.equals(wField)){
                    continue;
                }
                Object value = f.get(targetClass);
                if (value!=null && value instanceof String){
                    String item1 = String.format("`%s`='%s',",name,value);
                    part1 = part1 + item1;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        if (part1.length()>0){
            part1 = part1.substring(0,part1.length()-1);
            sql = String.format("update %s set %s where `%s` = '%s'",tableName,part1,wField,wValue);
        }
        return sql;
    }
}
