package org.kl.smartbuy.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView

import org.kl.smartbuy.R
import org.kl.smartbuy.util.toast
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    internal var purchaseFragment: PurchaseFragment? = null
    private var itemSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            notifyItemSelected(false)
            true
        }
        R.id.action_search -> {
            applicationContext.toast("click search button")
            true
        }
        R.id.action_sort -> {
            purchaseFragment?.sortAction()
            applicationContext.toast("click sort button")
            true
        }
        R.id.action_edit -> {
            applicationContext.toast("click edit button")
            true
        }
        R.id.action_delete -> {
            purchaseFragment?.removeAction()
            applicationContext.toast("Selected purchase was deleted")
            true
        }
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

    fun notifyItemSelected(selected: Boolean) {
        this.itemSelected = selected

        this.invalidateOptionsMenu()
    }
}
