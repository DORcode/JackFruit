package common.lib.utils.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import common.lib.Constants;
import common.lib.R;

/**
 * @ClassName: PermissionHelper
 * @Description:
 * @author kh
 * @date 2016-6-14 下午4:20:27
 *
 */
public class PermissionHelper {
	private static final String TAG = "PermissionHelper";
	private Activity context;
	private static PermissionHelper instance;

	private PermissionHelper(Activity context) {
		this.context = context;
	}
	
	public static PermissionHelper getInstance(Activity context) {
		if(instance == null) {
			instance = new PermissionHelper(context);
		}
		return instance;
	}
	
	public void cameraPermissionCheck() {
		if(!PermissionsManager.get().isCameraGranted()) {//没有相机权限
			if(!PermissionsManager.get().hasAskedForCameraPermission()) {//没有申请相机权限
				PermissionsManager.get().requestCameraPermission(context);
			} else {//申请过相机权限
				if(PermissionsManager.get().neverAskForCamera(context)) {//勾选不再询问
					 permissionDialog(Constants.PERMISSION_CAMERA_MESSAGE);
				} else {//未勾选
					PermissionsManager.get().requestCameraPermission(context);
				}
			}
		}
	}
	
	public void audioPermissionCheck() {
		if(!PermissionsManager.get().isAudioRecordingGranted()) {//没有麦克风权限
			if(!PermissionsManager.get().hasAskedForAudioRecordingPermission()) {//没有申请麦克风权限
				PermissionsManager.get().requestAudioRecordingPermission(context);
			} else {//申请过麦克风权限
				if(PermissionsManager.get().neverAskForAudio(context)) {//勾选不再询问
					 permissionDialog(Constants.PERMISSION_AUDIO_MESSAGE);
				} else {//未勾选
					PermissionsManager.get().requestAudioRecordingPermission(context);
				}
			}
		}
	}
	
	public void storagePermissionCheck() {

		if(!PermissionsManager.get().isStorageGranted()) {//没有存储权限
			if(!PermissionsManager.get().hasAskedForStoragePermission()) {//没有申请过存储权限
				PermissionsManager.get().requestStoragePermission(context);
			} else {//申请过存储权限
				if(PermissionsManager.get().neverAskForStorage(context)) {//勾选不再询问
					 permissionDialog(Constants.PERMISSION_STORAGE_MESSAGE);
				} else {//未勾选
					PermissionsManager.get().requestStoragePermission(context);
				}
			}
		}
	}

	public void locationPermissionCheck() {

		if(!PermissionsManager.get().isLocationGranted()) {//没有定位权限
			if(!PermissionsManager.get().hasAskedForLocationPermission()) {//没有申请过定位权限
				PermissionsManager.get().requestLocationPermission(context);
			} else {//申请过定位权限
				if(PermissionsManager.get().neverAskForLocation(context)) {//勾选不再询问
					permissionDialog(Constants.PERMISSION_LOCATION_MESSAGE);
				} else {//未勾选
					PermissionsManager.get().requestLocationPermission(context);
				}
			}
		}
	}
	
	public void permissionDialog(String message) {
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(false);
		
		dialog.show();
		
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_permission_apply);
		LinearLayout ll_title = (LinearLayout) window.findViewById(R.id.ll_title);
		ll_title.setVisibility(View.VISIBLE);
		TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
		TextView tv_info = (TextView) window.findViewById(R.id.tv_content1);
		tv_info.setText(message);
		TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel);
		TextView tv_comfirm = (TextView) window.findViewById(R.id.tv_comfirm);
        tv_comfirm.setVisibility(View.VISIBLE);
        tv_comfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
				dialog.dismiss();
			}
		});
        
        tv_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
           	 	context.finish();
           	 	System.exit(0);
			}
		});
	}
}
