package com.example.avatarwikiapplication.view.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.ActivityLoginBinding
import com.example.avatarwikiapplication.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var edMail: String
    private lateinit var edPasword: String

    private var time: Int = 0
    private var probablyMail: String = "   "
    private lateinit var signupDateTipa: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AvatarWikiApplication)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        lateinit var intent: Intent
        var oldIntent: Intent = getIntent()
        time = oldIntent.getIntExtra("time", 0)
        auth = Firebase.auth
    }


    protected override fun onStart() {
        super.onStart()
        checkIsSessionExists()
    }

    private fun checkIsSessionExists() {
        val currentUser = auth.currentUser
        var signupDate = currentUser?.metadata?.creationTimestamp.toString()
        signupDateTipa = signupDate.toString()
//        updateUI(currentUser);
        if (currentUser != null) {
            if (time == 1) {
                auth.signOut()
            } else {
                makeIntentToMainActivity(currentUser.email!!)
            }
        }
    }

//


    fun makeIntentToMainActivity(customerMail: String) {
        intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("mail", customerMail)
        intent.putExtra("date", signupDateTipa)
        startActivity(intent)
        finish()

    }


}