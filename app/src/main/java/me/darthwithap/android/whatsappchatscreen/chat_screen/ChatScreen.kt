package me.darthwithap.android.whatsappchatscreen.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.darthwithap.android.whatsappchatscreen.R
import me.darthwithap.android.whatsappchatscreen.chat_screen.composables.ChatAppBar
import me.darthwithap.android.whatsappchatscreen.chat_screen.composables.ChatBubble
import me.darthwithap.android.whatsappchatscreen.chat_screen.composables.InputRow
import me.darthwithap.android.whatsappchatscreen.data.model.ChatMessage
import me.darthwithap.android.whatsappchatscreen.ui.theme.chatBg

@Composable
fun ChatScreen() {
    var messages by remember { mutableStateOf(ChatMessage.fakeMessages) }
    var newMessage by remember { mutableStateOf("") }
    var replyMessage by remember { mutableStateOf("") }
    var isReplying by remember { mutableStateOf(false) }
    var yourMessageReply by remember { mutableStateOf(false) }
    val person = "Parth Takkar" // ideally from viewmodel

    val lazyListState = rememberLazyListState()

    LaunchedEffect(messages) {
        val lastIndex = messages.lastIndex
        if (lastIndex >= 0) {
            lazyListState.scrollToItem(lastIndex)
        }
    }

    Column(
        modifier = Modifier
            .background(chatBg)
            .fillMaxSize()
    ) {
        // TopBar
        ChatAppBar(
            hasAvatar = true,
            avatarId = R.drawable.profile_img,
            name = person,
            subtitle = "Last seen 10 minutes ago",
            onBackClick = {},
            onAvatarClick = {},
            onCallClick = {},
            onShowOptionsMenu = {}
        )

        // Chat messages
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .weight(1f)
        ) {
            items(messages) { message ->
                ChatBubble(message) {
                    replyMessage = message.text
                    isReplying = true
                    yourMessageReply = message.isSent
                }
            }
        }

        // Input field
        InputRow(
            newMessage,
            onMessageChange = { newMessage = it },
            onMessageSend = { hasReply, replyMessage, replyPerson ->
                messages += ChatMessage(
                    newMessage,
                    isSent = true,
                    hasReply = hasReply,
                    replyPerson = replyPerson,
                    replyMessage = replyMessage
                )
                newMessage = ""
            },
            personName = person,
            isReplyingToMessage = isReplying,
            yourReplyMessage = yourMessageReply,
            replyMessage = replyMessage,
            onReplyClose = {
                isReplying = false
            }
        )
    }
}
