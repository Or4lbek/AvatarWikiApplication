package com.example.avatarwikiapplication.view.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentWriteDataBinding
import com.example.avatarwikiapplication.model.CustomerRecord
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class WriteDataFragment : Fragment(R.layout.fragment_write_data) {

    private var _binding: FragmentWriteDataBinding? = null
    private val binding get() = _binding!!

    // firebase property
    private lateinit var mDatabase: DatabaseReference
    private val USER_KEY: String = "User"
    private lateinit var mail: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWriteDataBinding.bind(view)
        init()

    }

    private fun init() {

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.editTextNewRecord.isFocusableInTouchMode = true
        binding.editTextNewRecord.requestFocus()
        (activity as MainActivity).window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        ((activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            binding.editTextNewRecord,
            0
        )

        binding.buttonClose.setOnClickListener {
            binding.editTextNewRecord.clearFocus()
//            (activity as MainActivity).window
//                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//            ((activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
//                binding.editTextNewRecord,
//                0
//            )
            (activity as MainActivity).setBottomAppBarForHome()
        }

        binding.buttonSendData.setOnClickListener {
            if (binding.editTextNewRecord.text.toString().isNotEmpty()) {
                saveCustomerRecord(
                    mDatabase.key.toString(),
                    mail,
                    binding.editTextNewRecord.text.toString()
                )
                binding.editTextNewRecord.clearFocus()
                (activity as MainActivity).setBottomAppBarForHome()
            }
        }

        mail = (activity as MainActivity).getUserMail()

        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)
        addConditionEditTextData()
    }


    companion object {
        @JvmStatic
        fun newInstance() = WriteDataFragment()

    }


    // to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveCustomerRecord(
        customerId: String,
        customerMail: String,
        customerRecord: String
    ) {
        var newCustomerRecord: CustomerRecord =
            CustomerRecord(customerId, customerMail, customerRecord)
        mDatabase.push().setValue(newCustomerRecord)
    }

    private fun addConditionEditTextData() {
        binding.editTextNewRecord.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                binding.textInputLayoutForTextField.error = getString(R.string.need_more_8)
            } else {
                binding.textInputLayoutForTextField.error = null
            }
        }
    }
}