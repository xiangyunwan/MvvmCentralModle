package my.test.myapplication;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Created by YiVjay
 * on 2020/8/25
 */
public class InitIntent extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitIntent(String name) {
        super("InitIntent");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }
}
