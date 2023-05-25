package com.carefer.matches.ui.activity

import com.carefer.core.base.activity.BaseActivity
import com.carefer.matches.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchesActivity : BaseActivity() {
    override var navGraphResourceId: Int = R.navigation.matches_nav_graph

}