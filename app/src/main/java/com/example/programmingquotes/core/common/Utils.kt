package com.example.programmingquotes.core.common

internal fun createEmojiWithDecimalCode(emojiCode: Int): String =
    String(Character.toChars(emojiCode))

internal fun generateRandomEmoji() = (128512..128580).random()