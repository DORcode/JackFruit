package com.lib.imageselector.utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;

import com.lib.imageselector.beans.MediaFolder;
import com.lib.imageselector.beans.MediaInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.utils
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/19 14:17
 * @修改
 * @修改时期 2017/1/19 14:17
 */
public class MediaLoader {
    private static final String TAG = "MediaLoader";
    private static MediaLoader instance;
    private Context context;
    private ContentResolver cr;
    private List<MediaFolder> folders;

    private static final String[] IMAGES_PROJECTION = new String[] {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.PICASA_ID,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.HEIGHT
    };

    private String[] IMAGE_THUMBNAILS_PROJECTION = new String[] {
            MediaStore.Images.Thumbnails._ID,
            MediaStore.Images.Thumbnails.DATA,
            MediaStore.Images.Thumbnails.IMAGE_ID,
            MediaStore.Images.Thumbnails.WIDTH,
            MediaStore.Images.Thumbnails.HEIGHT
    };

    private String[] VIDEOS_PROJECTION = new String[] {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.ALBUM,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DESCRIPTION,
            MediaStore.Video.Media.DURATION,
    };

    private String[] AUDIOS_PROJECTION = new String[] {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM_KEY
    };

    public static MediaLoader getInstance(Context context) {
        if(instance == null) {
            instance = new MediaLoader(context);
        }
        return instance;
    }

    public MediaLoader(Context context) {
        this.context = context.getApplicationContext();
        cr = context.getContentResolver();
    }

    /**
     * 获取图片所在文件夹数据
     * @return
     */
    public List<MediaFolder> getImageFolders() {
        long time = System.currentTimeMillis();
        folders = new ArrayList<MediaFolder>();
        Cursor cursor = MediaStore.Images.Media.query(cr,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGES_PROJECTION, null, MediaStore.Images.Media.DATE_ADDED + " desc");
        MediaFolder allImageFolder = new MediaFolder("all images", "图片和视频");
        List<MediaInfo> allImageInfo = new ArrayList<MediaInfo>();
        while(cursor.moveToNext()) {

            //图片所在文件夹
            MediaFolder subFolder;
            String id = cursor.getString(cursor.getColumnIndex(IMAGES_PROJECTION[0]));
            //路径
            String path = cursor.getString(cursor.getColumnIndex(IMAGES_PROJECTION[1]));
            //修改日期
            String dateModified = cursor.getString(cursor.getColumnIndex(IMAGES_PROJECTION[2]));
            //名称
            String name = cursor.getString(cursor.getColumnIndex(IMAGES_PROJECTION[3]));
            //图片file对象
            File imageFile = new File(path);

            //图片所在文件夹路径
            String parentPath = imageFile.getParent();
            //图片所在文件夹名称
            String parentName = imageFile.getParentFile().getName();

            MediaInfo image = new MediaInfo(path, name, MediaInfo.MediaType.IMAGE, dateModified);
            //加入到所有图片列表中
            allImageInfo.add(image);
            MediaFolder newFolder = new MediaFolder(parentPath, parentName);
            //从文件夹列表中查找该图片文件夹是否已出现
            //subFolder = getMediaFolder(parentPath);
            //subFolder = folders.get(folders.indexOf(newFolder));

            //该图片所在文件夹未出现
            if(!folders.contains(newFolder)) {
                Log.d(TAG, "getImageFolders: " + (System.currentTimeMillis() - time + parentPath + "_" + parentName));
                //subFolder = new MediaFolder(parentPath, parentName);
                folders.add(newFolder);
                newFolder.getList().add(image);
            } else {
                folders.get(folders.indexOf(newFolder)).getList().add(image);
                //subFolder.addMedia(image);
            }

        }
        allImageFolder.setList(allImageInfo);
        folders.add(0, allImageFolder);
        Log.d(TAG, "getImageFolders: " + (System.currentTimeMillis() - time));
        cursor.close();
        return folders;
    }

    public MediaFolder getVideoFolder() {
        MediaFolder videoFolder = new MediaFolder("all video", "所有视频");
        List<MediaInfo> videoList = new ArrayList<MediaInfo>();
        videoFolder.setList(videoList);
        //查询出手机中所有的视频显示在一个文件夹中
        Cursor cursor = MediaStore.Video.query(cr, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEOS_PROJECTION);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(VIDEOS_PROJECTION[0]));
            //路径
            String path = cursor.getString(cursor.getColumnIndex(VIDEOS_PROJECTION[1]));
            //修改日期
            String dateModified = cursor.getString(cursor.getColumnIndex(VIDEOS_PROJECTION[2]));
            //名称
            String name = cursor.getString(cursor.getColumnIndex(VIDEOS_PROJECTION[3]));
            MediaInfo videoInfo = new MediaInfo(path, name, MediaInfo.MediaType.VIDEO, dateModified);
            videoList.add(videoInfo);
        }
        return videoFolder;
    }

    public List<MediaFolder> getImagesAndVideo() {
        getImageFolders();
        if(folders.size()== 0) {
            folders.add(getVideoFolder());
        } else {
            folders.get(0).getList().addAll(0, getVideoFolder().getList());
            folders.add(1, getVideoFolder());
        }
        return folders;
    }

    public MediaInfo findImagePathByUri(Uri uri) {
        Cursor cursor = MediaStore.Images.Media.query(cr,uri, null, null, null, null);
        MediaInfo info = null;
        String path;
        if(cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();
        } else {
            path = uri.getPath();
        }

        File file = new File(path);
        info= new MediaInfo(path, file.getName(), MediaInfo.MediaType.IMAGE, new Date(file.lastModified()).toString());

        return info;
    }

    /**
     * 判断文件夹是否出现过
     * @param path
     * @return
     */
    private boolean isFoldPathHas(String path) {
        for(MediaFolder mf : folders) {
            if(mf.getPath().equals(path)) {
                return true;
            }
        }
        return false;
    }

    private MediaFolder getMediaFolder(String path) {
        for(MediaFolder mf : folders) {
            if(mf.getPath().equals(path)) {
                return mf;
            }
        }
        return null;
    }

    public List<MediaInfo> getMediaListFromFoldList(int position) {
        return folders.get(position).getList();
    }
}
