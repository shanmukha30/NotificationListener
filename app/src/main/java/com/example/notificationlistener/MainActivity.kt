package com.example.notificationlistener

import android.app.AlertDialog
import android.app.Notification
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notificationlistener.ui.theme.NotificationListenerTheme



/*fun getNotificationList(): MutableList<NotificationData>
{
    //val list = mutableListOf<NotificationData>()
    notificationListProperty.add(NotificationData("Whatsapp", "Hello", 2151))
    notificationListProperty.add(NotificationData("LinkedIn", "Congratulation!",2233))

    return notificationListProperty
}*/




class MainActivity : ComponentActivity() {

    /*private val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    private val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"*/
    private lateinit var enableNotificationListenerAlertDialog: AlertDialog
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isNotificationServiceEnabled()) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog()
            enableNotificationListenerAlertDialog.show()
        }
        if(!notificationListProperty.isEmpty()) {
            Log.i("App Name", notificationListProperty.get(0).appName)
            Log.i("Message", notificationListProperty.get(0).notificationText)
        }

        /*handler.postDelayed(object : Runnable {
            override fun run() {*/
                setContent {
                    NotificationListenerTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                            MainList()
                        }
                    }
                }
                /*handler.postDelayed(this, 5000) // Repeat every 5 seconds
            }
        }, 2000)*/

    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver,"enabled_notification_listeners")
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":")
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.notification_listener_service)
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
        alertDialogBuilder.setPositiveButton(R.string.yes) { dialog, id ->
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { dialog, id ->
            // If you choose to not enable the notification listener,
            // the app will not work as expected
        }
        return alertDialogBuilder.create()
    }
}

@Composable
fun AppScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { /* Handle Clear All action here */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear All")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Add your list view or other content here
    }
}


@Preview
@Composable
fun MainList() {
    Column (modifier = Modifier.fillMaxSize().padding(16.dp)){
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
        ) {
            Button(
                onClick = { /*itemList = emptyList()*/ }, // Clear the list
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear All")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.absolutePadding(top = 25.dp, bottom = 15.dp, left = 2.dp, right = 2.dp), content = {
            items(notificationListProperty){ item ->
                NotificationList(appName = item.appName, message = item.notificationText)
            }
        })
    }

}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotificationListenerTheme {
        Greeting("Android")
    }
}*/