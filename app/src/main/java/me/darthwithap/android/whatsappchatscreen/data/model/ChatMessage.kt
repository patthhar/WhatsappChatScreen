package me.darthwithap.android.whatsappchatscreen.data.model

data class ChatMessage(
    val text: String,
    val isSent: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val status: MessageStatus = MessageStatus.SENT,
    val hasReply: Boolean = false,
    val replyPerson: String = "You",
    val replyMessage: String = ""
) {
    companion object {
        val fakeMessages = listOf(
            ChatMessage("Hello!", isSent = true),
            ChatMessage("Hi there!", isSent = false, status = MessageStatus.DOUBLE_TICK),
            ChatMessage(
                "How are you doing?",
                isSent = false,
                status = MessageStatus.DOUBLE_TICK
            ),
            ChatMessage(
                "I'm great, thanks for asking!",
                isSent = true,
                hasReply = true,
                replyMessage = "How are you doing?",
                replyPerson = "Parth Takkar"
            ),
            ChatMessage("What's the weather like today?", isSent = true),
            ChatMessage(
                "It's sunny and warm, perfect for outdoor activities.",
                isSent = false,
                status = MessageStatus.SEEN
            ),
            ChatMessage("That sounds lovely! Let's plan something.", isSent = true),
            ChatMessage(
                "Sure, where would you like to go?",
                isSent = false,
                status = MessageStatus.DOUBLE_TICK
            ),
            ChatMessage(
                "How about the park?",
                isSent = false,
                status = MessageStatus.DOUBLE_TICK
            ),
            ChatMessage("Great! I'll see you there at 3 PM.", isSent = true, hasReply = true,
                replyMessage = "How about the park?",
                replyPerson = "Parth Takkar")
        )
    }
}

enum class MessageStatus {
    SENT, // Single tick
    DOUBLE_TICK, // Double tick
    SEEN // Seen (Read)
}
