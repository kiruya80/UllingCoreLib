package com.ulling.lib.core.utils;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.view.Surface;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class QcCameraUtils {
    public static String TAG = "CameraUtils";
    // based on ApiDemos

    private static final double ASPECT_TOLERANCE = 0.1;

    /**
     * 단말기 방향 가져오기
     *
     * @param activity
     * @return
     */
    public static int getDisplayOrientation(AppCompatActivity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        QcLog.e("getDisplayOrientation == " + degrees);
        return degrees;
    }

    /**
     * 프리뷰와 사진 사이즈 같은거 가져오기
     *
     * @param parameters
     * @return
     */
    public static List<Size> getEqualSize(Camera.Parameters parameters) {
        List<Size> equalSizeList = new ArrayList<>();

        List<Size> previewSizeList = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizeList, Collections.reverseOrder(new SizeComparator()));
        for (int i = 0; i < previewSizeList.size(); i++) {
            Size size = previewSizeList.get(i);
            QcLog.e("previewS izeList ====== Width : " + size.width + ", Height : " + size.height);
        }


        List<Size> pictureSizeList = parameters.getSupportedPictureSizes();
        Collections.sort(pictureSizeList, Collections.reverseOrder(new SizeComparator()));
        for (int i = 0; i < pictureSizeList.size(); i++) {
            Size size = pictureSizeList.get(i);
            QcLog.e("picture SizeList====== Width : " + size.width + ", Height : " + size.height);
        }

        if (previewSizeList != null && previewSizeList.size() > 0
                && pictureSizeList != null && pictureSizeList.size() > 0) {
            for (Size pictureSize : pictureSizeList) {
                for (Size previewSize : previewSizeList) {
                    if (pictureSize.height == previewSize.height
                            && pictureSize.width == previewSize.width) {
                        equalSizeList.add(previewSize);
                    }
                }
            }
        } else {
            return null;
        }
        return equalSizeList;
    }


    /**
     *
     * @param displayOrientation
     * @param viewWidth
     * @param viewHeight
     * @param sizePercent
     * @param supportPreviewSize
     * @Date : 2016. 3. 25.
     * @author : KILHO
     * @Method ㄴ 뷰 크기와 비슷한 비율의 프리뷰 사이즈 가져오기
     * @return
     */
    public static Size getBestDisplayPreviewSize(
            int displayOrientation,
            int viewWidth,
            int viewHeight,
            int sizePercent,
            List<Size> supportPreviewSize) {
        if (supportPreviewSize == null)
            return null;
        if (viewWidth == 0 || viewHeight == 0)
            return null;
        if (sizePercent != 0) {
            viewWidth = (viewWidth / 100) * sizePercent;
            viewHeight = (viewHeight / 100) * sizePercent;
        }
        double newViewWidth = (double) viewWidth;
        double newViewHeight = (double) viewHeight;

        if (displayOrientation == 90 || displayOrientation == 270) {
            // 세로
            newViewWidth = (double) viewHeight;
            newViewHeight = (double) viewWidth;
        }

        double minDiff = 300;

        Collections.sort(supportPreviewSize, Collections.reverseOrder(new SizeComparator()));
        Size bestSize = supportPreviewSize.get(0);

        for (Size size : supportPreviewSize) {
            if (Math.abs(size.width - newViewWidth) < minDiff
                    && Math.abs(size.height - newViewHeight) < minDiff) {
                // 화면 해상도와 비슷한 비율의 프리뷰인 경우
                bestSize = size;
                break;
            }
        }

        QcLog.e("bestSize ======" + " === " + bestSize.width + " , " + bestSize.height);
        return bestSize;
    }


    /**
     * @param displayOrientation
     * @param viewWidth
     * @param viewHeight
     * @param parameters
     * @return
     * @MethodName : getRatioPreviewSize
     * @Date : 2016. 3. 23.
     * @author : KILHO
     * @Method ㄴ SimpleCameraHost 에서 사용
     * 프리뷰 사이즈 가져오기
     * @변경이력
     */
    public static Size getBestRatioPreviewSize(
            int displayOrientation,
            int viewWidth,
            int viewHeight,
            Camera.Parameters parameters) {
        // 뷰 크기와 비슷한 비율의 프리뷰리스트 가져오기
        ArrayList<Size> ratioPreviewSize = getRatioPreviewSize(displayOrientation, viewWidth, viewHeight,
                parameters, 0.0d);

        checkSupportedPictureSizeAtPreviewSize(parameters);

        return getBestDisplayPreviewSize(displayOrientation, viewWidth, viewHeight,100,
                ratioPreviewSize);
    }

    /**
     * 비율에 따른 뷰
     *
     * @param displayOrientation
     * @param widthRatio
     * @param heightRatio
     * @param parameters
     * @param closeEnough
     * @return
     */
    public static Size getBestAspectPreviewSize(int displayOrientation,
                                                int widthRatio,
                                                int heightRatio,
                                                Camera.Parameters parameters,
                                                double closeEnough) {
        double targetRatio = (double) widthRatio / heightRatio;
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        if (displayOrientation == 90 || displayOrientation == 270) {
            targetRatio = (double) heightRatio / widthRatio;
        }

        List<Size> sizes = parameters.getSupportedPreviewSizes();

        Collections.sort(sizes,
                Collections.reverseOrder(new SizeComparator()));

        android.util.Log.d("CWAC-Camera", "getBestAspectPreviewSize == ");
        for (Size size : sizes) {
            android.util.Log.d("CWAC-Camera", String.format("%d x %d", size.width, size.height));
            double ratio = (double) size.width / size.height;

            if (Math.abs(ratio - targetRatio) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(ratio - targetRatio);
            }

            if (minDiff < closeEnough) {
                break;
            }
        }

        return (optimalSize);
    }


    /**
     * @param displayOrientation
     * @param viewWidth
     * @param viewHeight
     * @param parameters
     * @param closeEnough
     * @return
     * @MethodName : getBestAspectPreviewSize
     * @Date : 2016. 3. 23.
     * @author : KILHO
     * @Method ㄴ 최상 프리뷰 사이즈 가져오기
     * ㄴ 카메라뷰에 맞는 최상의 프리뷰 비율 사이즈 가져온다
     * @변경이력
     */
    public static ArrayList<Size> getRatioPreviewSize(int displayOrientation,
                                                      int viewWidth,
                                                      int viewHeight,
                                                      Camera.Parameters parameters,
                                                      double closeEnough) {

        double newViewWidth = (double) viewWidth;
        double newViewHeight = (double) viewHeight;

        if (displayOrientation == 90 || displayOrientation == 270) {
            // 세로
            newViewWidth = (double) viewHeight;
            newViewHeight = (double) viewWidth;
        }

        double targetRatio = (double) newViewWidth / newViewHeight;


        ArrayList<Size> supportPreviewSize = new ArrayList<Size>();

        double minDiff = Double.MAX_VALUE;

        List<Size> sizes = parameters.getSupportedPreviewSizes();

        Collections.sort(sizes, Collections.reverseOrder(new SizeComparator()));

        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) < minDiff) {
                // 화면 해상도와 비슷한 비율의 프리뷰인 경우
                supportPreviewSize.add(size);
                minDiff = Math.abs(ratio - targetRatio);
            }
        }

        return supportPreviewSize;
    }


    /**
     * @param displayOrientation
     * @param screenWidth
     * @param screenHeight
     * @param bestDisplayPreviewSize
     * @return
     * @MethodName : getDisplayPreviewReSize
     * @Date : 2016. 3. 27.
     * @author : KILHO
     * @Method ㄴ 베스트 프리뷰 사이즈를 디스플레이 뷰 사이즈로 리사이징
     * @변경이력
     */
    public static Size getDisplayPreviewReSize(
            int displayOrientation,
            int screenWidth,
            int screenHeight,
            Size bestDisplayPreviewSize) {

        Size newSizeList = bestDisplayPreviewSize;
        int supportPreviewWidth = bestDisplayPreviewSize.width;
        int supportPreviewHeight = bestDisplayPreviewSize.height;

        if (displayOrientation == 90 || displayOrientation == 270) {
            supportPreviewWidth = bestDisplayPreviewSize.height;
            supportPreviewHeight = bestDisplayPreviewSize.width;
        }

        int preWidth = screenWidth;
        int preHeight = screenHeight;

        QcLog.e("getDisplayPreviewSize ======" + screenWidth + " , " + screenHeight);

        double preViewRatio = (double) supportPreviewWidth / supportPreviewHeight;

        if (preViewRatio <= 1) {
            // 세로
            preWidth = (screenHeight * supportPreviewWidth) / supportPreviewHeight;
            preHeight = screenHeight;
            QcLog.e("세로 기준 리사이징 ==========" + preWidth + " , " + preHeight);

        } else {
            // 가로
            preWidth = screenWidth;
            preHeight = (screenWidth * supportPreviewHeight) / supportPreviewWidth;
            QcLog.e("세로 기준 리사이징 ==========" + preWidth + " , " + preHeight);
        }

        newSizeList.width = preWidth;
        newSizeList.height = preHeight;

        return newSizeList;
    }


    /**
     * @param parameters
     * @return
     * @MethodName : checkSupportedPictureSizeAtPreviewSize
     * @Date : 2016. 3. 27.
     * @author : KILHO
     * @Method ㄴ !! 실제촬영 가능한 촬영 해상도 가져오기 !!
     * <p>
     * ㄴ 프리뷰에서 지원하지 않는 사진 사이즈 제거
     * <p>
     * http://lsit81.tistory.com/entry/Android-Camera-Picture-Size-%EC%84%A0%ED%83%9D-%EB%B0%A9%EB%B2%95
     * <p>
     * 그러나 갤럭시 노트에서 확인해 본 결과 촬영 해상도를 1280 * 720으로 설정하고 위 getCameraPreviewSize()통해 얻은 프리뷰 해상도 1280 * 800으로 설정하여 촬영할 경우
     * 프리뷰 화면과 촬영 화면의 비율이 서로 다르게되어 사용자가 화면에서본 이미지 그대로 영상이 촬영되지 않는 문제가 있었습니다.
     * getCameraPreviewSize() 알고리즘 상에 문제는 없습니다.
     * 한 마디로 촬영 해상도인 1280 * 720와 비율이 맞는 프리뷰 해상도가 없다는 것이 문제였습니다.
     * 갤럭시 노트의 경우에는 촬영 비율이 틀어지면서 촬영은 되었지만 다른 단말에서는 앱이 죽을 수도 있는 문제 입니다.
     * 제조사에서 지원 해상도 리스트를 getSupportedPictureSize()로 얻었을때 1280 * 720 같은 크기는 제외를 시켜야되는데...그렇게 하고 있지를 않더군요... ㅠㅠ
     * @변경이력
     */
    public static List<Size> checkSupportedPictureSizeAtPreviewSize(
            Camera.Parameters parameters) {
        QcLog.e("checkSupportedPictureSizeAtPreviewSize == ");

        List<Size> pictureSizes = parameters.getSupportedPictureSizes();
        Collections.sort(pictureSizes, Collections.reverseOrder(new SizeComparator()));

        List<Size> previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, Collections.reverseOrder(new SizeComparator()));
        Size pictureSize;
        Size previewSize = null;

        double pictureRatio = 0;
        double previewRatio = 0;

        final double aspectTolerance = 0.05;
        boolean isUsablePicture = false;

        for (int indexOfPicture = pictureSizes.size() - 1; indexOfPicture >= 0; --indexOfPicture) {
            pictureSize = pictureSizes.get(indexOfPicture);
            pictureRatio = (double) pictureSize.width / (double) pictureSize.height;

            isUsablePicture = false;

            for (int indexOfPreview = previewSizes.size() - 1; indexOfPreview >= 0; --indexOfPreview) {
                previewSize = previewSizes.get(indexOfPreview);
                previewRatio = (double) previewSize.width / (double) previewSize.height;

                if (Math.abs(pictureRatio - previewRatio) < aspectTolerance) {
                    isUsablePicture = true;
                    /**
                     *  프리뷰와 사진 해상도 비율이 같은 경우 pass (오차 0.05)
                     */
                    break;
                }
            }

            if (!isUsablePicture) {
                /**
                 *  프리뷰에서 지원하지 않는 사진 해상도 삭제
                 */
                pictureSizes.remove(indexOfPicture);
            }
        }

        Collections.sort(pictureSizes, Collections.reverseOrder(new SizeComparator()));
        return pictureSizes;
    }


    /**
     *
     * @MethodName   : getLargestPictureSize
     * @Date         : 2016. 3. 28.
     * @author       : KILHO
     * @param host
     * @param parameters
     * @return
     *
     * @Method
     *  takePicture
     *   사진 사이즈 가져오기
     *
     * @변경이력
     */
