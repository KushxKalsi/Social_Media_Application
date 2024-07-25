package com.example.instrgramclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instrgramclone.Models.Post
import com.example.instrgramclone.Models.User
import com.example.instrgramclone.R
import com.example.instrgramclone.adapter.FollowRvAdapter
import com.example.instrgramclone.adapter.PostAdapter
import com.example.instrgramclone.databinding.FragmentHomeBinding
import com.example.instrgramclone.databinding.ReelDgBinding
import com.example.instrgramclone.utils.FOLLOW
import com.example.instrgramclone.utils.POST
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private  var postList=ArrayList<Post>()
    private lateinit var adapter: PostAdapter
    private var followList=ArrayList<User>()
    private lateinit var followAdapter:FollowRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        adapter= PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter
        followAdapter= FollowRvAdapter(requireContext(),followList)
        binding.followRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter=followAdapter
        setHasOptionsMenu(true)

        (requireContext() as  AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get().addOnSuccessListener { var templist=ArrayList<User>()
                followList.clear()
            for(i in it.documents)
            {

                var user:User=i.toObject<User>()!!
                templist.add(user)
            }
            followList.addAll(templist)
            followAdapter.notifyDataSetChanged()
        }

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var templist=ArrayList<Post>()
            postList.clear()
            for(i in it.documents)
            {
                var post:Post=i.toObject<Post>()!!
                templist.add(post)
            }
            postList.addAll(templist)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
            super.onCreateOptionsMenu(menu, inflater)
    }
}