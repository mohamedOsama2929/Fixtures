package com.carefer.fixtures.ui.activity

import com.carefer.core.base.activity.BaseActivity
import com.carefer.fixtures.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixturesActivity : BaseActivity() {
    override var navGraphResourceId: Int = R.navigation.fixtures_nav_graph

}