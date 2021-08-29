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
import com.andraganoid.verymuchtodo.util.Preferences
import com.andraganoid.verymuchtodo.util.myUser
import kotlinx.android.synthetic.main.users_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class UsersFragment : TodoBaseFragment() {

    private val viewModel: UsersViewModel by viewModel()
    private lateinit var userAdapter: UsersAdapter
    private val preferences: Preferences by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.users_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UsersAdapter(this)
        usersRecView.adapter = userAdapter
        viewModel.allUsers.observe(viewLifecycleOwner, Observer { userList ->
            val uList = userList as ArrayList<User>
            val me = uList.filter { user ->
                user.uid.equals(myUser.uid)
            }
            uList.remove(me.get(0))
            uList.add(0, me.get(0))
            userAdapter.setUserList(uList)
        })
    }

    fun onUserClick(user: User) {
        Toast.makeText(activity, user.name, Toast.LENGTH_LONG).show()
    }

}
