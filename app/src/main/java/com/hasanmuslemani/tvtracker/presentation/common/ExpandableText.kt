package com.hasanmuslemani.tvtracker.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ExpandableText(modifier: Modifier = Modifier, text: String, fontSize: TextUnit = 18.sp, color: Color = Color.Black, maxLines: Int = 3) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var adjustedText by remember { mutableStateOf(text) }
    var showText by remember { mutableStateOf( " Show Less") }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                adjustedText = text
                showText = " Show Less"
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(maxLines - 1)
                val showMoreString = "... Show More"
                adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                showText = showMoreString
                isClickable = true
            }
        }
    }

    Text(
        text = buildAnnotatedString {
            append(adjustedText)
            if(isExpanded || isClickable) {
                withStyle(style = SpanStyle(Color(0xFF2A8FFF))) {
                    append(showText)
                }
            }
        },
        fontSize = fontSize,
        color = color,
        maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = isClickable
            ) {
                isExpanded = !isExpanded
            }
            .animateContentSize(),
    )
}