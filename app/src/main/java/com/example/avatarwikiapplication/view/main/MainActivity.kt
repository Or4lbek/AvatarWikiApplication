package com.example.avatarwikiapplication.view.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.avatarwikiapplication.R
import com.example.avatarwikiapplication.databinding.ActivityMainBinding
import com.example.avatarwikiapplication.view.Drawer
import com.example.avatarwikiapplication.view.registration.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity(), Drawer {
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
        setContentView(binding.root)
        setTheme(R.style.Theme_AvatarWikiApplication)
        initUI()
        init()
        changeHeaderContent()
    }

    private fun initUI() {
        setToolbar()
        initBtnNavView()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun init() {
        // initialize database in firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY)
        // add toggle to Drawer layout
//
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.close, R.string.open)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            if (destination.id == R.id.newsFeedFragment2) {
//                Log.i("voca", "newsFeed")
////                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
////                toggle.isDrawerIndicatorEnabled = false
//            } else {
//                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//                toggle.isDrawerIndicatorEnabled = false
//            }
//        }

        // get intent to define mail address of user
        val intent: Intent = intent
        mail = intent.getStringExtra("mail").toString()
        date = intent.getStringExtra("date").toString()
    }

    private fun initBtnNavView() {


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.newsFeedFragment2, R.id.mapFragment, R.id.quotesFragment
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.btnNavView.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
        binding.fab.setOnClickListener {
            binding.btnNavView.menu.getItem(2).isChecked = false
            navController.navigate(R.id.writeDataFragment)
            hideBottomAppBar()
        }
        binding.btnNavView.background = null
        binding.btnNavView.menu.getItem(2).isEnabled = false

    }

    fun hideBottomAppBar() {
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
//    fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.log_out -> {
//                Toast.makeText(this, "log out", Toast.LENGTH_SHORT).show()
//                onClickLogOut()
//            }
//
//        }
//        return true
//    }

    fun getUserMail(): String {
        return mail
    }

    fun getUserDate(): String {
        return date
    }

    fun onClickLogOut() {
        val newIntent: Intent = Intent(applicationContext, LoginActivity::class.java)
        newIntent.putExtra("time", 1)
        startActivity(newIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun lockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun unlockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}