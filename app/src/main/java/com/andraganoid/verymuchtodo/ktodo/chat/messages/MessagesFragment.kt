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
        setTitle(viewModel.currentChat!!.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer { loaderVisibility(it) })

        viewModel.allChats.observe(viewLifecycleOwner, Observer { chats: List<Chat> ->
            viewModel.currentChat = chats.filter { chat ->
                viewModel.currentChat!!.id.equals(chat.id)
            }.firstOrNull()
            lifecycleScope.launch {
                if (viewModel.updateCurrentChat()) {
                    messageAdapter = MessagesAdapter(viewModel.currentChat?.messages!!, this@MessagesFragment)
                    messagesRecView.adapter = messageAdapter
                    if (viewModel.currentChat!!.messages.size > 0) {
                        messagesRecView.scrollToPosition(viewModel.currentChat?.messages!!.size - 1)
                    }
                }
            }
        })
    }

}
