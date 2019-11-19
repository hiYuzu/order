package com.tcb.service.impl;

import com.tcb.dao.IAssemblePartDao;
import com.tcb.pojo.AssemblePartPojo;
import com.tcb.service.IAssemblePartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装部件服务接口实现类
 * @Date: Create in 2019/3/18 15:33
 * @Modify by WangLei
 */
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
