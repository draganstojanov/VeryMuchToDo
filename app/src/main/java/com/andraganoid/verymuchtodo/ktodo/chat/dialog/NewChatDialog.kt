package com.andraganoid.verymuchtodo.ktodo.chat.dialog

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.lifecycleScope
import com.andraganoid.verymuchtodo.databinding.FragmentNewChatDialogBinding
import com.andraganoid.verymuchtodo.kmodel.Chat
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.ktodo.chat.ChatsViewModel
import com.andraganoid.verymuchtodo.util.CHAT_ALL_MEMBERS
import com.andraganoid.verymuchtodo.util.myUser
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_new_chat_dialog.*
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel


class NewChatDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewChatDialogBinding
   private val viewModel: ChatsViewModel by sharedViewModel()
    private lateinit var userList: ArrayList<NewUser>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewChatDialogBinding.inflate(inflater, container, false)
        //  binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userList = arrayListOf()
        userList.add(NewUser(User(name = CHAT_ALL_MEMBERS, uid = CHAT_ALL_MEMBERS), ObservableBoolean(false)))
        lifecycleScope.launch {
            viewModel.allUsers.forEach { user ->
                if (user.uid != myUser.uid) {
                    userList.add(NewUser(user, ObservableBoolean(false)))
                }
            }
        }
        val height = if (userList.size > 8) 320f else userList.size * 40f
        newChatRecView.layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, height, resources.displayMetrics).toInt()
        newChatRecView.adapter = NewChatAdapter(userList)
    }

    fun createChat() {

        var selectedNewUsers = arrayListOf<String>(myUser.uid)

        userList.forEach { newUser ->
            if (newUser.isSelected.get()) {
                selectedNewUsers.add(newUser.user.uid)
            }
        }
        var name = ""
        if (selectedNewUsers.contains(CHAT_ALL_MEMBERS)) {
            selectedNewUsers.clear()
            selectedNewUsers = arrayListOf(CHAT_ALL_MEMBERS)
            name = CHAT_ALL_MEMBERS
        }

        viewModel._newChat.value=Chat(
                members = selectedNewUsers,
                name = name,
                id = "${myUser.uid}-${System.currentTimeMillis()}"

        )
        dismiss()
}

    fun cancel() {
        dismiss()
    }

}
