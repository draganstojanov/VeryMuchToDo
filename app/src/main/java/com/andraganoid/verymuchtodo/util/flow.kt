package com.andraganoid.verymuchtodo.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
val _networkStateFlow = MutableStateFlow<Boolean>(false)

@ExperimentalCoroutinesApi
val networkStateFlow: StateFlow<Boolean> = _networkStateFlow

//@ExperimentalCoroutinesApi
//val networkStateChannel = ConflatedBroadcastChannel<Boolean>()
//
//val _networkStatus = MutableLiveData<Boolean>()
//val networkStatus: LiveData<Boolean>
//    get() = _networkStatus


@ExperimentalCoroutinesApi
val _messageStateFlow = MutableStateFlow<String>("")

@ExperimentalCoroutinesApi
val messageStateFlow: StateFlow<String> = _messageStateFlow
