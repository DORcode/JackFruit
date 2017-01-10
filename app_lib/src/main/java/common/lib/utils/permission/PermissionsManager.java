package common.lib.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
import static android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS;

/**
 * PermissionsManager is the point of entry to finding about and requesting permissions.
 *
 * This class helper methods for the following permissions:
 * <ul>
 * <li>Camera</li>
 * <li>Location</li>
 * <li>Audio Recording</li>
 * <li>Calendar</li>
 * <li>Contacts</li>
 * <li>Storage</li>
 * <li>Phone Call</li>
 * </ul>
 *
 * All other permissions can be requested. But it requires more management from the developer.
 * However they can easily be added in the future.
 *
 * The method {@link #intentToAppSettings(Activity)} can be used to open the app's settings
 * to turn on a permission if the user checked "Never ask again".
 */
public class PermissionsManager {

    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check camera permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_CAMERA_PERMISSION          = 0x01;
    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check location permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_LOCATION_PERMISSION        = 0x02;
    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check audio recording permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_AUDIO_RECORDING_PERMISSION = 0x03;
    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check calendar permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_CALENDAR_PERMISSION        = 0x04;
    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check contacts permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_CONTACTS_PERMISSION        = 0x06;
    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check storage permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_STORAGE_PERMISSION         = 0x08;
    /**
     * Request code to use in your {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * or {@link Fragment#onRequestPermissionsResult(int, String[], int[])} to check phone call permission
     * was granted. You can also easily check in the {@link Activity#onResume()} or {@link Fragment#onResume()}
     */
    public static final int REQUEST_CALL_PHONE_PERMISSION      = 0x09;

    private static PermissionsManager    mInstance;
    private final  Context               mContext;
    private        MockSystemPermissions mMockSystemPermissions;

    /**
     * Instantiate the PermissionsManager. This should be done in the Application subclass or
     * another singleton.
     *
     * @param context this should be the Application Context
     */
    public static void init(Context context) {
        if (mInstance == null) {
        	mInstance = new PermissionsManager(context);
        }
        
        PermissionPreferenceUtil.init(context);
    }

    private PermissionsManager(Context context) {
        mContext = context;
    }

    /**
     * Entry point for the PermissionsManager. Gets a singleton that was instantiate in
     * the {@link #init} method.
     *
     * @return PermissionsManager instance
     */
    public static PermissionsManager get() {
        return mInstance;
    }

    // ==== CAMERA PERMISSION ======================================================================

