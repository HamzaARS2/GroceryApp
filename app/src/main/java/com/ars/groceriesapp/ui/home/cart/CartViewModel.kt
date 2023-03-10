package com.ars.groceriesapp.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ars.domain.model.CartItem
import com.ars.domain.usercase.cart.DeleteCartItemUseCase
import com.ars.domain.usercase.cart.GetCartItemsUseCase
import com.ars.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase
): ViewModel() {

    private val _cartItemsFlow: MutableStateFlow<Resource<List<CartItem>>?> = MutableStateFlow(null)
    val cartItems: StateFlow<Resource<List<CartItem>>?> get() = _cartItemsFlow

    fun getCustomerCartItems(id: String) = viewModelScope.launch {
        _cartItemsFlow.value = Resource.Loading
        val response = getCartItemsUseCase(id)
        _cartItemsFlow.emit(response)
    }

    fun removeItemFromCart(id: Int, onSuccessDelete:() -> Unit, onDeleteFailed: (e: Exception) -> Unit) = viewModelScope.launch {
        deleteCartItemUseCase(id, onSuccessDelete, onDeleteFailed)
    }

}