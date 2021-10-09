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
    private lateinit var mail:String
    lateinit var toggle:ActionBarDrawerToggle
    private var USER_KEY:String = "User"
    private lateinit var mDatabase: DatabaseReference
    lateinit var tvUserName:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_AvatarWikiApplication)
        setContentView(binding.root)
        init()
        // add toggle to Drawer layout
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,R.string.close, R.string.open )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        var textHeader: TextView? = binding.navView.getHeaderView(R.id.textViewUserName) as TextView?
//        textHeader!!.text = "Oralbek"
//        tvUserName = findViewById(R.id.textViewUserNameHeader)
//        tvUserName.setText("Oralbek")

//        val appBarConfiguration = AppBarConfiguration(setOf<>())
        val actionBar = supportActionBar
        actionBar!!.title = "News"
        binding.btnNavView.selectedItemId = R.id.item1

        // get intent to define mail address of user
        var intent:Intent = getIntent()
        mail = intent.getStringExtra("mail").toString()
        changeHeaderContent()

        // define main fragment in home page
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, News.newInstance()).commit()

        // click listener to bottom navigation view
        binding.btnNavView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.item1->{
                    // change the action bar title
                    actionBar!!.title = "News"
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.place_holder, News.newInstance()).commit()
                }
                R.id.item2->{
                    // change the action bar title
                    actionBar!!.title = "Map"
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.place_holder, Map.newInstance()).commit()

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

        // drawer layout click
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener(this)




    }

    private fun changeHeaderContent() {
        val navigationView : NavigationView  = findViewById(R.id.navView)
        val headerView : View = navigationView.getHeaderView(0)
        val navUsername : TextView = headerView.findViewById(R.id.textViewUserNameHeader)
        navUsername.text = mail
    }

    fun init(){
        // initialize database in firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)
    }

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


    fun onClickLogOut() {
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

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_movies, menu);
        return true;
    }


    fun onClickWriteNew(mi: MenuItem) {
        // handle click here
        Toast.makeText(this, "do want to write some new?",Toast.LENGTH_SHORT).show()
        openDialog()
    }

    fun openDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog,null)
        //alert dialog builder

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(getString(R.string.new_record))
            .setNegativeButton("cancel"){dialog,i->
                Toast.makeText(this,"closed",Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("send"){dialog,i->
                val alertDialog = dialog as AlertDialog
                val editTextNews: EditText? = alertDialog.findViewById<EditText>(R.id.editTextNews)
                val news:String = editTextNews?.text.toString()
                Toast.makeText(this, "send clicked $news", Toast.LENGTH_SHORT).show()

                //to save data
                var id:String = mDatabase.key.toString()
                var userMail:String = mail
                var newRecord:String = news
                var newUser:User = User(id,userMail,newRecord)

                mDatabase.push().setValue(newUser)
            }

        val mAlertDialog = mBuilder.show()

    }




}