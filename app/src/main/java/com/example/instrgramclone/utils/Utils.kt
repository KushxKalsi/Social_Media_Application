package com.example.instrgramclone.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.ProgressBar
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import javax.security.auth.callback.Callback

fun uploadImage(uri: Uri,folderName:String,callback: (String?)->Unit){
        var imageurl:String?=null
        FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()
     ).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageurl=it.toString()
                callback(imageurl)
            }
        }

}fun uploadVideo(uri: Uri,folderName:String,progressDialog:ProgressDialog  , callback: (String?)->Unit){
    var imageurl:String?=null
    progressDialog.setTitle("Upload...")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()
    ).putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageurl=it.toString()
                progressDialog.dismiss()
                callback(imageurl)
            }
        }
        .addOnProgressListener {
            val uploadValue:Long=(it.bytesTransferred/it.totalByteCount)*100
            progressDialog.setMessage("Uploaded $uploadValue %")
        }

}