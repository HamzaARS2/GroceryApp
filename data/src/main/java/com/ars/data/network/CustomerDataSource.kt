package com.ars.data.network

import com.ars.data.network.api.CustomerApi
import com.ars.domain.model.Customer
import com.ars.domain.repository.ICRUDRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class CustomerDataSource @Inject constructor(
    private val customerApi: CustomerApi
): ICRUDRepository<Customer,String> {
    override suspend fun insert(data: Customer): Resource<Customer> {
        return try {
            val response = customerApi.insertCustomer(data)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun retrieve(id: String): Customer? {
       return customerApi.retrieveCustomer(id)
    }

    override suspend fun retrieveAll(): Resource<List<Customer>> {
        return try {
            val response = customerApi.retrieveAllCustomers()
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun update(data: Customer): Resource<Customer> {
        return try {
            val response = customerApi.updateCustomer(data)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun delete(id: String): Resource<String> {
        return try {
            val response = customerApi.deleteCustomer(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }




}