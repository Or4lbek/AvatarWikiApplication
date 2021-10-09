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
    lateinit var edMail:String
    lateinit var edPasword:String
    private var time:Int = 0
    private var probablyMail:String = "   "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AvatarWikiApplication)
//        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        lateinit var intent: Intent
        var oldIntent: Intent = getIntent()
        time = oldIntent.getIntExtra("time",0)
        if (!oldIntent.getStringExtra("mail").toString().isNullOrEmpty()){
            Toast.makeText(this,oldIntent.getStringExtra("mail").toString(),Toast.LENGTH_SHORT).show()
            probablyMail = oldIntent.getStringExtra("mail").toString()
            binding.editTextTextEmailAddress.text = Editable.Factory.getInstance().newEditable(probablyMail)
            if (binding.editTextTextEmailAddress.text.toString() == "null"){
                binding.editTextTextEmailAddress.text = Editable.Factory.getInstance().newEditable("")
            }
        }
        auth = Firebase.auth

        binding.editTextTextPassword.doOnTextChanged{text, start, before, count ->
            if (text!!.length < 8){
                binding.textInputLayoutForPassword.error = getString(R.string.need_more_8)
            }
            else{
                binding.textInputLayoutForPassword.error = null
            }

        }


    }

    protected override fun onStart() {
        super.onStart()

        var currentUser = auth.getCurrentUser()

//        updateUI(currentUser);
        if (currentUser != null){
            if (time == 1){
                auth.signOut()
            }
            else{
                Toast.makeText(this,"user not null", Toast.LENGTH_LONG).show()
                intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("mail", currentUser.email)
                startActivity(intent)
            }

        }
        else{
            Toast.makeText(this,"user null", Toast.LENGTH_LONG).show()

        }
    }

    fun onClickSignIn(view: View) {
        edMail = binding.editTextTextEmailAddress.text.toString()
        edPasword = binding.editTextTextPassword.text.toString()

        if ((!TextUtils.isEmpty(edMail)) && (!TextUtils.isEmpty(edPasword))){
            auth.signInWithEmailAndPassword(edMail,edPasword).addOnCompleteListener(object:
                OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful){
                        Toast.makeText(this@LoginActivity, getString(R.string.user_sign_in_s), Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("mail", edMail)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@LoginActivity, getString(R.string.user_sign_in_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        else if (TextUtils.isEmpty(edMail)) {
            Toast.makeText(this@LoginActivity, "please enter email", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@LoginActivity, "please enter password", Toast.LENGTH_SHORT).show()
        }

    }

    fun onClickButtonGoReg(view: android.view.View) {
        val newIntent = Intent(applicationContext, RegistrationActivity::class.java)
        startActivity(newIntent)
    }


}