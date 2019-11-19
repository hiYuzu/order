package com.lly.service.impl;

import com.lly.dao.IAssembleDao;
import com.lly.dao.IAssemblePartDao;
import com.lly.pojo.AssemblePartPojo;
import com.lly.pojo.AssemblePojo;
import com.lly.service.IAssembleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装服务接口实现类
 * @Date: Create in 2019/3/18 14:50
 * @Modify by WangLei
 */
@Service("assembleService")
@Transactional(rollbackFor = Exception.class)
public class AssembleServiceImpl implements IAssembleService {

    @Resource
    private IAssembleDao assembleDao;

    @Resource
    private IAssemblePartDao assemblePartDao;

    @Override
    public int getAssembleCount(AssemblePojo assemblePojo, List<String> statusCodeList) {
        return assembleDao.getAssembleCount(assemblePojo, statusCodeList);
    }

    @Override
    public List<AssemblePojo> getAssemble(AssemblePojo assemblePojo, List<String> statusCodeList) {
        return assembleDao.getAssemble(assemblePojo, statusCodeList);
    }

    @Override
    public int insertAssemble(AssemblePojo assemblePojo, List<AssemblePartPojo> assemblePartPojoList) throws Exception {
        int result;
        result = assembleDao.insertAssemble(assemblePojo);
        if (result > 0) {
            if (assemblePartPojoList != null && assemblePartPojoList.size() > 0) {
                for (AssemblePartPojo temp : assemblePartPojoList) {
                    AssemblePojo assemblePojoTemp = new AssemblePojo();
                    assemblePojoTemp.setAssembleId(assemblePojo.getAssembleId());
                    temp.setAssemblePojo(assemblePojoTemp);
                }
                assemblePartDao.insertAssemblePart(assemblePartPojoList);
            }
        }
        return result;
    }

    @Override
    public int updateAssemble(AssemblePojo assemblePojo, List<AssemblePartPojo> assemblePartPojoList, boolean updatePartFlag) throws Exception {
        int result;
        result = assembleDao.updateAssemble(assemblePojo);
        if (result > 0) {
            if (updatePartFlag) {
                if (assemblePartPojoList != null && assemblePartPojoList.size() > 0) {
                    for (AssemblePartPojo temp : assemblePartPojoList) {
                        AssemblePojo assemblePojoTemp = new AssemblePojo();
                        assemblePojoTemp.setAssembleId(assemblePojo.getAssembleId());
                        temp.setAssemblePojo(assemblePojoTemp);
                    }
                    assemblePartDao.updateAssemblePart(assemblePartPojoList);
                }
            } else {
                assemblePartDao.deleteAssemblePart(String.valueOf(assemblePojo.getAssembleId()), null);
                if (assemblePartPojoList != null && assemblePartPojoList.size() > 0) {
                    for (AssemblePartPojo temp : assemblePartPojoList) {
                        AssemblePojo assemblePojoTemp = new AssemblePojo();
                        assemblePojoTemp.setAssembleId(assemblePojo.getAssembleId());
                        temp.setAssemblePojo(assemblePojoTemp);
                    }
                    assemblePartDao.insertAssemblePart(assemblePartPojoList);
                }
            }
        }
        return result;
    }

    @Override
    public int deleteAssembles(List<String> idList) throws Exception {
        return assembleDao.deleteAssemble(idList);
    }

    @Override
    public boolean canAssembleDelete(String assembleId) throws Exception {
        boolean canDelete;
        if (assembleDao.canAssembleDelete(assembleId) > 0) {
            canDelete = true;
        } else {
            canDelete = false;
        }
        return canDelete;
    }

    @Override
    public boolean canAssembleModify(String assembleId) throws Exception {
        boolean canModify;
        if (assembleDao.canAssembleModify(assembleId) > 0) {
            canModify = true;
        } else {
            canModify = false;
        }
        return canModify;
    }

}
