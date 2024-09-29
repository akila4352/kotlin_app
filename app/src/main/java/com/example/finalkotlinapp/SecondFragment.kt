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

class SecondFragment : Fragment() {

    private lateinit var deliveryRecyclerView: RecyclerView
    private lateinit var deliveryAdapter: DeliveryAdapter
    private lateinit var deliveryList: MutableList<Delivery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        deliveryRecyclerView = view.findViewById(R.id.deliveryRecyclerView)
        deliveryRecyclerView.layoutManager = LinearLayoutManager(context)
        deliveryList = mutableListOf()
        deliveryAdapter = DeliveryAdapter(deliveryList)
        deliveryRecyclerView.adapter = deliveryAdapter

        fetchAcceptedOrders()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    private fun fetchAcceptedOrders() {
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val acceptedOrdersRef = database.child("accepted_orders")

        acceptedOrdersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                deliveryList.clear()
                for (orderSnapshot in dataSnapshot.children) {
                    val delivery = orderSnapshot.getValue(Delivery::class.java)
                    if (delivery != null) {
                        deliveryList.add(delivery)
                    }
                }
                deliveryAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Database error: ${databaseError.message}")
            }
        })
    }

    companion object {
        private const val TAG = "SecondFragment"
    }
}
