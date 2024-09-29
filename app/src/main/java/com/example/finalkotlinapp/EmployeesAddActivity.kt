package com.example.finalkotlinapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class EmployeesAddActivity : AppCompatActivity() {

    private var foodImageUri: Uri? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_add)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI components
        nameEditText = findViewById(R.id.editTextTextName)
        emailEditText = findViewById(R.id.loginEmail)
        passwordEditText = findViewById(R.id.editTextTextPassword2)
        signUpButton = findViewById(R.id.signUpButton)
        val selectImageButton = findViewById<TextView>(R.id.selectImage)
        val selectedImage = findViewById<ImageView>(R.id.selectedImage)
        progressBar = findViewById(R.id.progressBar)

        // Set up click listener for the ImageView
        selectedImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                uploadImageAndSaveUser(name, email, password)
            }
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

    private fun uploadImageAndSaveUser(name: String, email: String, password: String) {
        if (foodImageUri != null) {
            val storageReference = FirebaseStorage.getInstance().reference.child("user_images/${System.currentTimeMillis()}.jpg")

            // Show progress bar
            progressBar.visibility = View.VISIBLE

            storageReference.putFile(foodImageUri!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        saveUserToDatabase(name, email, password, uri.toString())
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
            saveUserToDatabase(name, email, password, "")
        }
    }

    private fun saveUserToDatabase(name: String, email: String, password: String, imageUrl: String) {
        val userMap = mapOf(
            "uid" to auth.currentUser?.uid.orEmpty(),
            "name" to name,
            "email" to email,
            "imageUrl" to imageUrl
        )

        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(auth.currentUser?.uid.orEmpty()).setValue(userMap)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                    // Optionally, redirect to another activity
                    // startActivity(Intent(this, AnotherActivity::class.java))
                    // finish()
                } else {
                    Toast.makeText(this, "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
