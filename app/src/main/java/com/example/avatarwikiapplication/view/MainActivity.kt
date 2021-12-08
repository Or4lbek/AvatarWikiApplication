package com.example.avatarwikiapplication.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.avatarwikiapplication.model.CustomerRecord
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    // bissmiliah
    lateinit var binding: ActivityMainBinding //using view binding
    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mail: String
    private lateinit var date: String
    lateinit var navController: NavController




    private val USER_KEY: String = "User"
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_AvatarWikiApplication)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newsFeedFragment2, R.id.mapFragment, R.id.quotesFragment
            ), binding.drawerLayout
        )
//            )
//        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.btnNavView.setupWithNavController(navController)

        binding.navView.setupWithNavController(navController)


        init()
        changeHeaderContent()
//        binding.navView.setNavigationItemSelectedListener(this)


    }

    private fun init() {
        // initialize database in firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)
        // add toggle to Drawer layout
//
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.newsFeedFragment2) {
                binding.drawerLayout.apply {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setDisplayShowHomeEnabled(true)
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            } else {

                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
                binding.drawerLayout.apply {
                    isEnabled = false
                    setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.close, R.string.open)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // get intent to define mail address of user
        val intent: Intent = intent
        mail = intent.getStringExtra("mail").toString()
        date = intent.getStringExtra("date").toString()
        initBtnNavView()

    }

    private fun initBtnNavView() {
        binding.fab.setOnClickListener {
            val navigation =  navController.navigate(R.id.writeDataFragment)
            binding.btnNavView.menu.getItem(2).isChecked = false

            hideBottomAppBar()
        }

        binding.btnNavView.background = null
        binding.btnNavView.menu.getItem(2).isEnabled = false

    }

    private fun hideBottomAppBar() {
        binding.run {
            btnAppBar.performHide()
            // Get a handle on the animator that hides the bottom app bar so we can wait to hide
            // the fab and bottom app bar until after it's exit animation finishes.
            btnAppBar.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false
                override fun onAnimationEnd(animation: Animator?) {
                    if (isCanceled) return

                    // Hide the BottomAppBar to avoid it showing above the keyboard
                    // when composing a new email.
                    btnAppBar.visibility = View.GONE
                    fab.visibility = View.INVISIBLE
                }
                override fun onAnimationCancel(animation: Animator?) {
                    isCanceled = true
                }
            })
        }
    }

    fun setBottomAppBarForHome() {
        binding.run {
            fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
            btnAppBar.visibility = View.VISIBLE
//            btnAppBar.replaceMenu(menuRes)
//            fab.contentDescription = getString(R.string.fab_compose_email_content_description)
//            bottomAppBarTitle.visibility = View.VISIBLE
            btnAppBar.performShow()
            fab.show()
            navController.navigate(R.id.newsFeedFragment2)
        }
    }

    private fun changeHeaderContent() {
        val navigationView: NavigationView = findViewById(R.id.navView)
        val headerView: View = navigationView.getHeaderView(0)
        val navUsername: TextView = headerView.findViewById(R.id.textViewUserNameHeader)
        navUsername.text = mail
    }

//    fun onClickWriteNew(mi: MenuItem) {
//        // handle click here
//        Toast.makeText(this, "do want to write some new?", Toast.LENGTH_SHORT).show()
//        buildDialogToMakeNewRecord()
//    }

//    private fun buildDialogToMakeNewRecord() {
//        val mDialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null)
//        //alert dialog builder
//
//        val mBuilder = AlertDialog.Builder(this)
//            .setView(mDialogView)
//            .setTitle(getString(R.string.new_record))
//            .setNegativeButton("cancel") { dialog, i ->
//            }
//            .setPositiveButton("send") { dialog, i ->
//                val alertDialog = dialog as AlertDialog
//                val editTextNewRecord: EditText? =
//                    alertDialog.findViewById<EditText>(R.id.editTextNewRecord)
//                //to save Customer data
//                saveCustomerRecord(
//                    mDatabase.key.toString(),
//                    mail,
//                    editTextNewRecord?.text.toString()
//                )
//            }
//        val mAlertDialog = mBuilder.show()
//    }

//    private fun saveCustomerRecord(
//        customerId: String,
//        customerMail: String,
//        customerRecord: String
//    ) {
//        var newCustomerRecord: CustomerRecord =
//            CustomerRecord(customerId, customerMail, customerRecord)
//        mDatabase.push().setValue(newCustomerRecord)
//    }

    //drawer layout function
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.log_out -> {
//                Toast.makeText(this, "log out", Toast.LENGTH_SHORT).show()
//                onClickLogOut()
//            }
//            R.id.aboutMeFragment -> {
////                Toast.makeText(this@MainActivity, "item3", Toast.LENGTH_SHORT).show()
////                NewsFeedFragmentDirections.actionNewsFeedFragmentTo
////                NewsFeedFragmentDirections.action
////                NewsFeedFragmentDirections.action
////                NewsFeedFragmentDirections.actionNewsFedFragmentToRecordDetailFragment()
//
//            }
//        }
//        return true
//    }

    fun getUserMail(): String {
        return mail
    }

    fun getUserDate(): String {
        return date
    }

    private fun onClickLogOut() {
        val newIntent: Intent = Intent(applicationContext, LoginActivity::class.java)
        newIntent.putExtra("time", 1)
        startActivity(newIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onBackPressed(){
//        navController.navigate(R.id.newsFeedFragment2)
//    }


}