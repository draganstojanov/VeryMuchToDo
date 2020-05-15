package com.andraganoid.verymuchtodo.ktodo.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.andraganoid.verymuchtodo.databinding.MessagesFragmentBinding
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
        messageAdapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY
        messagesRecView.adapter = messageAdapter
        setObservers()
    }

    fun setObservers() {
        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer { loaderVisibility(it) })
        viewModel.allMessages.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launchWhenCreated {
                val mList = viewModel.getAllMessages()
                messageAdapter.setMessageList(mList)
                viewModel.messageText.set("")
                messagesRecView.scrollToPosition(mList!!.size - 1)
            }
        })

    }
}
