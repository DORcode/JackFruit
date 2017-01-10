package common.lib.utils;

import android.os.Environment;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Stats on 2016-11-28.
 */

public class HttpUtils {
    private static final String TAG = "HttpUtils";

    public static boolean writeResponseBodyToFile(ResponseBody responseBody, String path) {
        File file = new File(path);

        ByteArrayOutputStream bos = null;
        OutputStream fos = null;
        InputStream is = null;

        try {
            bos = new ByteArrayOutputStream();
            fos = new FileOutputStream(file);

            is = responseBody.byteStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            int len;
            byte[] buf = new byte[1024];
            Log.i(TAG, "writeResponseBodyToFile: " + is.available());
            while ((len = is.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            fos.write(bos.toByteArray());

            fos.close();
            is.close();
            bos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    public static String changeInputStream(InputStream in) {
        String str = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        try {
            while ((len = in.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            str = new String(baos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static JSONObject getJSONFromStr(InputStream in) {
        String str = changeInputStream(in);
        JSONObject json = null;
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
