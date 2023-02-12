package com.ars.groceriesapp.ui.auth.phone_location.phone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ars.groceriesapp.databinding.FragmentPhoneVerificationBinding
import com.ars.groceriesapp.ui.auth.phone_location.PhoneLocationViewModel


class PhoneVerificationFragment : Fragment() {

    private val binding by lazy { FragmentPhoneVerificationBinding.inflate(layoutInflater) }
    private val viewModel: PhoneLocationViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.textView7.setOnClickListener {
            val smsCode = binding.smsEdt.text.toString()
            val verificationId = viewModel.verificationId

            viewModel.linkPhoneWithCustomerAccount(verificationId,smsCode,::onSuccess,::onFailure)
        }
    }

    private fun onSuccess(phone: String) {
        // Phone verified successfully
        viewModel.customer.phone = phone
        Navigation
            .findNavController(requireView())
            .navigate(PhoneVerificationFragmentDirections.toLocationFrag())
    }

    private fun onFailure(e: Exception) {
        // Phone verification failed!
        Toast.makeText(requireContext(), "Error : ${e.message}", Toast.LENGTH_SHORT).show()

    }


}