package com.andraganoid.verymuchtodo.ktodo.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.databinding.ChatsFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import kotlinx.android.synthetic.main.chats_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ChatsFragment : Fragment() {

    private lateinit var binding: ChatsFragmentBinding
    private val viewModel: ChatsViewModel by viewModel()
    private lateinit var chatsAdapter: ChatsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ChatsFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatsAdapter = ChatsAdapter(this)
        chatsRecView.adapter = chatsAdapter
        viewModel.allChats.observe(viewLifecycleOwner, Observer{chats->
            lifecycleScope.launch{  chatsAdapter.setChatsList(chats as ArrayList<Chat>?,viewModel.allUsers().size)}
        })
    }

    fun createNewChat() {
showMessages(Chat())
    }

    fun showMessages(chat: Chat) {
        val action=ChatsFragmentDirections.actionChatsFragmentToMessagesFragment()
        action.chat=chat
        findNavController().navigate(action)
    }


}
