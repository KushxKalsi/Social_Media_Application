package com.example.instrgramclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instrgramclone.Models.Post
import com.example.instrgramclone.R
import com.example.instrgramclone.adapter.MyPostRvAdapter
import com.example.instrgramclone.databinding.FragmentMypostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MypostFragment : Fragment() {
    private lateinit var binding:FragmentMypostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMypostBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        var postList=ArrayList<Post>()
        var adapter=MyPostRvAdapter(requireContext(),postList,)
        binding.rv.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
         var tempList= arrayListOf<Post>()
            for(i in it.documents)
            {
                if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString()))
                {

                }
                else {


                    var post: Post = i.toObject<Post>()!!
                    tempList.add(post)
                }
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}