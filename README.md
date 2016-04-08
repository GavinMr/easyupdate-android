# EasyUpdate
EasyUpdate（Android）帮助开发者将移动终端上的应用升级到最新版本，是进行存量用户更新的有效手段。

EasyUpdate 提供多个检查更新插件，开发者可根据本身需求进行集成。不同的检查更新插件会对不同的渠道提高新增激活，更新激活（应用市场的应用下载量）。

## 开始使用EasyUpdate

在 Application 初始化时，或在应用调用 检查更新 之前，进行初始化：

```
EasyUpdateConfig config = EasyUpdateConfig.newBuilder()
                                        .setDeltaUpdate(false) // 是否开启增量更新，默认false
                                        .setUpdateOnlyWifi(true) // 是否仅在WIFI环境下检查更新, 默认true
                                        .setUpdateAutoPopup(true) // 是否检查更新自动弹出更新对话框
                                        .setUpdatePlugin(new UmengUpdatePlugin()) // 设置检查更新使用的插件，必须设置
                                        .build();
EasyUpdate.initialize(config);

```

### 静默更新
如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。

```
EasyUpdate.silentUpdate(Context);
```

### 检查更新
检查是否有新版本。

```
EasyUpdate.checkUpdate(Context, EasyUpdateListener)
```

### 强制检查更新
检查是否有新版本，强制立即联网检查更新。该方法不会检查UpdateOnlyWifi设置。

```
EasyUpdate.checkUpdateForce(Context, EasyUpdateListener)
```

### 弹出更新提示对话框
弹出更新提示对话框，配合EasyUpdateConfig.setUpdateAutoPopup(false)在更新回调中使用，调用该方法弹出更新提醒对话框，提醒用户有更新。
如果 setUpdateAutoPopup 设置为 true，请不要调用该方法，会导致显示两次提醒。

```
EasyUpdate.showUpdateDialog(Context, Object) // Object为EasyUpdateListener回调中的updateInfo对象
```

### 自定义功能
#### 自定义检查更新插件
实现 com.gary.android.easyupdate.EasyUpdatePlugin 接口，注册到EasyUpdate即可。


## 配置更新插件

### 友盟

[http://www.umeng.com/](http://www.umeng.com/)

可根据渠道上传对应的APK

#### 配置

添加APPKEY和渠道到&lt;application&gt;标签下：（如果已经集成了统计SDK等友盟其他服务，不需要重复添加APPKEY）

```
<meta-data android:value="YOUR APP KEY" android:name="UMENG_APPKEY"/>
<meta-data android:value="Channel ID" android:name="UMENG_CHANNEL"/>
```

#### 上传最新的APK

如果开发者已经有了最新的APK版本，只要上传到友盟网站，同时客户端版本的版本号（VersionName和VersionCode）小于上传的最新版本，客户端就会有更新提示。

#### 友盟检查更新SDK版本
v2.7.0

### 百度
[http://app.baidu.com/](http://app.baidu.com/)

只要在百度手机助手发布新版，用户都可以收到新版发布提醒。用户更新会增加百度手机助手的下载量。百度手机助手强制集成该SDK，否则无法通过审核。

#### 配置
添加APPKEY和APPID到&lt;application&gt;标签下：（如果已经集成了统计SDK等百度其他服务，不需要重复添加APPKEY和APPID）

```
<meta-data android:name="BDAPPID" android:value="YOUR APP ID "/>
<meta-data android:name="BDAPPKEY" android:value="YOUR APP KEY"/>
```
#### 百度检查更新SDK版本

v1.2.0

## License
* [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)