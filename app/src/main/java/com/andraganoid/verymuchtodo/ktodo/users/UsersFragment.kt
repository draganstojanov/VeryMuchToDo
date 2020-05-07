package com.andraganoid.verymuchtodo.ktodo.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment
import kotlinx.android.synthetic.main.users_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class UsersFragment : TodoBaseFragment() {

    private val viewModel: UsersViewModel by viewModel()
    private lateinit var userAdapter: UsersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.users_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UsersAdapter(arrayListOf(), this)
        usersRecView.adapter = userAdapter
        viewModel.allUsers.observe(viewLifecycleOwner, Observer { userList ->
            userAdapter.setUserList(userList as ArrayList<User>?)
        })
    }

    fun onUserClick(user: User) {
        Toast.makeText(activity, user.name, Toast.LENGTH_LONG).show()
    }

}
