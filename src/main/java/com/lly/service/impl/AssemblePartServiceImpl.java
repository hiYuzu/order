package com.lly.service.impl;

import com.lly.dao.IAssemblePartDao;
import com.lly.pojo.AssemblePartPojo;
import com.lly.service.IAssemblePartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("assemblePartService")
@Transactional(rollbackFor = Exception.class)
public class AssemblePartServiceImpl implements IAssemblePartService {

    @Resource
    private IAssemblePartDao assemblePartDao;

    @Override
    public int getAssemblePartCount(AssemblePartPojo assemblePartPojo) {
        return assemblePartDao.getAssemblePartCount(assemblePartPojo);
    }

    @Override
    public List<AssemblePartPojo> getAssemblePart(AssemblePartPojo assemblePartPojo) {
        return assemblePartDao.getAssemblePart(assemblePartPojo);
    }

    @Override
    public List<AssemblePartPojo> getPartsByAssembleId(String assembleId) {
        return assemblePartDao.getPartsByAssembleId(assembleId);
    }

}
