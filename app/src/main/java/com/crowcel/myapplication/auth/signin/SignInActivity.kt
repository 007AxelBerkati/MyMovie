package com.crowcel.myapplication.auth.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crowcel.myapplication.home.HomeActivity
import com.crowcel.myapplication.R
import com.crowcel.myapplication.auth.signup.SignUpActivity
import com.crowcel.myapplication.utils.Preferences
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {

    private lateinit var sUsername: String
    private lateinit var sPassword: String

    lateinit var preferences: Preferences


    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        preferences = Preferences(this)
        preferences.setValues("onboarding", "1")

        if (preferences.getValues("status").equals("1")) {
            finishAffinity()

            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }


        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnDaftarAkun = findViewById<Button>(R.id.btn_daftar_akun)

        val eUsername = findViewById<EditText>(R.id.et_username)
        val ePassword = findViewById<EditText>(R.id.et_password)


        btnLogin.setOnClickListener {
            sUsername = eUsername.text.toString()
            sPassword = ePassword.text.toString()

            if (sUsername == "") {
                eUsername.error = "Silahkan isi username anda"
                eUsername.requestFocus()
            } else if (sPassword == "") {
                ePassword.error = "Silahkan isi password anda"
                ePassword.requestFocus()
            } else {
                //create function to sign in using email and password firebase
                pushLogin(sUsername, sPassword)
            }
        }

        btnDaftarAkun.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }


    }

    private fun pushLogin(sUsername: String, sPassword: String) {
        mDatabase.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    if (user.password == sPassword) {
                        //login success

                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("password", user.password.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("status", "1")


                        Toast.makeText(this@SignInActivity, "Login berhasil", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)

                        startActivity(intent)
                    } else {

                        Toast.makeText(this@SignInActivity, "Password salah", Toast.LENGTH_SHORT)
                            .show()
                        //login failed
                    }
                } else {
                    //login failed
                    Toast.makeText(this@SignInActivity, "Username salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //login failed
                Toast.makeText(this@SignInActivity, "" + error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }
}