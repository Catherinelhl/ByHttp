package cn.catherine.byhttp.thread

import android.os.Handler
import android.webkit.WebView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MyThread : Thread {

    private var url: String
    private var webView: WebView
    private var handler: Handler

    constructor(url: String, webView: WebView, handler: Handler) {
        this.url = url
        this.webView = webView
        this.handler = handler
    }

    override fun run() {
        doGet()

    }

    private fun doGet() {
        var httpUrl = URL(url)
        var httpURLConnection = httpUrl.openConnection() as HttpURLConnection
        httpURLConnection.readTimeout = 5000
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.doInput=true
//        httpURLConnection.contentType="text/html;charset=utf-8"
        var sb = StringBuffer()
        var bf = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
        var str: String? = null
        while ((bf.readLine().apply { str = this }) != null) {
            sb.append(str)

        }
//"text/html;charset=utf-8"
        handler.post {
            webView.loadData(sb.toString(), "text/html;charset='utf-8'", null)
        }
    }
}