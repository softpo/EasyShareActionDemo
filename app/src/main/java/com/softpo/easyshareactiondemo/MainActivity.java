package com.softpo.easyshareactiondemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int READ_EXTERNAL_STORAGE = 101;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("flag", "----------------->onCreate: ");
        if(PackageManager.PERMISSION_GRANTED==ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode){
            case READ_EXTERNAL_STORAGE:
                Toast.makeText(this, "获得读取Sd卡权限", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 填充菜单
        getMenuInflater().inflate(R.menu.share, menu);

        // 获取菜单条目
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // 获取分享的类
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //确保自己的sk卡中相应目录下包含这个文件
        File rootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Uri image_uri = Uri.fromFile(new File(rootFile, "best.jpg"));
        shareIntent.setType("image/*");

        shareIntent.putExtra(Intent.EXTRA_STREAM,image_uri);
        setShareIntent(shareIntent);
        // 返回true，显示菜单
        return true;
    }
    // 调用该方法，分享数据，我们只需要设置Intent 剩下的事情交给ShareActionProvider
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
