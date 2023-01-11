package com.example.programmingquotes.core.common

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

internal fun createEmojiWithDecimalCode(emojiCode: Int): String =
    String(Character.toChars(emojiCode))

internal fun generateRandomEmoji() = (128512..128580).random()

internal var lastTimeClick = 0L
internal fun safeOnClick(action: () -> Unit) {
    if (System.currentTimeMillis() - lastTimeClick >= 300) {
        lastTimeClick = System.currentTimeMillis()
        action()
    }
}

internal fun Modifier.safeClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { safeOnClick(onClick) },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}