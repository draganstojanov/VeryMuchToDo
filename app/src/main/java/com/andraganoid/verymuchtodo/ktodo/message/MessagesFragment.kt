package com.andraganoid.verymuchtodo.ktodo.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.andraganoid.verymuchtodo.databinding.MessagesFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Message
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import kotlinx.android.synthetic.main.messages_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class MessagesFragment : TodoBaseFragment() {

    private lateinit var binding: MessagesFragmentBinding
    private val viewModel: MessagesViewModel by viewModel()
    private lateinit var messageAdapter: MessagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MessagesFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageAdapter = MessagesAdapter(this)
        messagesRecView.adapter = messageAdapter
        setObservers()
    }

    fun setObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.allMessages().observe(viewLifecycleOwner, Observer { messagesList ->
                    messageAdapter.setMessageList(messagesList as ArrayList<Message>?)
                    viewModel.messageText.set("")
            })
        }
    }
}
