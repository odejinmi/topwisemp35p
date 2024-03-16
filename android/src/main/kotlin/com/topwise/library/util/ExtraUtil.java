//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

import android.util.Log;
import com.topwise.library.TopApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExtraUtil {
    public ExtraUtil() {
    }

    public static String getCustomVersionMsg(String originalMsg) {
        StringBuilder version = new StringBuilder();
        if (originalMsg != null) {
            version.append(originalMsg);
            version.append("-");
        }

        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = TopApplication.getApp().getAssets().open("version.ver");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            if ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }

        version.append(content.toString());
        Log.i("topwise", "getCustomVersionMsg: " + version.toString());
        return version.toString();
    }
}
