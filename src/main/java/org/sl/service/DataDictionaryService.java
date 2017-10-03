package org.sl.service;

import org.sl.bean.DataDictionary;

import java.util.List;

/**
 * Created by hasee on 2017/10/3.
 */
public interface DataDictionaryService {
    /**
     * 查询数据字典表返回集合
     * @param dictionary
     * @return
     */
    List<DataDictionary> findByDataDictionarys(DataDictionary dictionary)throws Exception;

}
