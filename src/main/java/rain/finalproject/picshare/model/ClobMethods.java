package rain.finalproject.picshare.model;

import java.sql.Clob;

public class ClobMethods {
    public static String clobToString(Clob clob) {
        try {
            return clob.getSubString(1, (int)clob.length());
        } catch (Exception Ex) {
            System.out.println("err");
            return "";
        }
    }
}
