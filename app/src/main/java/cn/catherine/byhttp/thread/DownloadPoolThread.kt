package cn.catherine.byhttp.thread

import android.os.Environment
import android.os.Handler
import android.os.Message
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/***
 * create by catherine
 *
 * 一个分段下载，多线程下载的例子
 * */
class DownloadPoolThread {
    var executor: Executor = Executors.newFixedThreadPool(3)
    private var handler: Handler

    constructor(handler: Handler) {
        this.handler = handler
    }

    fun downloadFile(urlStr: String) {
        var url = URL(urlStr)
        var conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.readTimeout = 5000
        var count = conn.contentLength
        var block = count / 3

        val fileName: String = urlStr.getFileName()
        var parent: File = Environment.getExternalStorageDirectory()
        var fileDownload: File = File(parent, fileName)
        for (i in 0..2) {
            println("=====$i")
            var start = i * block
            var end = if (i == 2) count else (i + 1) * block - 1
            downloadRunnable(urlStr, fileDownload.absolutePath, start.toLong(), end.toLong(), handler)
                    .let {
                        executor.execute(it)

                    }
        }

    }

    private fun String.getFileName(): String {
        return this.substring(this.lastIndexOf("/") + 1)
    }

}



class downloadRunnable : Runnable {
    private var url: String? = null
    private var fileName: String? = null
    private var start: Long = 0
    private var end: Long = 0
    private var handler: Handler

    constructor(url: String, fileName: String, start: Long, end: Long, handler: Handler) {
        println("======downloadRunnable${Thread.currentThread()}")
        this.url = url
        this.fileName = fileName
        this.start = start
        this.end = end
        this.handler = handler
    }

    override fun run() {
        var url = URL(url)
        var conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.readTimeout = 5000
        conn.setRequestProperty("Range", "bytes=$start-$end")//设置下载格式
        var accessFile: RandomAccessFile? = RandomAccessFile(File(fileName), "rwd")
        accessFile?.seek(start)
        var inputStream = conn.inputStream
        var b = ByteArray(1024 * 4)
        var len = 0
        while (inputStream.read(b).apply { len = this } != -1) {
            accessFile?.write(b, 0, len)
        }
        accessFile?.close()
        inputStream?.close()
        var message = Message()
        message.what = 1
        handler.sendMessage(message)
    }


}