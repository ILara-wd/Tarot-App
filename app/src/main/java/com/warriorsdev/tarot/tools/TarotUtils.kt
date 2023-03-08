package com.warriorsdev.tarot.tools

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import com.warriorsdev.tarot.MainActivity
import com.warriorsdev.tarot.R
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random

object TarotUtils {

    var ARGS_ITEM_CARD = "tarotCards"

    fun getResourceByName(nameImage: String): Int = when (nameImage) {
        "el_mago.jpg" -> R.drawable.el_mago
        "el_loco.jpg" -> R.drawable.el_loco
        "la_sacerdotisa.jpg" -> R.drawable.la_sacerdotisa
        "la_emperatriz.jpg" -> R.drawable.la_emperatriz
        "el_emperador.jpg" -> R.drawable.el_emperador
        "el_sumo_sacerdote.jpg" -> R.drawable.el_sumo_sacerdote
        "los_enamorados.jpg" -> R.drawable.los_enamorados
        "el_carro.jpg" -> R.drawable.el_carro
        "la_justicia.jpg" -> R.drawable.la_justicia
        "hermitano.jpg" -> R.drawable.hermitano
        "rueda_de_la_fortuna.jpg" -> R.drawable.rueda_de_la_fortuna
        "la_fuerza.jpg" -> R.drawable.la_fuerza
        "el_colgado.jpg" -> R.drawable.el_colgado
        "la_muerte.jpg" -> R.drawable.la_muerte
        "la_templanza.jpg" -> R.drawable.la_templanza
        "el_diablo.jpg" -> R.drawable.el_diablo
        "la_torre.jpg" -> R.drawable.la_torre
        "la_estrella.jpg" -> R.drawable.la_estrella
        "la_luna.jpg" -> R.drawable.la_luna
        "el_sol.jpg" -> R.drawable.el_sol
        "el_juicio.jpg" -> R.drawable.el_juicio
        "el_mundo.jpg" -> R.drawable.el_mundo
        else -> R.drawable.card_mini
    }

    fun readFile(context: Context, fileName: String): String {
        val string: String? = try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        return string.toString()
    }


    fun showNotification(activity: Activity, description: String) {

        var builder: Notification.Builder? = null
        val notificationChannel: NotificationChannel
        val notificationManager: NotificationManager =
            activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(activity, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(
                    activity.getString(R.string.app_notification_chanel_id),
                    activity.getString(R.string.app_notification),
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(
                activity,
                activity.getString(R.string.app_notification_chanel_id)
            )
                .setContentTitle(activity.getString(R.string.app_name))
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        activity.resources, R.mipmap.ic_launcher
                    )
                ).setContentIntent(pendingIntent)
        }
        notificationManager.notify(Random(100).nextInt(), builder?.build())
    }

}