    /**
     * @return if we have the camera permission
     */
    public boolean isCameraGranted() {
        /*if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(CAMERA);
        }*/
        return ContextCompat.checkSelfPermission(mContext, CAMERA)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#CAMERA}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CAMERA_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestCameraPermission(@NonNull Fragment fragment) {
        PermissionPreferenceUtil.get()
                .setCameraPermissionsAsked();
        requestPermission(fragment, REQUEST_CAMERA_PERMISSION, CAMERA);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#CAMERA}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CAMERA_PERMISSION}
     *
     * @param activity asking for the permission
     */
    public void requestCameraPermission(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setCameraPermissionsAsked();
        requestPermission(activity,
        		REQUEST_CAMERA_PERMISSION,
                          CAMERA);
    }

    /**
     * @return if this app has previously asked for the camera permission.
     */
    public boolean hasAskedForCameraPermission() {
        return PermissionPreferenceUtil.get()
                       .isCameraPermissionsAsked();
    }

    /**
     * @param fragment calling this method
     * @return If the user has seen the permission before and denied it.
     */
    @VisibleForTesting
    protected boolean shouldShowCameraRationale(@NonNull Fragment fragment) {
        return (!isCameraGranted()
                && shouldShowRequestPermissionRationale(fragment, CAMERA));
    }

    /**
     * @param activity calling this method
     * @return If the user has seen the permission before and denied it.
     */
    private boolean shouldShowCameraRationale(@NonNull Activity activity) {
        return (!isCameraGranted()
                && shouldShowRequestPermissionRationale(activity, CAMERA));
    }

    /**
     * If true the user has checked the "Never ask again" option. We get this by checking two things.
     * Whether we've asked before, checked with {@link #hasAskedForCameraPermission()}, which returns
     * false if the user has never seen the dialog. We also check whether we should request the permission
     * rational for the system. If these two don't match up, the user has selected "Never ask again".
     *
     * <b>Note: if we have the camera permission, and we call this method, it will return true.
     * This is intentional as we don't want to ask for permissions once we have them. If you
     * do that, you will loose the permission and dialog will come up again.</b>
     *
     * <b>Another note: if the user selected "Never ask again", then they give you permissions in
     * the app settings page, and then remove them in the same page. This method will return true.
     * Even though at that point you can ask for permissions. I have not been able to figure out a
     * way around this.</b>
     *
     * @param fragment asking for permission
     * @return whether the user has checked "Never ask again" option
     */
    public boolean neverAskForCamera(@NonNull Fragment fragment) {
        return !(hasAskedForCameraPermission() == shouldShowCameraRationale(fragment));
    }

    /**
     * If true the user has checked the "Never ask again" option. We get this by checking two things.
     * Whether we've asked before, checked with {@link #hasAskedForCameraPermission()}, which returns
     * false if the user has never seen the dialog. We also check whether we should request the permission
     * rational for the system. If these two don't match up, the user has selected "Never ask again".
     *
     * <b>Note: if we have the camera permission, and we call this method, it will return true.
     * This is intentional as we don't want to ask for permissions once we have them. If you
     * do that, you will loose the permission and dialog will come up again.</b>
     *
     * <b>Another note: if the user selected "Never ask again", then they give you permissions in
     * the app settings page, and then remove them in the same page. This method will return true.
     * Even though at that point you can ask for permissions. I have not been able to figure out a
     * way around this.</b>
     *
     * @param activity asking for permission
     * @return whether the user has checked "Never ask again" option
     */
    public boolean neverAskForCamera(@NonNull Activity activity) {
        return !(hasAskedForCameraPermission() == shouldShowCameraRationale(activity));
    }

    // ==== LOCATION PERMISSION ====================================================================

    /**
     * @return if we have location permissions
     */
    public boolean isLocationGranted() {
        return isFineLocationGranted()
               || isCoarseLocationGranted();
    }

    @VisibleForTesting
    protected boolean isFineLocationGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(ACCESS_FINE_LOCATION);
        }
        return ContextCompat.checkSelfPermission(mContext,
                                                 ACCESS_FINE_LOCATION)
               == PERMISSION_GRANTED;
    }

    @VisibleForTesting
    protected boolean isCoarseLocationGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(ACCESS_COARSE_LOCATION);
        }
        return ContextCompat.checkSelfPermission(mContext,
                                                 ACCESS_COARSE_LOCATION)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#ACCESS_FINE_LOCATION}</li>
     * <li>{@link Manifest.permission#ACCESS_COARSE_LOCATION}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_LOCATION_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestLocationPermission(@NonNull Fragment fragment) {
        PermissionPreferenceUtil.get()
                .setLocationPermissionsAsked();
        requestPermission(fragment,
                          REQUEST_LOCATION_PERMISSION,
                          ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#ACCESS_FINE_LOCATION}</li>
     * <li>{@link Manifest.permission#ACCESS_COARSE_LOCATION}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_LOCATION_PERMISSION}
     *
     * @param activity asking for the permission
     */
    public void requestLocationPermission(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setLocationPermissionsAsked();
        requestPermission(activity,
                          REQUEST_LOCATION_PERMISSION,
                          ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION);
    }

    /**
     * @return if location permission has been previously requested.
     */
    public boolean hasAskedForLocationPermission() {
        return PermissionPreferenceUtil.get()
                       .isLocationPermissionsAsked();
    }

    @VisibleForTesting
    protected boolean shouldShowLocationRationale(@NonNull Fragment fragment) {
        return (!isLocationGranted()
                && shouldShowRequestPermissionRationale(fragment, ACCESS_FINE_LOCATION)
                && shouldShowRequestPermissionRationale(fragment, ACCESS_COARSE_LOCATION));
    }

    private boolean shouldShowLocationRationale(@NonNull Activity activity) {
        return (!isLocationGranted()
                && shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)
                && shouldShowRequestPermissionRationale(activity, ACCESS_COARSE_LOCATION));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}, its the same logic, but for location
     *
     * @param fragment to check with
     * @return if should not ask
     */
    public boolean neverAskForLocation(@NonNull Fragment fragment) {
        return !(hasAskedForLocationPermission() == shouldShowLocationRationale(
                fragment));
    }

    /**
     * See {@link #neverAskForCamera(Activity)}, its the same logic, but for location
     *
     * @param activity to check with
     * @return if should not ask
     */
    public boolean neverAskForLocation(@NonNull Activity activity) {
        return !(hasAskedForLocationPermission() == shouldShowLocationRationale(
                activity));
    }

    // ==== AUDIO RECORDING PERMISSION =============================================================

    /**
     * @return if we have audio recording permissions
     */
    public boolean isAudioRecordingGranted() {
        /*if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(RECORD_AUDIO);
        }*/
        return ContextCompat.checkSelfPermission(mContext,
                                                 RECORD_AUDIO)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#RECORD_AUDIO}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_AUDIO_RECORDING_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestAudioRecordingPermission(@NonNull Fragment fragment) {
        PermissionPreferenceUtil.get()
                .setAudioPermissionsAsked();
        requestPermission(fragment,
                          REQUEST_AUDIO_RECORDING_PERMISSION,
                          RECORD_AUDIO);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#RECORD_AUDIO}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_AUDIO_RECORDING_PERMISSION}
     *
     * @param activity asking for the permission
     */
    public void requestAudioRecordingPermission(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setAudioPermissionsAsked();
        requestPermission(activity,
                          REQUEST_AUDIO_RECORDING_PERMISSION,
                          RECORD_AUDIO);
    }

    /**
     * @return if audio recording permission has been previously requested.
     */
    public boolean hasAskedForAudioRecordingPermission() {
        return PermissionPreferenceUtil.get()
                       .isAudioPermissionsAsked();
    }

    @VisibleForTesting
    protected boolean shouldShowAudioRecordingRationale(@NonNull Fragment fragment) {
        return (!isAudioRecordingGranted()
                && shouldShowRequestPermissionRationale(fragment, RECORD_AUDIO));
    }

    private boolean shouldShowAudioRecordingRationale(@NonNull Activity activity) {
        return (!isAudioRecordingGranted()
                && shouldShowRequestPermissionRationale(activity, RECORD_AUDIO));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param fragment to check with
     * @return if should not ask
     */
    public boolean neverAskForAudio(@NonNull Fragment fragment) {
        return !(hasAskedForAudioRecordingPermission() == shouldShowAudioRecordingRationale(fragment));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param activity to check with
     * @return if should not ask
     */
    public boolean neverAskForAudio(@NonNull Activity activity) {
        return !(hasAskedForAudioRecordingPermission() == shouldShowAudioRecordingRationale(activity));
    }

    // ==== CALENDAR ===============================================================================

    /**
     * @return if we have calendar permissions
     */
    public boolean isCalendarGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(READ_CALENDAR);
        }
        return ContextCompat.checkSelfPermission(mContext, READ_CALENDAR)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#READ_CALENDAR}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CALENDAR_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestCalendarPermission(@NonNull Fragment fragment) {
        PermissionPreferenceUtil.get()
                .setCalendarPermissionsAsked();
        requestPermission(fragment, REQUEST_CALENDAR_PERMISSION, READ_CALENDAR);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#READ_CALENDAR}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CALENDAR_PERMISSION}
     *
     * @param activity asking for the permission
     */
    public void requestCalendarPermission(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setCalendarPermissionsAsked();
        requestPermission(activity, REQUEST_CALENDAR_PERMISSION, READ_CALENDAR);
    }

    /**
     * @return if calendar permission has been previously requested.
     */
    public boolean hasAskedForCalendarPermission() {
        return PermissionPreferenceUtil.get()
                       .isCalendarPermissionsAsked();
    }

    @VisibleForTesting
    protected boolean shouldShowCalendarRationale(@NonNull Fragment fragment) {
        return !isCalendarGranted() && shouldShowRequestPermissionRationale(fragment,
                                                                            READ_CALENDAR);
    }

    private boolean shouldShowCalendarRationale(@NonNull Activity activity) {
        return !isCalendarGranted() && shouldShowRequestPermissionRationale(activity,
                                                                            READ_CALENDAR);
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param fragment to check with
     * @return if should not ask
     */
    public boolean neverAskForCalendar(@NonNull Fragment fragment) {
        return !(hasAskedForCalendarPermission() == shouldShowCalendarRationale(fragment));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param activity to check with
     * @return if should not ask
     */
    public boolean neverAskForCalendar(@NonNull Activity activity) {
        return !(hasAskedForCalendarPermission() == shouldShowCalendarRationale(activity));
    }

    // ==== CONTACTS ===============================================================================

    /**
     * @return if we have contact permissions
     */
    public boolean isContactsGranted() {
        return isReadContactsPermissionGranted()
               || isWriteContactsPermissionGranted()
               || isGetAccountsPermissionGranted();
    }

    private boolean isReadContactsPermissionGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(READ_CONTACTS);
        }
        return ContextCompat.checkSelfPermission(mContext, READ_CONTACTS)
               == PERMISSION_GRANTED;
    }

    private boolean isWriteContactsPermissionGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(WRITE_CONTACTS);
        }
        return ContextCompat.checkSelfPermission(mContext, WRITE_CONTACTS)
               == PERMISSION_GRANTED;
    }

    private boolean isGetAccountsPermissionGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(GET_ACCOUNTS);
        }
        return ContextCompat.checkSelfPermission(mContext, GET_ACCOUNTS)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#READ_CONTACTS}</li>
     * <li>{@link Manifest.permission#WRITE_CONTACTS}</li>
     * <li>{@link Manifest.permission#GET_ACCOUNTS}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CONTACTS_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestContactsPermission(@NonNull Fragment fragment) {
        final String[] permissions = markAskedAndGetContactPermissions();
        requestPermission(fragment,
                          REQUEST_CONTACTS_PERMISSION,
                          permissions);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#READ_CONTACTS}</li>
     * <li>{@link Manifest.permission#WRITE_CONTACTS}</li>
     * <li>{@link Manifest.permission#GET_ACCOUNTS}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CONTACTS_PERMISSION}
     *
     * @param activity asking for the permission
     */
    public void requestContactsPermission(@NonNull Activity activity) {
        final String[] permissions = markAskedAndGetContactPermissions();
        requestPermission(activity,
                          REQUEST_CONTACTS_PERMISSION,
                          permissions);
    }

    private String[] markAskedAndGetContactPermissions() {
        PermissionPreferenceUtil.get()
                .setContactsPermissionsAsked();
        return getContactPermissionsToRequest();
    }

    private String[] getContactPermissionsToRequest() {
        List<String> list = new ArrayList<String>();
        if (!isReadContactsPermissionGranted()) {
            list.add(READ_CONTACTS);
        }
        if (!isWriteContactsPermissionGranted()) {
            list.add(WRITE_CONTACTS);
        }
        if (!isGetAccountsPermissionGranted()) {
            list.add(GET_ACCOUNTS);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * @return if contacts permission has been previously requested.
     */
    public boolean hasAskedForContactsPermission() {
        return PermissionPreferenceUtil.get()
                       .isContactsPermissionsAsked();
    }

    @VisibleForTesting
    public boolean shouldShowContactsRationale(@NonNull Fragment fragment) {
        return !isContactsGranted()
               && shouldShowRequestPermissionRationale(fragment,
                                                       READ_CONTACTS)
               && shouldShowRequestPermissionRationale(fragment,
                                                       WRITE_CONTACTS)
               && shouldShowRequestPermissionRationale(fragment,
                                                       GET_ACCOUNTS);
    }

    private boolean shouldShowContactsRationale(@NonNull Activity activity) {
        return !isContactsGranted()
               && shouldShowRequestPermissionRationale(activity,
                                                       READ_CONTACTS)
               && shouldShowRequestPermissionRationale(activity,
                                                       WRITE_CONTACTS)
               && shouldShowRequestPermissionRationale(activity,
                                                       GET_ACCOUNTS);
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     * @param fragment to check with
     * @return if should not ask
     */
    public boolean neverAskForContacts(@NonNull Fragment fragment) {
        return !(hasAskedForContactsPermission()
                 == shouldShowContactsRationale(fragment));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param activity to check with
     * @return if should not ask
     */
    public boolean neverAskForContacts(@NonNull Activity activity) {
        return !(hasAskedForContactsPermission()
                 == shouldShowContactsRationale(activity));
    }

    // ==== CALLING ================================================================================

    /**
     * @return if it has calling permissions
     */
    public boolean isCallingGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(CALL_PHONE);
        }
        return ContextCompat.checkSelfPermission(mContext, CALL_PHONE)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#CALL_PHONE}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CALL_PHONE_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestCallingPermission(@NonNull Fragment fragment) {
        PermissionPreferenceUtil.get()
                .setCallingPermissionsAsked();
        requestPermission(fragment,
                          REQUEST_CALL_PHONE_PERMISSION,
                          CALL_PHONE);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#CALL_PHONE}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_CALL_PHONE_PERMISSION}
     *
     * @param activity asking for the permission
     */
    public void requestCallingPermission(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setCallingPermissionsAsked();
        requestPermission(activity,
                          REQUEST_CALL_PHONE_PERMISSION,
                          CALL_PHONE);
    }

    /**
     * @return if calling permission has been previously requested.
     */
    public boolean hasAskedForCallingPermission() {
        return PermissionPreferenceUtil.get()
                       .isCallingPermissionsAsked();
    }

    @VisibleForTesting
    protected boolean shouldShowCallingRationale(@NonNull Fragment fragment) {
        return !isCallingGranted()
               && shouldShowRequestPermissionRationale(fragment,
                                                       CALL_PHONE);
    }

    private boolean shouldShowCallingRationale(@NonNull Activity activity) {
        return !isCallingGranted()
               && shouldShowRequestPermissionRationale(activity,
                                                       CALL_PHONE);
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param fragment to check with
     * @return if should not ask
     */
    public boolean neverAskForCalling(@NonNull Fragment fragment) {
        return !(hasAskedForCallingPermission()
                 == shouldShowCallingRationale(fragment));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param activity to check with
     * @return if should not ask
     */
    public boolean neverAskForCalling(@NonNull Activity activity) {
        return !(hasAskedForCallingPermission()
                 == shouldShowCallingRationale(activity));
    }

    // ==== STORAGE ================================================================================

    /**
     * @return if storage permissions are granted.
     */
    public boolean isStorageGranted() {
    	return isWriteStorageGranted();
    }
    
    
    private boolean isOneStorageGranted() {
        return isWriteStorageGranted()
               || isReadStorageGranted();
    }

    private boolean isReadStorageGranted() {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.checkSelfPermission(READ_EXTERNAL_STORAGE);
        }
        return ContextCompat.checkSelfPermission(mContext,
                                                 READ_EXTERNAL_STORAGE)
               == PERMISSION_GRANTED;
    }

    private boolean isWriteStorageGranted() {
        return ContextCompat.checkSelfPermission(mContext,
                                                 WRITE_EXTERNAL_STORAGE)
               == PERMISSION_GRANTED;
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#WRITE_EXTERNAL_STORAGE}</li>
     * <li>{@link Manifest.permission#READ_EXTERNAL_STORAGE}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_STORAGE_PERMISSION}
     *
     * @param fragment asking for the permission
     */
    public void requestStoragePermission(@NonNull Fragment fragment) {
        PermissionPreferenceUtil.get()
                .setStoragePermissionsAsked();
        requestPermission(fragment,
                          REQUEST_STORAGE_PERMISSION,
                          WRITE_EXTERNAL_STORAGE,
                          READ_EXTERNAL_STORAGE);
    }

    /**
     * Will request the following permission if not already granted:
     * <ul>
     * <li>{@link Manifest.permission#WRITE_EXTERNAL_STORAGE}</li>
     * <li>{@link Manifest.permission#READ_EXTERNAL_STORAGE}</li>
     * </ul>
     *
     * Resulting permission can be checked using {@link #REQUEST_STORAGE_PERMISSION}

     * @param activity asking for the permission
     */
    public void requestStoragePermission(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setStoragePermissionsAsked();
        requestPermission(activity,
                          REQUEST_STORAGE_PERMISSION,
                          WRITE_EXTERNAL_STORAGE);
    }
    
    public void requestStoragePermissionTwo(@NonNull Activity activity) {
        PermissionPreferenceUtil.get()
                .setStoragePermissionsAsked();
        requestPermission(activity,
                          REQUEST_STORAGE_PERMISSION,
                          WRITE_EXTERNAL_STORAGE,
                          READ_EXTERNAL_STORAGE);
    }

    /**
     * @return if storage permission has been previously requested.
     */
    public boolean hasAskedForStoragePermission() {
        return PermissionPreferenceUtil.get()
                       .isStoragePermissionsAsked();
    }

    @VisibleForTesting
    protected boolean shouldShowRequestStorageRationale(@NonNull Fragment fragment) {
        return !isOneStorageGranted()
               && shouldShowRequestPermissionRationale(fragment, WRITE_EXTERNAL_STORAGE)
               && shouldShowRequestPermissionRationale(fragment, READ_EXTERNAL_STORAGE);
    }
    
    private boolean shouldShowRequestStorageRationale(@NonNull Activity activity) {
        return !isStorageGranted()
               && shouldShowRequestPermissionRationale(activity, WRITE_EXTERNAL_STORAGE);
    }

    private boolean shouldShowRequestStorageRationaleTwo(@NonNull Activity activity) {
        return !isOneStorageGranted()
               && shouldShowRequestPermissionRationale(activity, WRITE_EXTERNAL_STORAGE)
               && shouldShowRequestPermissionRationale(activity, READ_EXTERNAL_STORAGE);
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param fragment to check with
     * @return if should not ask
     */
    public boolean neverAskForStorage(@NonNull Fragment fragment) {
        return !(hasAskedForStoragePermission() == shouldShowRequestStorageRationale(
                fragment));
    }

    /**
     * See {@link #neverAskForCamera(Fragment)}
     *
     * @param activity to check with
     * @return if should not ask
     */
    public boolean neverAskForStorage(@NonNull Activity activity) {
        return !(hasAskedForStoragePermission() == shouldShowRequestStorageRationale(
                activity));
    }

    // ==== PERMISSION REQUESTS ====================================================================

    // ---- ACTIVITIES -----------------------------------------------------------------------------

    /**
     * Request a permission.
     *
     * @param activity    requesting the permission
     * @param requestCode to listen for in {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * @param permissions to request
     */
    public void requestPermission(@NonNull Activity activity,
                                  int requestCode,
                                  @NonNull String... permissions) {
        //assertPermissionsNotGranted(permissions);
        if (isTestEnvironment()) {
            mMockSystemPermissions.requestPermissions(permissions);
        } else {
            ActivityCompat.requestPermissions(activity,
                                              permissions,
                                              requestCode);
        }
    }

    // ---- FRAGMENTS ------------------------------------------------------------------------------

    /**
     * Request a permission.
     *
     * @param fragment    requesting the permission
     * @param requestCode to listen for in {@link Fragment#onRequestPermissionsResult(int, String[], int[])}
     * @param permissions to request
     */
    public void requestPermission(@NonNull Fragment fragment,
                                  int requestCode,
                                  @NonNull String... permissions) {
        //assertPermissionsNotGranted(permissions);
        if (isTestEnvironment()) {
            mMockSystemPermissions.requestPermissions(permissions);
        } else {
            fragment.requestPermissions(permissions,
                                        requestCode);
        }
    }

    // ---- REQUEST PERMISSION RATIONALE -----------------------------------------------------------

    /**
     * Wraps {@link Fragment#shouldShowRequestPermissionRationale(String)}
     *
     * @param fragment   checking for permissions
     * @param permission to check
     * @return if we should show
     */
    public boolean shouldShowRequestPermissionRationale(@NonNull Fragment fragment,
                                                        @NonNull String permission) {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.shouldShowRequestPermissionRationale(permission);
        }
        return fragment.shouldShowRequestPermissionRationale(permission);
    }

    /**
     * Wraps {@link ActivityCompat#shouldShowRequestPermissionRationale(Activity, String)}
     *
     * @param activity   checking for permissions
     * @param permission to check
     * @return if we should show
     */
    public boolean shouldShowRequestPermissionRationale(@NonNull Activity activity,
                                                        @NonNull String permission) {
        if (isTestEnvironment()) {
            return mMockSystemPermissions.shouldShowRequestPermissionRationale(permission);
        }
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    // ---- INTENT TO SETTINGS ---------------------------------------------------------------------

    /**
     * Open the app's settings page so the user could switch an activity.
     *
     * @param activity starting this intent.
     */
    public void intentToAppSettings(@NonNull Activity activity) {
        //Open the specific App Info page:
        Intent intent = new Intent(ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            intent = new Intent(ACTION_MANAGE_APPLICATIONS_SETTINGS);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }
        }
    }

    // ---- CHECK PERMISSIONS GRANTED --------------------------------------------------------------

    /**
     * Check if permissions are granted.
     *
     * @param grantResults permissions returned in {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * @return whether all permissions were granted
     */
    public boolean arePermissionsGranted(@NonNull int[] grantResults) {
        for (int result : grantResults) {
            if (result != PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will request all the permissions declared in your application manifest
     * for the specified. The purpose of this method is to enable
     * all permissions to be requested at one shot. The PermissionsResultAction is used to notify
     * you of the user allowing or denying each permission. The Activity and PermissionsResultAction
     * parameters are both annotated Nullable, but this method will not work if the Activity
     * is null. It is only annotated Nullable as a courtesy to prevent crashes in the case
     * that you call this from a Fragment where {@link Fragment#getActivity()} could yield
     * null. Additionally, you will not receive any notification of permissions being granted
     * if you provide a null PermissionsResultAction.
     *
     * @param activity the Activity necessary to request and check permissions.
     */
    @SuppressWarnings("unused")
    public synchronized void requestAllManifestPermissionsIfNecessary(final @Nullable Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        String[] perms = getManifestPermissions(activity);
        requestPermission(activity, requestCode, perms);
    }

    /**
     * This method retrieves all the permissions declared in the application's manifest.
     * It returns a non null array of permisions that can be declared.
     *
     * @param activity the Activity necessary to check what permissions we have.
     * @return a non null array of permissions that are declared in the application manifest.
     */
    @NonNull
    private synchronized String[] getManifestPermissions(@NonNull final Activity activity) {
        PackageInfo packageInfo = null;
        List<String> list = new ArrayList<String>(1);
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String perm : permissions) {
                    list.add(perm);
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    @SuppressWarnings("unused")
    public synchronized void requestMultiPermissionsIfNecessary(final @Nullable Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        String[] perms = getMultiPermissions(activity);
        if(perms.length == 0) {
            return;
        }
        requestPermission(activity, requestCode, perms);
    }

    @NonNull
    public synchronized String[] getMultiPermissions(@NonNull final Activity activity) {

        List<String> list = new ArrayList<String>(1);
        if(!isLocationGranted()) {
            list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!isCameraGranted()) {
            list.add(Manifest.permission.CAMERA);
        }

        if(!isWriteStorageGranted()) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        return list.toArray(new String[list.size()]);
    }

    // ===== TESTING ===============================================================================

    @VisibleForTesting
    public void injectMockSystemPermissions() {
        mMockSystemPermissions = new MockSystemPermissions();
    }

    @VisibleForTesting
    public MockSystemPermissions getMockSystemPermissions() {
        return mMockSystemPermissions;
    }

    private boolean isTestEnvironment() {
        return mMockSystemPermissions != null;
    }

    protected static void tearDown() {
        PermissionPreferenceUtil.get()
                .tearDown();
        mInstance = null;
    }

    @VisibleForTesting
    public static void clearDb() {
        PermissionPreferenceUtil.get()
                .clearData();
    }
}
