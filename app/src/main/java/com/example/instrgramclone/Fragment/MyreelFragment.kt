package com.example.instrgramclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instrgramclone.Models.Post
import com.example.instrgramclone.Models.Reel
import com.example.instrgramclone.R
import com.example.instrgramclone.adapter.MyPostRvAdapter
import com.example.instrgramclone.adapter.MyReelAdapter
import com.example.instrgramclone.databinding.ActivityReelsBinding
import com.example.instrgramclone.databinding.FragmentMyreelBinding
import com.example.instrgramclone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyreelFragment : Fragment() {
    private lateinit var binding: FragmentMyreelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyreelBinding.inflate(inflater,container,false)
        var reelList=ArrayList<Reel>()
        var adapter= MyReelAdapter(requireContext(),reelList,)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).get().addOnSuccessListener {
            var tempList= arrayListOf<Reel>()
            for(i in it.documents)
            {
                var reel: Reel =i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}