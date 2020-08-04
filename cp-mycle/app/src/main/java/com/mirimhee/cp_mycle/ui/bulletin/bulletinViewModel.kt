package com.mirimhee.cp_mycle.ui.bulletin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class bulletinViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is bulletin Fragment"
    }
    val text: LiveData<String> = _text
}