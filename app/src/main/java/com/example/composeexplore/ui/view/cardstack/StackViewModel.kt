package com.example.composeexplore.ui.view.cardstack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeexplore.data.model.Card

class StackViewModel: ViewModel() {

    private lateinit var deck: List<Card>

    private val _position = MutableLiveData(0)
    var position: LiveData<Int> = _position

    val leftStack
        get() = deck[position.value?.plus(1) ?: 0]
    val rightStack
        get() = deck[position.value?.minus(1) ?: 0]
    val flipCard
        get() = deck[position.value ?: 0]

    fun setPosition(newPosition: Int) {
        _position.value = newPosition
    }

    fun setDeck(newDeck: List<Card>) {
        deck = newDeck
    }

    fun nextDeck() {
        _position.value = (_position.value ?: 0) + 1
    }
}