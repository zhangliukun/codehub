package zalezone.retrofitlibrary.network.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zale on 16/12/21.
 */

public class OkUtil {

    public static String convertMap2SGetParams(Map<String,String> map,boolean isNeedSort){
        if (!map.isEmpty()){
            StringBuilder builder = new StringBuilder();
            if (isNeedSort){
                Map<String,String> sortedMap = new TreeMap<>();
                sortedMap.putAll(map);
                map = sortedMap;
            }
            for (Map.Entry<String,String> entry:map.entrySet()){
                String value = entry.getValue();
                if (value!=null){
                    builder.append(entry.getKey()).append("=").append(value).append("&");
                }
            }
            if (!map.isEmpty()){
                builder.deleteCharAt(builder.length()-1);
            }
            return builder.toString();
        }
        return "";
    }
}
