# android-share-utils
社会化分享工具:微信好友,微信朋友圈,微博,qq空间(必须已经安装qq空间客户端),qq好友 [参考这篇文章](http://www.eoeandroid.com/thread-288401-1-1.html)
主要使用了Google的[ClassyShark](https://github.com/google/android-classyshark)工具

# 指南
## 分享图片给微信好友
```Java
ShareUtils.getInstance().shareToWeiXinFriend(file, MainActivity.this);
```
file:图片文件对象；
MainActivity.this:当前上下文
## 分享到微信朋友圈
```Java
ArrayList<Uri> uris = new ArrayList<>();
for (String url : urls) {
    File file = getFileByUrl(url);

    if (file.exists()) {
        uris.add(Uri.fromFile(file));
    }
}
if (uris.isEmpty()) {
    Toast.makeText(MainActivity.this, "请先下载图片", Toast.LENGTH_SHORT).show();
} else {
    String content = "https://www.baidu.com/";
    ShareUtils.getInstance().shareToWeiXinTimeLine(content, uris, MainActivity.this);
}

private File getFileByUrl(String url) {
    String filename = MD5Utils.md5(url);
    return new File(getExternalCacheDir(), filename);
}
```
主要代码：
```Java
ShareUtils.getInstance().shareToWeiXinTimeLine(content, uris, MainActivity.this);
```
## 分享文本到新浪微博
```Java
String title = "https://www.baidu.com/";
ShareUtils.getInstance().shareToSinaText(title, MainActivity.this);
```
## 分享文本多图到新浪微博
```Java
ArrayList<Uri> uris = new ArrayList<>();
for (String url : urls) {
    File file = getFileByUrl(url);

    if (file.exists()) {
        uris.add(Uri.fromFile(file));
    }
}

if (uris.isEmpty()) {
    Toast.makeText(MainActivity.this, "请先下载图片", Toast.LENGTH_SHORT).show();
} else {
    String content = "https://www.baidu.com/";
    ShareUtils.getInstance().shareToSinaImage(content, uris, MainActivity.this);
}
```
## 分享到qq和qq空间
下面的代码类似于上面的使用：
```Java
ShareUtils.getInstance().shareToQzoneText(content, MainActivity.this);// 分享文本到QQ空间
ShareUtils.getInstance().shareToQzoneImage(content, uris, MainActivity.this);// 分享文本多图到QQ空间
ShareUtils.getInstance().shareToQQText(content, MainActivity.this);// 分享文本到QQ
ShareUtils.getInstance().shareToQQImage(uris, MainActivity.this);// 分享多图到QQ
```
# Gradle引入
```Gradle
compile 'com.zyl.androidshareutils:android-share-utils:0.0.2'
```
