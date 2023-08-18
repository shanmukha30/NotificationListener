package com.example.notificationlistener

import android.app.Notification
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class NotificationData(val appName: String, val notificationText: String, val timestamp: Long)

public var notificationListProperty = mutableListOf<NotificationData>()

class MyNotificationListener : NotificationListenerService() {
    //public val notificationList = mutableListOf<NotificationData>()

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val appName = sbn.packageName

        /*val pm: PackageManager = applicationContext.packageManager
        var ai: ApplicationInfo? = null
        try {
            ai = pm.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            ai = null
        }
        val appName = if (ai != null) pm.getApplicationLabel(ai) as String else "(unknown)"*/

        val notificationText = sbn.notification?.extras?.getString(Notification.EXTRA_TEXT)?: ""

        val timestamp = sbn.postTime



        val newNotification = NotificationData(appName,notificationText,timestamp)
        notificationListProperty.add(0,newNotification)
        Log.i("App Name", notificationListProperty[0].appName)
        Log.i("Message", notificationListProperty[0].notificationText)
    }

}


/*@Preview
@Composable
fun PreviewItem() {
    Column {
        getNotificationList().map { item ->
            NotificationList(appName = item.appName, message = item.notificationText)
        }
    }
}*/


@Composable
fun NotificationList(appName: String, message: String) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.absolutePadding(left = 10.dp, right = 20.dp, top = 5.dp, bottom = 5.dp)
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)) {
            Image(painter = painterResource(id = R.drawable.baseline_notifications_24),
                contentDescription = "Notification Icon",
                modifier = Modifier
                    .size(30.dp)
                    .absolutePadding(left = 0.dp, top = 2.dp, right = 5.dp, bottom = 2.dp)
                    .weight(.2f)
            )
            NotificationDescription(appName, message, Modifier.weight(.8f))
        }

    }
}

@Composable
public fun NotificationDescription(appName: String, message: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = appName,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = message,
            fontFamily = FontFamily.SansSerif
        )
    }
}


