/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2021 https://github.com/klappdev
 *
 * Permission is hereby  granted, free of charge, to any  person obtaining a copy
 * of this software and associated  documentation files (the "Software"), to deal
 * in the Software  without restriction, including without  limitation the rights
 * to  use, copy,  modify, merge,  publish, distribute,  sublicense, and/or  sell
 * copies  of  the Software,  and  to  permit persons  to  whom  the Software  is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE  IS PROVIDED "AS  IS", WITHOUT WARRANTY  OF ANY KIND,  EXPRESS OR
 * IMPLIED,  INCLUDING BUT  NOT  LIMITED TO  THE  WARRANTIES OF  MERCHANTABILITY,
 * FITNESS FOR  A PARTICULAR PURPOSE AND  NONINFRINGEMENT. IN NO EVENT  SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS  BE  LIABLE FOR  ANY  CLAIM,  DAMAGES OR  OTHER
 * LIABILITY, WHETHER IN AN ACTION OF  CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE  OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kl.smartbuy.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.R
import org.kl.smartbuy.view.fragment.PurchaseFragment
import org.kl.smartbuy.view.fragment.CategoryFragment
import org.kl.smartbuy.databinding.ActivityMainBinding
import org.kl.smartbuy.event.purchase.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sortPurchaseListener: SortPurchaseListener
    private lateinit var navigatePurchaseListener: NavigatePurchaseListener
    private lateinit var deletePurchaseListener: DeletePurchaseListener
    private lateinit var resetPurchaseListener: ResetPurchaseListener

    private lateinit var purchaseFragment: PurchaseFragment
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var searchMenuItem: MenuItem
    private var menuItemSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchMenuItem = menu?.findItem(R.id.action_search)!!

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> resetPurchaseListener()
        R.id.action_edit -> navigatePurchaseListener.navigateEditPurchase()
        R.id.action_sort -> sortPurchaseListener()
        R.id.action_delete -> deletePurchaseListener()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(menuItemSelected)
        supportActionBar?.setDisplayShowHomeEnabled(menuItemSelected)

        menu?.findItem(R.id.action_search)?.isVisible = !menuItemSelected
        menu?.findItem(R.id.action_sort)?.isVisible = !menuItemSelected
        menu?.findItem(R.id.action_edit)?.isVisible = menuItemSelected
        menu?.findItem(R.id.action_delete)?.isVisible = menuItemSelected

        return true
    }

    fun initCategoryListeners(fragment: CategoryFragment) {
        this.categoryFragment = fragment

        /*TODO: init listeners*/
    }

    fun initPurchaseListeners(fragment: PurchaseFragment) {
        this.purchaseFragment = fragment

        this.sortPurchaseListener = SortPurchaseListener(fragment)
        this.navigatePurchaseListener = NavigatePurchaseListener(fragment)
        this.deletePurchaseListener = DeletePurchaseListener(fragment)
        this.resetPurchaseListener = ResetPurchaseListener(fragment)

        searchMenuItem.setOnActionExpandListener(SearchPurchaseListener(fragment))
    }

    fun notifyMenuItemSelected(selected: Boolean): Boolean {
        this.menuItemSelected = selected
        invalidateOptionsMenu()

        return true
    }
}
