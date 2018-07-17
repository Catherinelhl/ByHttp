package cn.catherine.byhttp.thread

import android.graphics.BitmapFactory
import android.os.Handler
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
/**
 * create by catherine
 *
 * 一个下载图片的例子
 * */
class DownloadThread : Thread {
    private var url: String
    private var img: ImageView
    private var handler: Handler

    constructor(url: String, img: ImageView, handler: Handler) {
        this.url = url
        this.img = img
        this.handler = handler
    }


    override fun run() {
        doGet()
    }

    private fun doGet() {

        var url = URL(url)
        var conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.readTimeout = 5000
        var inputStream = conn.inputStream
        var bitmap = BitmapFactory.decodeStream(inputStream)
        handler.post { img.setImageBitmap(bitmap) }

    }
}