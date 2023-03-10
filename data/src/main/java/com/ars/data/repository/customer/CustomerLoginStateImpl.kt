package com.ars.data.repository.customer

import com.ars.domain.repository.customer.ICustomerLoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class CustomerLoginStateImpl @Inject constructor(
    private val mAuth: FirebaseAuth
): ICustomerLoginState {
    override val currentUser: FirebaseUser?
        get() = mAuth.currentUser

    override fun isLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }
}