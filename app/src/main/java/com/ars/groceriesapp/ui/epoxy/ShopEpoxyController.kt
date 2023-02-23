package com.ars.groceriesapp.ui.epoxy

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.bumptech.glide.Glide
import com.ars.groceriesapp.R
import com.ars.groceriesapp.databinding.PopularCategoryItemBinding
import com.ars.groceriesapp.databinding.ShopHeaderItemBinding
import com.ars.groceriesapp.databinding.ShopProductItemBinding
import com.ars.groceriesapp.databinding.ShopSectionTitleItemBinding
import com.ars.domain.model.Category
import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.groceriesapp.ui.epoxy.helper.ViewBindingKotlinModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ShopEpoxyController(
    private val context: Context
) : EpoxyController() {

    private var exclusiveProductsResource: Resource<List<Product>?>? = null
    private var onSaleProductsResource: Resource<List<OnSaleProduct>?>? = null
    private var mostRatedProductsResource: Resource<List<Product>?>? = null
    private var categoriesResource: Resource<List<Category>?>? = null


    override fun buildModels() {
        ShopHeader()
            .id("shop_header")
            .addTo(this)

        ShopSection("Exclusive Offer")
            .id("shop_section_1")
            .addTo(this)

        loadExclusives()

        loadCategories()

        loadOnSaleProducts()

        loadMostRatedProducts()


    }


    private fun loadExclusives() {
        val exclusiveModels = mutableListOf<ShopProduct>()

        when (exclusiveProductsResource) {
            is Resource.Success -> {
                val response =
                    (exclusiveProductsResource as Resource.Success<List<Product>?>).result

                response?.forEachIndexed { index, product ->
                    exclusiveModels.add(
                        ShopProduct(product, ::onProductAddToCartClick).apply {
                            id(index)
                        }
                    )
                }

                carousel {
                    id("exclusive_offer_carousel")
                    numViewsToShowOnScreen(2F)
                    models(exclusiveModels)

                }

            }
            is Resource.Failure -> {
                Toast.makeText(context, "Error exclusives", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Loading
                //Toast.makeText(context, "Loading exclusives", Toast.LENGTH_SHORT).show()
                repeat(2) {
                    exclusiveModels.add(
                        ShopProduct(null, ::onProductAddToCartClick).apply {
                            id(it)
                        }
                    )
                }

                carousel {
                    id("exclusive_offer_carousel")
                    numViewsToShowOnScreen(2F)
                    models(exclusiveModels)

                }
            }
        }
    }

    private fun loadCategories() {

        val categoriesModels = mutableListOf<ShopCategory>()

        ShopSection("Popular Categories")
            .id("shop_section_2")
            .addTo(this)

        when (categoriesResource) {
            is Resource.Success -> {
                val response = (categoriesResource as Resource.Success<List<Category>?>).result
                response?.forEachIndexed { index, category ->
                    categoriesModels.add(
                        ShopCategory(category, ::onCategoryClick).apply {
                            id(index)
                        }
                    )
                }

                carousel {
                    id("popular_categories_carousel")
                    numViewsToShowOnScreen(1.5F)
                    models(categoriesModels)
                }

            }
            is Resource.Failure -> {
                Toast.makeText(context, "Error categories", Toast.LENGTH_SHORT).show()
            }
            else -> {
                 // Loading
//                repeat(2) {
//                    categoriesModels.add(
//                        ShopCategory(null, ::onProductAddToCartClick).apply {
//                            id(it)
//                        }
//                    )
//                }
//
//                carousel {
//                    id("popular_categories_carousel")
//                    numViewsToShowOnScreen(1.5F)
//                    models(categoriesModels)
//                }
            }
        }




    }

    private fun loadOnSaleProducts() {
        val onSaleProductsModels = mutableListOf<ShopProduct>()

        ShopSection("On Sale")
            .id("shop_section_3")
            .addTo(this)


        when (onSaleProductsResource) {
            is Resource.Success -> {
                val response =
                    (onSaleProductsResource as Resource.Success<List<OnSaleProduct>?>).result
                response?.forEachIndexed { index, onSaleProduct ->
                    onSaleProductsModels.add(
                        ShopProduct(onSaleProduct.product, ::onProductAddToCartClick).apply {
                            id(index)
                        }
                    )
                }

                carousel {
                    id("on_sale_carousel")
                    numViewsToShowOnScreen(2F)
                    models(onSaleProductsModels)
                }

            }
            is Resource.Failure -> {
                Toast.makeText(context, "Error OnSales", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Loading
                repeat(2) {
                    onSaleProductsModels.add(
                        ShopProduct(null, ::onProductAddToCartClick).apply {
                            id(it)
                        }
                    )
                }

                carousel {
                    id("on_sale_carousel")
                    numViewsToShowOnScreen(2F)
                    models(onSaleProductsModels)
                }
            }
        }
    }

    private fun loadMostRatedProducts() {
        val productsModels = mutableListOf<ShopProduct>()
        ShopSection("Most Rated")
            .id("shop_section_4")
            .addTo(this)
        when (mostRatedProductsResource) {
            is Resource.Success -> {

                val mostRatedProducts =
                    (mostRatedProductsResource as Resource.Success<List<Product>?>).result
                mostRatedProducts?.forEachIndexed { index, product ->
                    productsModels.add(
                        ShopProduct(product, ::onProductAddToCartClick).apply {
                            id(index)
                        }
                    )
                }

                carousel {
                    id("most_rated_carousel")
                    numViewsToShowOnScreen(2F)
                    models(productsModels)
                }
            }
            is Resource.Failure -> {

            }

            else -> {
                // Loading
                repeat(2) {
                    productsModels.add(
                        ShopProduct(null, ::onProductAddToCartClick).apply {
                            id(it)
                        }
                    )
                }
                carousel {
                    id("most_rated_carousel")
                    numViewsToShowOnScreen(2F)
                    models(productsModels)
                }
            }

        }


    }

    fun setExclusiveProducts(resource: Resource<List<Product>?>?) {
        this.exclusiveProductsResource = resource
        requestModelBuild()
    }

    fun setOnSaleProducts(resource: Resource<List<OnSaleProduct>?>?) {
        this.onSaleProductsResource = resource
        requestModelBuild()
    }

    fun setMostRatedProducts(resource: Resource<List<Product>?>?) {
        this.mostRatedProductsResource = resource
        requestModelBuild()
    }

    fun setCategories(resource: Resource<List<Category>?>?) {
        this.categoriesResource = resource
        requestModelBuild()
    }



    private fun onCategoryClick(name: String) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
    }

    private fun onProductAddToCartClick(name: String) {
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
    }

}

class ShopHeader : ViewBindingKotlinModel<ShopHeaderItemBinding>(R.layout.shop_header_item) {
    override fun ShopHeaderItemBinding.bind() {
    }
}

data class ShopSection(
    val sectionTitle: String
) : ViewBindingKotlinModel<ShopSectionTitleItemBinding>(R.layout.shop_section_title_item) {
    override fun ShopSectionTitleItemBinding.bind() {
        shopSectionTitleTv.text = sectionTitle
    }
}

data class ShopProduct(
    val product: Product?,
    val onProductAddToCartClick: (name: String) -> Unit
) : ViewBindingKotlinModel<ShopProductItemBinding>(R.layout.shop_product_item) {
    @SuppressLint("SetTextI18n")
    override fun ShopProductItemBinding.bind() {

        if (product != null) {
            shopProductProgress.visibility = View.INVISIBLE
            shopProductItemViewsGroup.visibility = View.VISIBLE
            shopProductNameTv.text = product.name
            shopProductKgPcsTv.text = product.priceUnit
            shopProductPriceTv.text = "$${product.price}"
            Glide.with(shopProductImageImv).load(product.image).into(shopProductImageImv)
            shopProductAddBtn.setOnClickListener {
                onProductAddToCartClick("${product.name}\nAdded to cart")
            }
        } else {
            shopProductProgress.visibility = View.VISIBLE
            shopProductItemViewsGroup.visibility = View.INVISIBLE
        }


    }
}

data class ShopCategory(
    val category: Category?,
    val onCategoryClick: (name: String) -> Unit
) : ViewBindingKotlinModel<PopularCategoryItemBinding>(R.layout.popular_category_item) {
    override fun PopularCategoryItemBinding.bind() {
        if (category != null) {
            popularCategoryNameTv.text = category.name
            val color = category.color.substring(0,1) + "1A" + category.color.substring(1)
            itemBg.setBackgroundColor(Color.parseColor(color))

            Glide.with(popularCategoryImageImv).load(category.image).into(popularCategoryImageImv)
            root.setOnClickListener {
                onCategoryClick(category.name)
            }
        }
    }
}

