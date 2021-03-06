package com.majorik.moviebox.ui.about_tmdb

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.setWindowTransparency
import com.majorik.moviebox.extensions.updateMargin
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import kotlinx.android.synthetic.main.activity_about_the_movie_database.*

class AboutTheMovieDatabaseActivity : BaseSlidingActivity() {

    override fun getRootView(): View = window.decorView.rootView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_the_movie_database)

        setWindowTransparency(::updateMargins)

        setSupportActionBar(about_toolbar)

        supportActionBar?.run {
            setDisplayUseLogoEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        webview.run {
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.userAgentString = "Android"
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()

            loadUrl(UrlConstants.ABOUT_TMDB)
        }
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        about_toolbar.updateMargin(top = statusBarSize)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = false

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (Build.VERSION.SDK_INT in 21..25 && (resources.configuration.uiMode == resources.configuration.uiMode)) {
            return
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }
}
