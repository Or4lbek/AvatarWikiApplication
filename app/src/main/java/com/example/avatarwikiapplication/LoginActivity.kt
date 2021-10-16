package com.example.avatarwikiapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.avatarwikiapplication.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth


class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var edMail:String
    private lateinit var edPasword:String

    private var time:Int = 0
    private var probablyMail:String = "   "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AvatarWikiApplication)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        supportActionBar?.hide()

        lateinit var intent: Intent
        var oldIntent: Intent = getIntent()
        time = oldIntent.getIntExtra("time",0)

        //to get customer mail if we come from RegistrationActivity

        if (oldIntent.getStringExtra("mail").toString().isNotEmpty()){
            Toast.makeText(this,oldIntent.getStringExtra("mail").toString(),Toast.LENGTH_SHORT).show()
            probablyMail = oldIntent.getStringExtra("mail").toString()
            binding.editTextTextEmailAddress.text = Editable.Factory.getInstance().newEditable(probablyMail)
            if (binding.editTextTextEmailAddress.text.toString() == "null"){
                binding.editTextTextEmailAddress.text = Editable.Factory.getInstance().newEditable("")
            }
        }
        auth = Firebase.auth

        addConditionEditTextPassword()
    }

    protected override fun onStart() {
        super.onStart()
        checkIsSessionExists()
    }

    private fun checkIsSessionExists(){
        var currentUser = auth.currentUser
//        updateUI(currentUser);
        if (currentUser != null){
            if (time == 1){
                auth.signOut()
            }
            else{
                makeIntentToMainActivity(currentUser.email!!,)
            }
        }
    }

    fun onClickSignIn(view: View) {
        edMail = binding.editTextTextEmailAddress.text.toString()
        edPasword = binding.editTextTextPassword.text.toString()
        checkEmptiness(edMail, edPasword)
    }

    private fun checkEmptiness(customerMail:String, customerPassword:String){
        if ((!TextUtils.isEmpty(customerMail)) && (!TextUtils.isEmpty(customerPassword))){
            signInWithEmailAndPassword()
        }
        else if (TextUtils.isEmpty(customerMail)) {
            Toast.makeText(this@LoginActivity, "please enter email", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@LoginActivity, "please enter password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithEmailAndPassword(){
        auth.signInWithEmailAndPassword(edMail,edPasword).addOnCompleteListener(object:
            OnCompleteListener<AuthResult> {
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful){
                    Toast.makeText(this@LoginActivity, getString(R.string.user_sign_in_s), Toast.LENGTH_SHORT).show()
                    makeIntentToMainActivity(edMail)
                }
                else{
                    Toast.makeText(this@LoginActivity, getString(R.string.user_sign_in_failed), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun makeIntentToMainActivity(customerMail: String) {
        intent = Intent(applicationContext,MainActivity::class.java)
        intent.putExtra("mail", customerMail)
        startActivity(intent)
    }

    fun onClickButtonGoReg(view: android.view.View) {
        makeIntentToRegActivity()
    }

    private fun makeIntentToRegActivity() {
        intent = Intent(applicationContext,RegistrationActivity::class.java)
        startActivity(intent)
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

}