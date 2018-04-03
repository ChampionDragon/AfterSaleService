package com.bs.afterservice.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bs.afterservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 自定义圆形分布统计图
 * AUTHOR: Champion Dragon
 * created at 2018/3/7
 * 参考网址：http://blog.csdn.net/XiFangzheng/article/details/78122127
 **/

public class RingView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mPaintWidth = 0;        // 画笔的宽
    private int topMargin = 0;         // 上边距30
    private int leftMargin = 0;        // 左边距 80
    private Resources mRes;
    private DisplayMetrics dm;
    private int showRateSize = 8; // 展示文字的大小 10

    private int MaxRidus = 66;
    private int Ridus = MaxRidus;
    /*默认MaxRidus为外圆半径，内圆占其1/2,点所在圆的半径占5/6     */
    private int circleCenterX = Ridus;     // 圆心点X  要与外圆半径相等96
    private int circleCenterY = Ridus;     // 圆心点Y  要与外圆半径相等96

    private int ringOuterRidus = Ridus;     // 外圆的半径96
    private int ringInnerRidus = Ridus * 1 / 2;     // 内圆的半径33
    private int ringPointRidus = Ridus * 5 / 6;    // 点所在圆的半径80

    private float rate = 0.2f;     //点的外延距离  与  点所在圆半径的长度比率
    private float extendLineWidth = 2;     //点外延后  折的横线的长度 20

    private RectF rectF;                // 外圆所在的矩形
    private RectF rectFPoint;           // 点所在的矩形

    private List<Integer> colorList;
    private List<Float> rateList;
    private boolean isRing;
    private boolean isShowCenterPoint;
    private boolean isShowRate;

    public RingView(Context context) {
        super(context, null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
//        initView();
    }


    public void setShow(List<Integer> colorList, List<Float> rateList) {
        setShow(colorList, rateList, 40);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, int Ridus) {
        setShow(colorList, rateList, Ridus, true);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, int Ridus, boolean isShowRate) {
        setShow(colorList, rateList, Ridus, isShowRate, false);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, int Ridus, boolean isShowRate, boolean isRing) {
        setShow(colorList, rateList, isShowRate, isRing, false, Ridus);
    }

    /*画图的方向从右向左顺时针旋转 colorList传进来得颜色集合  rateList所占的比例集合（切记和要为100）*/
    //    isRing:是否画内圆     isShowRate：是否显示比例占比的线和点      isShowCenterPoint:是否画圆心  Ridus:自定义半径
    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isShowRate,
                        boolean isRing, boolean isShowCenterPoint, int Ridus) {
        this.colorList = colorList;
        this.rateList = rateList;
        this.isRing = isRing;
        this.isShowRate = isShowRate;
        this.isShowCenterPoint = isShowCenterPoint;
        this.Ridus = Ridus;
    }

    private void initView() {
        this.mRes = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        leftMargin = (px2dip(screenWidth) - (2 * circleCenterX)) / 2;

        mPaint.setColor(ContextCompat.getColor(mContext, R.color.red));
        mPaint.setStrokeWidth(dip2px(mPaintWidth));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        rectF = new RectF(dip2px(mPaintWidth + leftMargin),
                dip2px(mPaintWidth + topMargin),
                dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin));

        rectFPoint = new RectF(dip2px(mPaintWidth + leftMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(mPaintWidth + topMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(circleCenterX + ringPointRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringPointRidus + mPaintWidth * 2 + topMargin));

        Log.e("矩形点:", dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2) + " --- " + dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pointList.clear();

        /*自定义初始化圆的半径*/
        if (Ridus != MaxRidus) {
            circleCenterX = Ridus;
            circleCenterY = Ridus;
            ringOuterRidus = Ridus;
            ringInnerRidus = Ridus * 1 / 2;
            ringPointRidus = Ridus * 5 / 6;
        }

        initView();

        if (colorList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);
                drawOuter(canvas, i);
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        if (isRing) {
            drawInner(canvas);
        }
        if (isShowCenterPoint) {
            drawCenterPoint(canvas);
        }

    }

    /*画圆的中心点*/
    private void drawCenterPoint(Canvas canvas) {
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.red));
//        Log.e("中心点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(1), mPaint);
    }

    /*画内圆*/
    private void drawInner(Canvas canvas) {
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.white));
//        Log.e("内部圆点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(ringInnerRidus), mPaint);
    }

    private float preRate;

    /*画显示比例占比的线和点*/
    private void drawArcCenterPoint(Canvas canvas, int position) {
        mPaint.setStyle(Paint.Style.STROKE);
// 显示比例占比的外部轮廓颜色尽量选透明transparent不然好丑
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.transparent));
        mPaint.setStrokeWidth(dip2px(1));
        canvas.drawArc(rectFPoint, preAngle, (endAngle) / 2, true, mPaint);
        dealPoint(rectFPoint, preAngle, (endAngle) / 2, pointArcCenterList);
        Point point = pointArcCenterList.get(position);
