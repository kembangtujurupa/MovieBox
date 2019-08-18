package com.majorik.moviebox.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.TraktWatchlistAdapter
import com.majorik.moviebox.ui.login.LoginActivity
import com.majorik.moviebox.utils.SharedPrefsManager
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private val sharedPrefsManager: SharedPrefsManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}