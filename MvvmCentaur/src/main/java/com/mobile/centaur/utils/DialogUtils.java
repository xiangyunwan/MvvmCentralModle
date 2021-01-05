package com.mobile.centaur.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.app.mobile.centaur.R;
import com.mobile.centaur.base.BaseConstants;

/**
 * author:zzz
 * date:2020/4/22
 * Description:
 **/
public class DialogUtils {

    static AlertDialog onButtondiaglog;
    static AlertDialog onConfirmdiaglog;
    static AlertDialog onSelectdiaglog;
    static AlertDialog twoButtondiaglog;
    static AlertDialog progressDiaglog;

    private static DialogUtils dialogUtils;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }
//    private OnDialogOnclick onDialogOnclick;

    /**
     * 有取消和确定按钮的弹出框
     *
     * @param activity
     * @param title
     * @param content
     * @param onDialogOnclick
     */
    public void showTwoButtonDialog(Activity activity, @NonNull String title, @NonNull String content, final OnDialogOnclick onDialogOnclick) {
        if (twoButtondiaglog != null) {
            twoButtondiaglog.show();
            return;
        }
//        this.onDialogOnclick=onDialogOnclick;
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(activity, R.style.yzDialog);
        final View view = LayoutInflater.from(activity).inflate(R.layout.layout_twobt_dialog, null);
        alterDiaglog.setView(view);//加载进去
        twoButtondiaglog = alterDiaglog.create();

        Window window = twoButtondiaglog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                window.getDecorView().setBackground(activity.getResources().getDrawable(res));
//            }
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            WindowManager.LayoutParams lp = twoButtondiaglog.getWindow()
                    .getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = display.getWidth() * 4 / 5;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            twoButtondiaglog.getWindow().setAttributes(lp);
//            diaglog.setCancelable(touchCancel);
//            diaglog.setCanceledOnTouchOutside(touchCancel);
//            window.setWindowAnimations(R.style.alertDialogStyle01);
        }


        TextView tvTitle = view.findViewById(R.id.tv_dialog_twobt_tile);
        TextView tvContent = view.findViewById(R.id.tv_dialog_twobt_content);
        tvTitle.setText(title);
        tvContent.setText(content);

        Button bt_twobt_cancle = view.findViewById(R.id.bt_twobt_cancle);
        Button bt_twobt_confirm = view.findViewById(R.id.bt_twobt_confirm);

        if (onDialogOnclick != null) {
            bt_twobt_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDialogOnclick.onCancle();
                }
            });
            bt_twobt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDialogOnclick.onConfirm();
                }
            });
        }


        //显示
        twoButtondiaglog.show();
    }

    /**
     * 只有确定按钮的弹出框
     *
     * @param activity
     * @param title
     * @param content
     * @param onDialogOnclick
     */
    public void showOneButtonDialog(Activity activity, @NonNull String title, @NonNull String content, final OnDialogOnclick onDialogOnclick) {
        if (onButtondiaglog != null) {
            onButtondiaglog.show();
            return;
        }
//        this.onDialogOnclick=onDialogOnclick;
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(activity, R.style.yzDialog);
        final View view = LayoutInflater.from(activity).inflate(R.layout.layout_onebt_dialog, null);
        alterDiaglog.setView(view);//加载进去
        onButtondiaglog = alterDiaglog.create();

        Window window = onButtondiaglog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                window.getDecorView().setBackground(activity.getResources().getDrawable(res));
//            }
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            WindowManager.LayoutParams lp = onButtondiaglog.getWindow()
                    .getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = display.getWidth() * 4 / 5;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            onButtondiaglog.getWindow().setAttributes(lp);
//            diaglog.setCancelable(touchCancel);
//            diaglog.setCanceledOnTouchOutside(touchCancel);
//            window.setWindowAnimations(R.style.alertDialogStyle01);
        }


        TextView tvTitle = view.findViewById(R.id.tv_dialog_onebt_tile);
        TextView tvContent = view.findViewById(R.id.tv_dialog_onebt_content);
        tvTitle.setText(title);
        tvContent.setText(content);

