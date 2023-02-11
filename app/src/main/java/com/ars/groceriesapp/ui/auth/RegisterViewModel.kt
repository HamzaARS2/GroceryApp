package com.ars.groceriesapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.Customer
import com.ars.domain.usercase.customer.RegisterCustomerUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerCustomerUseCase: RegisterCustomerUseCase
): ViewModel() {

    private val _customerRegisterFlow: MutableStateFlow<Resource<Customer>?> =
        MutableStateFlow(null)
    val customerRegisterFlow: StateFlow<Resource<Customer>?> get() = _customerRegisterFlow

    fun register(username: String, email: String, password: String)  = viewModelScope.launch {
        _customerRegisterFlow.value = Resource.Loading
        val registeredCustomer = registerCustomerUseCase(username, email, password)
        _customerRegisterFlow.emit(registeredCustomer)
    }
}