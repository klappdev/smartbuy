package org.kl.smartbuy.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.activity.viewModels
import androidx.navigation.navArgs
import androidx.appcompat.app.AppCompatActivity

import org.kl.smartbuy.databinding.ActivityEditPurchaseBinding
import org.kl.smartbuy.event.purchase.EditPurchaseListener
import org.kl.smartbuy.event.purchase.SelectDatePurchaseListener
import org.kl.smartbuy.util.Injector
import org.kl.smartbuy.viewmodel.PurchaseDetailViewModel

class EditPurchaseActivity : AppCompatActivity() {
    internal lateinit var nameTextView: EditText
    internal lateinit var dateTextView: EditText
    private lateinit var editPurchaseButton: Button

    private val arguments: EditPurchaseActivityArgs by navArgs()
    internal val purchaseDetailViewModel: PurchaseDetailViewModel by viewModels {
        Injector.providePurchaseDetailViewModelFactory(applicationContext, arguments.purchaseId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditPurchaseBinding.inflate(layoutInflater)
        binding.viewModel = purchaseDetailViewModel
        binding.lifecycleOwner = this

        nameTextView = binding.namePurchaseTextView
        dateTextView = binding.datePurchaseTextView
        editPurchaseButton = binding.editPurchaseButton
        dateTextView.setOnClickListener(SelectDatePurchaseListener(this))
        editPurchaseButton.setOnClickListener(EditPurchaseListener(this))

        setContentView(binding.root)
    }
}