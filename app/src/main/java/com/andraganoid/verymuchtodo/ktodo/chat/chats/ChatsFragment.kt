package com.andraganoid.verymuchtodo.ktodo.chat.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.databinding.ChatsFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.ktodo.chat.ChatsViewModel
import com.andraganoid.verymuchtodo.ktodo.chat.dialog.NewChatDialog
import kotlinx.android.synthetic.main.chats_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ChatsFragment : TodoBaseFragment() {

    private lateinit var binding: ChatsFragmentBinding
    private val viewModel: ChatsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ChatsFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  lifecycleScope.launch {
        viewModel.allChats.observe(viewLifecycleOwner, Observer { chats ->
            val sortedChats = chats.sortedByDescending { chat -> chat.lastEdit }
            lifecycleScope.launch { chatsRecView.adapter = ChatsAdapter(sortedChats, viewModel.getUserMap().size, this@ChatsFragment)
                if (chats.size > 0) {
                    chatsRecView.smoothScrollToPosition(sortedChats.size - 1)
                }
            }

        })
        //  }

        viewModel.newChat.observe(viewLifecycleOwner, Observer { chat ->
            if (chat != null) {
                viewModel._newChat.value = null
                showMessages(chat)
            }
        })
    }


    fun createNewChat() {
        val newChatDialog = NewChatDialog()
        newChatDialog.show(todo.supportFragmentManager, null)
    }

    fun showMessages(chat: Chat) {
        viewModel.currentChat = chat
        val action = ChatsFragmentDirections.actionChatsFragmentToMessagesFragment()
        action.currentChat = chat
        findNavController().navigate(action)
    }

}
