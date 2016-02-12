package smart.tuke.sk.todolist.scheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Matúš on 11.2.2016.
 */
public class Alarm extends WakefulBroadcastReceiver
{
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent service = new Intent(context, SampleSchedulingService.class);
		startWakefulService(context, service);
	}

	public void setAlarm(Context context, int hour, int minutes, Date date)
	{
		alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, Alarm.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
		calendar.set(Calendar.MONTH, date.getMonth());
		calendar.set(Calendar.YEAR, date.getYear());
	}
}
