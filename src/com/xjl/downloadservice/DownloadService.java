package com.xjl.downloadservice;

import java.util.HashMap;
import java.util.Map;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/*
 * ���ط���
 */
public class DownloadService extends Service {

	private NotificationManager manager;
	// private NotificationCompat.Builder builder;
	private String url;
	/* ��Ϣ���� */
	public static final int MSG_UPDATE = 0;// ���ظ���
	public static final int MSG_SUCCESS = 1;// ���سɹ�
	public static final int MSG_FAILED = 2;// ����ʧ��
	Map<Integer, NotificationCompat.Builder> notifications;// ֪ͨ����

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notifications = new HashMap<Integer, NotificationCompat.Builder>();
		Log.i("Service", "onCreate()");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Service", "onDestroy()");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("Service", "onStartCommand()");
		url = intent.getStringExtra("url");
		Log.i("url", url + "--" + startId);
		addNotification(startId, getStr(url));
		/* �����߳����� */
		new DownloadThread(this, url, startId).start();
		return super.onStartCommand(intent, flags, startId);
	}

	private String getStr(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}

	/* ��ʾ���� */
	private void addNotification(int id, String title) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle(title);
		builder.setContentText("��������..." + "0%");
		builder.setSmallIcon(android.R.drawable.ic_dialog_info);
		/* ���õ����Ϣʱ����ʾ�Ľ��� */
		Intent nextIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder task = TaskStackBuilder.create(this);
		task.addNextIntent(nextIntent);
		PendingIntent pengdingIntent = task.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pengdingIntent);
		builder.setProgress(100, 0, false);
		builder.setAutoCancel(true);
		builder.setTicker("������....");
		notifications.put(id, builder);
		manager.notify(id, builder.build());
	}

	// �������ؽ���
	private void updateNotification(int id, int progress) {
		NotificationCompat.Builder notification = notifications.get(id);
		notification.setContentText("��������..." + progress + "%");
		notification.setProgress(100, progress, false);
		manager.notify(id, notification.build());
	}

	/*��Ϣ����*/
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case DownloadService.MSG_UPDATE: {
				updateNotification(msg.arg1, msg.arg2);
			}
				break;

			case DownloadService.MSG_SUCCESS: {
				NotificationCompat.Builder notification = notifications
						.get(msg.arg1);
				notification.setTicker("�������!");
				notification.setContentText("�������!");
				manager.notify(msg.arg1, notification.build());
				notifications.remove(msg.arg1);
				if (notifications.isEmpty()) {// ȫ�������꣬��ֹͣ����
					stopSelf();
				}
			}
				break;
			case DownloadService.MSG_FAILED: {
				NotificationCompat.Builder notification = notifications
						.get(msg.arg1);
				notification.setContentTitle("����ʧ��");
				notification.setContentText("����������������!");
				manager.notify(msg.arg1, notification.build());
			}
				break;
			}
		}

	};

}
