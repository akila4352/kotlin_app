package com.example.finalkotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    private lateinit var orderCard: CardView
    private lateinit var addProductCard: CardView
    private lateinit var salesRepLocationCard: CardView
    private lateinit var employeesAddCard: CardView
    private var welcomeText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        orderCard = findViewById(R.id.order_card)
        addProductCard = findViewById(R.id.add_product_card)
        salesRepLocationCard = findViewById(R.id.sales_rep_location_card)
        employeesAddCard = findViewById(R.id.employees_add_card)
       // Assuming there's a TextView with this id

        // Set click listeners
        orderCard.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        addProductCard.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        salesRepLocationCard.setOnClickListener {
            val intent = Intent(this, SalesRepLocationActivity::class.java)
            startActivity(intent)
        }

        employeesAddCard.setOnClickListener {
            val intent = Intent(this, EmployeesAddActivity::class.java)
            startActivity(intent)
        }
    }
}
