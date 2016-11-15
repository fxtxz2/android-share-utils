package com.zyl.androidshareutils;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 社会化分享工具:微信好友,微信朋友圈,微博,qq空间(必须已经安装qq空间客户端),qq好友
 * Created by zyl on 16/4/18.
 */
public class ShareUtils {

    /**
     * 微信主包名
     */
    private static final String WEIXIN_PACKAGE = "com.tencent.mm";
    /**
     * 发送图片给微信好友
     */
    private static final String WEIXIN_SHARE_IMAGE_FRIEND = "com.tencent.mm.ui.tools.ShareImgUI";
    /**
     * 微信朋友圈
     */
    private static final String WEIXIN_TIME_LINE = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    /**
     * 图片MIME
     */
    private static final String MIME_IMAGE = "image/*";
    /**
     * 微信朋友圈图片
     */
    private static final String WEIXIN_TIME_LINE_CONTENT = "Kdescription";
    /**
     * 文本MIME
     */
    private static final String MIME_TEXT = "text/plain";
    /**
     * 新浪主包名
     */
    private static final String SINA_PACKAGE = "com.sina.weibo";
    /**
     * 新浪多图分享
     */
    private static final String SINA_DISPATCH = "com.sina.weibo.composerinde.ComposerDispatchActivity";
    /**
     * qq空间主包名
     */
    private static final String QZONE_PACKAGE = "com.qzone";
    /**
     * qq空间图片分享
     */
    private static final String QZONE_IMAGE = "com.qzonex.module.operation.ui.QZonePublishMoodActivity";
    /**
     * qq主包名
     */
    private static final String QQ_PACKAGE = "com.tencent.mobileqq";
    /**
     * qq发送到分享
     */
    private static final String QQ_JUMP = "com.tencent.mobileqq.activity.JumpActivity";


    private ShareUtils(){}
    private static class ShareUtilsHolder{
        private final static ShareUtils INSTANCE = new ShareUtils();
    }
    public static ShareUtils getInstance(){
        return ShareUtilsHolder.INSTANCE;
    }

    /**
     * 分享图片给微信好友
     * @param content 内容
     * @param context 当前上下文
     */
    public void shareToWeiXinFriend(String content, Context context) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(WEIXIN_PACKAGE, WEIXIN_SHARE_IMAGE_FRIEND);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(MIME_TEXT);
//        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    /**
     * 分享图片给微信好友
     * @param file 文件
     * @param context 当前上下文
     */
    public void shareToWeiXinFriend(File file, Context context) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(WEIXIN_PACKAGE, WEIXIN_SHARE_IMAGE_FRIEND);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(MIME_IMAGE);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    /**
     * 分享多图到朋友圈,多张图片加文字
     *
     * @param content  分享图片描述
     * @param uris 文件
     * @param context 当前上下文
     */
    public void shareToWeiXinTimeLine(String content, ArrayList<Uri> uris, Context context) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(WEIXIN_PACKAGE, WEIXIN_TIME_LINE);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(MIME_IMAGE);
        intent.putExtra(WEIXIN_TIME_LINE_CONTENT, content);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    /**
     * 分享到新浪微博文本
     * @param context 当前上下文
     * @param content 分享内容
     */
    public void shareToSinaText(String content, Context context){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType(MIME_TEXT);
        // 直接去新浪
        sendIntent.setPackage(SINA_PACKAGE);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(sendIntent);
    }


