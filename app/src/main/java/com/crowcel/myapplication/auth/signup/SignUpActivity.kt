package com.crowcel.myapplication.auth.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crowcel.myapplication.R
import com.crowcel.myapplication.auth.signin.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sEmail: String
    lateinit var sPassword: String
    lateinit var sNama: String

    lateinit var mFirebaseInstance : FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance.getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")


        val btnDaftar = findViewById<Button>(R.id.btn_daftar)

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etNama = findViewById<EditText>(R.id.et_nama)


        btnDaftar.setOnClickListener {

            sUsername = etUsername.text.toString()
            sEmail = etEmail.text.toString()
            sPassword = etPassword.text.toString()
            sNama = etNama.text.toString()

            if (sUsername.equals("")) {
                etUsername.error = "Silahkan isi username anda"
                etUsername.requestFocus()
            } else if (sEmail.equals("")) {
                etEmail.error = "Silahkan isi email anda"
                etEmail.requestFocus()
            } else if (sPassword.equals("")) {
                etPassword.error = "Silahkan isi password anda"
                etPassword.requestFocus()
            } else if (sNama.equals("")) {
                etNama.error = "Silahkan isi nama anda"
                etNama.requestFocus()
            } else {
                pushNewUser(sUsername, sEmail, sPassword, sNama)
            }



        }


    }
    private fun pushNewUser(sUsername: String, sEmail: String, sPassword: String, sNama: String) {
        val user = User()
        user.username = sUsername
        user.email = sEmail
        user.password = sPassword
        user.nama = sNama
        checkingUsername(sUsername, user)
    }

    private fun checkingUsername(sUsername: String, data: Any) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user == null) {
                    mDatabaseReference.child(sUsername).setValue(data)

                    Toast.makeText(this@SignUpActivity, "Berhasil membuat akun", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@SignUpActivity, SignUpPhotoScreenActivity::class.java).putExtra("nama", sNama)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity, "Username sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }

        })

    }
}