package com.xjl.downloadservice;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

/**
 * 
 * @author BillKalin ������
 */
public class MainActivity extends Activity {

	private Intent service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		service = new Intent(this, DownloadService.class);
	}

	/* �������� */
	public void startService(View v) {
		/* ����������ʱ���ȼ��SD�����Ƿ��и��ļ� */
		service.putExtra("url",
				"http://dl.dc.ijinshan.com/bd/kBatteryDoctor_new.apk");
		startService(service);
	}

	/* ֹͣ���� */
	public void stopService(View v) {
		service.putExtra(
				"url",
				"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V8.8.2.266_Android_pf139_(Build13050715).apk");
		startService(service);
	}
 
	/* �󶨷��� */
	public void boundService(View v) {
		service.putExtra(
				"url",
				"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V8.8.3.278_Android_pf151_(Build13040218).apk");
		startService(service);
	}

	/* ������� */
	public void unboundService(View v) {
		service.putExtra(
				"url",
				"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V9.0.1.284_ip5.x_pf42_(Build13041018).ipa");
		startService(service);
	}
}
