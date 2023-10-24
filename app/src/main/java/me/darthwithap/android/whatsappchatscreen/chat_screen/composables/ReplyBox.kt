package me.darthwithap.android.whatsappchatscreen.chat_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.darthwithap.android.whatsappchatscreen.ui.theme.lightWhite
import me.darthwithap.android.whatsappchatscreen.ui.theme.purple

@Composable
fun ReplyBox(
    modifier: Modifier = Modifier,
    message: String,
    name: String,
    isEditing: Boolean = false,
    onReplyClose: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(80.dp)
            .background(lightWhite)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                name,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = purple,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            if (isEditing) {
                IconButton(onClick = onReplyClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            message, textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color.Black,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}