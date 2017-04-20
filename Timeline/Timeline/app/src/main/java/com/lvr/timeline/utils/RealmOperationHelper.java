package com.lvr.timeline.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by lvr on 2017/4/20.
 */

public class RealmOperationHelper {
    private static Realm mRealm;

    private static class SingletonHolder {
        private static RealmOperationHelper INSTANCE = new RealmOperationHelper(
                mRealm);
    }

    private RealmOperationHelper(Realm realm) {
        this.mRealm = realm;

    }

    /**
     * 获取RealmOperation的单例
     *
     * @param realm 传入realm实例对象
     * @return 返回RealmOperation的单例
     */
    public static RealmOperationHelper getInstance(Realm realm) {
        if (realm != null) {
            mRealm = realm;
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 增加单条数据到数据库中
     *
     * @param bean 数据对象，必须继承了RealmObject
     */
    public void add(final RealmObject bean) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.copyToRealm(bean);

            }
        });

    }

    /**
     * 增加多条数据到数据库中
     *
     * @param beans 数据集合，其中元素必须继承了RealmObject
     */
    public void add(final List<? extends RealmObject> beans) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(beans);

            }
        });

    }
    /**
     * 增加多条数据到数据库中
     *
     * @param beans 数据集合，其中元素必须继承了RealmObject
     */
    public void addAsync(final List<? extends RealmObject> beans) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(beans);
            }
        });

    }

    /**
     * 删除数据库中clazz类所属所有元素
     *
     * @param clazz
     */
    public void deleteAll(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteAllFromRealm();

            }
        });

    }
    /**
     * 删除数据库中clazz类所属所有元素
     *
     * @param clazz
     */
    public void deleteAllAsync(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteAllFromRealm();

            }
        });


    }

    /**
     * 删除数据库中clazz类所属第一个元素
     *
     * @param clazz
     */
    public void deleteFirst(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteFirstFromRealm();

            }
        });

    }

    /**
     * 删除数据库中clazz类所属最后一个元素
     *
     * @param clazz
     */
    public void deleteLast(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteLastFromRealm();

            }
        });

    }

    /**
     * 删除数据库中clazz类所属数据中某一位置的元素
     *
     * @param clazz
     * @param position
     */
    public void deleteElement(Class<? extends RealmObject> clazz, final int position) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteFromRealm(position);

            }
        });

    }

    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz
     * @return
     */
    public RealmResults<? extends RealmObject> queryAll(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        return beans;
    }
    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz
     * @return
     */
    public RealmResults<? extends RealmObject> queryAllAsync(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAllAsync();

        return  beans;
    }

    /**
     * 查询满足条件的第一个数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public RealmObject queryByFieldFirst(Class<? extends RealmObject> clazz, String fieldName, String value) throws NoSuchFieldException {

        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, value).findFirst();

        return bean;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public RealmResults<? extends RealmObject> queryByFieldAll(Class<? extends RealmObject> clazz, String fieldName, String value) throws NoSuchFieldException {

        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAll();

        return beans;
    }
    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public RealmResults<? extends RealmObject> queryByFieldAllAsync(Class<? extends RealmObject> clazz, String fieldName, String value) throws NoSuchFieldException {

        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAllAsync();

        return beans;
    }

    /**
     * 查询满足条件的第一个数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public RealmObject queryByFieldFirst(Class<? extends RealmObject> clazz, String fieldName, int value) throws NoSuchFieldException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, value).findFirst();

        return bean;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public RealmResults<? extends RealmObject> queryByFieldAll(Class<? extends RealmObject> clazz, String fieldName, int value) throws NoSuchFieldException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAll();
        return beans;
    }
    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public RealmResults<? extends RealmObject> queryByFieldAllAsync(Class<? extends RealmObject> clazz, String fieldName, int value) throws NoSuchFieldException {

        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, value).findAllAsync();
        return beans;
    }

    /**
     * 查询数据，按增量排序
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public List<? extends RealmObject> queryAllByAscending(Class<? extends RealmObject> clazz, String fieldName) {
        RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        RealmResults<? extends RealmObject> results = beans.sort(fieldName, Sort.ASCENDING);
        return mRealm.copyFromRealm(results);
    }


    /**
     * 查询数据，按降量排序
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public List<? extends RealmObject> queryAllByDescending(Class<? extends RealmObject> clazz, String fieldName) {
        RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();
        RealmResults<? extends RealmObject> results = beans.sort(fieldName, Sort.DESCENDING);
        return mRealm.copyFromRealm(results);
    }

    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz
     * @param fieldName
     * @param oldValue
     * @param newValue
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void updateFirstByField(Class<? extends RealmObject> clazz, String fieldName,String oldValue,String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, oldValue).findFirst();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, String.class);
        method.invoke(bean,newValue);
        mRealm.commitTransaction();

    }
    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz
     * @param fieldName
     * @param oldValue
     * @param newValue
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void updateFirstByField(Class<? extends RealmObject> clazz, String fieldName,int oldValue,int newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmObject bean = mRealm.where(clazz).equalTo(fieldName, oldValue).findFirst();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, int.class);
        method.invoke(bean,newValue);
        mRealm.commitTransaction();

    }
    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz
     * @param fieldName
     * @param oldValue
     * @param newValue
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void updateAllByField(Class<? extends RealmObject> clazz, String fieldName,String oldValue,String newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, oldValue).findAll();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, String.class);
        for(int i=0;i<beans.size();i++){
            RealmObject realmObject = beans.get(i);
            method.invoke(realmObject,newValue);
        }
        mRealm.commitTransaction();

    }
    /**
     * 更新满足某个条件的第一个数据的属性值
     * @param clazz
     * @param fieldName
     * @param oldValue
     * @param newValue
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void updateAllByField(Class<? extends RealmObject> clazz, String fieldName,int oldValue,int newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).equalTo(fieldName, oldValue).findAll();
        mRealm.beginTransaction();
        Method method = clazz.getMethod(fieldName, int.class);
        for(int i=0;i<beans.size();i++){
            RealmObject realmObject = beans.get(i);
            method.invoke(realmObject,newValue);
        }
        mRealm.commitTransaction();

    }


}
