package com.jackfruit.mall.utils.permission;

import android.support.annotation.NonNull;

public interface OnActivityPermissionCallback {
	void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
