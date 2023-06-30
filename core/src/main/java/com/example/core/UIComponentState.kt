package com.example.core

sealed class UIComponentState {
    object Show: UIComponentState()
    object Hide: UIComponentState()
}