    /**
     * 分享多图到新浪微博,多张图片加文字
     * @param content 分享内容
     * @param uris 文件
     * @param context 当前上下文
     */
    public void shareToSinaImage(String content, ArrayList<Uri> uris, Context context) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(SINA_PACKAGE, SINA_DISPATCH);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(MIME_IMAGE);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (ClassCastException e) {
            // Key android.intent.extra.TEXT expected ArrayList<CharSequence> but value was a java.lang.String.  The default value <null> was returned.
            e.printStackTrace();
        }
    }

    /**
     * qq空间文本分享
     * @param content 分享文本
     * @param context 当前上下文
     */
    public void shareToQzoneText(String content, Context context){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType(MIME_TEXT);
        // 直接去qq空间
        sendIntent.setPackage(QZONE_PACKAGE);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(sendIntent);
    }

    /**
     * 分享多图到qq空间,多张图片加文字
     * @param content 分享文本
     * @param uris 文件
     * @param context 当前上下文
     */
    public void shareToQzoneImage(String content, ArrayList<Uri> uris, Context context) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(QZONE_PACKAGE, QZONE_IMAGE);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(MIME_IMAGE);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (ClassCastException e) {
            // Key android.intent.extra.TEXT expected ArrayList<CharSequence> but value was a java.lang.String.  The default value <null> was returned.
            e.printStackTrace();
        }
    }

    /**
     * 分享到qq多张图片
     * @param uris 分享多图片
     * @param context 当前上下文
     */
    public void shareToQQImage(ArrayList<Uri> uris, Context context) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(QQ_PACKAGE, QQ_JUMP);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(MIME_IMAGE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (ClassCastException e) {
            // Key android.intent.extra.TEXT expected ArrayList<CharSequence> but value was a java.lang.String.  The default value <null> was returned.
            e.printStackTrace();
        }
    }

    /**
     * 分享到qq文本
     * @param content 分享文本内容
     * @param context 当前上下文
     */
    public void shareToQQText(String content, Context context){
        Intent sendIntent = new Intent();
        ComponentName comp = new ComponentName(QQ_PACKAGE, QQ_JUMP);// 发送到
        sendIntent.setComponent(comp);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType(MIME_TEXT);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(sendIntent);
    }

    public void shareNative(final Context context, String imageUrl, final String content, String downloadDir) {
        if (!TextUtils.isEmpty(imageUrl)){
            String fileName = FilenameUtils.getName(imageUrl);//检索文件名
            final File file = new File(downloadDir + fileName);
            if (!fileIsExists(file.getPath())){
                FileDownloader.getImpl().create(imageUrl)
                        .setPath(file.getPath())
                        .setListener(new FileDownloadLargeFileListener() {
                            @Override
                            protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                            }

                            @Override
                            protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                            }

                            @Override
                            protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                // 重新实现系统分享
                                ArrayList<Uri> uris = new ArrayList<>();
                                if (file.exists()){
                                    uris.add(Uri.fromFile(file));
                                }
                                shareDialog(context, content, uris);
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {

                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {

                            }
                        }).start();
            } else {
                // 重新实现系统分享
                ArrayList<Uri> uris = new ArrayList<>();
                if (file.exists()){
                    uris.add(Uri.fromFile(file));
                }
                shareDialog(context, content, uris);
            }
        }
    }

    public void shareNative(final Context context, List<String> imageUrls, final String content, String downloadDir) {
        final int size = imageUrls.size();
        final ArrayList<Uri> uris = new ArrayList<>();
        final FileDownloadListener queueTarget =new FileDownloadLargeFileListener() {
            @Override
            protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                Log.i("file:", task.getUrl());
            }

            @Override
            protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                Log.i("file:", task.getUrl());
            }

            @Override
            protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                Log.i("file:", task.getUrl());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                File file = new File(task.getPath());
                if (file.exists()){
                    uris.add(Uri.fromFile(file));
                }
                // 判断是否全部突破全部下载完成，并实现系统分享
                int endSize = uris.size();
                if (endSize == size){
                    shareDialog(context, content, uris);
                }

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.i("file:", e.getMessage());

            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.i("file:", task.getUrl());
            }
        };
        for (int i = 0; i < size; i++) {
            String imageUrl = imageUrls.get(i);
            if (!TextUtils.isEmpty(imageUrl)){
                String fileName = FilenameUtils.getName(imageUrl);//检索文件名
                final File file = new File(downloadDir + fileName);
                if (!fileIsExists(file.getPath())){
                    FileDownloader.getImpl().create(imageUrl)
                            .setPath(file.getPath())
                            .setCallbackProgressTimes(0)
                            .setListener(queueTarget)
                            .asInQueueTask()
                            .enqueue();
                } else {
                    if (file.exists()){
                        uris.add(Uri.fromFile(file));
                    }
                }
            }
        }
        FileDownloader.getImpl().start(queueTarget, true);
        // 判断是否全部突破全部下载完成，并实现系统分享
        int endSize = uris.size();
        if (endSize == size){
            shareDialog(context, content, uris);
        }
    }

    public Dialog shareDialog(final Context context, final String content, final ArrayList<Uri> uris){
        Dialog dialog = new Dialog(context, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.share_dialog, null);
        dialog.getWindow();
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
        TextView wechat = (TextView)dialogView.findViewById(R.id.wechat);
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPkgInstalled("com.tencent.mm", context)){
                    ShareUtils.getInstance().shareToWeiXinFriend(content, context);
                } else {
                    Toast.makeText(context, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView wechatmonments = (TextView)dialogView.findViewById(R.id.wechatmonments);
        wechatmonments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPkgInstalled("com.tencent.mm", context)){
                    if (uris.isEmpty()) {
                        Toast.makeText(context, "请先下载图片", Toast.LENGTH_SHORT).show();
                    } else {
                        ShareUtils.getInstance().shareToWeiXinTimeLine(content, uris, context);
                    }
                } else {
                    Toast.makeText(context, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView qq = (TextView)dialogView.findViewById(R.id.qq);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPkgInstalled("com.tencent.mobileqq", context)){
                    ShareUtils.getInstance().shareToQQText(content, context);
                } else {
                    Toast.makeText(context, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView qzone = (TextView)dialogView.findViewById(R.id.qzone);
        qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPkgInstalled("com.qzone", context)){
                    if (uris.isEmpty()) {
                        Toast.makeText(context, "请先下载图片", Toast.LENGTH_SHORT).show();
                    } else {
                        ShareUtils.getInstance().shareToQzoneImage(content, uris, context);
                    }
                } else {
                    Toast.makeText(context, "请安装QQ空间客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return dialog;
    }

    /**
     * 判断文件是否存在
     * @param filePath 保持文件路径
     * @return true表示文件存在；false表示文件不存在
     */
    public static boolean fileIsExists(String filePath){
        try{
            File f=new File(filePath);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            //: handle exception
            return false;
        }
        return true;
    }
    /**
     * 检测apk是否已安装
     * @param pkgName 检查的主包名
     * @param context 当前上下文
     * @return true 表示已经安装；false表示未安装
     */
    public static boolean isPkgInstalled(String pkgName, Context context) {
        synchronized (ShareUtils.class) {
            // 分析 Package manager has died http://www.lai18.com/content/2402015.html
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
