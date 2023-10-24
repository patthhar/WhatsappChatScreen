package me.darthwithap.android.whatsappchatscreen.chat_screen.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.darthwithap.android.whatsappchatscreen.R
import me.darthwithap.android.whatsappchatscreen.ui.theme.darkGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    hasAvatar: Boolean,
    @DrawableRes avatarId: Int = 0,
    name: String,
    subtitle: String? = null,
    onBackClick: () -> Unit,
    onAvatarClick: () -> Unit,
    onCallClick: () -> Unit,
    onShowOptionsMenu: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.background(darkGreen),
        title = {
            Row {
                val avatar = if (hasAvatar) avatarId else R.drawable.default_profile
                Image(
                    painter = painterResource(avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable { onAvatarClick() }
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black // Customize text color as needed
                    )
                    subtitle?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            maxLines = 1,
                            color = Color.Gray
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onCallClick) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(horizontal = 4.dp),
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call"
                )
            }
            IconButton(onClick = onShowOptionsMenu) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 8.dp),
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.options_menu)
                )
            }
        }
    )
}