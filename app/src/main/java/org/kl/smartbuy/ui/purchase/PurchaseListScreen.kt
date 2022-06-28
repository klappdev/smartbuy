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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

import org.kl.smartbuy.R
import org.kl.smartbuy.db.entity.Purchase
import org.kl.smartbuy.viewmodel.PurchaseListViewModel

@Composable
fun PurchaseListScreen(viewModel: PurchaseListViewModel/* = viewModel()*/) {
    val purchases: LazyPagingItems<Purchase> = viewModel.purchases.collectAsLazyPagingItems()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(top = 4.dp)
    ) {
        items(purchases) { purchase ->
            if (purchase != null) {
                PurchaseItem(purchase)
            } else {
                PurchaseItemPlaceholder()
            }
        }
    }
}

@Composable
private fun PurchaseItem(purchase: Purchase) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.White)
    ) {
        ImagePurchaseView()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            NamePurchaseView(purchase)
            DatePurchaseView(purchase)
        }
    }
}

@Composable
private fun PurchaseItemPlaceholder() {
    PurchaseItem(Purchase(-1, "<no name>", "<no date>"))
}

@Composable
private fun ImagePurchaseView() {
    Image(
        painter = painterResource(R.drawable.purchase_icon),
        contentDescription = stringResource(R.string.purchase_name),
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .padding(10.dp)
    )
}

@Composable
private fun NamePurchaseView(purchase: Purchase) {
    Text(
        text = purchase.name,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif,
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, top = 8.dp, end = 4.dp, bottom = 4.dp)
            .wrapContentWidth(Alignment.Start)
    )
}

@Composable
private fun DatePurchaseView(purchase: Purchase) {
    Text(
        text = purchase.date,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif,
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 36.dp, top = 8.dp, end = 4.dp, bottom = 4.dp)
            .wrapContentWidth(Alignment.Start)
    )
}