//	public static Size getLargestPictureSize(CameraHost host,
//			Camera.Parameters parameters) {
//		return(getLargestPictureSize(host, parameters, true));
//	}

    /**
     *
     * @MethodName   : getLargestPictureSize
     * @Date         : 2016. 3. 23.
     * @author       : KILHO
     * @param host
     * @param parameters
     * @param enforceProfile
     * @return
     *
     * @Method
     *  ㄴ SimpleCameraHost 에서 사용
     *  사진 사이즈 가져오기
     *  가장 큰사이즈 가져오기
     *
     *
     * @변경이력
     */
//	public static Size getLargestPictureSize(CameraHost host,
//			Camera.Parameters parameters,
//			boolean enforceProfile) {
//		CameraUtilLog.eLog(TAG, "getLargestPictureSize == " + enforceProfile);
//		Size result=null;
//
//
//		Size previewSize = parameters.getPreviewSize();
//		CameraUtilLog.eLog(TAG, "previewSize == " + previewSize.width + " , " + previewSize.height);
//
//		List<Size> supportPictureSizes = parameters.getSupportedPictureSizes();
//		//		List<Camera.Size> supportPictureSizes = checkSupportedPictureSizeAtPreviewSize(parameters);
//
//		for (Size size : supportPictureSizes) {
//			if (!enforceProfile || (size.height <= Integer.MAX_VALUE && size.height >= 0)) {
//				if (result == null) {
//					result=size;
//				} else {
//					int resultArea = result.width * result.height;
//					int newArea = size.width * size.height;
//
//					if (newArea > resultArea) {
//						result = size;
//					}
//				}
//			}
//		}
//
//		if (result == null && enforceProfile) {
//			result=getLargestPictureSize(host, parameters, false);
//		}
//
//		CameraUtilLog.iLog(TAG, "getLargestPictureSize ======" +   result.width  + " , h= " + result.height);
//		return(result);
//	}


    /**
     * @param parameters
     * @return
     * @MethodName : getSmallestPictureSize
     * @Date : 2016. 3. 28.
     * @author : KILHO
     * @Method ㄴ 작은 사진 사이즈 가져오기
     * @변경이력
     */
    public static Size getSmallestPictureSize(Camera.Parameters parameters) {
        Size result = null;

        for (Size size : parameters.getSupportedPictureSizes()) {
            if (result == null) {
                result = size;
            } else {
                int resultArea = result.width * result.height;
                int newArea = size.width * size.height;

                if (newArea < resultArea) {
                    result = size;
                }
            }
        }

        return (result);
    }

    /**
     * @author : KILHO
     * @ProjectName : CameraLib
     * @FileName : CameraUtils.java
     * @FilePath : com.ullim.cameralib
     * @Date : 2016. 3. 28.
     * @프로그램 ㄴ 정렬
     * @변경이력
     */
    private static class SizeComparator implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            int left = lhs.width * lhs.height;
            int right = rhs.width * rhs.height;

            if (left < right) {
                return (-1);
            } else if (left > right) {
                return (1);
            }

            return (0);
        }
    }
}
