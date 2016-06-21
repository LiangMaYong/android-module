package com.androidmodule.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * LSelectImage
 * 
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("InlinedApi")
public class AMSelectImage {

	public static String DIR_NAME = "L_SELECT_IMAGE";
	public static String PREFIX_NAME = "TEMP_";

	/**
	 * getImageSelectIntent
	 * 
	 * @return Intent
	 */
	public static Intent getImageSelectIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		return intent;
	}

	/**
	 * getImageTakeIntent
	 * 
	 * @return Intent
	 */
	public static Intent getImageTakeIntent(String name) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageTempUri(name));
		return intent;
	}

	/**
	 * getImageCropIntent
	 * 
	 * @param uri
	 *            old file uri
	 * @param name
	 *            new filename
	 * @param size
	 *            size
	 * @return Intent
	 */
	public static Intent getImageCropIntent(Uri uri, String name, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageTempUri(name));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}

	/**
	 * getImageSelectIntent
	 * 
	 * @return Intent
	 */
	public static Intent getImageSelectIntent(String name) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageTempUri(name));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}

	/**
	 * getImageCropIntent
	 * 
	 * @param uri
	 *            old file uri
	 * @param name
	 *            new filename
	 * @param width
	 *            bitmap width
	 * @param height
	 *            bitmap height
	 * @return Intent
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static Intent getImageCropIntent(Uri uri, String name, int width, int height) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", width);
		intent.putExtra("aspectY", height);
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageTempUri(name));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}

	/**
	 * getImageClipIntent
	 * 
	 * @param name
	 *            tempname
	 * @param size
	 *            bitmap size
	 * @return Intent
	 */
	public static Intent getImageClipIntent(String name, int size) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageTempUri(name));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}

	/**
	 * getImageClipIntent
	 * 
	 * @param name
	 *            tempname
	 * @param width
	 *            bitmap width
	 * @param height
	 *            bitmap height
	 * @return Intent
	 */
	public static Intent getImageClipIntent(String name, int width, int height) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", width);
		intent.putExtra("aspectY", height);
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageTempUri(name));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		return intent;
	}

	/**
	 * getImageTempUri
	 * 
	 * @param name
	 *            tempname
	 * @return Uri
	 */
	public static Uri getImageTempUri(String name) {
		String fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + DIR_NAME;
		if (!createDir(fileDir)) {
			fileDir = Environment.getExternalStorageDirectory().getPath() + "/";
		}
		return Uri.parse("file://" + "/" + fileDir + "/" + PREFIX_NAME + name + ".jpg");
	}

	/**
	 * createDir
	 * 
	 * @param dirName
	 *            dirName
	 * @return boolean
	 */
	private static boolean createDir(String dirName) {
		File file = new File(dirName);
		if (!file.exists()) {
			return file.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * getImageThumbnail
	 * 
	 * @param name
	 *            name
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * @return Bitmap
	 */
	@TargetApi(Build.VERSION_CODES.FROYO)
	public static Bitmap getImageThumbnail(String name, int width, int height) {

		String fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + DIR_NAME;
		File file = new File(fileDir);
		String imagePath = "";
		if (file.exists()) {
			imagePath = fileDir + "/" + PREFIX_NAME + name + ".jpg";
		} else {
			imagePath = Environment.getExternalStorageDirectory().getPath() + "/" + PREFIX_NAME + name + ".jpg";
		}
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false;
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * getImageBase64String
	 * 
	 * @param name
	 *            name
	 * @return String
	 */
	public static String getImageBase64String(String name) {
		String fileDir = Environment.getExternalStorageDirectory().getPath() + "/" + DIR_NAME;
		File fileTemp = new File(fileDir);
		String imagePath = "";
		if (fileTemp.exists()) {
			imagePath = fileDir + "/" + PREFIX_NAME + name + ".jpg";
		} else {
			imagePath = Environment.getExternalStorageDirectory().getPath() + "/" + PREFIX_NAME + name + ".jpg";
		}
		return AMBase64.encodeAsFile(imagePath);
	}

}
