package com.mobile.centaur.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

import com.app.mobile.centaur.R;
import com.mobile.centaur.utils.DensityUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jane on 2020/9/21.
 */

public class BottomTabNavigation extends LinearLayout implements View.OnClickListener {
    public static final String TAB_NONE = "none";
    public static final String TAB_HOME = "home";
    public static final String TAB_COURSE = "course";
    public static final String TAB_MESSAGE = "Message";
    public static final String TAB_USER_CENTER = "user_center";
    private Context mContext;
    private List<TabItem> tabList = new ArrayList<>();
    private TabSelectedListener tabSelectedListener;
    private String selectedTabType = TAB_NONE;

    @StringDef({TAB_HOME, TAB_COURSE, TAB_MESSAGE, TAB_USER_CENTER, TAB_NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BottomTabType {
    }

    public BottomTabNavigation(@NonNull Context context) {
        this(context, null);
    }

    public BottomTabNavigation(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabNavigation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BottomTabNavigation(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
    }

//    public void initView(){
//        LayoutInflater inflater=LayoutInflater.from(mContext);
//        View view=inflater.inflate(R.layout.layout_bottom_tab,null);
//        addView(view);
//    }

    public void setTabItems(List<TabItem> tabList) {
        removeAllViews();
        this.tabList = tabList;
        if (null == tabList || tabList.size() == 0)
            return;
        addView(getSideSpaceView());
        for (int i = 0; i < tabList.size(); i++) {
            TabItem tab = tabList.get(i);
            TabItemView tabItemView = new TabItemView(mContext)
                    .setTabName(tab.getName())
                    .setStaticIcon(tab.getStaticRes())
                    .setAnimateIcon(tab.getAnimateRes());
            tabItemView.setOnClickListener(this);
            tabItemView.setTag(tab.getTabType());
            addView(tabItemView);
            if (i != tabList.size() - 1) {
                addView(getSpaceView());
            } else {
                addView(getSideSpaceView());
            }
        }

        if (selectedTabType.equals(TAB_NONE)) {
            getChildAt(1).performClick();
        }
    }

    private Space getSpaceView() {
        Space space = new Space(mContext);
        space.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        return space;
    }

    private Space getSideSpaceView() {
        Space space = new Space(mContext);
        space.setLayoutParams(new LayoutParams(DensityUtil.dp2px(mContext, getResources().getDimension(R.dimen.dimens_size_6)), LayoutParams.MATCH_PARENT));
        return space;
    }

    @Override
    public void onClick(View v) {
        if (null == v.getTag())
            return;
        String tabType = (String) v.getTag();
        if (tabType.equals(selectedTabType))
            return;
        if (null != tabSelectedListener) {
            this.selectedTabType = tabType;
            for (int i = 0; i < tabList.size(); i++) {
                TabItem tab = tabList.get(i);
                if (tabType.equals(tab.getTabType())) {
                    TabItemView tabView = (TabItemView) v;
                    tabView.setTabSelected(true);
                    tabSelectedListener.tabSelected(tabView);
                } else {
                    TabItemView tabView = (TabItemView) getChildAt(i * 2 + 1);
                    tabSelectedListener.tabUnselected(tabView);
                    tabView.setTabSelected(false);
                }
            }
        }
    }

    public void addOnTabSelectedListener(TabSelectedListener selectedListener) {
        this.tabSelectedListener = selectedListener;
    }

    public interface TabSelectedListener {
        void tabSelected(TabItemView itemView);

        void tabUnselected(TabItemView itemView);
    }
}