//        Button bt_twobt_cancle=view.findViewById(R.id.bt_onebt_cancle);
        Button bt_twobt_confirm = view.findViewById(R.id.bt_onebt_confirm);

        if (onDialogOnclick != null) {
//            if (bt_twobt_cancle!=null){
//                bt_twobt_cancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onDialogOnclick.onCancle();
//                    }
//                });
//            }
            if (bt_twobt_confirm != null) {
                bt_twobt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogOnclick.onConfirm();
                    }
                });
            }
        }
        //显示
        onButtondiaglog.show();
    }

    /**
     * 只有确定按钮的弹出框
     *
     * @param activity
     * @param title
     * @param content
     * @param onDialogOnclick
     */
    public void showComfirmDialog(Activity activity, @NonNull String title, @NonNull String content, final OnDialogConfirmClick onDialogOnclick) {
        if (onConfirmdiaglog != null) {
            onConfirmdiaglog.show();
            return;
        }
//        this.onDialogOnclick=onDialogOnclick;
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(activity, R.style.yzDialog);
        final View view = LayoutInflater.from(activity).inflate(R.layout.layout_confirm_dialog, null);
        alterDiaglog.setView(view);//加载进去
        onConfirmdiaglog = alterDiaglog.create();

        Window window = onConfirmdiaglog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                window.getDecorView().setBackground(activity.getResources().getDrawable(res));
//            }
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            WindowManager.LayoutParams lp = onConfirmdiaglog.getWindow()
                    .getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = display.getWidth() * 4 / 5;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            onConfirmdiaglog.getWindow().setAttributes(lp);
            onConfirmdiaglog.setCancelable(false);
            onConfirmdiaglog.setCanceledOnTouchOutside(false);
//            window.setWindowAnimations(R.style.alertDialogStyle01);
        }


        TextView tvTitle = view.findViewById(R.id.tv_dialog_confirm_tile);
        TextView tvContent = view.findViewById(R.id.tv_dialog_confirm_content);
        tvTitle.setText(title);
        tvContent.setText(content);

//        Button bt_twobt_cancle=view.findViewById(R.id.bt_onebt_cancle);
        Button bt_twobt_confirm = view.findViewById(R.id.bt_onebt_confirm);

        if (onDialogOnclick != null) {
            if (bt_twobt_confirm != null) {
                bt_twobt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onDialogOnclick.onConfirm();
                    }
                });
            }
        }
        //显示
        onConfirmdiaglog.show();
    }

    /**
     * 只有确定按钮的弹出框
     *
     * @param activity
     * @param title
     * @param onSelectDialogOnclick
     */
    public void showSelectDialog(Activity activity, @NonNull String title, final CompoundButton.OnCheckedChangeListener checkedChangeListener, final OnSelectDialogOnclick onSelectDialogOnclick) {
        if (activity != null && onSelectdiaglog != null) {
            onSelectdiaglog.show();
            return;
        }
//        this.onDialogOnclick=onDialogOnclick;
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(activity, R.style.yzDialog);
        final View view = LayoutInflater.from(activity).inflate(R.layout.layout_select_dialog, null);
        alterDiaglog.setView(view);//加载进去
        onSelectdiaglog = alterDiaglog.create();

        Window window = onSelectdiaglog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            WindowManager.LayoutParams lp = onSelectdiaglog.getWindow()
                    .getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = display.getWidth();
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            onSelectdiaglog.getWindow().setAttributes(lp);
//            diaglog.setCancelable(touchCancel);
//            diaglog.setCanceledOnTouchOutside(touchCancel);
//            window.setWindowAnimations(R.style.alertDialogStyle01);
        }


        TextView tvTitle = view.findViewById(R.id.tv_dialog_onebt_tile);
//        TextView tvContent = view.findViewById(R.id.tv_dialog_onebt_content);
        tvTitle.setText(title);
