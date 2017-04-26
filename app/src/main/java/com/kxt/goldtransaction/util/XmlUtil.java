package com.kxt.goldtransaction.util;

import com.kxt.goldtransaction.bean.XmlOpenBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2017/4/26.
 */

public class XmlUtil {
    public final  static  String headXml="<?xml version=\"1.0\" encoding=\"GBK\"?>";
    public static String getStringFormBean(XmlOpenBean xmlOpen){
        XStream xstream2 = new XStream(new DomDriver());
        xstream2.alias("request", XmlOpenBean.class);
        String tempBoystr =xstream2.toXML(xmlOpen);
        tempBoystr=tempBoystr.replace("__","_");
        tempBoystr=headXml+"\n"+ tempBoystr;
        tempBoystr= String.format("%08d", tempBoystr.length())+tempBoystr;
        return  tempBoystr;
    }
}
