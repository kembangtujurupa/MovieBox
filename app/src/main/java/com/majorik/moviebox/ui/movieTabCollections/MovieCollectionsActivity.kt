package com.majorik.moviebox.ui.movieTabCollections

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.FragmentsPagerAdapter
import com.majorik.moviebox.extensions.setWindowTransparency
import com.majorik.moviebox.extensions.updateMargin
import com.majorik.moviebox.ui.base.BaseSlidingActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_tab_collections.*

class MovieCollectionsActivity : BaseSlidingActivity() {
    private lateinit var pagerAdapter: FragmentsPagerAdapter

    private val fragments =
        listOf<Fragment>(
            MovieCollectionsFragment(MovieCollectionType.NOW_PLAYING),
            MovieCollectionsFragment(MovieCollectionType.POPULAR),
            MovieCollectionsFragment(MovieCollectionType.TOP_RATED),
            MovieCollectionsFragment(MovieCollectionType.UPCOMING)
        )

    override fun getRootView(): View = window.decorView.rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_collections)

        setWindowTransparency(::updateMargins)

        setSupportActionBar(m_toolbar_collections)

        m_toolbar_collections.navigationIcon?.setTint(
            ContextCompat.getColor(
                this,
                R.color.mine_shaft
            )
        )

        m_toolbar_collections.setNavigationOnClickListener {
            onBackPressed()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.mine_shaft)
        }

        supportActionBar?.run {
            setDisplayUseLogoEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        configureTabLayout()
        setPageByName(intent.extras?.getString("collection_name"))
    }

    private fun setPageByName(collectionName: String?) {
        when (collectionName) {
            MovieCollectionType.NOW_PLAYING.name -> {
                collection_view_pager.setCurrentItem(0, true)
            }

            MovieCollectionType.POPULAR.name -> {
                collection_view_pager.setCurrentItem(1, true)
            }

            MovieCollectionType.TOP_RATED.name -> {
                collection_view_pager.setCurrentItem(2, true)
            }

            MovieCollectionType.UPCOMING.name -> {
                collection_view_pager.setCurrentItem(3, true)
            }
            else -> {
                collection_view_pager.setCurrentItem(0, true)
            }
        }
    }

    private fun updateMargins(statusBarSize: Int, @Suppress("UNUSED_PARAMETER") navigationBarSize: Int) {
        m_toolbar_collections.updateMargin(top = statusBarSize)
    }

    override fun onSlidingStarted() {}

    override fun onSlidingFinished() {}

    override fun canSlideDown(): Boolean = false

    private fun configureTabLayout() {
        val pagerTitles: Array<String> =
            arrayOf(
                getString(R.string.now_playing),
                getString(R.string.collection_popular),
                getString(R.string.collection_top_rated),
                getString(R.string.upcoming)
            )

        pagerAdapter = FragmentsPagerAdapter(
            fragments,
            supportFragmentManager,
            lifecycle
        )

        collection_view_pager.adapter = pagerAdapter
        collection_view_pager.offscreenPageLimit = fragments.size

        TabLayoutMediator(p_tab_layout, collection_view_pager) { tab, position ->
            tab.text = pagerTitles.getOrNull(position)
        }.attach()
    }
}