//        tvContent.setText(content);

        Button tv_dialog_select_left = view.findViewById(R.id.tv_dialog_select_left);
        Button tv_dialog_select_right = view.findViewById(R.id.tv_dialog_select_right);
        Button bt_twobt_confirm = view.findViewById(R.id.bt_onebt_confirm);
        CheckBox cb_auto = view.findViewById(R.id.cb_auto);
        boolean isAuto = (Boolean) SpUtils.get(BaseConstants.ISAUTO, false);
        cb_auto.setChecked(isAuto);

        String zuotiType = (String) SpUtils.get(BaseConstants.ZUOTI_TYPE, "");
        if (zuotiType.equals("开卷刷题")) {
            tv_dialog_select_right.setTextColor(Color.parseColor("#d43c33"));
        }

        if (onSelectDialogOnclick != null) {
            if (tv_dialog_select_left != null) {
                tv_dialog_select_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSelectDialogOnclick.onLeft();
                    }
                });
            }
            if (cb_auto != null && checkedChangeListener != null) {
                cb_auto.setOnCheckedChangeListener(checkedChangeListener);
            }
            if (tv_dialog_select_right != null) {
                tv_dialog_select_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv_dialog_select_right.setTextColor(Color.parseColor("#d43c33"));
                        onSelectDialogOnclick.onRight();
                    }
                });
            }
            if (bt_twobt_confirm != null) {
                bt_twobt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSelectDialogOnclick.onConfirm();
                    }
                });
            }
            onSelectdiaglog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onSelectDialogOnclick.onCancle();
                }
            });
        }
        //显示
        onSelectdiaglog.show();
    }

    /**
     * 只有确定按钮的弹出框
     *
     * @param activity
     */
    public void showProgressDialog(Activity activity) {
        if (progressDiaglog != null) {
            progressDiaglog.show();
            return;
        }
//        this.onDialogOnclick=onDialogOnclick;
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(activity, R.style.yzProgressDialog);
        final View view = LayoutInflater.from(activity).inflate(R.layout.layout_progress_dialog, null);
        alterDiaglog.setView(view);//加载进去
        progressDiaglog = alterDiaglog.create();

        Window window = progressDiaglog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                window.getDecorView().setBackground(activity.getResources().getDrawable(res));
//            }
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            WindowManager.LayoutParams lp = progressDiaglog.getWindow()
                    .getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = DensityUtil.dp2px(activity, 95);
            lp.height = DensityUtil.dp2px(activity, 95);
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            progressDiaglog.getWindow().setAttributes(lp);
            progressDiaglog.setCancelable(false);
            progressDiaglog.setCanceledOnTouchOutside(false);
//            window.setWindowAnimations(R.style.alertDialogStyle01);
        }

        ImageView tv_dialog_progress = view.findViewById(R.id.tv_dialog_progress);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_dialog_progress, "rotation", 360f);
        objectAnimator.setDuration(500);

        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);//匀速
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
        //显示
        progressDiaglog.show();
    }

    public static void dismissOnBtDialog() {
        if (onButtondiaglog != null) {
            onButtondiaglog.dismiss();
            onButtondiaglog = null;
        }
    }

    public static void dismissTwoBtDialog() {
        if (twoButtondiaglog != null) {
            twoButtondiaglog.dismiss();
            twoButtondiaglog = null;
        }
    }

    public static void dismissProgressDiaglog() {
        if (progressDiaglog != null) {
            progressDiaglog.dismiss();
            progressDiaglog = null;
        }
    }

    public static void dismissSelectDiaglog() {
        if (onSelectdiaglog != null) {
            onSelectdiaglog.dismiss();
            onSelectdiaglog = null;
        }
    }

    public static void dismisConfirmDiaglog() {
        if (onConfirmdiaglog != null) {
            onConfirmdiaglog.dismiss();
            onConfirmdiaglog = null;
        }
    }

    public interface OnDialogOnclick {
        void onCancle();

        void onConfirm();
    }

    public interface OnDialogConfirmClick {
        void onConfirm();
    }

    public interface OnSelectDialogOnclick {
        void onCancle();

        void onConfirm();

        void onLeft();

        void onRight();
    }
}
