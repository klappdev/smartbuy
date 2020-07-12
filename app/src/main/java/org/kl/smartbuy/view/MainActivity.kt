package org.kl.smartbuy.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

import org.kl.smartbuy.R
import org.kl.smartbuy.view.adapter.SectionPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: SectionPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar
    private lateinit var lastPurchaseButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        this.tabLayout = findViewById(R.id.tabs)
        with(tabLayout) {
            addTab(newTab().setText(R.string.category_tab))
            addTab(newTab().setText(R.string.purchase_tab))

            tabGravity = TabLayout.GRAVITY_FILL
        }

        this.pagerAdapter = SectionPagerAdapter(2, supportFragmentManager)

        this.viewPager = findViewById(R.id.page_container)
        this.tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

        with(viewPager) {
            offscreenPageLimit = 2
            adapter = pagerAdapter

            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            /*addOnPageChangeListener(ChangeTabListener(pagerAdapter, this.context))*/
        }

        this.lastPurchaseButton = findViewById(R.id.last_purchase_button)
        /*this.lastPurchaseButton.setOnClickListener(::clickShowLastPurchase)*/
    }
}
