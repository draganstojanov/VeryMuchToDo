package com.andraganoid.verymuchtodo.ktodo.chat.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.andraganoid.verymuchtodo.databinding.MessagesFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.ktodo.chat.ChatsViewModel
import com.andraganoid.verymuchtodo.util.ARGS_CURRENT_CHAT
import kotlinx.android.synthetic.main.messages_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MessagesFragment : TodoBaseFragment() {

    private lateinit var binding: MessagesFragmentBinding
    private val viewModel: ChatsViewModel by sharedViewModel()
    private lateinit var messageAdapter: MessagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MessagesFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        messageAdapter = MessagesAdapter(this)
        arguments?.takeIf { it.containsKey(ARGS_CURRENT_CHAT) }?.apply {
            viewModel.currentChat = arguments?.getSerializable(ARGS_CURRENT_CHAT) as Chat?
        }
        setTitle(viewModel.currentChat!!.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesRecView.adapter = messageAdapter

        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer { loaderVisibility(it) })
        lifecycleScope.launch {
            viewModel.allChats().observe(viewLifecycleOwner, Observer {
                viewModel._loaderVisibility.value = false

                messageAdapter.apply {
                    userMap = viewModel.userMap
                    msgList = viewModel.currentChat?.messages!!
                }
                viewModel.currentChat?.lastRead = System.currentTimeMillis()
                viewModel.messageText.set("")
                if (it.size > 0) {
                    messagesRecView.smoothScrollToPosition(viewModel.currentChat?.messages!!.size - 1)
                }
            })
        }
    }

}
