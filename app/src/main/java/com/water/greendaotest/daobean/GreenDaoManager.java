package com.water.greendaotest.daobean;



import com.water.greendaotest.MyApplication;
import com.water.greendaotest.greendao.DaoMaster;
import com.water.greendaotest.greendao.DaoMaster.DevOpenHelper;
import com.water.greendaotest.greendao.DaoSession;
import com.water.greendaotest.greendao.UserDao;

import java.util.List;

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





































































}
