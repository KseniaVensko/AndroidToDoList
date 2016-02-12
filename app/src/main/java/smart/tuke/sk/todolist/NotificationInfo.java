package smart.tuke.sk.todolist;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Steve on 12.2.2016.
 */
public class NotificationInfo
{
	public static void showNotification(Context context, String title, String text)
	{
		NotificationCompat.Builder notification =
			new NotificationCompat.Builder(context)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle(title)
				.setContentText(text);

		NotificationManager notificationManager =
			(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, notification.build());
	}
}
