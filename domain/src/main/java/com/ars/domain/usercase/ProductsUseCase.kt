package com.ars.domain.usercase

import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails
import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend fun getAllProducts(): Resource<List<ProductDetails>?> {
        return productRepository.retrieveAll()
    }

    suspend fun getExclusiveProducts(): Resource<List<Product>?> {
        return productRepository.retrieveExclusive()
    }

    suspend fun getMostRatedProducts(): Resource<List<Product>?> {
        return productRepository.retrieveMostRated()
    }
}