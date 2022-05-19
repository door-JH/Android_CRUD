package com.example.ps6.App;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
import androidx.core.app.ActivityCompat;
//import android.support.v4.content.FileProvider;
import androidx.core.content.FileProvider;
import android.util.Log;

import com.example.ps6.PhotographyActivity;
import com.karumi.dexter.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class AppCamera {

    /**
     * Refreshes gallery on adding new image/video. Gallery won't be refreshed
     * on older devices until device is rebooted
     * 새로운 이미지 / 비디오를 추가 할 때 갤러리를 새로 고칩니다.
     * 기기가 재부팅 될 때까지 이전 기기에서 갤러리가 새로 고쳐지지 않습니다.
     */
    public static void refreshGallery(Context context, String filePath) {
        // ScanFile so it will be appeared on Gallery
        MediaScannerConnection.scanFile(context,
                new String[]{filePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    public static boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Downsizing the bitmap to avoid OutOfMemory exceptions
     * OutOfMemory 예외를 피하기 위해 비트 맵 크기 축소
     */
    public static Bitmap optimizeBitmap(int sampleSize, String filePath) {
        // bitmap factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Checks whether device has camera or not. This method not necessary if
     * android:required="true" is used in manifest file
     * 장치에 카메라가 있는지 여부를 확인합니다.
     * 매니페스트 파일에 android : required = "true"가 사용 된 경우이 메소드는 필요하지 않습니다.
     */
    public static boolean isDeviceSupportCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Open device app settings to allow user to enable permissions
     * 사용자가 권한을 사용하도록 허용하는 기기 앱 설정 열기
     */
    public static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static Uri getOutputMediaFileUri(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
    }

    /**
     * Creates and returns the image or video file before opening the camera
     */
    public static File getOutputMediaFile(Context context, int type) {

        /* External sdcard location
        File mediaStorageDir = new File(
                Environment.
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                // 여러 애플리케이션에서 공용으로 사용할 수 있는 데이터들을 저장합니다.
                // 데이터의 유형에 따라 별도의 디렉터리를 사용합니다.
                PhotographyActivity.GALLERY_DIRECTORY_NAME);
                */
        File mediaStorageDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                // 여러 애플리케이션에서 공용으로 사용할 수 있는 데이터들을 저장합니다.
                // 데이터의 유형에 따라 별도의 디렉터리를 사용합니다.
                PhotographyActivity.GALLERY_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(PhotographyActivity.GALLERY_DIRECTORY_NAME, "Oops! Failed create "
                        + PhotographyActivity.GALLERY_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Preparing media file naming convention
        // adds timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == PhotographyActivity.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + "." + PhotographyActivity.IMAGE_EXTENSION);
        } else if (type == PhotographyActivity.MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + "." + PhotographyActivity.VIDEO_EXTENSION);
        } else {
            return null;
        }

        return mediaFile;
    }

}
