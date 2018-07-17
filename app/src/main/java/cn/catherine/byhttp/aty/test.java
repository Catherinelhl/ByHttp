package cn.catherine.byhttp.aty;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class test {
    URL url;

    {
        try {
            url = new URL("http://www.baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            StringBuffer sb = new StringBuffer();
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            while ((str = bf.readLine()) != null) {
                sb.append(str);
            }
            System.out.println(str);
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
            byte[] b = new byte[1024 * 4];

            for (int i = 0; i < 3; i++) {
                int end = (i == 2) ? 3 : 4;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

