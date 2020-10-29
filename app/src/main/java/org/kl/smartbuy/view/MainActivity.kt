package org.kl.smartbuy.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView

import org.kl.smartbuy.R
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.databinding.ActivityMainBinding
import org.kl.smartbuy.event.purchase.DeletePurchaseListener
import org.kl.smartbuy.event.purchase.NavigateEditPurchaseListener
import org.kl.smartbuy.event.purchase.SearchPurchaseListener
import org.kl.smartbuy.event.purchase.SortPurchaseListener

class MainActivity : AppCompatActivity() {
    private lateinit var sortPurchaseListener: SortPurchaseListener
    private lateinit var searchPurchaseListener: SearchPurchaseListener
    private lateinit var navigateEditPurchaseListener: NavigateEditPurchaseListener
    private lateinit var deletePurchaseListener: DeletePurchaseListener

    internal var purchaseFragment: PurchaseFragment? = null
    private var itemSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        this.searchPurchaseListener = SearchPurchaseListener(this)
        this.sortPurchaseListener = SortPurchaseListener(this)
        this.navigateEditPurchaseListener = NavigateEditPurchaseListener(this)
        this.deletePurchaseListener = DeletePurchaseListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchMenuItem = menu?.findItem(R.id.action_search)
        searchMenuItem?.setOnActionExpandListener(searchPurchaseListener)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> notifyItemSelected(false)
        R.id.action_edit -> navigateEditPurchaseListener()
        R.id.action_sort -> sortPurchaseListener()
        R.id.action_delete -> deletePurchaseListener()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(itemSelected)
        supportActionBar?.setDisplayShowHomeEnabled(itemSelected)

        menu?.findItem(R.id.action_search)?.isVisible = !itemSelected
        menu?.findItem(R.id.action_sort)?.isVisible = !itemSelected
        menu?.findItem(R.id.action_edit)?.isVisible = itemSelected
        menu?.findItem(R.id.action_delete)?.isVisible = itemSelected

        return true
    }

    fun notifyItemSelected(selected: Boolean): Boolean {
        this.itemSelected = selected
        this.invalidateOptionsMenu()

        return true
    }
}
