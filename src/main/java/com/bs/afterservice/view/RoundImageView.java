package com.bs.afterservice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bs.afterservice.R;

/**
 * Description: 圆形View自定义类
 * AUTHOR: Champion Dragon
 * created at 2018/2/25
 **/

public class RoundImageView extends ImageView {
    private int mBorderThickness = 0;
    private Context mContext;
    private int defaultColor = 0xFFFFFF;
    // 如果只有其中一个值，则只画一个圆的边框
    private int mBorderOutsideColor = 0;// 圆外边界颜色
    private int mBorderInsideColor = 0;// 圆内边界颜色
    // 控制默认长宽
    private int defaultWidth = 0;
    private int defaultHeight = 0;

    public RoundImageView(Context context) {
        super(context);
        mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCustomAttributes(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setCustomAttributes(attrs);
    }

    /**
     * 设置默认属性
     */
    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);
        if (typedArray != null) {
            mBorderInsideColor = typedArray.getColor(
                    R.styleable.RoundImageView_border_inside_color,
                    defaultColor);
            mBorderOutsideColor = typedArray.getColor(
                    R.styleable.RoundImageView_border_outside_color,
                    defaultColor);
            // 边界宽度
            mBorderThickness = typedArray.getDimensionPixelSize(
                    R.styleable.RoundImageView_border_thickness, 0);
            typedArray.recycle();
        }
    }

    /**
     * 画自定义组件的图
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null || getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);
        if (drawable.getClass() == NinePatchDrawable.class) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }
        if (defaultHeight == 0) {
            defaultHeight = getHeight();
        }
        int radius;
        // 定义画两个边框，内边框和外边框
        if (mBorderInsideColor != defaultColor
                && mBorderOutsideColor != defaultColor) {
            radius = (defaultWidth < defaultHeight ? defaultWidth
                    : defaultHeight) / 2 - 2 * mBorderThickness;
            // 画内圆
            drawCircleBorder(canvas, radius + mBorderThickness / 2,
                    mBorderInsideColor);
            // 画外圆
            drawCircleBorder(canvas, radius + mBorderThickness * 3 / 2,
                    mBorderOutsideColor);
        } else if (mBorderOutsideColor == defaultColor
                && mBorderInsideColor != defaultColor) {
            radius = (defaultHeight < defaultWidth ? defaultHeight
                    : defaultWidth) / 2 - mBorderThickness;
            drawCircleBorder(canvas, radius + mBorderThickness / 2,
                    mBorderInsideColor);
        } else if (mBorderOutsideColor != defaultColor
                && mBorderInsideColor == defaultColor) {
            radius = (defaultHeight < defaultWidth ? defaultHeight
                    : defaultWidth) / 2 - mBorderThickness;
            drawCircleBorder(canvas, radius + mBorderThickness / 2,
                    mBorderOutsideColor);
        } else {
            radius = (defaultWidth < defaultHeight ? defaultWidth
                    : defaultHeight) / 2;
        }
        Bitmap roundBitmap = getCropRoundBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight
                / 2 - radius, null);
    }

    /**
     * 获取裁剪后的圆形图片
     */
    public Bitmap getCropRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;// 图片解码时使用的颜色模式，也就是图片中每个像素颜色的表示方式
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        // 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中间位置最大的正方形图片
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        int squareWidth = 0, squareHeight = 0;
        int x = 0, y = 0;
//		Log.i("lcb", "bmpWidth  " + bmpWidth + "      bmpHeight  " + bmpHeight);
        Bitmap squareBitmap;

        if (bmpHeight > bmpWidth) {
            squareHeight = squareWidth = bmpWidth;
            x = 0;
            y = (bmpHeight - bmpWidth) / 2;
            // 截取正方形图片
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else if (bmpHeight < bmpWidth) {
            squareHeight = squareWidth = bmpHeight;
            x = (bmpWidth - bmpHeight) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else {
            squareBitmap = bmp;
        }
        // 将截取得图片按一定比例缩小生成新的图片
        if (squareBitmap.getWidth() != diameter
                || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
                    diameter, true);
        } else {
            scaledSrcBmp = squareBitmap;
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
                paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        // bitmap回收
        bmp.recycle();
        squareBitmap.recycle();
        scaledSrcBmp.recycle();
        bmp = null;
        scaledSrcBmp = null;
        squareBitmap = null;

        return output;
    }

    /**
     * 边缘画圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
    }
}
