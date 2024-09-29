package com.example.finalkotlinapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ChartFragment : Fragment() {

    private lateinit var barChart: BarChart
    private lateinit var profitValue: TextView
    private lateinit var userContainer: LinearLayout
    private lateinit var productContainer: GridLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        barChart = view.findViewById(R.id.idBarChart)
        profitValue = view.findViewById(R.id.profit_value)
        userContainer = view.findViewById(R.id.user_container)
        productContainer = view.findViewById(R.id.product_container)

        setupBarChart()
        setProfitValue("$12,345")


        for (i in 1..10) {
            addUser("User $i", R.drawable.sample_user)
        }


        for (i in 1..10) {
            addProduct("Product $i", R.drawable.product, "$${i * 10}")
        }

        return view
    }

    private fun setupBarChart() {
        val barEntriesList = arrayListOf(
            BarEntry(1f, 1f),
            BarEntry(2f, 2f),
            BarEntry(3f, 3f),
            BarEntry(4f, 4f),
            BarEntry(5f, 5f)
        )

        val barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.color = resources.getColor(R.color.purple_200)
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.description.isEnabled = false
    }

    private fun setProfitValue(profit: String) {
        profitValue.text = profit
    }

    private fun addUser(name: String, imageResId: Int) {
        val userView = layoutInflater.inflate(R.layout.user_item, userContainer, false)
        val userImage = userView.findViewById<ImageView>(R.id.user_image)
        val userName = userView.findViewById<TextView>(R.id.user_name)
        val deleteUserIcon = userView.findViewById<ImageView>(R.id.delete_user_icon)

        userImage.setImageResource(imageResId)
        userName.text = name

        deleteUserIcon.setOnClickListener {
            userContainer.removeView(userView) // Remove the user view from the container
        }

        userContainer.addView(userView)
    }

    private fun addProduct(name: String, imageResId: Int, price: String) {
        val productView = layoutInflater.inflate(R.layout.product_item, productContainer, false)
        val productImage = productView.findViewById<ImageView>(R.id.product_image)
        val productName = productView.findViewById<TextView>(R.id.product_name)
        val productPrice = productView.findViewById<TextView>(R.id.product_price)
        val deleteProductIcon = productView.findViewById<ImageView>(R.id.delete_product_icon)

        productImage.setImageResource(imageResId)
        productName.text = name
        productPrice.text = price

        deleteProductIcon.setOnClickListener {
            productContainer.removeView(productView)
        }

        productContainer.addView(productView)
    }
}
