package com.example.instrgramclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instrgramclone.Models.Reel
import com.example.instrgramclone.R
import com.example.instrgramclone.adapter.ReelAdapter
import com.example.instrgramclone.databinding.FragmentMyreelBinding
import com.example.instrgramclone.databinding.FragmentReelBinding
import com.example.instrgramclone.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class reelFragment : Fragment() {
    private lateinit var binding :FragmentReelBinding
    lateinit var adapter:ReelAdapter
    var reelList=ArrayList<Reel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentReelBinding.inflate(inflater,container,false )
        adapter= ReelAdapter(requireContext(),reelList)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var templist=ArrayList<Reel>()
            reelList.clear()
            for(i in it.documents)
            {
                var reel=i.toObject<Reel>()!!
                templist.add(reel)

            }
            reelList.addAll(templist)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}