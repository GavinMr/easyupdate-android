# EasyUpdate
可快速集成应用检查更新功能，支持百度、小米应用检查更新。提供多个检查更新插件，开发者可根据本身需求进行集成。

## 开始使用EasyUpdate

在 Application 初始化时，或在应用调用 检查更新 之前，进行初始化：

```
EasyUpdate.initialize(new EasyUpdateConfig.Builder()
                    .setDeltaUpdate(false) // 是否开启增量更新，默认false
                    .setUpdateOnlyWifi(true) // 是否仅在WIFI环境下检查更新，默认true
                    .setUpdateAutoPopup(true) // 是否检查更新自动弹出更新对话框，默认true
                    .setUpdatePlugin(new XiaomiUpdatePlugin()) // 设置检查更新使用的插件，必须设置
//                    .setUpdatePlugin(new BaiduUpdatePlugin()) // 可根据渠道设置不同的检查更新
                    .create()
        );

```

### 静默更新
如果处于wifi环境检查更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。

```
EasyUpdate.silentUpdate(Context);
```

### 检查更新
检查是否有新版本。

```
EasyUpdate.checkUpdate(Context, EasyUpdateListener)
```

### 弹出更新提示对话框
弹出更新提示对话框，配合EasyUpdateConfig.setUpdateAutoPopup(false)在更新回调中使用，调用该方法弹出更新提醒对话框，提醒用户有更新。
如果 setUpdateAutoPopup 设置为 true，请不要调用该方法，会导致显示两次提醒。

```
EasyUpdate.showUpdateDialog(Context, Object) // Object为EasyUpdateListener回调中的updateInfo对象
```

### 自定义功能
#### 自定义检查更新插件
实现 com.github.garymr.android.easyupdate.EasyUpdatePlugin 接口，注册到EasyUpdate即可。

## 下载

### Gradle

```
dependencies {
	compile 'com.github.garymr.android:easyupdate:1.0.0'

    // 百度检查更新，不使用可以不引入
	compile 'com.github.garymr.android:easyupdate-baidu:1.0.0'

	// 小米检查更新，不使用可以不引入
    compile 'com.github.garymr.android:easyupdate-xiaomi:1.1.0'
}
```

### Maven

```
<dependency>
  <groupId>com.github.garymr.android</groupId>
  <artifactId>easyupdate</artifactId>
  <version>1.0.0</version>
</dependency>

<!-- 百度检查更新，不使用可以不引入 -->
<dependency>
  <groupId>com.github.garymr.android</groupId>
  <artifactId>easyupdate-baidu</artifactId>
  <version>1.0.0</version>
</dependency>

<!-- 小米检查更新，不使用可以不引入 -->
<dependency>
  <groupId>com.github.garymr.android</groupId>
  <artifactId>easyupdate-xiaomi</artifactId>
  <version>1.2.0</version>
</dependency>
```

检查更新插件可以按需配置，不使用可以不配置，减少APK大小。

## 配置更新插件

### 百度
[http://app.baidu.com/](http://app.baidu.com/)

只要在百度手机助手发布新版，用户都可以收到新版发布提醒。百度手机助手强制集成该SDK，否则无法通过审核。

#### 配置
添加APPKEY和APPID到&lt;application&gt;标签下：（如果已经集成了统计SDK等百度其他服务，不需要重复添加APPKEY和APPID）

```
<meta-data android:name="BDAPPID" android:value="YOUR APP ID "/>
<meta-data android:name="BDAPPKEY" android:value="YOUR APP KEY"/>
```
#### 百度检查更新SDK版本

v1.2.0

### 小米
[http://dev.xiaomi.com/](http://dev.xiaomi.com/)

#### 配置

```
<!-- authorities需要替换成自己的包名 -->
<provider android:name="android.support.v4.content.FileProvider"
    android:authorities="your_packagename.selfupdate.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true" >
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```


#### 小米检查更新SDK版本

| easyupdate版本 | 小米SDK版本 |
| ------------- |:---------:|
| 1.0.0         | v0.7      |
| 1.1.0         | v2.6      |
| 1.2.0         | v4.0.3      |

## License
* [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)