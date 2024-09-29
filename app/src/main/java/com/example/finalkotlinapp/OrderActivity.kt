package com.example.finalkotlinapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class OrderActivity : AppCompatActivity() {

    // Track the current displayed fragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Initialize buttons
        val buttonAdd = findViewById<Button>(R.id.button_add)
        val buttonReplace = findViewById<Button>(R.id.button_replace)
        val buttonRemove = findViewById<Button>(R.id.button_remove)

        // Show the dashboard (chart fragment) by default
        showDashboard()

        buttonAdd.setOnClickListener {
            showFragment(FirstFragment())
        }

        buttonReplace.setOnClickListener {
            showFragment(SecondFragment())
        }

        buttonRemove.setOnClickListener {
            showDashboard()
        }
    }

    // Function to show the chart fragment as the default dashboard
    private fun showDashboard() {
        showFragment(ChartFragment())
    }

    // Function to replace the current fragment with a new one
    private fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (currentFragment != null) {
            fragmentTransaction.replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, FRAGMENT_TAG)
        }
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        currentFragment = fragment
    }

    companion object {
        const val FRAGMENT_TAG = "SIMPLE_FRAGMENT"
    }
}
