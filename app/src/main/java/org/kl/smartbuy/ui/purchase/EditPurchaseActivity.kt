/*
 * Licensed under the MIT License <http://opensource.org/licenses/MIT>.
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2020 - 2022 https://github.com/klappdev
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
package org.kl.smartbuy.ui.purchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.composethemeadapter.MdcTheme

import dagger.hilt.android.AndroidEntryPoint

import org.kl.smartbuy.R
import org.kl.smartbuy.util.toast
import org.kl.smartbuy.viewmodel.PurchaseDetailViewModel

@AndroidEntryPoint
class EditPurchaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MdcTheme {
                EditPurchaseScreen()
            }
        }
    }

    /*@Preview*/
    @Composable
    private fun EditPurchaseScreen(viewModel: PurchaseDetailViewModel = viewModel()) {
        val name by viewModel.name.collectAsState()
        val date by viewModel.date.collectAsState()

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            item { ImagePurchaseView() }
            item { NamePurchaseView(name, viewModel::onNameChange) }
            item { DatePurchaseView(date, viewModel::onDateChange) }
            item { SubmitPurchaseView(viewModel) }
        }
    }

    @Composable
    private fun ImagePurchaseView() {
        Image(
            painter = painterResource(R.drawable.purchase_icon),
            contentDescription = stringResource(R.string.purchase_name),
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .padding(PaddingValues(16.dp))
        )
    }

    @Composable
    private fun NamePurchaseView(name: String, onNameChange: (String) -> Unit) {
        val nameValid = name matches "^[a-zA-Z0-9]+".toRegex()

        TextField(
            value = name,
            onValueChange = onNameChange,
            label = {
                if (nameValid) {
                    Text(stringResource(R.string.name_hint))
                } else {
                    Text(stringResource(R.string.name_value_error))
                }
            },
            isError = !nameValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(16.dp, 8.dp, 16.dp, 8.dp))
        )
    }

    @Composable
    private fun DatePurchaseView(date: String, onDateChange: (String) -> Unit) {
        TextField(
            value = date,
            onValueChange = onDateChange,
            label = {
                if (date.isEmpty()) {
                    Text(stringResource(R.string.date_value_error))
                } else {
                    Text(stringResource(R.string.date_hint))
                }
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                /*SelectDatePurchaseListener(this)*/
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = date.isEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(16.dp, 8.dp, 16.dp, 8.dp))
        )
    }

    @Composable
    private fun SubmitPurchaseView(viewModel: PurchaseDetailViewModel) {
        val context = LocalContext.current

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.storePurchase()
                    context.toast("Purchase ${viewModel.name.value} was updated")
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_color)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(PaddingValues(32.dp, 8.dp, 32.dp, 8.dp))
            ) {
                Text(text = stringResource(R.string.edit_title), color = Color.White)
            }
        }
    }
}