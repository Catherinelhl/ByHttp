package cn.catherine.byhttp.aty

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import cn.catherine.byhttp.R
import cn.catherine.byhttp.thread.DownloadPoolThread
import cn.catherine.byhttp.thread.DownloadThread
import cn.catherine.byhttp.thread.MyThread
import kotlinx.android.synthetic.main.aty_main.*

class AtyMain : AppCompatActivity() {
    private val url: String = "https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=abcd861a6081800a7ae8815cd05c589f/9345d688d43f8794de5737bbde1b0ef41ad53afe.jpg"


    private var count: Int = 0
    var downloadHandler = Handler {
        var result = it.what
        count += result
        textview.text = if (count >= 3) "download success!" else count.toString()

        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aty_main)
        MyThread("https://www.baidu.com", webview, Handler()).start()
        DownloadThread(url, imageView, Handler()).start()
        btn.setOnClickListener {
            Thread {
                DownloadPoolThread(downloadHandler)
                        .apply {
                            downloadFile(url)
                        }
            }.start()
        }


    }
}
