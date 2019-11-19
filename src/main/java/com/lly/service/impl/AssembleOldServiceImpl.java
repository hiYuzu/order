package com.lly.service.impl;

import com.lly.dao.IAssembleDao;
import com.lly.dao.IAssembleOldDao;
import com.lly.pojo.AssembleOldPojo;
import com.lly.service.IAssembleOldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("assembleOldService")
@Transactional(rollbackFor = Exception.class)
public class AssembleOldServiceImpl implements IAssembleOldService {

    @Resource
    private IAssembleOldDao assembleOldDao;

    @Resource
    private IAssembleDao assembleDao;

    @Override
    public int getAssembleOldCount(AssembleOldPojo assembleOldPojo, List<String> statusCodeList) {
        return assembleOldDao.getAssembleOldCount(assembleOldPojo, statusCodeList);
    }

    @Override
    public List<AssembleOldPojo> getAssembleOld(AssembleOldPojo assembleOldPojo, List<String> statusCodeList) {
        return assembleOldDao.getAssembleOld(assembleOldPojo, statusCodeList);
    }

    @Override
    public int operateAssembleOld(AssembleOldPojo assembleOldPojo) throws Exception {
        int result = 0;
        if (assembleOldPojo != null) {
            if (assembleOldPojo.getAssemblePojo() != null
                    && assembleOldDao.existAssembleOld(String.valueOf(assembleOldPojo.getAssemblePojo().getAssembleId())) > 0) {
                result = assembleOldDao.updateAssembleOld(assembleOldPojo);
            } else {
                result = assembleOldDao.insertAssembleOld(assembleOldPojo);
            }
            if (result > 0) {
                String statusCode = assembleOldPojo.getAssemblePojo().getAssembleStatus().getStatusCode();
                assembleDao.updateAssembleStatus(String.valueOf(assembleOldPojo.getAssemblePojo().getAssembleId()), statusCode);
            }
        }
        return result;
    }

    @Override
    public int deleteAssembleOld(List<String> idList) {
        return assembleOldDao.deleteAssembleOld(idList);
    }

    @Override
    public boolean canAssembleOldDelete(String assembleId) {
        boolean canDelete;
        if (assembleOldDao.canAssembleOldDelete(assembleId) > 0) {
            canDelete = true;
        } else {
            canDelete = false;
        }
        return canDelete;
    }

    @Override
    public boolean canAssembleOldModify(String assembleId) {
        boolean canModify;
        if (assembleOldDao.canAssembleOldModify(assembleId) > 0) {
            canModify = true;
        } else {
            canModify = false;
        }
        return canModify;
    }
}
