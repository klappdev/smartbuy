package org.kl.smartbuy.event.purchase

import androidx.navigation.Navigation
import org.kl.smartbuy.R
import org.kl.smartbuy.view.MainActivity
import org.kl.smartbuy.view.fragment.TabPagerFragmentDirections

class NavigateEditPurchaseListener(private val activity: MainActivity) {

    operator fun invoke(): Boolean {
        val purchaseAdapter = activity.purchaseFragment?.purchaseAdapter
        val purchaseId: Int = purchaseAdapter?.getCurrentItemId() ?: -1

        if (purchaseId != -1) {
            val direction = TabPagerFragmentDirections
                            .actionTabPagerFragmentToEditPurchaseActivity(purchaseId)

            val navigationController = Navigation.findNavController(activity, R.id.navigation_host_fragment)
            navigationController.navigate(direction)

            return true
        }

        return false
    }
}