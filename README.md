Add to `AndroidManifest.xml`:

```
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:pathPrefix="" android:scheme="PACKAGE_ID" />
    <data android:host="PACKAGE_ID" android:pathPrefix="" android:scheme="android-app" />
</intent-filter>
```

```
adb shell am start -W -a android.intent.action.VIEW -d <URI> <PACKAGE>
```