//       显示比例占比的圆点的颜色
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.gold));
        canvas.drawCircle(point.x, point.y, dip2px(2), mPaint);
/*当比率很小时调大折线角度   preRate / 2 + rateList.get(position) / 2   */
        if (rateList.get(position) < 15) {
            extendLineWidth = 11;//线长度大小 20
            rate = 0.15f;//0.05
        } else if (rateList.get(position) > 50) {
//            Logs.i(rateList.get(position) + " 占比");
            extendLineWidth = 11;
            rate = 0.3f;
        } else {
            extendLineWidth = 8;
            rate = 0.2f;//0.4f
        }

        // 外延画折线
        float lineXPoint1 = (point.x - dip2px(leftMargin + ringOuterRidus)) * (1 + rate);
        float lineYPoint1 = (point.y - dip2px(topMargin + ringOuterRidus)) * (1 + rate);

        float[] floats = new float[8];
        floats[0] = point.x;
        floats[1] = point.y;
        floats[2] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
        floats[3] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        floats[4] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
        floats[5] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        if (point.x >= dip2px(leftMargin + ringOuterRidus)) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 + dip2px(extendLineWidth);
        } else {
            mPaint.setTextAlign(Paint.Align.RIGHT);
            floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 - dip2px(extendLineWidth);
        }
        floats[7] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        mPaint.setColor(mRes.getColor(colorList.get(position)));
        canvas.drawLines(floats, mPaint);
        mPaint.setTextSize(dip2px(showRateSize));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(rateList.get(position) + "%", floats[6], floats[7] + dip2px(showRateSize) / 3, mPaint);
        preRate = rateList.get(position);
    }

    List<Point> pointList = new ArrayList<>();
    List<Point> pointArcCenterList = new ArrayList<>();

    private void dealPoint(RectF rectF, float startAngle, float endAngle, List<Point> pointList) {
        Path orbit = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        orbit.addArc(rectF, startAngle, endAngle);

        PathMeasure measure = new PathMeasure(orbit, false);
        Log.e("路径的测量长度:", "" + measure.getLength());

        float[] coords = new float[]{0f, 0f};
        //利用PathMeasure分别测量出各个点的坐标值coords
        int divisor = 1;
        measure.getPosTan(measure.getLength() / divisor, coords, null);
        Log.e("coords:", "x轴:" + coords[0] + " -- y轴:" + coords[1]);
        float x = coords[0];
        float y = coords[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointList.add(point);
    }

    private void drawOuter(Canvas canvas, int position) {
//        canvas.drawCircle(circleCenterX, circleCenterY, ringInnerRidus, mPaint);
        if (rateList != null) {
            endAngle = getAngle(rateList.get(position));
        }
//        Log.e("preAngle:", "" + preAngle + "   endAngle:" + endAngle);
        canvas.drawArc(rectF, preAngle, endAngle, true, mPaint);
//        dealPoint(rectF, preAngle, endAngle, pointList);

        if (isShowRate) {
            drawArcCenterPoint(canvas, position);
        }

        preAngle = preAngle + endAngle;
    }

    private float preAngle = -90;
    private float endAngle = -90;

    /**
     * @param percent 百分比
     * @return
     */
    private float getAngle(float percent) {
        float a = 360f / 100f * percent;
        return a;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / dm.density + 0.5f);
    }

}
