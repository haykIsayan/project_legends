package com.example.android_bleed.main

import android.os.Bundle
import android.view.MenuItem
import com.example.android_bleed.R
import com.example.android_bleed.data.models.User
import com.example.android_bleed.flow.AndroidFlow
import com.example.android_bleed.flow.view.FlowActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : FlowActivity() {

    override fun getFragmentContainerId(): Int {
        return R.id.fl_main_container_activity_main
    }

    private lateinit var mMainFlow: AndroidFlow

    private lateinit var bnvMainNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.bnvMainNavigation = findViewById(R.id.bnv_nav_view_activity_main)

        this.mMainFlow = MainFlow(this.application)

        registerFlow(mMainFlow)
        launchFlow(mMainFlow)

        this.bnvMainNavigation.setOnNavigationItemSelectedListener {
            onNavigationItemSelected(menuItem = it)
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val user = intent.getParcelableExtra<User>(User.EXTRA_USER)
        val bundle = Bundle()
        bundle.putString(User.EXTRA_USERNAME, user.userName)
        executeFlow(mMainFlow, menuItem.title.toString(), bundle)
        return true
    }

}