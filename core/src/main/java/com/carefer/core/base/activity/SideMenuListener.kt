package com.carefer.core.base.activity

import com.carefer.core.domain.entities.side_menu.SideMenuEnum


interface SideMenuListener {
    fun onSideMenuItemSelected(item: SideMenuEnum)
    fun closeSideMenu()
    fun onMyProfileClicked()
}