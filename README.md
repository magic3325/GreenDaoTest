# GreenDaoTest

## GreenDao 的配置

一、需要在工程（Project）的build.gradle中添加依赖

buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.3'

        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

二、在项目（Module）的build.gradle中添加依赖
``` java
  apply plugin: 'com.android.application'
  apply plugin: 'org.greenrobot.greendao'

  android {
      compileSdkVersion 28
      //使用greendao
      greendao {
          schemaVersion 2
          daoPackage 'com.water.greendaotest.greendao'
          targetGenDir 'src/main/java'
      }

      defaultConfig {
          applicationId "com.water.greendaotest"
          minSdkVersion 15
          targetSdkVersion 28
          versionCode 1
          versionName "1.0"
          testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
      }
      buildTypes {
          release {
              minifyEnabled false
              proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
          }
      }
  }

  dependencies {
  
      // base
      implementation fileTree(dir: 'libs', include: ['*.jar'])
      implementation 'com.android.support:appcompat-v7:28.0.0-alpha3'
      implementation 'com.android.support:support-v4:28.0.0-alpha3'
      implementation 'com.android.support:design:28.0.0-alpha3'
      implementation 'com.android.support.constraint:constraint-layout:1.1.2'
      testImplementation 'junit:junit:4.12'
      androidTestImplementation 'com.android.support.test:runner:1.0.2'
      androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

      // Greendao
      implementation 'org.greenrobot:greendao:3.2.2'
      implementation 'org.greenrobot:greendao-generator:3.2.2'

  }
```


## GreenDao3.2的使用


### 一、创建Bean对象（表名和字段名）

    这里对Bean对象的注释进行解释
    @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
    @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
    @Property：可以自定义字段名，注意外键不能使用该属性
    @NotNull：属性不能为空
    @Transient：使用该注释的属性不会被存入数据库的字段中
    @Unique：该属性值必须在数据库中是唯一值
    @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改


``` java
package com.water.greendaotest.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by pc on 2018/7/21.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    //用户名
    private String userName;
    //年龄
    private int age;
    //学习成绩
    private double score;
    //排名
    private String random;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", random='" + random + '\'' +
                '}';
    }

    @Generated(hash = 1487427874)
    public User(Long id, String userName, int age, double score, String random) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.score = score;
        this.random = random;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRandom() {
        return this.random;
    }

    public void setRandom(String random) {
        this.random = random;
    }


}
```

### 二、创建数据库（数据库名）
``` java
  import com.water.greendaotest.MyApplication;
  import com.water.greendaotest.greendao.DaoMaster;
  import com.water.greendaotest.greendao.DaoMaster.DevOpenHelper;
  import com.water.greendaotest.greendao.DaoSession;
  import com.water.greendaotest.greendao.UserDao;

public class GreenDaoManager {


    private static GreenDaoManager instance = null;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager(){
        initDataBase();
    }


    public static synchronized GreenDaoManager getInstance() {
        // 这个方法比上面有所改进，不用每次都进行生成对象，只是第一次
        // 使用时生成实例，提高了效率！
        if (instance == null)
            instance = new GreenDaoManager();

        return instance;

    }

    private void initDataBase(){
        DevOpenHelper devOpenHelper = new DevOpenHelper(MyApplication.getInstances(),"test.db");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession =mDaoMaster.newSession();
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void insertUser(User user){
        mDaoSession.getUserDao().insert(user);
    }

    public void delByKeyUser(Long key){
        mDaoSession.getUserDao().deleteByKey(key);
    }
    public void delByKeyUser(User user){
        mDaoSession.getUserDao().delete(user);
    }
    public void delAllUser(){
        mDaoSession.getUserDao().deleteAll();
    }
    public void updateUser(User user){
        mDaoSession.getUserDao().update(user);
    }

    public List<User> searchAllUser(){
        return mDaoSession.getUserDao().loadAll();
    }
    public User searchByKeyUser(Long key){
        return mDaoSession.getUserDao().load(key);
    }
    public List<User> searchByConditionUser(String userName){
        return mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.UserName.like(userName)).list();
    }
```
    DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
    DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
    DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
    由于我们已经创建好了DaoSession和Shop的Bean对象，编译后会自动生成我们的ShopDao对象，可通过DaoSession获得
    ShopDao dao = daoSession.getShopDao();
    这里的Dao（Data Access Object）是指数据访问接口，即提供了数据库操作一些API接口，可通过dao进行增删改查操作

三、数据库的增删改查
``` java
public void insertUser(User user){
        mDaoSession.getUserDao().insert(user);
    }

    public void delByKeyUser(Long key){
        mDaoSession.getUserDao().deleteByKey(key);
    }
    public void delByKeyUser(User user){
        mDaoSession.getUserDao().delete(user);
    }
    public void delAllUser(){
        mDaoSession.getUserDao().deleteAll();
    }
    public void updateUser(User user){
        mDaoSession.getUserDao().update(user);
    }

    public List<User> searchAllUser(){
        return mDaoSession.getUserDao().loadAll();
    }
    public User searchByKeyUser(Long key){
        return mDaoSession.getUserDao().load(key);
    }
    public List<User> searchByConditionUser(String userName){
        return mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.UserName.like(userName)).list();
    }
``` 
    效果很明显，GreenDao的封装更加短小精悍，语义明朗，下面对GreenDao中Dao对象其他API的介绍
    增加单个数据
    getShopDao().insert(shop);
    getShopDao().insertOrReplace(shop);
    增加多个数据
    getShopDao().insertInTx(shopList);
    getShopDao().insertOrReplaceInTx(shopList);
    查询全部
    List< Shop> list = getShopDao().loadAll();
    List< Shop> list = getShopDao().queryBuilder().list();
    查询附加单个条件
    .where()
    .whereOr()
    查询附加多个条件
    .where(, , ,)
    .whereOr(, , ,)
    查询附加排序
    .orderDesc()
    .orderAsc()
    查询限制当页个数
    .limit()
    查询总个数
    .count()
    修改单个数据
    getShopDao().update(shop);
    修改多个数据
    getShopDao().updateInTx(shopList);
    删除单个数据
    getTABUserDao().delete(user);
    删除多个数据
    getUserDao().deleteInTx(userList);
    删除数据ByKey
    getTABUserDao().deleteByKey();







