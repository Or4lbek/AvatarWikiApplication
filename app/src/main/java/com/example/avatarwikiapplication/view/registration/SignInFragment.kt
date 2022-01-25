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
import com.example.avatarwikiapplication.databinding.FragmentSignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var binding: FragmentSignInBinding? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var edMail: String
    private lateinit var edPasword: String

    private var time: Int = 0
    private var probablyMail: String = "   "
    private lateinit var signupDateTipa: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {


        //to get customer mail if we come from RegistrationActivity


        auth = Firebase.auth

        addConditionEditTextPassword()

        binding?.buttonGoReg?.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding?.buttonSignIn?.setOnClickListener {
            edMail = binding?.editTextTextEmailAddress?.text.toString()
            edPasword = binding?.editTextTextPassword?.text.toString()
            checkEmptiness(edMail, edPasword)
        }
    }


    private fun checkEmptiness(customerMail: String, customerPassword: String) {
        if ((!TextUtils.isEmpty(customerMail)) && (!TextUtils.isEmpty(customerPassword))) {
            signInWithEmailAndPassword()
        } else if (TextUtils.isEmpty(customerMail)) {
            Toast.makeText((activity as LoginActivity), "please enter email", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText((activity as LoginActivity), "please enter password", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun signInWithEmailAndPassword() {
        auth.signInWithEmailAndPassword(edMail, edPasword).addOnCompleteListener(object :
            OnCompleteListener<AuthResult> {
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful) {
                    Toast.makeText(
                        (activity as LoginActivity),
                        getString(R.string.user_sign_in_s),
                        Toast.LENGTH_SHORT
                    ).show()
                    (activity as LoginActivity).makeIntentToMainActivity(edMail)
                } else {
                    Toast.makeText(
                        (activity as LoginActivity),
                        getString(R.string.user_sign_in_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    private fun addConditionEditTextPassword() {
        binding?.editTextTextPassword?.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 8) {
                binding?.textInputLayoutForPassword?.error = getString(R.string.need_more_8)
            } else {
                binding?.textInputLayoutForPassword?.error = null
            }
        }
    }

    fun onClickSignIn(view: View) {
//        edMail = binding.editTextTextEmailAddress.text.toString()
//        edPasword = binding.editTextTextPassword.text.toString()
//        checkEmptiness(edMail, edPasword)
    }


    companion object {

        @JvmStatic
        fun newInstance() = SignInFragment()

    }
}