package piotrmadry.demowebviewinjectedjs

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.BufferedReader


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById<WebView>(R.id.webView) as WebView

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                injectJS()
            }
        }
        webView.loadUrl("https://www.google.com")
    }

    private fun injectJS() {
        val jsContent: String?
        jsContent = try {
            val inputStream = assets.open("style.js")
            val fileContent = inputStream.bufferedReader().use(BufferedReader::readText)
            inputStream.close()
            fileContent
        } catch (e: Exception) {
            null
        }
        jsContent?.let { webView.loadUrl("javascript:($jsContent)()") }
    }
}
