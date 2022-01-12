package com.mobile.centaur.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 解决SrollView中嵌套Listview数据显示不全问题
 * @version 1.0
 *
 */
public class SListView extends ListView implements RecyclerListener {
	public SListView(Context context) {
		super(context);
	}

	public SListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	@Override
	public void onMovedToScrapHeap(View arg0) {
//		reclayBitmap(arg0);
	}
//mImageView.setImageResource(R.drawable.shape_common_default);
			
	private void reclayBitmap(View arg0){
		if(arg0 instanceof ViewGroup) {
			for(int i = 0; i < ((ViewGroup) arg0).getChildCount(); i++) {
				View v = ((ViewGroup)arg0).getChildAt(i);
				if(v instanceof ViewGroup)
					reclayBitmap(v);
				else if(v instanceof ImageView) {
					reclay(v);
				}
			}
		} else if(arg0 instanceof ImageView) {
			reclay(arg0);
		}
	}
	
	private void reclay(View v){
		Drawable mDrawable = ((ImageView) v).getDrawable();
		if(mDrawable instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable)mDrawable).getBitmap();
			if(bitmap!=null && !bitmap.isRecycled()){
				bitmap.recycle();
			}
		}
	}
}
