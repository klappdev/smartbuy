package org.kl.smartbuy.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil.setContentView

import org.kl.smartbuy.R
import org.kl.smartbuy.util.toast
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.databinding.ActivityMainBinding
import org.kl.smartbuy.event.purchase.DeletePurchaseListener
import org.kl.smartbuy.event.purchase.SearchPurchaseListener
import org.kl.smartbuy.event.purchase.SortPurchaseListener

class MainActivity : AppCompatActivity() {
    private lateinit var sortPurchaseListener: SortPurchaseListener
    private lateinit var searchPurchaseListener: SearchPurchaseListener
    private lateinit var deletePurchaseListener: DeletePurchaseListener

    internal var purchaseFragment: PurchaseFragment? = null
    private var itemSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        this.sortPurchaseListener = SortPurchaseListener(this)
        this.searchPurchaseListener = SearchPurchaseListener(this)
        this.deletePurchaseListener = DeletePurchaseListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(searchPurchaseListener)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> notifyItemSelected(false)
        R.id.action_edit -> navigateToEditPurchase()
        R.id.action_sort -> sortPurchaseListener()
        R.id.action_delete -> deletePurchaseListener()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (itemSelected) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)

            menu?.findItem(R.id.action_search)?.isVisible = false
            menu?.findItem(R.id.action_sort)?.isVisible = false
            menu?.findItem(R.id.action_edit)?.isVisible = true
            menu?.findItem(R.id.action_delete)?.isVisible = true
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)

            menu?.findItem(R.id.action_search)?.isVisible = true
            menu?.findItem(R.id.action_sort)?.isVisible = true
            menu?.findItem(R.id.action_edit)?.isVisible = false
            menu?.findItem(R.id.action_delete)?.isVisible = false
        }

        return true
    }

    fun notifyItemSelected(selected: Boolean): Boolean {
        this.itemSelected = selected
        this.invalidateOptionsMenu()

        return true
    }

    private fun navigateToEditPurchase(): Boolean {
        applicationContext.toast("click edit button")
        return true
    }
}
