package com.andraganoid.verymuchtodo.ktodo.chat.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.databinding.ChatsFragmentBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import com.andraganoid.verymuchtodo.ktodo.chat.dialog.NewChatDialog
import kotlinx.android.synthetic.main.chats_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ChatsFragment : TodoBaseFragment() {

    private lateinit var binding: ChatsFragmentBinding
    private val viewModel: ChatsViewModel by sharedViewModel()
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


        viewModel.allChats.observe(viewLifecycleOwner, Observer { chats ->
            viewModel.allChatsList=chats
            viewModel.allUsers()
            // lifecycleScope.launch { chatsAdapter.setChatsList(chats as ArrayList<Chat>?, viewModel.allUsers().size) }
        })

        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            chatsAdapter.setChatsList(viewModel.allChatsList as ArrayList<Chat>?, it.size)
        })

        viewModel.newChat.observe(viewLifecycleOwner,Observer{chat->
            if (chat!=null){
                viewModel._newChat.value=null
                showMessages(chat)
            }

        })

    }

    fun createNewChat() {
        val newChatDialog = NewChatDialog()
        newChatDialog.show(todo.supportFragmentManager, null)

    }


//    fun createnewAllUsersChat(name: String?) {
//        val newChat = Chat(
//                members = arrayListOf(CHAT_ALL_MEMBERS),
//                name = if (name.isNullOrBlank()) CHAT_ALL_MEMBERS else name,
//                id = "${myUser.uid}-${System.currentTimeMillis()}"
//        )
//        showMessages(newChat)
//    }

    fun showMessages(chat: Chat) {
        val action = ChatsFragmentDirections.actionChatsFragmentToMessagesFragment()
        action.currentChat = chat
        findNavController().navigate(action)
    }


}
