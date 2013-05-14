package com.xjl.downloadservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

/*
 * 下载线程
 */
public class DownloadThread extends Thread {

	private String strUrl;
	private Context ctx;
	private int count = 0;
	private int totalSize = 0;// 下载文件总大小
	private int noticficationId;

	/*
	 * 构造函数 ctx:所在的上下文 strUrl:下载的地址 notificationId:通知的ID
	 */
	public DownloadThread(Context ctx, String strUrl, int notificationId) {
		this.strUrl = strUrl;
		this.ctx = ctx;
		this.noticficationId = notificationId;
	}

	/*
	 * 截取文件名
	 */
	private String getStr(String url) {
		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Log.i("state", getState().name());
		long completeSize = downloadFile();
		/** 如果下载完成，发送消息给服务 */
		if (completeSize >= totalSize) {
			sendMsg(DownloadService.MSG_SUCCESS, 0);
			
		}
	}

	// 下载文件
	public long downloadFile() {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream input = null;
		FileOutputStream out = null;
		long size = 0;
		try {
			url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			input = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/" + getStr(strUrl));
			Log.i("strurl", getStr(strUrl));
			out = new FileOutputStream(file);
			totalSize = conn.getContentLength();
			byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = input.read(buffer)) > 0) {
				size += length;
				out.write(buffer, 0, length);
				if ((count == 0) || (int) (size * 100 / totalSize) > count) {
					count += 1;
					Log.i("progress", size + "--" + totalSize);
					sendMsg(DownloadService.MSG_UPDATE,
							(int) (size * 100 / totalSize));
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendMsg(DownloadService.MSG_FAILED, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			sendMsg(DownloadService.MSG_FAILED, 0);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (conn != null) {
				conn.disconnect();
			}

			if (url != null) {
				url = null;
			}
		}
		return size;
	}

	/*
	 * 发送消息 what:消息类型 arg2:下载进度
	 */

	private void sendMsg(int what, int arg2) {
		Message msg = new Message();
		msg.what = what;// 消息类型
		msg.arg1 = noticficationId;// 通知ID
		msg.arg2 = arg2;// 下载进度
		((DownloadService) ctx).handler.sendMessage(msg);
	}

}
