package com.ulling.lib.core.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.ulling.lib.core.base.QcBaseApplication;

import java.io.IOException;

public class QcBitmapUtil {
    private static QcBitmapUtil SINGLE_U;

    public static synchronized QcBitmapUtil getInstance() {
        if (QcBaseApplication.getInstance() == null) {
            QcLog.i("QcBackPressClose init failed !");
            return null;
        }
        if (SINGLE_U == null) {
            SINGLE_U = new QcBitmapUtil();
        }
        return SINGLE_U;
    }

    /**
     * 이미지 리사이징
     *
     * @param isResize
     * @param oriBitmap
     * @param maxImageSize
     * @param filter
     * @return
     */
    public static Bitmap resizeMaxBitmap(boolean isResize, Bitmap oriBitmap, float maxImageSize, boolean filter) {
        if (!isResize) {
            return oriBitmap;
        }
        if (oriBitmap.getWidth() > maxImageSize || oriBitmap.getWidth() > maxImageSize) {
            float ratio = Math.min(
                    (float) maxImageSize / oriBitmap.getWidth(),
                    (float) maxImageSize / oriBitmap.getHeight());
            int width = Math.round((float) ratio * oriBitmap.getWidth());
            int height = Math.round((float) ratio * oriBitmap.getHeight());

            return Bitmap.createScaledBitmap(oriBitmap, width, height, filter);
        } else {
            return oriBitmap;
        }
    }


    /**
     * 비트맵 회전
     * ㄴ 카메라등 사진이 일부 단말등에서 회전되어 나와 재설정하는 단계
     *
     * @param bitmap
     * @param degree
     * @return
     */
    public Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 이미지 기울기 체크
     *
     * @param imageFilePath
     * @return
     */
    public int getImageDegree(String imageFilePath) {
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(imageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }

        return exifDegree;
    }

    /**
     * 이미지 파일 정보에서 기울기 체크
     *
     * @param exifOrientation
     * @return
     */
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

}
