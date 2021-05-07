package com.atechy.atechytwitterclone.teamreply

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.databinding.FragmentTeamReplyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class shows the team reply
 */
class TeamReplyFragment : Fragment() {
    companion object {
        val TAG = TeamReplyFragment::class.qualifiedName
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TeamReplyAdapter
    private lateinit var teamReplyViewModel: TeamReplyViewModel
    private val messages: MutableList<Message> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTeamReplyBinding>(
            inflater,
            R.layout.fragment_team_reply,
            container,
            false
        )
        teamReplyViewModel = ViewModelProvider(this).get(TeamReplyViewModel::class.java)
        initTeamReplayView(binding)
        observeData(binding)
        return binding.root
    }

    /**
     * Observe data messages data and show in list
     */
    private fun observeData(binding: FragmentTeamReplyBinding?) {
        teamReplyViewModel.mListOfMessages.observe(
            viewLifecycleOwner,
            Observer { messageList: List<Message> ->
                if (messageList.isNotEmpty()) {
                    adapter = activity?.let { TeamReplyAdapter(messageList) }!!
                    binding?.teamReplyRecyclerView?.adapter = adapter
                }
            })
    }

    /**
     * Initialize views
     */
    private fun initTeamReplayView(binding: FragmentTeamReplyBinding) {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.teamReplyRecyclerView.layoutManager = linearLayoutManager

        binding.backImage.setOnClickListener {
            activity?.finish()
        }

        binding.editReply.setOnClickListener {
            if (binding.editReply.text.isBlank()) {
                Toast.makeText(activity, "Please write message", Toast.LENGTH_SHORT).show()
            } else {
                val myMessage = Message(
                    teamReplyViewModel.getUserObject()?.name,
                    binding.editReply.text.toString(),
                    null
                )
                messages.add(myMessage)
                if (messages.isNotEmpty()) {
                    adapter = activity?.let { TeamReplyAdapter(messages) }!!
                    binding.teamReplyRecyclerView.adapter = adapter
                }
                val message = teamReplyViewModel.getUserObject()?.name?.let { it1 ->
                    Message(
                        message = binding.editReply.text.toString(),
                        name = it1,
                        email = teamReplyViewModel.getUserObject()!!.email,
                        key = teamReplyViewModel.getUserObject()!!.uid
                    )
                }
                teamReplyViewModel.getMessageDb().push().setValue(message)
                binding.editReply.setText("")
            }
        }

    }

    /**
     * Fetch messages on starting the fragment
     */
    override fun onStart() {
        super.onStart()
        teamReplyViewModel.callInOnStart()
    }
}