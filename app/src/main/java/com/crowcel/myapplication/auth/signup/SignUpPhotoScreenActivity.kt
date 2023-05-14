package com.crowcel.myapplication.auth.signup

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crowcel.myapplication.HomeActivity
import com.crowcel.myapplication.R
import com.crowcel.myapplication.utils.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.UUID

class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        val tvHello = findViewById<TextView>(R.id.tv_hello)
        val ivAdd = findViewById<ImageView>(R.id.iv_add)
        val btnUpload = findViewById<Button>(R.id.btn_upload)
        val ivProfile = findViewById<ImageView>(R.id.iv_profile)
        val btnHome = findViewById<Button>(R.id.btn_home)

        tvHello.text = "Selamat Datang\n"+intent.getStringExtra("nama")

        ivAdd.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btnUpload.visibility = View.VISIBLE
                ivAdd.setImageResource(R.drawable.ic_btn_upload)
                ivProfile.setImageResource(R.drawable.user_pic)

            } else {
                Dexter.withContext(this@SignUpPhotoScreenActivity)
                    .withPermission(android.Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
                statusAdd = true
                ivAdd.setImageResource(R.drawable.ic_btn_delete)
            }
        }

        btnHome.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btnUpload.setOnClickListener {
        val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference.child("images/"+UUID.randomUUID().toString())
            ref.putFile(filePath).addOnCanceledListener {
                progressDialog.dismiss()
                Toast.makeText(this@SignUpPhotoScreenActivity, "Canceled", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(this@SignUpPhotoScreenActivity, "Failed", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this@SignUpPhotoScreenActivity, "Uploaded", Toast.LENGTH_SHORT).show()
                ref.downloadUrl.addOnSuccessListener {
                    preferences.setValues("url", it.toString())
                }
                finishAffinity()
                val intent = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
                startActivity(intent)
            }.addOnProgressListener {
                taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                progressDialog.setMessage("Upload "+progress.toInt()+"%")
            }
        }

    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }

        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        TODO("Not yet implemented")
        Toast.makeText(this, "You can't add profile photo", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? Klik tombol upload nanti aja", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           val bitmap = data?.extras?.get("data") as Bitmap
              statusAdd = true

           val ivProfile = findViewById<ImageView>(R.id.iv_profile)

           filePath = data.getData()!!
              Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(ivProfile)

              val btnUpload = findViewById<Button>(R.id.btn_upload)
                btnUpload.visibility = View.VISIBLE

                val ivAdd = findViewById<ImageView>(R.id.iv_add)

                ivAdd.setImageResource(R.drawable.ic_btn_delete)


       }

    }
}