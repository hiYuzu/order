package com.lly.service.impl;

import com.lly.dao.IParamDao;
import com.lly.pojo.ParamPojo;
import com.lly.service.IParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 系统参数服务接口实现类
 * @Date: Create in 2018/2/11 14:36
 * @Modify by WangLei
 */
@Service("paramService")
@Transactional(rollbackFor = Exception.class)
public class ParamServiceImpl implements IParamService {

    @Resource
    private IParamDao paramDao;

    @Override
    public int getParamCount(ParamPojo paramPojo) {
        return paramDao.getParamCount(paramPojo);
    }

    @Override
    public List<ParamPojo> getParam(ParamPojo paramPojo) {
        return paramDao.getParam(paramPojo);
    }

    @Override
    public List<ParamPojo> getParamByType(String paramTypeCode) {
        return paramDao.getParamByType(paramTypeCode);
    }

    @Override
    public int insertParam(ParamPojo paramPojo) throws Exception {
        return paramDao.insertParam(paramPojo);
    }

    @Override
    public int updateParam(ParamPojo paramPojo) throws Exception {
        return paramDao.updateParam(paramPojo);
    }

    @Override
    public int deleteParam(List<String> idList) throws Exception {
        return paramDao.deleteParam(idList);
    }

    @Override
    public int existUpdateParam(String paramId, String paramCode) {
        return paramDao.existUpdateParam(paramId,paramCode);
    }

    @Override
    public int existDeleteParam(List<String> paramIdList) {
        return paramDao.existDeleteParam(paramIdList);
    }
}
