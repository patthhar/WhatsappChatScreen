package me.darthwithap.android.whatsappchatscreen.chat_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.darthwithap.android.whatsappchatscreen.R
import me.darthwithap.android.whatsappchatscreen.ui.theme.backgroundWhite
import me.darthwithap.android.whatsappchatscreen.ui.theme.darkGreen

@Composable
fun InputRow(
    newMessage: String,
    isReplyingToMessage: Boolean = false,
    onMessageChange: (String) -> Unit,
    onMessageSend: (Boolean, String, String) -> Unit,
    personName: String,
    yourReplyMessage: Boolean = false,
    replyMessage: String = "",
    onReplyClose: () -> Unit
) {
    val replyPerson = if (yourReplyMessage) "You" else personName
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            if (isReplyingToMessage) {
                ReplyBox(
                    modifier = Modifier.fillMaxWidth(),
                    message = replyMessage,
                    isEditing = true,
                    name = replyPerson,
                    onReplyClose = onReplyClose
                )
            }
            TextField(
                value = newMessage,
                placeholder = {
                    Text(
                        text = stringResource(R.string.message),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )
                },
                maxLines = 5,
                onValueChange = onMessageChange,
                keyboardActions = KeyboardActions(
                    onSend = { onMessageSend(isReplyingToMessage, replyMessage, replyPerson) }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(painterResource(R.drawable.emoji), contentDescription = null)
                    }
                },
                trailingIcon = {
                    Row {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(painterResource(R.drawable.clip), contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(painterResource(R.drawable.camera), contentDescription = null)
                        }
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .background(backgroundWhite)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            onClick = { onMessageSend(isReplyingToMessage, replyMessage, replyPerson) },
            modifier = Modifier
                .weight(1f)
                .size(48.dp)
                .clip(CircleShape)
                .background(darkGreen)
                .padding(end = 2.dp)
                .align(Alignment.Bottom)
        ) {
            Icon(painterResource(R.drawable.send), contentDescription = null)
        }
    }
}
