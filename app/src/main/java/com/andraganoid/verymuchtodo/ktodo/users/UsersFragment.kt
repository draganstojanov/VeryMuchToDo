package com.andraganoid.verymuchtodo.ktodo.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ktodo.TodoBaseFragment

class UsersFragment : TodoBaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.users_fragment, container, false)


}
