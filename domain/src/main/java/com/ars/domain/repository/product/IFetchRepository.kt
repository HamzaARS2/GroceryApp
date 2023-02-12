package com.ars.domain.repository.product

import com.ars.domain.utils.Resource

interface IFetchRepository<H,U> {
    suspend fun retrieve(id: U): Resource<H>
    suspend fun retrieveAll(): Resource<List<H>?>
}