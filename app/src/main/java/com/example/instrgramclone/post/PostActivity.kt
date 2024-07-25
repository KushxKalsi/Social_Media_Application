    package com.example.instrgramclone.post

    import android.content.Intent
    import android.os.Bundle
    import androidx.activity.result.contract.ActivityResultContracts

    import androidx.appcompat.app.AppCompatActivity
    import com.example.instrgramclone.HomeActivity
    import com.example.instrgramclone.Models.Post
    import com.example.instrgramclone.Models.User

    import com.example.instrgramclone.databinding.ActivityPostBinding
    import com.example.instrgramclone.utils.POST
    import com.example.instrgramclone.utils.USER_NODE
    import com.example.instrgramclone.utils.USER_POST_FOLODER
    import com.example.instrgramclone.utils.USER_PROFILE_FOLODER
    import com.example.instrgramclone.utils.uploadImage
    import com.google.firebase.Firebase
    import com.google.firebase.auth.auth
    import com.google.firebase.firestore.firestore
    import com.google.firebase.firestore.toObject


    class PostActivity : AppCompatActivity() {
        val binding by lazy {
            ActivityPostBinding.inflate(layoutInflater)
        }
        var imageUri:String?=null
        private  val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
                uri->
            uri?.let{
                uploadImage(uri, USER_POST_FOLODER){
                    url->
                    if(url!=null)
                    {
                        binding.selectImage.setImageURI(uri)
                        imageUri=url

                    }

                }
            }
        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)
            setSupportActionBar(binding.materialToolbar)
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            binding.materialToolbar.setNavigationOnClickListener{
                startActivity(Intent(this@PostActivity,HomeActivity::class.java))

                finish()
            }
            binding.selectImage.setOnClickListener{
                launcher.launch("image/*")
            }
            binding.cancelButton.setOnClickListener{
                startActivity(Intent(this@PostActivity,HomeActivity::class.java))
                finish()
            }
            binding.postButton.setOnClickListener{
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    var user=it.toObject<User>()!!


                val post:Post= Post(postUrl=imageUri!!,caption=binding.caption.editText?.text.toString(),uid=Firebase.auth.currentUser!!.uid.toString(),
                    time=System.currentTimeMillis().toString())
                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
                        .addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                }
                }
            }
        }
    }