package com.c9mj.platform.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author: LMJ
 * date: 2016/9/7
 * 相片处理工具类
 */
public class PhotoUtil {
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Activity activity, int requestCode, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startPhotoZoom(Fragment fragment, int requestCode, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        fragment.startActivityForResult(intent, requestCode);
    }


    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap decodeBitmapFromFile(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 1920, 1920);
        // Decode bitmap init inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据fromPath获取图片进行压缩保存至toPath
     *
     * @param fromPath 源路径
     * @param toPath   目的路径
     * @param quality  压缩比例
     * @return
     */
    public static String compressImageFromPathToPath(String fromPath, String toPath, int quality) {
        Bitmap bitmap = decodeBitmapFromFile(fromPath);
        File bitmapFile = new File(toPath);

        if (!bitmapFile.getParentFile().exists()) {
            bitmapFile.getParentFile().mkdirs();
        }
        FileOutputStream bitmapWtriter = null;
        try {
            bitmapWtriter = new FileOutputStream(bitmapFile);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bitmapWtriter)) {
                bitmapWtriter.flush();
                bitmapWtriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapFile.getAbsolutePath();
    }

    /**
     * 根据uri获取图片进行压缩保存至toPath
     *
     * @param context
     * @param uri
     * @param toPath
     * @param quality
     * @return
     */
    public static String compressImageFromUriToPath(Context context, Uri uri, String toPath, int quality) {

        String fromPath = getRealPathFromURI(context, uri);
        Bitmap bitmap = decodeBitmapFromFile(fromPath);
        File bitmapFile = new File(toPath);

        if (!bitmapFile.getParentFile().exists()) {
            bitmapFile.getParentFile().mkdirs();
        }
        FileOutputStream bitmapWtriter = null;
        try {
            bitmapWtriter = new FileOutputStream(bitmapFile);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bitmapWtriter)) {
                bitmapWtriter.flush();
                bitmapWtriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapFile.getAbsolutePath();
    }

    /**
     * 将imagePath路径中的图片转换为二进制
     *
     * @param imagePath
     * @return
     */
    public static byte[] convertImageToByte(String imagePath) {
        Bitmap bitmap = decodeBitmapFromFile(imagePath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
//            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
//        byte[] bytes = out.toByteArray();
//        return Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
    }

    /**
     * 根据Uri获取到文件路径
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 发送广播更新图库
     *
     * @param context
     * @param imagePath
     */
    public static void updateGallery(Context context, String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(imagePath));
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 调用系统拍照
     */
    public static void startTakePhoto(Activity activity, int requestCode, String photoName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + photoName)));
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startTakePhoto(Fragment fragment, int requestCode, String photoName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + photoName)));
        fragment.startActivityForResult(intent, requestCode);
    }


    /**
     * 调用系统图库选择图片
     */
    public static void startGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startGallery(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        fragment.startActivityForResult(intent, requestCode);
    }

}
