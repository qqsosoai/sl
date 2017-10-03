package org.sl.service.impl;

import org.apache.log4j.Logger;
import org.sl.bean.DataDictionary;
import org.sl.dao.DataDictionaryMapper;
import org.sl.service.DataDictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hasee on 2017/10/3.
 * 数字字典表service层
 */
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
    private Logger logger= Logger.getLogger(DataDictionaryServiceImpl.class);
    @Resource
    private DataDictionaryMapper mapper;
    //查询数据字典表
    public List<DataDictionary> findByDataDictionarys(DataDictionary dictionary) throws Exception{
        return mapper.findByDataDictionarys(dictionary);
    }
}
