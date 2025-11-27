package com.wannacry.tngassessment.presentation.screen.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.wannacry.tngassessment.config.Constants.AVATAR_URL
import com.wannacry.tngassessment.config.Constants.EXTEND_URL_BACKGROUND
import com.wannacry.tngassessment.config.Constants.EXTEND_URL_NAME
import com.wannacry.tngassessment.config.Constants.GOOGLE_MAP_PACKAGE
import com.wannacry.tngassessment.config.Constants.GOOGLE_MAP_URL
import com.wannacry.tngassessment.config.Constants.RANDOM
import com.wannacry.tngassessment.config.Constants.WEBSITE_BASE_URL
import com.wannacry.tngassessment.domain.data.User

@Composable
fun UserItem(user: User) {
    val context = LocalContext.current
    val avatarName = user.username.takeIf { !it.isNullOrBlank() } ?: user.name
    val avatarUrl = "$AVATAR_URL$EXTEND_URL_NAME$avatarName&$EXTEND_URL_BACKGROUND$RANDOM"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "user avatar",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = user.name ?: "Unknown",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "@${user.username ?: "Unknown"}",
                    fontStyle = FontStyle.Italic
                )
            }
        }
        Text(
            "User Information",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
                .fillMaxWidth()
        )
        Row(modifier = Modifier.padding(horizontal = 4.dp)) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        start = 16.dp,
                        end = 4.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(text = "Email")
                Text(text = "Phone No")
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        horizontal = 4.dp,
                        vertical = 8.dp
                    )
            ) {
                Text(text = ": ${user.email ?: "-"}")
                Text(text = ": ${user.phone ?: "-"}")
            }
        }
        Text(
            "Company Information",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 4.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        start = 16.dp,
                        end = 4.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(text = "Company")
                Text(text = "Website")
                Text(text = "Location")
                Text(text = "Address")
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        horizontal = 4.dp,
                        vertical = 8.dp
                    )
            ) {
                    Text(text = ": ${user.companyName ?: "-"}")
                    Text(
                        text = ": ${user.website ?: "-"}",
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            goToWebsite(user.website, context)
                        }
                    )
                    Text(
                        text = ": ${user.location ?: "-"}",
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            openGoogleMap(
                                lat = user.address?.geo?.lat,
                                lang = user.address?.geo?.lng,
                                context = context
                            )
                        }
                    )
                    Text(text = ": ${user.fullAddress ?: "-"}")
                }
            }
        }
    }

fun openGoogleMap(lat: String?, lang: String?, context: Context) {
    val intentUri =
        "geo:$lat,$lang?q=$lat,$lang".toUri()

    val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
    mapIntent.setPackage(GOOGLE_MAP_PACKAGE)

    try {
        context.startActivity(mapIntent)
    } catch (e: Exception) {
        val fallbackIntent = Intent(
            Intent.ACTION_VIEW,
            "$GOOGLE_MAP_URL$lat,$lang".toUri()
        )
        context.startActivity(fallbackIntent)
    }
}

fun goToWebsite(website: String?, context: Context) {
    val intent =
        Intent(Intent.ACTION_VIEW, "$WEBSITE_BASE_URL${website}".toUri())
    context.startActivity(intent)
}