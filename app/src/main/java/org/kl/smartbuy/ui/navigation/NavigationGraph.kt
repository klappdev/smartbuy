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
package org.kl.smartbuy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import org.kl.smartbuy.ui.category.CategoryDetailScreen
import org.kl.smartbuy.ui.category.CategoryListScreen
import org.kl.smartbuy.ui.product.ProductDetailScreen
import org.kl.smartbuy.ui.purchase.PurchaseListScreen
import org.kl.smartbuy.viewmodel.CategoryDetailViewModel
import org.kl.smartbuy.viewmodel.CategoryListViewModel
import org.kl.smartbuy.viewmodel.ProductDetailViewModel
import org.kl.smartbuy.viewmodel.PurchaseListViewModel

@Composable
fun NavigationGraph(
    navigationController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    modifier: Modifier
) {
    NavHost(navigationController,
        startDestination = Screen.Category.route,
        modifier = modifier
    ) {
        categoryListGraph(navigationController, bottomBarState)

        composable(Screen.Purchase.route) {
            bottomBarState.value = true
            val viewModel = hiltViewModel<PurchaseListViewModel>()

            PurchaseListScreen(navigationController, viewModel)
        }
    }
}

private fun NavGraphBuilder.categoryListGraph(
    navigationController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = "categoryList",
        route = Screen.Category.route
    ) {
        composable("categoryList") {
            bottomBarState.value = true
            val viewModel = hiltViewModel<CategoryListViewModel>()
            CategoryListScreen(navigationController, viewModel)
        }

        categoryDetailGraph(navigationController, bottomBarState)
    }
}

private fun NavGraphBuilder.categoryDetailGraph(
    navigationController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = "categoryDetail/{categoryId}",
        route = "categoryDetail"
    ) {
        composable(
            route = "categoryDetail/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            bottomBarState.value = false
            val categoryId = backStackEntry.arguments?.getLong("categoryId")
            val viewModel = hiltViewModel<CategoryDetailViewModel>()
            CategoryDetailScreen(navigationController, viewModel, categoryId ?: -1)
        }

        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            bottomBarState.value = false
            val productId = backStackEntry.arguments?.getLong("productId")
            val viewModel = hiltViewModel<ProductDetailViewModel>()
            ProductDetailScreen(viewModel, productId ?: -1)
        }
    }
}