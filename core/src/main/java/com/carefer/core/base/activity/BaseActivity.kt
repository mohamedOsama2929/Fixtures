package com.carefer.core.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.carefer.core.R
import com.carefer.core.data.local.StorageManager
import com.carefer.core.databinding.ActivityCommonBinding
import com.carefer.core.domain.entities.side_menu.SideMenuEnum
import com.carefer.core.utils.LoadingListener
import com.carefer.core.utils.loadImg
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), LoadingListener, ToolbarListener {
    private val TAG = BaseActivity::class.java.simpleName

    private lateinit var navFragment: NavHostFragment
    lateinit var navController: NavController

    abstract var navGraphResourceId: Int
    protected lateinit var bundle: Bundle

    private lateinit var _binding: ActivityCommonBinding

    @LayoutRes
    open var layoutRes = R.layout.activity_common

    @Inject
    lateinit var storageManager: StorageManager

    private var selectedSideMenuItem: SideMenuEnum? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCommonBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initViews()
        initViewClicks()
        setNavFragment()
    }

    private fun initViews() {
        _binding.imgProfile.loadImg("", R.drawable.ic_profile_dummy)
    }

    private fun initViewClicks() {
        _binding.imgProfile.setOnClickListener(provideViewClickListener)
        _binding.llBackGroup.setOnClickListener(provideViewClickListener)
        _binding.ivImageBack.setOnClickListener(provideViewClickListener)
    }

    private val provideViewClickListener = View.OnClickListener {
        when (it) {
            _binding.llBackGroup, _binding.ivImageBack -> {
                onBackPressed()
            }
        }
    }

    private fun setNavFragment() {
        navFragment =
            supportFragmentManager.findFragmentById(R.id.common_nav_fragment) as NavHostFragment
        navController = navFragment.navController
        if (::bundle.isInitialized) navController.setGraph(navGraphResourceId, bundle)
        else navController.setGraph(navGraphResourceId)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            _binding.tvTitle.text = destination.label
        }
    }

    override fun showActivityToolbar(currentSideMenuItem: SideMenuEnum?) {
        _binding.toolbar.visibility = View.VISIBLE
        initToolbarNavigationIcon(_binding.toolbar, currentSideMenuItem)
    }

    override fun hideActivityToolbar() {
        _binding.toolbar.visibility = View.GONE
    }

    override fun hideProfileImage() {
        _binding.imgProfile.visibility = View.GONE
    }

    override fun loadUserProfileImage(userProfileImageUrl: String) {
        _binding.imgProfile.loadImg(userProfileImageUrl, R.drawable.ic_profile_dummy)
    }

    override fun setActivityToolbarTitle(title: String, gravity: Int?, isHome: Boolean) {
        _binding.tvTitle.text = title
        _binding.tvWelcome.text = title
        _binding.tvTitle.isVisible = isHome.not()
        _binding.tvWelcome.isVisible = isHome
        _binding.toolbar.isVisible = true
        gravity?.let {
            _binding.tvTitle.gravity = it
        }
    }


    override fun initToolbarNavigationIcon(
        toolbar: androidx.appcompat.widget.Toolbar,
        currentSideMenuItem: SideMenuEnum?
    ) {
        if (currentSideMenuItem == null) {
            _binding.llBackGroup.isVisible = true
            _binding.imgProfile.isVisible = false
            _binding.imgNotification.isVisible = false
            _binding.imgCalnder.isVisible = false
            _binding.imgSearch.isVisible = false
        } else {
            selectedSideMenuItem = currentSideMenuItem
            _binding.llBackGroup.isVisible = false
            _binding.imgProfile.isVisible = true
            _binding.imgNotification.isVisible = true
            _binding.imgCalnder.isVisible = true
            _binding.imgSearch.isVisible = true
        }
    }


    override fun openUserProfile() {
    }

    fun setFragmentDestination(@IdRes resId: Int, bundle: Bundle?) =
        navController.navigate(resId, bundle)

    fun popupFragment() = navController.popBackStack()

    override fun showLoading(show: Boolean, isShimmer: Boolean) {
        if (!isShimmer) {
            _binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}