package com.cgpacalc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val dbVersion: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        // Main
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Database
        DbManager(applicationContext, this.dbVersion)

        // Buttons Functions
        this.buttonFunctions()

        // Setting default Subject and Laboratory credit
        this.defaultSubLabCredit()

        // Hide Keyboard when activity starts
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    // Adding three dot navigation menu to this activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Adding actions to menu item
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.actionEditSheet -> startActivity(Intent(this, EditSheetActivity::class.java))
            R.id.actionAbout -> startActivity(Intent(this, AboutActivity::class.java))
            R.id.actionExit -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure to exit?")
                builder.setCancelable(true)
                builder.setNegativeButton("NO") { dialogInterface, _ -> dialogInterface.cancel() }
                builder.setPositiveButton("EXIT") { _, _ ->
                    run {
                        Toast.makeText(applicationContext, "Exited", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                builder.create().show()
            }
            R.id.actionRefresh -> {
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                finish()
            }
            R.id.actionClear -> Toast.makeText(this@MainActivity, "Clear", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    // Buttons Functions
    private fun buttonFunctions() {
        val subjectListViewAdaptar = SubjectListViewAdaptar(this@MainActivity)
        this.subjectListView.adapter = subjectListViewAdaptar

        subAddButton.setOnClickListener {


            Toast.makeText(this@MainActivity, "Hdd", Toast.LENGTH_SHORT).show()
        }

    }

    private fun defaultSubLabCredit() {
        val creditData = DbManager(this@MainActivity, MainActivity().dbVersion)
            .readDataToList(false)
            .asReversed()
        subCreditEditText.setText(creditData[0]["Subject"].toString())
        labCreditEditText.setText(creditData[1]["Laboratory"].toString())
    }

}
