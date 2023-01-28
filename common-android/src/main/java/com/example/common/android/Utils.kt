package com.example.common.android

private var lastTimeClick = 0L
fun safeOnClick(action: () -> Unit) {
    if (System.currentTimeMillis() - lastTimeClick >= 300) {
        lastTimeClick = System.currentTimeMillis()
        action()
    }
}