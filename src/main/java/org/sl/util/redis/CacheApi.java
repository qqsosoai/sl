package org.sl.util.redis;

/**
 * Created by hasee on 2017/9/30.
 */
public interface CacheApi {
    /**
     * 添加对象缓存方法
     * @param key
     * @param value
     * @return 是否添加成功
     */
    boolean set(Object key,Object value);
    /**
     * 添加对象缓存方法
     * @param key
     * @param value
     * @return 是否添加成功
     */
    boolean set(Object key,Object value,long date);

    /**
     * 添加方法(当缓存中没有该key值时添加)
     * @param key
     * @param Value
     * @return 是否添加成功
     */
    boolean add(Object key,Object Value);

    /**
     * 添加方法(当缓存中没有该key值时添加)
     * @param key
     * @param Value
     * @param date 对象存活时间
     * @return 是否添加成功
     */
    boolean add(Object key,Object Value,long date);
    /**
     * 判断该key是否存在
     * @param key
     * @return
     */
    boolean exist(Object key);
    /**
     * 获取缓存对象
     * @param key
     * @return
     */
    Object get(Object key);

    /**
     * 根据key删除value
     * @param key
     * @return 是否删除成功
     */
    boolean delete(Object key);
}
