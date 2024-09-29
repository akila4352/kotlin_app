package com.example.finalkotlinapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddProductActivity : AppCompatActivity() {

    private var foodImageUri: Uri? = null
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)


        val backButton = findViewById<ImageButton>(R.id.backButton)
        val addItemButton = findViewById<AppCompatButton>(R.id.addItemButton)
        val foodName = findViewById<EditText>(R.id.product)
        val foodPrice = findViewById<EditText>(R.id.productPrice)
        val foodDescription = findViewById<EditText>(R.id.description)
        val foodIngredients = findViewById<EditText>(R.id.ingredients)
        val selectImageButton = findViewById<TextView>(R.id.selectImage)
        val selectedImage = findViewById<ImageView>(R.id.selectedImage)
        progressBar = findViewById(R.id.progressBar)

        // Set up click listener for the ImageView
        selectedImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        // Set up button click listeners
        addItemButton.setOnClickListener {
            // Get data from fields
            val name = foodName.text.toString().trim()
            val price = foodPrice.text.toString().trim()
            val description = foodDescription.text.toString().trim()
            val ingredients = foodIngredients.text.toString().trim()

            if (name.isNotBlank() && price.isNotBlank() && description.isNotBlank() && ingredients.isNotBlank()) {
                progressBar.visibility = View.VISIBLE
                uploadImageAndSaveProduct(name, price, description, ingredients)
            } else {
                Toast.makeText(this, "Please fill in all the details", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    // Image picker
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val selectedImage = findViewById<ImageView>(R.id.selectedImage)
            selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }

    // Upload image and save product to Firebase
    private fun uploadImageAndSaveProduct(name: String, price: String, description: String, ingredients: String) {
        if (foodImageUri != null) {
            val storageReference = FirebaseStorage.getInstance().reference.child("product_images/${System.currentTimeMillis()}.jpg")

            // Show progress bar
            progressBar.visibility = View.VISIBLE

            storageReference.putFile(foodImageUri!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        saveProductToDatabase(name, price, description, ingredients, uri.toString())
                    }.addOnFailureListener {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            saveProductToDatabase(name, price, description, ingredients, "")
        }
    }

    // Save product details to Firebase Realtime Database
    private fun saveProductToDatabase(name: String, price: String, description: String, ingredients: String, imageUrl: String) {
        val productMap = hashMapOf(
            "name" to name,
            "price" to price,
            "description" to description,
            "ingredients" to ingredients,
            "imageUrl" to imageUrl
        )

        val database = FirebaseDatabase.getInstance().reference.child("products")
        database.push().setValue(productMap)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to Add Product: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
