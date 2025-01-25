package com.group.charity.presentation.activity.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group.charity.R
import com.group.charity.presentation.activity.BaseActivity
import com.group.charity.presentation.fragment.home.HomeFragment
import com.group.util.PrefData
import com.group.util.logOther
import com.group.util.replaceFragment
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        logOther("userToken ${Prefs.getString(PrefData.USER_TOKEN)}")
        supportFragmentManager.replaceFragment(
            HomeFragment(),
            R.id.homeFrame
        )
    }
}