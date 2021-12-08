package com.example.avatarwikiapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.ActivityRegistrationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    lateinit var customerMail:String
    lateinit var customerPassword:String

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_AvatarWikiApplication)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        supportActionBar?.hide()
        // to hide action bar

        auth = Firebase.auth
        //required for edit text password
        addConditionEditTextPassword()
    }
    private fun addConditionEditTextPassword(){
        binding.editTextTextPassword.doOnTextChanged{text, start, before, count ->
            if (text!!.length < 8){
                binding.textInputLayoutForPassword.error = getString(R.string.need_more_8)
            }
            else{
                binding.textInputLayoutForPassword.error = null
            }
        }
    }


    fun onClickSignUp(view: View) {
        customerMail = binding.editTextTextEmailAddress.text.toString()
        customerPassword = binding.editTextTextPassword.text.toString()
        checkEmptiness(customerMail,customerPassword)
    }

    private fun checkEmptiness(customerMail:String, customerPassword:String){
        if ((!TextUtils.isEmpty(this.customerMail)) && (!TextUtils.isEmpty(this.customerPassword))){
            createUserWithEmailAndPassword()
        }
        else if (TextUtils.isEmpty(this.customerMail)) {
            Toast.makeText(this@RegistrationActivity, "please enter email", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@RegistrationActivity, "please enter password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUserWithEmailAndPassword(){
        auth.createUserWithEmailAndPassword(customerMail,customerPassword).addOnCompleteListener(object:
            OnCompleteListener<AuthResult> {
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful){
                    sendEmailVer()
                    if (auth.currentUser!!.isEmailVerified){
                        Toast.makeText(this@RegistrationActivity, getString(R.string.user_sign_up_successful), Toast.LENGTH_SHORT).show()
                        makeIntentToMainActivity(customerMail)
                    }
                }
                else{
                    Toast.makeText(this@RegistrationActivity, getString(R.string.user_sign_up_failed), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun onClickButtonGoLogIntent(view: android.view.View) {
        customerMail = binding.editTextTextEmailAddress.text.toString()
        makeIntentToLogActivity(customerMail)
    }

    private fun makeIntentToLogActivity(customerMail: String) {
        intent = Intent(applicationContext, LoginActivity::class.java)
        intent.putExtra("mail", customerMail)
        startActivity(intent)
    }

    private fun makeIntentToMainActivity(customerMail: String) {
        intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("mail", customerMail)
        startActivity(intent)
    }

    private fun sendEmailVer() {
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, R.string.verif, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, R.string.noverif, Toast.LENGTH_SHORT).show()
            }
        })

    }




}