package com.atechy.atechytwitterclone.teamreply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atechy.atechytwitterclone.R
import com.atechy.atechytwitterclone.databinding.FragmentTeamReplyBinding

/**
 * @author Shital Awathe
 * Created on date 06 May 2021
 *
 * This class shows the team reply
 */
class TeamReplyFragment: Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TeamReplyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTeamReplyBinding>(inflater, R.layout.fragment_team_reply, container, false)
        initTeamReplayView(binding)
        return binding.root
    }

    private fun initTeamReplayView(binding: FragmentTeamReplyBinding) {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.teamReplyRecyclerView.layoutManager = linearLayoutManager
        adapter = activity?.let { TeamReplyAdapter(it) }!!
        binding.teamReplyRecyclerView?.adapter = adapter
    }
}