package me.darthwithap.android.whatsappchatscreen.chat_screen.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import me.darthwithap.android.whatsappchatscreen.data.model.ChatMessage
import me.darthwithap.android.whatsappchatscreen.data.model.MessageStatus
import me.darthwithap.android.whatsappchatscreen.ui.theme.backgroundWhite
import me.darthwithap.android.whatsappchatscreen.ui.theme.lightGreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

enum class SwipeState {
    Default, Swiped
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ChatBubble(
    message: ChatMessage,
    onSwipe: () -> Unit = {}
) {

    val dragSize = 50.dp
    val width = 200.dp
    var isSwipeComplete by remember { mutableStateOf(false) }
    val swipeableState = rememberSwipeableState(SwipeState.Default)
    val sizePx = with(LocalDensity.current) { (width - dragSize).toPx() }
    val anchors = mapOf(0f to SwipeState.Default, sizePx to SwipeState.Swiped)
    val progress = derivedStateOf {
        if (swipeableState.offset.value == 0f) 0f else swipeableState.offset.value / sizePx
    }

    LaunchedEffect(key1 = isSwipeComplete) {
        if (isSwipeComplete) {
            swipeableState.animateTo(SwipeState.Default)
        }
        isSwipeComplete = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
    ) {
        DraggableBubble(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) },
            progress = progress.value,
            message,
            onSwipe = {
                isSwipeComplete = true
                onSwipe()
            },
            hasReply = message.hasReply,
            replyMessage = message.replyMessage,
            replyPerson = message.replyPerson
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun DraggableBubble(
    modifier: Modifier,
    progress: Float,
    message: ChatMessage,
    hasReply: Boolean = false,
    replyMessage: String = "",
    replyPerson: String = "You",
    onSwipe: () -> Unit
) {
    Column(
        modifier
            .padding(4.dp),
        horizontalAlignment = if (!message.isSent) Alignment.Start else Alignment.End
    ) {
        val isSwiped = derivedStateOf { progress >= 0.8f }
        LaunchedEffect(isSwiped.value) {
            if (isSwiped.value) {
                onSwipe()
            }
        }
        if (hasReply) {
            ReplyBox(
                message = replyMessage,
                isEditing = false,
                name = replyPerson
            )
        }
        Box(
            modifier = Modifier
                .background(
                    if (message.isSent) backgroundWhite else lightGreen,
                    RoundedCornerShape(20.dp)
                )
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
        Row {
            Text(
                text = formatTimestamp(message.timestamp),
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(2.dp)
            )
            if (message.isSent) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = when (message.status) {
                        MessageStatus.SENT -> "✓"
                        MessageStatus.DOUBLE_TICK -> "✓✓"
                        MessageStatus.SEEN -> "✓✓✓"
                    },
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


private fun formatTimestamp(timestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return dateFormat.format(calendar.time)
}
