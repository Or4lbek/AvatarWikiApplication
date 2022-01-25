package com.example.avatarwikiapplication.view.registration

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {


    lateinit var binding: FragmentSignUpBinding
    lateinit var customerMail: String
    lateinit var customerPassword: String

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        auth = Firebase.auth
        //required for edit text password
        addConditionEditTextPassword()
        binding.buttonGoLog.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        binding.buttonGoReg.setOnClickListener {
            customerMail = binding.editTextTextEmailAddress.text.toString()
            customerPassword = binding.editTextTextPassword.text.toString()
            checkEmptiness(customerMail, customerPassword)
        }
    }


    private fun createUserWithEmailAndPassword() {
        auth.createUserWithEmailAndPassword(customerMail, customerPassword)
            .addOnCompleteListener(object :
                OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        sendEmailVer()
                        if (auth.currentUser!!.isEmailVerified) {
                            Toast.makeText(
                                (activity as LoginActivity),
                                getString(R.string.user_sign_up_successful),
                                Toast.LENGTH_SHORT
                            ).show()
                            (activity as LoginActivity).makeIntentToMainActivity(customerMail)
                        }
                    } else {
                        Toast.makeText(
                            (activity as LoginActivity),
                            getString(R.string.user_sign_up_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun checkEmptiness(customerMail: String, customerPassword: String) {
        if ((!TextUtils.isEmpty(this.customerMail)) && (!TextUtils.isEmpty(this.customerPassword))) {
            createUserWithEmailAndPassword()
        } else if (TextUtils.isEmpty(this.customerMail)) {
            Toast.makeText((activity as LoginActivity), "please enter email", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText((activity as LoginActivity), "please enter password", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addConditionEditTextPassword() {
        binding.editTextTextPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 8) {
                binding.textInputLayoutForPassword.error = getString(R.string.need_more_8)
            } else {
                binding.textInputLayoutForPassword.error = null
            }
        }
    }

    private fun sendEmailVer() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText((activity as LoginActivity), R.string.verif, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        (activity as LoginActivity),
                        R.string.noverif,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }


    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}