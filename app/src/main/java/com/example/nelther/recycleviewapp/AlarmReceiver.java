package com.example.nelther.recycleviewapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nelther on 10/11/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar now = GregorianCalendar.getInstance();
        Log.i("Moviles","Alarmando");
        Toast.makeText(context, "Tu lógica de negocio irá aquí. En caso de requerir más de unos milisegundos, debería de la tarea a un servicio", Toast.LENGTH_LONG).show();
        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Tareapp")
                        .setContentText("Tienes Tarea");
        Intent resultIntent = new Intent(context, InicioActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(InicioActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
