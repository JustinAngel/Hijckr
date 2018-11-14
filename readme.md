# Hijckr
### Misappropriating Your Android XML Tags since 2018 [ ![Download](https://api.bintray.com/packages/nisrulz/maven/awesomelib/images/download.svg) ](https://bintray.com/nisrulz/maven/awesomelib/_latestVersion)

![](https://i.imgur.com/51cTskB.png)

### What does it do? 
**Hijckr** interfers with Android's layout inflation and redirects named elements to other classes. For example for layout file contains <TextView /> Android would normally *load android.widget.TextView,* instead we can hijack <TextView /> xml tags to load *com.myapp.TextView*.

### Getting Started
Add gradle dependency to your build.gradle file: 

```xml
dependencies {
    implementation 'com.justinangel:hijckr:1+'
}
```

### Activity-Specific Hijacking 
Let's say we want all EditText's in a given activity to replace all ":)" apperances with the 😀emjoi. For that, we can subclass EditText, listen to text changes and whenever we find ":)" replace with the appropriate emoji. With Hijckr it's possible to implicitly route all usages of <EditText /> within an activity to our SmileyEditText.

Here's our SmileyEditText class. 
```java
public class SmileyEditText extends EditText {
    // ... c'tors 

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (text.toString().contains(":)")) {
            setText(text.toString().replace(":)", "\uD83D\uDE0A"));
        }
    }
}

```

Here's our MainActivity laoyut XML: 
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Type a smiley face"/>
</LinearLayout>
```

We'll now Hijack the EditText tag by subclassing getClassLoader and using a class loader that routes from EditText to SmileyEditText. 
```java
import com.justinangel.hijckr.HijckrClassLoader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public ClassLoader getClassLoader() {
        return new HijckrClassLoader(super.getClassLoader())
                .withClassRouting(EditText.class, SmileyEditText.class);
    }
}

```
And here's the result:
![](http://g.recordit.co/OFRVwwi6Rs.gif)

###  Global Hijacking  
Setting XML tag routing for each activity feels cumbersome. So any time prior to inflating XML it's possible to change the global routing of XML Tags. A best practice would be to do that during Application.onCreate. For example we can route all of our *<TextView />* tags to load an inherting class *AppTextView*. 

```java
import com.justinangel.hijckr.HijckrClassLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HijckrClassLoader.addGlobalClassRouting(TextView.class, AppTextView.class);
    }
}
```

In our MainActivity we'll change the base class to HijckrActivity. 
```java
import com.justinangel.hijckr.HijckrActivity;

public class MainActivity extends HijckrActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```
We'll also include a <TextView /> tag in our layout XML. 
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />
</LinearLayout>
```
And for the class we want to use to replace our TextView we'll  make the text Red and Font size 18. 
```java
package com.justinangel.hijckrsampleapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppTextView extends TextView {
    public AppTextView(Context context) {
        super(context);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setTextSize(28);
        this.setTextColor(Color.RED);
    }
}

```
Don't forget to wire up our custom Android Application class. '
```xml
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.justinangel.hijckrsampleapp">
    
        <application
            android:name=".App" ../>
    </manifest>
```

When we run this sample we can see our text is colored red and font size 18. 
![](https://i.imgur.com/5RoKjDG.png)

### Limitations 
- XML routing can't be changed on instintiated views.
- For named XML tags, XML routing can only be done for classes that inherit from the originally routed class. 
