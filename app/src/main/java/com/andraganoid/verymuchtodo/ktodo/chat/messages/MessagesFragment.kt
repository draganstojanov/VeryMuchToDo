package com.andraganoid.verymuchtodo.ktodo.chat.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.databinding.MessagesFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.util.ARGS_CURRENT_CHAT
import kotlinx.android.synthetic.main.messages_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class MessagesFragment : TodoBaseFragment() {

    private lateinit var binding: MessagesFragmentBinding
    private val viewModel: MessagesViewModel by viewModel()
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
        //  messageAdapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY
        messagesRecView.adapter = messageAdapter
        //  messageAdapter.setMessageList(viewModel.currentChat!!.messages)
        setObservers()
    }

    fun setObservers() {
        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer { loaderVisibility(it) })
//        viewModel.allMessages.observe(viewLifecycleOwner, Observer {
//            lifecycleScope.launchWhenCreated {
//                val mList = viewModel.getAllMessages()
//                messageAdapter.setMessageList(mList)
//                viewModel.messageText.set("")
//                messagesRecView.scrollToPosition(mList!!.size - 1)
//            }
//        })


        viewModel.allChats.observe(viewLifecycleOwner, Observer {
            viewModel.getAllMessages(it)
        })
        viewModel.allMessages.observe(viewLifecycleOwner, Observer {
            messageAdapter.msgList = it
            viewModel.currentChat?.lastRead = System.currentTimeMillis()
            viewModel.messageText.set("")
            if (it.size > 0) {
                messagesRecView.smoothScrollToPosition(it.size - 1)
            }
        })

    }
}
