package com.caracrepair.app.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.caracrepair.app.R
import com.caracrepair.app.consts.ServiceStatusConst
import com.caracrepair.app.presentation.servicedetail.ServiceDetailActivity
import com.caracrepair.app.service.consts.FcmNotificationType
import com.caracrepair.app.utils.GsonUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FcmNotificationService : FirebaseMessagingService() {
    companion object {
        private const val KEY_NOTIFICATION_TITLE = "title"
        private const val KEY_NOTIFICATION_CONTENT = "message"
        private const val KEY_NOTIFICATION_TYPE = "notification_type"
    }

    private var notificationManager: NotificationManager? = null
    private val generalPreferences by lazy { GeneralPreference(applicationContext, GsonUtil()) }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.notification_channel_id),
                "Alex AC Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager?.createNotificationChannel(channel)
        }

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val data = remoteMessage.data
        val notificationTitle = data[KEY_NOTIFICATION_TITLE]
        var notificationContent = data[KEY_NOTIFICATION_CONTENT]
        val notificationType = data[KEY_NOTIFICATION_TYPE]
        if (notificationTitle == null || generalPreferences.getUser() == null) return

        if (notificationType == FcmNotificationType.SERVICE_STATUS_CHANGED) {
            val serviceId = data["order_id"] ?: return
            val serviceStatus = data["order_status"].orEmpty()
            val serviceType = data["service_type"].orEmpty()

            notificationContent = ServiceStatusConst.getDescription(serviceStatus, serviceType).takeIf {
                ServiceStatusConst.isShowStaticDescription(serviceStatus)
            } ?: notificationContent
            val intent = ServiceDetailActivity.createIntent(applicationContext, serviceId)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            )
            val builder = NotificationCompat.Builder(applicationContext, getString(R.string.notification_channel_id))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            notificationManager?.notify(1, builder.build())
        }
    }
}