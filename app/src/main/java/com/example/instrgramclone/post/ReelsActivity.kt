package com.example.instrgramclone.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instrgramclone.HomeActivity
import com.example.instrgramclone.Models.Reel
import com.example.instrgramclone.Models.User
import com.example.instrgramclone.databinding.ActivityReelsBinding
import com.example.instrgramclone.utils.REEL
import com.example.instrgramclone.utils.REEL_FOLODER
import com.example.instrgramclone.utils.USER_NODE
import com.example.instrgramclone.utils.USER_POST_FOLODER
import com.example.instrgramclone.utils.uploadImage
import com.example.instrgramclone.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityReelsBinding.inflate(layoutInflater)

    }
    private lateinit var videoUrl:String
    lateinit var progressDialog:ProgressDialog
    private  val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {
            uploadVideo(uri, REEL_FOLODER, progressDialog) { url ->
                if (url != null) {
                    videoUrl = url

                }

            }
           }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog=ProgressDialog(this)
        binding.selectReel.setOnClickListener{

            launcher.launch("video/*")
        }
        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener{
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User=it.toObject<User>()!!
                val reel:Reel= Reel( videoUrl!!,binding.caption.editText?.text.toString(),user.image!!)
                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel).addOnSuccessListener {
                        startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                        finish()
                    }

                }

            }



        }

}
    }
