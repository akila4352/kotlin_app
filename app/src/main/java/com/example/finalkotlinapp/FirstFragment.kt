package com.example.finalkotlinapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class FirstFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        recyclerView = view.findViewById(R.id.pendingOrderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with the empty list initially
        ordersAdapter = OrdersAdapter(ordersList)
        recyclerView.adapter = ordersAdapter

        // Initialize Firebase Realtime Database reference
        database = FirebaseDatabase.getInstance().getReference("orders")

        // Fetch data from Firebase Realtime Database
        fetchOrders()

        return view
    }

    private fun fetchOrders() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    if (order != null) {
                        ordersList.add(order)
                    }
                }
                ordersAdapter.notifyDataSetChanged() // Notify adapter to refresh the list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FirstFragment", "Error getting data: ", error.toException())
            }
        })
    }
}