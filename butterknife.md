# Android Butterknife使用方法
ButterKnife的优势：
  * 强大的View绑定和Click事件处理功能，简化代码，提升开发效率
  * 方便的处理Adapter里的ViewHolder绑定问题
  * 运行时不会影响APP效率，使用配置方便
  * 代码清晰，可读性强
  
## 基本配置



一、需要在工程（Project）的build.gradle中添加依赖

``` java
buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.3'

        classpath 'com.jakewharton:butterknife-gradle-plugin:8.8.1'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
``` 

二、在项目（Module）的build.gradle中添加依赖

``` java
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0-alpha3'
    implementation 'com.android.support:support-v4:28.0.0-alpha3'
    implementation 'com.android.support:design:28.0.0-alpha3'

    // Butterknife
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'

}
```

## ButterKnife的注册与绑定

### ButterKnife使用心得与注意事项：

* 在Activity 类中绑定 ：ButterKnife.bind(this);必须在setContentView();之后绑定；且父类bind绑定后，子类不需要再bind。
* 在非Activity 类（eg：Fragment、ViewHold）中绑定： ButterKnife.bind(this，view);这里的this不能替换成getActivity（）。
* 在Activity中不需要做解绑操作，在Fragment 中必须在onDestroyView()中做解绑操作。
* 使用ButterKnife修饰的方法和控件，不能用private or static 修饰，否则会报错。错误: @BindView fields must not be private or static. (com.zyj.wifi.ButterknifeActivity.button1)
* setContentView()不能通过注解实现。（其他的有些注解框架可以）
* 使用Activity为根视图绑定任意对象时，如果你使用类似MVC的设计模式你可以在Activity 调用ButterKnife.bind(this, activity)，来绑定Controller。
* 使用ButterKnife.bind(this，view)绑定一个view的子节点字段。如果你在子View的布局里或者自定义view的构造方法里 使用了inflate,你可以立刻调用此方法。或者，从XML inflate来的自定义view类型可以在onFinishInflate回调方法中使用它。

由于每次都要在Activity中的onCreate绑定Activity，所以个人建议写一个BaseActivity完成绑定，子类继承即可。绑定Activity 必须在setContentView之后。使用ButterKnife.bind(this)进行绑定。代码如下：
``` java 
public class MainActivity extends AppCompatActivity{  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        //绑定初始化ButterKnife  
        ButterKnife.bind(this);  
    }  
}  
```

### 在Fragment中绑定ButterKnife：

Fragment的生命周期不同于activity。在onCreateView中绑定一个Fragment时，在onDestroyView中将视图设置为null。当你调用bind来为你绑定一个Fragment时,Butter Knife会返回一个Unbinder的实例。在适当的生命周期（onDestroyView）回调中调用它的unbind方法进行Fragment解绑。使用ButterKnife.bind(this, view)进行绑定。代码如下：
``` java
public class ButterknifeFragment extends Fragment{  
    private Unbinder unbinder;  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                             Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.fragment, container, false);  
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()  
        unbinder = ButterKnife.bind(this, view);  
        return view;  
    }  

    /** 
     * onDestroyView中进行解绑操作 
     */  
    @Override  
    public void onDestroyView() {  
        super.onDestroyView();  
        unbinder.unbind();  
    }  
}  
```
## 在Adapter中绑定ButterKnife：

在Adapter的ViewHolder中使用，将ViewHolder加一个构造方法，在new ViewHolder的时候把view传递进去。使用ButterKnife.bind(this, view)进行绑定，代码如下：
``` java
public class MyAdapter extends BaseAdapter {  

  @Override   
  public View getView(int position, View view, ViewGroup parent) {  
    ViewHolder holder;  
    if (view != null) {  
      holder = (ViewHolder) view.getTag();  
    } else {  
      view = inflater.inflate(R.layout.testlayout, parent, false);  
      holder = new ViewHolder(view);  
      view.setTag(holder);  
    }  

    holder.name.setText("Donkor");  
    holder.job.setText("Android");
    // etc...  
    return view;  
  }  

  static class ViewHolder {  
    @BindView(R.id.title) TextView name;  
    @BindView(R.id.job) TextView job;  

    public ViewHolder(View view) {  
      ButterKnife.bind(this, view);  
    }  
  }  
}  
```
## ButterKnife的基本使用
#### 绑定View：
控件id 注解： @BindView（）
``` java
@BindView( R2.id.button)  
public Button button;   
```

布局内多个控件id 注解： @BindViews（）
``` java
    @BindViews({ R2.id.button1, R2.id.button2,  R2.id.button3})  
    public List<Button> buttonList ;  
```

绑定string 字符串：@BindString()
``` java
 @BindString(R2.string.app_name)  //绑定资源文件中string字符串  
    String str;    
```

绑定string里面array数组：@BindArray()
``` java
 @BindArray(R2.array.city)  //绑定string里面array数组  
    String [] citys ;   
```
绑定Bitmap 资源：@BindBitmap( )
``` java
@BindBitmap( R2.mipmap.bm)//绑定Bitmap 资源  
    public Bitmap bitmap ;    
```
绑定一个颜色值：@BindColor( )
``` java
 @BindColor( R2.color.colorAccent ) //具体色值在color文件中  
    int black ;  //绑定一个颜色值   
```


绑定点击事件：
绑定控件点击事件：@OnClick( )
绑定控件长按事件：@OnLongClick( )
``` java
 @OnClick(R2.id.button1 )   //给 button1 设置一个点击事件  
    public void showToast(){  
        Toast.makeText(this, "is a click", Toast.LENGTH_SHORT).show();  
    }  

    @OnLongClick( R2.id.button1 )    //给 button1 设置一个长按事件  
    public boolean showToast2(){  
        Toast.makeText(this, "is a long click", Toast.LENGTH_SHORT).show();  
        return true ;  
    }  
```

### 更多绑定注解：
    @BindView—->绑定一个view；id为一个view 变量
    @BindViews —-> 绑定多个view；id为一个view的list变量
    @BindArray—-> 绑定string里面array数组；@BindArray(R.array.city ) String[] citys ;
    @BindBitmap—->绑定图片资源为Bitmap；@BindBitmap( R.mipmap.wifi ) Bitmap bitmap;
    @BindBool —->绑定boolean值
    @BindColor —->绑定color；@BindColor(R.color.colorAccent) int black;
    @BindDimen —->绑定Dimen；@BindDimen(R.dimen.borth_width) int mBorderWidth;
    @BindDrawable —-> 绑定Drawable；@BindDrawable(R.drawable.test_pic) Drawable mTestPic;
    @BindFloat —->绑定float
    @BindInt —->绑定int
    @BindString —->绑定一个String id为一个String变量；@BindString( R.string.app_name ) String meg;

### 更多事件注解：
    @OnClick—->点击事件
    @OnCheckedChanged —->选中，取消选中
    @OnEditorAction —->软键盘的功能键
    @OnFocusChange —->焦点改变
    @OnItemClick item—->被点击(注意这里有坑，如果item里面有Button等这些有点击的控件事件的，需要设置这些控件属性focusable为false)
    @OnItemLongClick item—->长按(返回真可以拦截onItemClick)
    @OnItemSelected —->item被选择事件
    @OnLongClick —->长按事件
    @OnPageChange —->页面改变事件
    @OnTextChanged —->EditText里面的文本变化事件
    @OnTouch —->触摸事件
    @Optional —->选择性注入，如果当前对象不存在，就会抛出一个异常，为了压制这个异常，可以在变量或者方法上加入一下注解,让注入变成选择性的,如果目标View存在,则注入, 不存在,则什么事情都不做




