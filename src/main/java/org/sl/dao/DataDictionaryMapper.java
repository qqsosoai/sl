package org.sl.dao;

import org.sl.bean.DataDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hasee on 2017/10/3.
 * 数据字典Dao层
 */
@Repository
public interface DataDictionaryMapper {
    /**
     * 根据字典条件查询集合
     * @param dictionary 字典条件
     * @return 字典集合
     */
    List<DataDictionary> findByDataDictionarys(DataDictionary dictionary)throws Exception;
}
