package com.bs.afterservice.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description: 图片工具类
 * AUTHOR: Champion Dragon
 * created at 2018/2/26
 **/

public class PhotoUtil {
    /**
     * 压缩图片
     */
    public static Bitmap SizeImage(Bitmap image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        while (os.toByteArray().length / 1024 > 1111) {
            os.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        options.inJustDecodeBounds = false;
        int h = options.outHeight;
        int w = options.outWidth;
        int hh = 666;
        int ww = 666;
        int b = 1;
        if (h > w && h > hh) {
            b = h / hh;
        } else if (w > h && w > ww) {
            b = w / ww;
        }
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = b;
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, options);
        return bitmap;
    }

    /**
     * 存储照片并返回文件
     */
    public static File SavePhoto(Bitmap bitmap, String path, String name) {
        String localpath = null;
        File photoFile = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            photoFile = new File(fileDir, name + ".png");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(photoFile);
                if (bitmap != null) {
                    if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)) {
                        localpath = photoFile.getAbsolutePath();
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            Logs.d("photoutil_72     " + e.getMessage());
                            photoFile.delete();
                            localpath = null;
                            e.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                Logs.d("photoutil_80     " + e.getMessage());
                photoFile.delete();
                localpath = null;
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fos = null;
                }
            }

        }

        return photoFile;
    }


    /**
     * 二次压缩，先按照像素压缩再按照质量压缩
     *
     * @param imgUrl   图片路径
     * @param reqWidth 期望宽度 可以根据市面上的常用分辨率来设置
     * @param size     期望图片的大小，单位为kb
     * @param quality  图片压缩的质量，取值1-100，越小表示压缩的越厉害，如输入30，表示压缩70%
     * @return Bitmap 压缩后得到的图片
     */
    public static Bitmap compressBitmap(String imgUrl, int reqWidth, int size, int quality) {
        // 创建bitMap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgUrl, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int reqHeight;
        reqHeight = (reqWidth * height) / width;
        // 在内存中创建bitmap对象，这个对象按照缩放比例创建的
        options.inSampleSize = calculateInSampleSize(
                options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(
                imgUrl, options);
        Bitmap mBitmap = compressImage(Bitmap.createScaledBitmap(
                bm, 480, reqHeight, false), size, quality);
        return mBitmap;
    }


    /**
     * 质量压缩图片，图片占用内存减小，像素数不变，常用于上传
     *
     * @param image
     * @param size    期望图片的大小，单位为kb
     * @param options 图片压缩的质量，取值1-100，越小表示压缩的越厉害,如输入30，表示压缩70%
     */
    private static Bitmap compressImage(Bitmap image, int size, int options) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > size) {
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


    /*计算像素压缩的缩放比例*/
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;


        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


}
