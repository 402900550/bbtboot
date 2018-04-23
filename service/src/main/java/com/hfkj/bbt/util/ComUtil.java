package com.hfkj.bbt.util;

import java.util.List;
import java.util.Set;

public class ComUtil {

    private ComUtil(){

    }


    public static boolean listNotNull(List list){
        return list!=null&&!list.isEmpty();
    }

    public static boolean setNotNull(Set set){
        return set!=null&&!set.isEmpty();
    }
    public static boolean stringIsNotNull(String... string){
        boolean flag=true;
        for (String str:string){
            boolean b = null != str && !"".equals(str);
            if(!b){
                flag=false;
                break;
            }
        }
        return flag;
    }

    /**
     * 设置数据库模糊查询的值
     * @param value
     * @return
     */
    public static String checkValue(String value){
        String str=value;
        if (stringIsNotNull(str)){
            return "%"+str+"%";
        }else {
            return "%%";
        }
    }

}
