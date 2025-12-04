package com.wannacry.tngassessment.presentation.screen.component

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.wannacry.tngassessment.config.Constants.GOOGLE_MAP_PACKAGE
import com.wannacry.tngassessment.config.Constants.GOOGLE_MAP_URL
import com.wannacry.tngassessment.config.Constants.WEBSITE_BASE_URL
import com.wannacry.tngassessment.domain.data.User

@Composable
fun UserItem(data: User, context: Context, avatarUrl: String?) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
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
                        text = data.name ?: "Unknown",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "@${data.username ?: "Unknown"}",
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "User Information",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            FieldRow(label = "Email", value = data.email ?: "-")
            FieldRow(label = "Phone No", value = data.phone ?: "-")
            FieldRow(
                label = "Website",
                value = data.website ?: "-",
                color = Color.Blue,
                onClick = { goToWebsite(data.website, context) }
            )
            FieldRow(
                label = "Location",
                value = data.location ?: "-",
                color = Color.Blue,
                onClick = {
                    openGoogleMap(
                        lat = data.address?.geo?.lat,
                        lang = data.address?.geo?.lng,
                        context = context
                    )
                }
            )
            FieldRow(label = "Address", value = data.fullAddress ?: "-")

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Company Information",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            FieldRow(label = "Name", value = data.companyName ?: "-")
            FieldRow(label = "Business", value = data.company?.bs ?: "-")
            FieldRow(label = "Tagline", value = data.company?.catchPhrase ?: "-")
        }
    }
}

@Composable
fun FieldRow(
    label: String,
    value: String,
    color: Color = Color.Unspecified,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.width(120.dp),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = ": ",
            modifier = Modifier.wrapContentWidth(),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = color,
            modifier = Modifier
                .weight(1f)
                .clickable(enabled = onClick != null) {
                    onClick?.invoke()
                }
        )
    }
}

fun openGoogleMap(lat: String?, lang: String?, context: Context) {
    val intentUri = "geo:$lat,$lang?q=$lat,$lang".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, intentUri).apply {
        setPackage(GOOGLE_MAP_PACKAGE)
    }
    try {
        context.startActivity(mapIntent)
    } catch (e: Exception) {
        context.startActivity(Intent(
            Intent.ACTION_VIEW,
            "$GOOGLE_MAP_URL$lat,$lang".toUri()
        ))
    }
}

fun goToWebsite(website: String?, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, "$WEBSITE_BASE_URL${website}".toUri())
    context.startActivity(intent)
}
