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
package org.kl.smartbuy.ui.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import org.kl.smartbuy.R
import org.kl.smartbuy.db.entity.Product
import org.kl.smartbuy.viewmodel.CategoryDetailViewModel

@Composable
fun CategoryDetailAppBar(onNavigate: (Product) -> Unit) {
    Column {
        TopAppBar(
            elevation = dimensionResource(R.dimen.appbar_elevation),
            title = { Text(stringResource(R.string.category_detail_title)) },
            backgroundColor = colorResource(R.color.primary_color)
        )

        CategoryDetailScreen(onNavigate)
    }
}

@Composable
private fun CategoryDetailScreen(
    onNavigate: (Product) -> Unit,
    viewModel: CategoryDetailViewModel = viewModel()
) {
    val products: List<Product>? by viewModel.products.observeAsState()

    if (!products.isNullOrEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(integerResource(R.integer.grid_columns)),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(products!!) { product ->
                ProductCard(product) {
                    onNavigate(product)
                }
            }
        }
    } else {
        Text(text = stringResource(R.string.empty_products),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductCard(product: Product, onNavigate: () -> Unit) {
    Card(
        onClick = onNavigate,
        elevation = dimensionResource(R.dimen.card_elevation),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = dimensionResource(R.dimen.card_corner_radius),
            bottomStart = dimensionResource(R.dimen.card_corner_radius),
            bottomEnd = 0.dp
        ),
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.card_side_margin))
            .padding(bottom = dimensionResource(R.dimen.card_bottom_margin))
    ) {
        Column(Modifier.fillMaxWidth()) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.iconUrl)
                    .error(android.R.drawable.ic_menu_report_image)
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.card_image_height))
            )

            Text(
                text = product.name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.margin_normal))
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}