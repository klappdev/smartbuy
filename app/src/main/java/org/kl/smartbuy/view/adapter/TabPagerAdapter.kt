package org.kl.smartbuy.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.kl.smartbuy.state.TabOrder
import org.kl.smartbuy.state.TabOrder.CATEGORY_TAB
import org.kl.smartbuy.state.TabOrder.PURCHASE_TAB
import org.kl.smartbuy.view.fragment.CategoryFragment
import org.kl.smartbuy.view.fragment.PurchaseFragment

class TabPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (TabOrder.findBy(position)) {
            CATEGORY_TAB -> CategoryFragment()
            PURCHASE_TAB -> PurchaseFragment()
        }
    }

    override fun getItemCount(): Int {
        return TabOrder.values().size
    }
}