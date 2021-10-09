package com.example.avatarwikiapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.example.avatarwikiapplication.databinding.ActivityRegistrationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.CompoundButton
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    lateinit var edMail:String
    lateinit var edPasword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_AvatarWikiApplication)

        setContentView(binding.root)
        supportActionBar?.hide()
        // to hide action bar


        auth = Firebase.auth

        //required for edit text password

        binding.editTextTextPassword.doOnTextChanged{text, start, before, count ->
            if (text!!.length < 8){
                binding.textInputLayoutForPassword.error = "нужно больше 8"
            }
            else{
                binding.textInputLayoutForPassword.error = null
            }

        }

//        if(binding.switchShowP.isChecked){
//            binding.editTextTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
//        }
//        else{
//            binding.editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//        }

//        binding.switch1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                // The toggle is enabled
//                binding.editTextTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
//            } else {
//                // The toggle is disabled
//                binding.editTextTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//            }
//        })

    }

    fun onClickSignUp(view: View) {
        edMail = binding.editTextTextEmailAddress.text.toString()
        edPasword = binding.editTextTextPassword.text.toString()
        if ((!TextUtils.isEmpty(edMail)) && (!TextUtils.isEmpty(edPasword))){
            auth.createUserWithEmailAndPassword(edMail,edPasword).addOnCompleteListener(object:
                OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful){
                        sendEmailVer()
                        if (auth.currentUser!!.isEmailVerified){
                            Toast.makeText(this@RegistrationActivity, getString(R.string.user_sign_up_successful), Toast.LENGTH_SHORT).show()
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            intent.putExtra("mail", edMail)
                            startActivity(intent)
                        }

                    }
                    else{
                        Toast.makeText(this@RegistrationActivity, getString(R.string.user_sign_up_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        else if (TextUtils.isEmpty(edMail)) {
            Toast.makeText(this@RegistrationActivity, "please enter email", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@RegistrationActivity, "please enter password", Toast.LENGTH_SHORT).show()
        }

//        auth.createUserWithEmailAndPassword()
    }

    fun onClickButtonGoLogIntent(view: android.view.View) {
        edMail = binding.editTextTextEmailAddress.text.toString()
        val newIntent = Intent(applicationContext, LoginActivity::class.java)
        newIntent.putExtra("mail", edMail)
        startActivity(newIntent)
    }

    private fun sendEmailVer() {
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
//                Log.d(TAG, "Email sent.")
                Toast.makeText(this, R.string.verif, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, R.string.noverif, Toast.LENGTH_SHORT).show()
            }
        })

    }


}