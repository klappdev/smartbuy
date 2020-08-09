package org.kl.smartbuy.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator

import org.kl.smartbuy.databinding.FragmentViewPagerBinding
import org.kl.smartbuy.state.TabOrder
import org.kl.smartbuy.view.adapter.TabPagerAdapter
import org.kl.smartbuy.R
import org.kl.smartbuy.state.TabOrder.*

class TabPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = TabPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) {  tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (TabOrder.findBy(position)) {
            CATEGORY_TAB -> getString(R.string.category_tab)
            PURCHASE_TAB -> getString(R.string.purchase_tab)
        }
    }
}
