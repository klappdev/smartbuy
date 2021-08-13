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
package org.kl.smartbuy.view.purchase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.appcompattheme.AppCompatTheme

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import org.kl.smartbuy.R
import org.kl.smartbuy.databinding.ActivityEditPurchaseBinding
import org.kl.smartbuy.event.purchase.EditPurchaseListener
import org.kl.smartbuy.event.purchase.SelectDatePurchaseListener
import org.kl.smartbuy.event.validate.ViewValidator
import org.kl.smartbuy.viewmodel.PurchaseDetailViewModel

@AndroidEntryPoint
class EditPurchaseActivity : AppCompatActivity() {
    @Inject
    public lateinit var viewValidator: ViewValidator

    public lateinit var nameTextView: EditText
    public lateinit var dateTextView: EditText
    private lateinit var editPurchaseButton: Button

    public val purchaseDetailViewModel: PurchaseDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        purchaseDetailViewModel.resetPurchase()

        /* ---- */
        val binding = ActivityEditPurchaseBinding.inflate(layoutInflater)
        binding.viewModel = purchaseDetailViewModel
        binding.lifecycleOwner = this

        initView(binding)

        //setContentView(binding.root)
        /* ---- */

        setContent {
            AppCompatTheme {
                EditPurchaseScreen()
            }
        }
    }

    @Preview
    @Composable
    private fun EditPurchaseScreen() {
        var namePurchase by remember { mutableStateOf("") }
        var datePurchase by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom) {
            Image(
                painter = painterResource(R.drawable.purchase_icon),
                contentDescription = "Purchase icon"
            )
            TextField(
                value = namePurchase,
                onValueChange = { namePurchase = it },
                label = { Text(stringResource(R.string.name_hint)) }
            )
            TextField(
                value = datePurchase,
                onValueChange = { datePurchase = it },
                label = { Text(stringResource(R.string.date_hint)) }
            )

        }
    }

    private fun initView(binding: ActivityEditPurchaseBinding) {
        nameTextView = binding.namePurchaseTextView
        dateTextView = binding.datePurchaseTextView
        editPurchaseButton = binding.editPurchaseButton
        dateTextView.setOnClickListener(SelectDatePurchaseListener(this))
        editPurchaseButton.setOnClickListener(EditPurchaseListener(this))
    }
}