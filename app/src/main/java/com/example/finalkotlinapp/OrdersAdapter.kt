package com.example.finalkotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OrdersAdapter(private val ordersList: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView = view.findViewById(R.id.CustomerName)
        val totalPay: TextView = view.findViewById(R.id.textView25)
        val foodQuantity: TextView = view.findViewById(R.id.foodQuantity)
        val foodImage: ImageView = view.findViewById(R.id.foodImage)
        val acceptButton: AppCompatButton = view.findViewById(R.id.acceptButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pending_order_items, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = ordersList[position]
        holder.customerName.text = order.customerName
        holder.totalPay.text = order.totalPay
        holder.foodQuantity.text = order.Quantity.toString()

        // Load the image using Glide
        Glide.with(holder.foodImage.context).load(order.imageUrl).into(holder.foodImage)

        holder.acceptButton.setOnClickListener {
            // Check if the order is already accepted to avoid redundant updates
            if (holder.acceptButton.text == "Accepted") {
                Toast.makeText(holder.itemView.context, "Order already accepted!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Change the button text to "Accepted"
            holder.acceptButton.text = "Accepted"
            holder.acceptButton.isEnabled = false // Disable button to prevent further clicks

            // Upload the order status to Firebase
            val orderRef = database.child("accepted_orders").child(order.id) // Adjust the path as needed
            orderRef.child("status").setValue("Accepted")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(holder.itemView.context, "Order Accepted!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Re-enable the button if update fails
                        holder.acceptButton.isEnabled = true
                        Toast.makeText(holder.itemView.context, "Failed to accept order. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
}
