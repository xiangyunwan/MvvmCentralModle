package com.zhangzhenzhong1.tools.utils;

import android.os.AsyncTask;
import android.widget.Toast;

import com.zhangzhenzhong1.tools.R;
import com.zhangzhenzhong1.tools.app.app;

import java.io.File;

/**
 *  清除缓存
 *
 *
 *  调用方式：new ClearCacheTask().execute();
 */
public class ClearCacheTask extends AsyncTask<Void, Object, Boolean>
{

    @Override
    protected Boolean doInBackground(Void... params)
    {
        deleteFolderFile(app.getContext().getCacheDir(), false);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        Toast.makeText(app.getContext(),"清除完成",Toast.LENGTH_SHORT).show();
    }

    public void deleteFolderFile(File file, boolean deleteThisPath)
    {
        try
        {
            if (file.isDirectory())
            {// 处理目录
                File files[] = file.listFiles();
                for(int i = 0; i < files.length; i++)
                {
                    deleteFolderFile(files[i], true);
                }
            }
            if (deleteThisPath)
            {
                if (!file.isDirectory())
                {// 如果是文件，删除
                    file.delete();
                } else
                {// 目录
                    if (file.listFiles().length == 0)
                    {// 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
