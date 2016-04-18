package com.zyl.androidshareutils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

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

}
