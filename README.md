# EasyUpdate
EasyUpdate（Android）帮助开发者将移动终端上的应用升级到最新版本，是进行存量用户更新的有效手段。

EasyUpdate 提供多个检查更新插件，开发者可根据本身需求进行集成。不同的检查更新插件会对不同的渠道提高新增激活，更新激活（应用市场的应用下载量）。

## 集成检查更新插件

### 友盟

http://www.umeng.com/

#### 配置

添加APPKEY和渠道到&lt;application&gt;标签下：（如果已经集成了统计SDK等友盟其他服务，不需要重复添加APPKEY）

```
<meta-data android:value="YOUR APP KEY" android:name="UMENG_APPKEY"/>
<meta-data android:value="Channel ID" android:name="UMENG_CHANNEL"/>
```

#### 上传最新的APK

如果开发者已经有了最新的APK版本，只要上传到友盟网站，同时客户端版本的版本号（VersionName和VersionCode）小于上传的最新版本，客户端就会有更新提示。

### 百度
http://app.baidu.com/


## License
* [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)