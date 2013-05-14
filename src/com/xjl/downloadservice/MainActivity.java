package com.xjl.downloadservice;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

/**
 * 
 * @author BillKalin 主界面
 */
public class MainActivity extends Activity {

	private Intent service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		service = new Intent(this, DownloadService.class);
	}

	/* 启动服务 */
	public void startService(View v) {
		/* 在启动服务时，先检测SD卡中是否有该文件 */
		service.putExtra("url",
				"http://dl.dc.ijinshan.com/bd/kBatteryDoctor_new.apk");
		startService(service);
	}

	/* 停止服务 */
	public void stopService(View v) {
		service.putExtra(
				"url",
				"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V8.8.2.266_Android_pf139_(Build13050715).apk");
		startService(service);
	}
 
	/* 绑定服务 */
	public void boundService(View v) {
		service.putExtra(
				"url",
				"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V8.8.3.278_Android_pf151_(Build13040218).apk");
		startService(service);
	}

	/* 解除服务 */
	public void unboundService(View v) {
		service.putExtra(
				"url",
				"http://wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V9.0.1.284_ip5.x_pf42_(Build13041018).ipa");
		startService(service);
	}
}
