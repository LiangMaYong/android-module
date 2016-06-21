package com.androidmodule.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * LNotificaiton
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMNotificaiton {
	private Context mContext;

	private AMNotificaiton(Context context) {
		mContext = context;
	}

	private static class NotificationHolder {
		static final AMNotificaiton getNotification(Context context) {
			return new AMNotificaiton(context);
		}
	}

	public static AMNotificaiton getInstance(Context content) {
		return NotificationHolder.getNotification(content);
	}

	public void cancel(int id) {
		try {
			NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancel(id);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("deprecation")
	public void showNotification(int id, int smallIcon, String notifi, String title, String content, Intent intent) {
		NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification();
		n.tickerText = notifi;
		n.icon = smallIcon;
		n.when = System.currentTimeMillis();
		n.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		n.contentIntent = contentIntent;
		nm.notify(id, n);
	}

	@SuppressWarnings("deprecation")
	public void showNotificationOne(int id, int smallIcon, String notifi, String title, String content, Intent intent) {
		NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification();
		n.tickerText = notifi;
		n.icon = smallIcon;
		n.defaults |= Notification.DEFAULT_SOUND;
		n.flags |= Notification.FLAG_AUTO_CANCEL;
		n.when = System.currentTimeMillis();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		n.contentIntent = contentIntent;
		nm.notify(id, n);
	}

}
