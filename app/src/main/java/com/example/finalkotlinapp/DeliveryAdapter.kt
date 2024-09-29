package com.example.finalkotlinapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DeliveryAdapter(private val deliveryList: List<Delivery>) :
    RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    class DeliveryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customerName: TextView = view.findViewById(R.id.CustomerName)
        val paymentStatus: TextView = view.findViewById(R.id.moneyStatus)
        val deliveryText: TextView = view.findViewById(R.id.deliveryTextView)
        val orderStatusCard: CardView = view.findViewById(R.id.orderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.delivery_item, parent, false)
        return DeliveryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val delivery = deliveryList[position]
        holder.customerName.text = delivery.customerName
        holder.paymentStatus.text = delivery.paymentStatus
        holder.deliveryText.text = delivery.deliveryText

        // Set card background color based on order status
        holder.orderStatusCard.setCardBackgroundColor(
            if (delivery.orderStatus) Color.GREEN
            else Color.RED
        )
    }

    override fun getItemCount(): Int {
        return deliveryList.size
    }
}
