package com.ars.data.repository.product

import com.ars.data.remote.ProductDataSource
import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails
import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : IProductRepository {


    override suspend fun retrieve(id: Int): Resource<ProductDetails> {
        return try {
            val response = productDataSource.fetchProductDetailsById(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveAll(): Resource<List<ProductDetails>?> {
        return try {
            val response = productDataSource.fetchAllProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveExclusive(): Resource<List<Product>?> {
        return try {
            val response = productDataSource.fetchExclusiveProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveOnSaleProducts(): Resource<List<OnSaleProduct>?> {
        return try {
            val response = productDataSource.fetchOnSaleProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieveMostRated(): Resource<List<Product>?> {
        return try {
            val response = productDataSource.fetchMostRatedProducts()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun searchProducts(query: String): Resource<List<Product>> {
        return try {
            val response = productDataSource.fetchProductsContaining(query)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}