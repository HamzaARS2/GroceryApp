package com.ars.domain.usercase.customer

import com.ars.domain.model.Customer
import com.ars.domain.repository.customer.ICustomerRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Validation
import com.ars.domain.utils.ValidationResponse
import javax.inject.Inject

class LoginCustomerUseCase @Inject constructor(
    private val customerRepository: ICustomerRepository
) {

    suspend operator fun invoke(email: String, password: String,onValidation: (response: Validation.LoginResponse) -> Unit): Resource<Customer> {
        val response = Validation.loginValidation(email, password)
        onValidation(response)
        if (!response.isValidEmail || !response.isValidPassword)
            return Resource.Loading
        return customerRepository.login(email, password)
    }
}