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
package org.kl.smartbuy.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import coil.request.ImageRequest
import coil.compose.rememberAsyncImagePainter

import org.kl.smartbuy.R
import org.kl.smartbuy.db.entity.Product
import org.kl.smartbuy.util.toast
import org.kl.smartbuy.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel = viewModel()) {
    val product by viewModel.product.observeAsState()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        item { ImageProductView(product) }
        item { NameProductView(product) }
        item { PriceProductView(product) }
        item { RangeProductView(product) }
        item { SubmitProductView(product) }
    }
}

@Composable
private fun ImageProductView(product: Product?) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product?.iconUrl)
            .error(android.R.drawable.ic_menu_report_image)
            .crossfade(true)
            .build()
    )

    Image(
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = product?.name,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(PaddingValues(16.dp))
    )
}

@Composable
private fun NameProductView(product: Product?) {
    OutlinedTextField(
        value = product?.name ?: "<no name>",
        onValueChange = { },
        readOnly = true,
        label = { Text(stringResource(R.string.name_hint)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(16.dp, 8.dp, 16.dp, 8.dp))
            .background(color = Color.White)
    )
}

@Composable
private fun PriceProductView(product: Product?) {
    OutlinedTextField(
        value = product?.price.toString(),
        onValueChange = { },
        readOnly = true,
        label = { Text(stringResource(R.string.price_hint)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(16.dp, 8.dp, 16.dp, 8.dp))
            .background(color = Color.White)
    )
}

@Composable
private fun RangeProductView(product: Product?) {
    OutlinedTextField(
        value = product?.range.toString(),
        onValueChange = { },
        readOnly = true,
        label = { Text(product?.measure ?: "<no unit>") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(16.dp, 8.dp, 16.dp, 8.dp))
            .background(color = Color.White)
    )
}

@Composable
private fun SubmitProductView(product: Product?) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                context.toast("Product ${product?.name} was added")
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_color)),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(PaddingValues(16.dp, 8.dp, 16.dp, 8.dp))
        ) {
            Text(text = stringResource(R.string.add_title), color = Color.White)
        }
    }
}