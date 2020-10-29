package org.kl.smartbuy.view.fragment

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator

import org.kl.smartbuy.R
import org.kl.smartbuy.state.TabOrder
import org.kl.smartbuy.state.TabOrder.*
import org.kl.smartbuy.view.adapter.TabPagerAdapter
import org.kl.smartbuy.databinding.FragmentViewPagerBinding

class TabPagerFragment : Fragment(R.layout.fragment_view_pager) {
    private var binding: FragmentViewPagerBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val innerBinding = FragmentViewPagerBinding.bind(view)
        this.binding = innerBinding

        val tabLayout = innerBinding.tabs
        val viewPager = innerBinding.viewPager

        viewPager.adapter = TabPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) {  tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(innerBinding.toolbar)
    }

    override fun onDestroyView() {
        this.binding = null
        super.onDestroyView()
    }

    private fun getTabTitle(position: Int): String? {
        return when (TabOrder.findBy(position)) {
            CATEGORY_TAB -> getString(R.string.category_tab)
            PURCHASE_TAB -> getString(R.string.purchase_tab)
        }
    }
}
