package com.mobile.centaur.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import cn.jianke.api.utils.DensityUtil;
import cn.jianke.hospital.R;

/**
 * Created by Hu on 2017/1/3.
 */

public class LineEditText extends androidx.appcompat.widget.AppCompatEditText {
    // 画笔 用来画下划线
    private Paint paint;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),0.5f));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.font_gray));
        // 开启抗锯齿 较耗内存
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setSingleLine();
        // 得到总行数
        int lineCount = getLineCount();
        // 得到每行的高度
//        int lineHeight = getLineHeight();
        int lineHeight = getHeight();
        // 根据行 数循环画线
        canvas.drawLine(0, lineHeight, this.getWidth(), lineHeight, paint);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        // 得到总行数
//        int lineCount = getLineCount();
//        // 得到每行的高度
////        int lineHeight = getLineHeight();
//        int lineHeight = getHeight();
//        // 根据行数循环画线
//        for (int i = 0; i < lineCount; i++) {
//            int lineY = (i + 1) * lineHeight;
//            canvas.drawLine(0, lineY, this.getWidth(), lineY, paint);
//        }
//    }
}