package common.lib.utils.permission;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionPreferenceUtil {

    private static final String PRE_NAME = "android_permissions_manager";

    private static final String HAS_ASKED_FOR_CAMERA_KEY          = "has_asked_for_camera";
    private static final String HAS_ASKED_FOR_LOCATION_KEY        = "has_asked_for_location";
    private static final String HAS_ASKED_FOR_AUDIO_RECORDING_KEY = "has_asked_for_audio_recording";
    private static final String HAS_ASKED_FOR_CALENDAR_KEY        = "has_asked_for_calendar";
    private static final String HAS_ASKED_FOR_CONTACTS_KEY        = "has_asked_for_contacts";
    private static final String HAS_ASKED_FOR_CALLING_KEY         = "has_asked_for_calling";
    private static final String HAS_ASKED_FOR_STORAGE_KEY         = "has_asked_for_storage";
    
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private static PermissionPreferenceUtil mInstance;
    
    private PermissionPreferenceUtil(Context context) {
    	mSharedPreferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    	editor = mSharedPreferences.edit();
    }

	protected static PermissionPreferenceUtil init(Context context) {
        if(mInstance == null) {
        	mInstance = new PermissionPreferenceUtil(context);
        }
        return mInstance;
    }

    protected void tearDown() {
        mInstance = null;
    }

    protected static PermissionPreferenceUtil get() {
        return mInstance;
    }

    // ==== CAMERA =================================================================================

    public void setCameraPermissionsAsked() {
        editor.putBoolean(HAS_ASKED_FOR_CAMERA_KEY, true);
        editor.commit();
    }

    public boolean isCameraPermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_CAMERA_KEY, false);
    }

    // ===== LOCATION ==============================================================================

    public void setLocationPermissionsAsked() {
    	editor.putBoolean(HAS_ASKED_FOR_LOCATION_KEY, true);
        editor.commit();
    }

    public boolean isLocationPermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_LOCATION_KEY, false);
    }

    // ===== AUDIO RECORDING =======================================================================

    public void setAudioPermissionsAsked() {
    	editor.putBoolean(HAS_ASKED_FOR_AUDIO_RECORDING_KEY, true);
    	editor.commit();
    }

    public boolean isAudioPermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_AUDIO_RECORDING_KEY, false);
    }

    // ===== CALENDAR ==============================================================================

    public void setCalendarPermissionsAsked() {
    	editor.putBoolean(HAS_ASKED_FOR_CALENDAR_KEY, true);
        editor.commit();
    }

    public boolean isCalendarPermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_CALENDAR_KEY, false);
    }

    // ===== CONTACTS ==============================================================================

    public void setContactsPermissionsAsked() {
    	editor.putBoolean(HAS_ASKED_FOR_CONTACTS_KEY, true);
        editor.commit();
    }

    public boolean isContactsPermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_CONTACTS_KEY, false);
    }

    // ===== CALLING ==============================================================================

    public void setCallingPermissionsAsked() {
    	editor.putBoolean(HAS_ASKED_FOR_CALLING_KEY, true);
        editor.commit();
    }

    public boolean isCallingPermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_CALLING_KEY, false);
    }

    // ===== STORAGE ===============================================================================

    public void setStoragePermissionsAsked() {
    	editor.putBoolean(HAS_ASKED_FOR_STORAGE_KEY, true);
        editor.commit();
    }

    public boolean isStoragePermissionsAsked() {
        return mSharedPreferences.getBoolean(HAS_ASKED_FOR_STORAGE_KEY, false);
    }

    // ===== TESTING ===============================================================================

    public void clearData() {
    	editor.putBoolean(HAS_ASKED_FOR_CAMERA_KEY, false);
        editor.commit();
        editor.putBoolean(HAS_ASKED_FOR_LOCATION_KEY, false);
        editor.commit();
        editor.putBoolean(HAS_ASKED_FOR_AUDIO_RECORDING_KEY, false);
        editor.commit();
        editor.putBoolean(HAS_ASKED_FOR_CALENDAR_KEY, false);
        editor.commit();
        editor.putBoolean(HAS_ASKED_FOR_CONTACTS_KEY, false);
        editor.commit();
        editor.putBoolean(HAS_ASKED_FOR_CALLING_KEY, false);
        editor.commit();
        editor.putBoolean(HAS_ASKED_FOR_STORAGE_KEY, false);
        editor.commit();
    }
}