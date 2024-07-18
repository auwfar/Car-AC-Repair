package com.caracrepair.app.presentation.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.caracrepair.app.presentation.main.home.HomeFragment
import com.caracrepair.app.presentation.main.repairshop.RepairShopFragment

class MainAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment.newInstance()
            1 -> RepairShopFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }
}