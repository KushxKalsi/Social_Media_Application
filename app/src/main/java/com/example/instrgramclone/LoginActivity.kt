package com.example.instrgramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instrgramclone.Models.User
import com.example.instrgramclone.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.w("Tags","CHevckin binding")
        binding.loginBtn.setOnClickListener{
            Log.w("tags","Inside SetOn Click listermer")
            if(binding.email.editText?.text.toString().equals("")or
                binding.passw.editText?.text.toString().equals(""))
            {
                Toast.makeText(this@LoginActivity,"Please Fill the deatil",Toast.LENGTH_SHORT).show()
            }
            else{
                var user= User(binding.email.editText?.text.toString(), binding.passw.editText?.text.toString())
                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            Log.w("Testing","Starting Hokme activity")
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()

                        }
                        else{
                            Log.w("roor","In else block")
                            Toast.makeText(this@LoginActivity,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        Log.e("helo","Outlise set on click lister")
    }
}


