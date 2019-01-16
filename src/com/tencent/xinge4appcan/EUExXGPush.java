package com.tencent.xinge4appcan;

import java.util.Arrays;

import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.engine.universalex.EUExCallback;

import android.app.NotificationManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

public class EUExXGPush extends EUExBase {

	public static final String TAG = EUExXGPush.class.getSimpleName();

	public static String cbRegister = "uexXGPush.onRegisterCb";
	public static String cbUnregister = "uexXGPush.onUnregisterCb";
	public static String cbSetTag = "uexXGPush.onSetTagCb";
	public static String cbDelTag = "uexXGPush.onDelTagCb";
	public static String cbTextMessage = "uexXGPush.onTextMessageCb";
	public static String cbNotificationShowed = "uexXGPush.onNotificationShowedCb";
	public static String cbNotificationClicked = "uexXGPush.onNotificationCilckedCb";
	
	static EUExXGPush instance = null;

	void onSetTagCallback(int code, String tagname) {
		jsCallback(cbSetTag, code, EUExCallback.F_C_TEXT, tagname);
	}

	void onDelTagCallback(int code, String tagname) {
		jsCallback(cbDelTag, code, EUExCallback.F_C_TEXT, tagname);
	}

	void onTextMessageCallback(String data) {
		jsCallback(cbTextMessage, 0, EUExCallback.F_C_JSON, data);
	}

	void onNotificationShowedCallback(String data) {
		jsCallback(cbNotificationShowed, 0, EUExCallback.F_C_JSON, data);
	}

	void onNotificationClickedCallback(String data) {
		jsCallback(cbNotificationClicked, 0, EUExCallback.F_C_JSON, data);
	}

	public EUExXGPush(Context context, EBrowserView view) {
		super(context, view);
		instance = this;
	}

	@Override
	protected boolean clean() {
		return true;
	}

	private boolean checkParms(String[] parm, int requestedSize) {
		if (parm == null || parm.length < requestedSize) {
			return false;
		}
		return true;
	}

	public void enableDebug(String[] parm) {
		Log.d(TAG, "enableDebug:" + Arrays.asList(parm));
		if (!checkParms(parm, 1)) {
			return;
		}
		boolean debug = Boolean.valueOf(parm[0]);
		XGPushConfig.enableDebug(mContext, debug);
	}

	public void setAccessidAndKey(String[] parm) {
		Log.d(TAG, "setAccessidAndKey:" + Arrays.asList(parm));
		
		if (!checkParms(parm, 2)) {
			return;
		}
		long accessid = Long.valueOf(parm[0]);
		String accesskey = parm[1];
		XGPushConfig.setAccessId(mContext, accessid);
		XGPushConfig.setAccessKey(mContext, accesskey);
	}

	public void registerPush(String[] parm) {
		Log.d(TAG, "registerPush:" + Arrays.asList(parm));
		String account = null;
		if (parm != null && parm.length > 0) {
			account = parm[0];
		}
		XGIOperateCallback cb = new XGIOperateCallback() {

			@Override
			public void onFail(Object arg0, int arg1, String arg2) {
				jsCallback(cbRegister, arg1, EUExCallback.F_C_TEXT, arg2);
			}

			@Override
			public void onSuccess(Object token, int arg1) {
				jsCallback(cbRegister, 0, EUExCallback.F_C_TEXT, (String) token);
			}

		};
		if (TextUtils.isEmpty(account)) {
			XGPushManager.registerPush(mContext, cb);
		} else {
			XGPushManager.registerPush(mContext, account, cb);
		}
	}

	public void unregisterPush(String[] parm) {
		Log.d(TAG, "unregisterPush:" + Arrays.asList(parm));
		XGIOperateCallback cb = new XGIOperateCallback() {

			@Override
			public void onFail(Object arg0, int arg1, String arg2) {
				jsCallback(cbUnregister, arg1, EUExCallback.F_C_TEXT, arg2);
			}

			@Override
			public void onSuccess(Object arg0, int arg1) {
				jsCallback(cbUnregister, 0, EUExCallback.F_C_TEXT, "");
			}

		};
		XGPushManager.unregisterPush(mContext, cb);
	}

	public void setTag(String[] parm) {
		Log.d(TAG, "setTag:" + Arrays.asList(parm));
		if (!checkParms(parm, 1)) {
			return;
		}
		String tag = parm[0];
		if (!TextUtils.isEmpty(tag)) {
			XGPushManager.setTag(mContext, tag);
		}
	}

	public void delTag(String[] parm) {
		Log.d(TAG, "delTag:" + Arrays.asList(parm));
		if (!checkParms(parm, 1)) {
			return;
		}
		String tag = parm[0];
		if (!TextUtils.isEmpty(tag)) {
			XGPushManager.setTag(mContext, tag);
		}
	}

	public void cancelNotification(String[] parm) {
		Log.d(TAG, "cancelNotification:" + Arrays.asList(parm));
		int id = -1;
		if (parm != null && parm.length > 0) {
			id = Integer.valueOf(parm[0]);
		}
		NotificationManager nm = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (id == -1) {
			nm.cancelAll();
		} else {
			nm.cancel(id);
		}
	}
}
