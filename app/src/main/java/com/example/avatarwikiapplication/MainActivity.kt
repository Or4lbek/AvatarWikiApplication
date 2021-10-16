package com.example.avatarwikiapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.example.avatarwikiapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    // bissmiliah
    lateinit var binding:ActivityMainBinding //using view binding
    lateinit var toggle:ActionBarDrawerToggle

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mail:String

    private val USER_KEY:String = "User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_AvatarWikiApplication)
        setContentView(binding.root)
        init()
        changeHeaderContent()
        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun init(){
        // initialize database in firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)
        // add toggle to Drawer layout
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,R.string.close, R.string.open )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // get intent to define mail address of user
        val intent:Intent = intent
        mail = intent.getStringExtra("mail").toString()

        initBtnNavView()
    }

    private fun initBtnNavView() {
        val actionBar = supportActionBar
        actionBar!!.title = "News"
        // define main fragment in home page
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, NewsFeedFragment.newInstance()).commit()

        binding.btnNavView.background = null
        binding.btnNavView.menu.getItem(2).isEnabled = false
        // click listener to bottom navigation view
        binding.btnNavView.setOnItemSelectedListener{it->
            when(it.itemId){
                R.id.item1->{
                    // change the action bar title
                    actionBar!!.title = "News"
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.place_holder, NewsFeedFragment.newInstance()).commit()
                }
                R.id.item2->{
                    // change the action bar title
                    actionBar!!.title = "Map"
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.place_holder, MapFragment.newInstance()).commit()

                }
                R.id.item3->{

                }
                R.id.item4->{

                }
                R.id.item5->{

                }
            }
            true
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun changeHeaderContent() {
        val navigationView : NavigationView  = findViewById(R.id.navView)
        val headerView : View = navigationView.getHeaderView(0)
        val navUsername : TextView = headerView.findViewById(R.id.textViewUserNameHeader)
        navUsername.text = mail
    }

    // add pencil btn to action bar
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_pencil, menu);
        return true;
    }
    fun onClickWriteNew(mi: MenuItem) {
        // handle click here
        Toast.makeText(this, "do want to write some new?",Toast.LENGTH_SHORT).show()
        buildDialogToMakeNewRecord()
    }

    private fun buildDialogToMakeNewRecord() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog,null)
        //alert dialog builder

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(getString(R.string.new_record))
            .setNegativeButton("cancel"){dialog,i->
            }
            .setPositiveButton("send"){dialog,i->
                val alertDialog = dialog as AlertDialog
                val editTextNewRecord: EditText? = alertDialog.findViewById<EditText>(R.id.editTextNewRecord)
                //to save Customer data
                saveCustomerRecord(mDatabase.key.toString(),mail,editTextNewRecord?.text.toString())
            }
        val mAlertDialog = mBuilder.show()
    }

    private fun saveCustomerRecord(customerId: String, customerMail: String, customerRecord: String) {
        var newCustomerRecord:CustomerRecord = CustomerRecord(customerId,customerMail,customerRecord)
        mDatabase.push().setValue(newCustomerRecord)
    }

    //drawer layout function
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item4 ->
            {
                Toast.makeText(this,"log out", Toast.LENGTH_SHORT).show()
                onClickLogOut()
            }
            R.id.item3 ->
            {
                Toast.makeText(this@MainActivity,"item3", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
    private fun onClickLogOut() {
        val newIntent:Intent = Intent(applicationContext, LoginActivity::class.java)
        newIntent.putExtra("time",1)
        startActivity(newIntent)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}