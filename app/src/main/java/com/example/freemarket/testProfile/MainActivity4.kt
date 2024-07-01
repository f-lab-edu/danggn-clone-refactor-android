package com.example.freemarket.testProfile

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.freemarket.databinding.ActivityMain4Binding
import com.example.freemarket.dto.ProfileDto
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity4 : AppCompatActivity() {
    private var uri: Uri? = null
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private val binding: ActivityMain4Binding by lazy {
        ActivityMain4Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseRef = FirebaseDatabase.getInstance().getReference("user")
        storageRef = FirebaseStorage.getInstance().getReference("profileImage")

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imgAdd.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.btnPickImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnSend.setOnClickListener {
            saveData()
        }

    }


    private fun saveData() {

        val name = binding.edtName.text.toString()
        if (name.isEmpty()) binding.edtName.error = "write a name"

        val contactId = firebaseRef.push().key!!
        var contacts: ProfileDto

        uri?.let {
            storageRef.child(contactId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            Toast.makeText(
                                this,
                                " Image stored successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            val imgUrl = url.toString()
                            contacts = ProfileDto(name, imgUrl)

                            //에러 확인하는 코드
                            firebaseRef.child(contactId).setValue(contacts)
                                .addOnCompleteListener {
                                    Toast.makeText(
                                        this,
                                        " data stored successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(
                                        this,
                                        "error ${error.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                }
        }
    }
}