package com.carefer.core.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.carefer.core.R
import com.carefer.core.base.activity.BottomNavListener
import com.carefer.core.base.activity.ToolbarListener
import com.carefer.core.base.view_model.BaseViewModel
import com.carefer.core.base.view_model.UiState
import com.carefer.core.data.local.StorageManager
import com.carefer.core.domain.entities.base.ErrorModel
import com.carefer.core.domain.entities.base.ErrorStatus
import com.carefer.core.domain.entities.side_menu.SideMenuEnum
import com.carefer.core.utils.LoadingListener
import com.carefer.core.utils.hideKeyBoardOutSideTap
import com.carefer.core.utils.updateStatusBarColor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ensureActive
import javax.inject.Inject


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

@ExperimentalCoroutinesApi
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel, HelperClass : BaseUiHelper?>(
    private val inflate: Inflate<VB>
) : NetworkBaseFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private val TAG = BaseFragment::class.java.simpleName

    private var mLoader: LoadingListener? = null

    abstract val viewModel: VM

    abstract val fragmentHelper: HelperClass

    protected var toolbarListener: ToolbarListener? = null

    private var bottomNavListener: BottomNavListener? = null

    // if null back btn will be displayed
    // otherwise menu icon will be displayed and this item will be selected when open side menu
    open var currentSideMenuItem: SideMenuEnum? = null

    @Inject
    lateinit var storageManager: StorageManager

    override fun onStart() {
        super.onStart()
        updateStatusBarColor(R.color.colorPrimary, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleState()
    }


    override fun onStop() {
        super.onStop()
        viewModel.setErrorReason(ErrorModel(ErrorStatus.NO_DATA))
        viewModel.reset()
    }

    open fun onViewModelError() {}

    open fun handleUnAuthError() {
        storageManager.clearUserData()
    }

    fun setActivityToolbarTitle(title: String, gravity: Int? = null, isHome: Boolean = false) {
        toolbarListener?.setActivityToolbarTitle(title, gravity, isHome)
    }

    fun hideActivityToolbar() {
        toolbarListener?.hideActivityToolbar()
    }

    fun showImageProfile() {
        toolbarListener?.showProfileImage()
    }

    fun hideImageProfile() {
        toolbarListener?.hideProfileImage()
    }

    fun loadImageProfile(userProfileImageUrl: String) {
        toolbarListener?.loadUserProfileImage(userProfileImageUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showLoading(false, false)
    }

    open fun showLoading(show: Boolean, isShimmer: Boolean = false) {
        mLoader?.showLoading(show, isShimmer)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let {
            if (it is LoadingListener) mLoader = it
            if (it is ToolbarListener) toolbarListener = it
            if (it is BottomNavListener) bottomNavListener = it
        }
    }

    override fun onDetach() {
        super.onDetach()
        mLoader = null
        toolbarListener = null
        bottomNavListener = null
        _binding = null
    }

    open fun handleState() {
        lifecycleScope.launchWhenStarted {
            ensureActive()
            viewModel.state.collect {
                when (it) {
                    is UiState.Loading -> {
                        showLoading(it.Loading, it.isShimmer)
                    }

                    is UiState.Error -> {
                        handleErrorStatus(it.Error)
                    }

                    is UiState.CancellationMessage -> {
                        Log.d(TAG, it.CancellationMessage)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.handelViewIntent()
    }

    private fun handleErrorStatus(it: ErrorModel) {
        when (it.errorStatus) {
            ErrorStatus.NO_DATA -> {
            }

            ErrorStatus.NO_CONNECTION -> {
                baseNetworkingDialog.showDialog(
                    requireActivity(),
                    it.errorStatus
                )
            }

            ErrorStatus.UNAUTHORIZED -> handleUnAuthError()
            ErrorStatus.INTERNAL_SERVER_ERROR -> baseNetworkingDialog.showDialog(
                requireActivity(),
                it.errorStatus
            )

            ErrorStatus.UNKNOWN_HOST -> baseNetworkingDialog.showDialog(
                requireActivity(),
                it.errorStatus
            )

            ErrorStatus.FORRBIDEN, ErrorStatus.NOT_FOUND -> Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()

            ErrorStatus.EMPTY_RESPONSE -> handleEmptyResponseError(it.message, it.errorStatus)
            ErrorStatus.CANNOT_DOWNLOAD_FILE -> Toast.makeText(
                context,
                getString(R.string.cant_download_file),
                Toast.LENGTH_SHORT
            ).show()

            else -> Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
        }
        onViewModelError()
    }

    open fun handleEmptyResponseError(errorMessage: String?, errorStatus: ErrorStatus) {
        baseNetworkingDialog.showDialog(
            requireActivity(),
            errorStatus,
            errorMessage
        )
    }

    open fun hideKeyBoardOutSideTap(view: View) {
        view.hideKeyBoardOutSideTap()
    }

    fun toggleBottomNav(isVisible: Boolean) {
        bottomNavListener?.hideBottomNavListener?.value = isVisible
    }
}