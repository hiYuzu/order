package com.tcb.service.impl;

import com.tcb.dao.IAssembleDao;
import com.tcb.dao.IAssemblePartDao;
import com.tcb.dao.IAssembleTestDao;
import com.tcb.pojo.AssemblePartPojo;
import com.tcb.pojo.AssemblePojo;
import com.tcb.pojo.AssembleTestPojo;
import com.tcb.service.IAssembleTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装测试接口实现类
 * @Date: Create in 2019/3/25 13:35
 * @Modify by WangLei
 */
@Service("assembleTestService")
@Transactional(rollbackFor = Exception.class)
public class AssembleTestServiceImpl implements IAssembleTestService {

    @Resource
    private IAssembleTestDao assembleTestDao;

    @Resource
    private IAssembleDao assembleDao;

    @Resource
    private IAssemblePartDao assemblePartDao;

    @Override
    public int getAssembleTestCount(AssembleTestPojo assembleTestPojo, List<String> statusCodeList) {
        return assembleTestDao.getAssembleTestCount(assembleTestPojo, statusCodeList);
    }

    @Override
    public List<AssembleTestPojo> getAssembleTest(AssembleTestPojo assembleTestPojo, List<String> statusCodeList) {
        return assembleTestDao.getAssembleTest(assembleTestPojo, statusCodeList);
    }

    @Override
    public int operateAssembleTest(AssembleTestPojo assembleTestPojo, List<AssemblePartPojo> assemblePartPojoList, String cruxNo) throws Exception {
        int result = 0;
        if (assembleTestPojo != null) {
            if (assembleTestPojo.getAssemblePojo() != null
                    && assembleTestDao.existAssembleTest(String.valueOf(assembleTestPojo.getAssemblePojo().getAssembleId())) > 0) {
                result = assembleTestDao.updateAssembleTest(assembleTestPojo);
            } else {
                result = assembleTestDao.insertAssembleTest(assembleTestPojo);
            }
            if (result > 0) {
                //更新部件
                if (assemblePartPojoList != null && assemblePartPojoList.size() > 0) {
                    for (AssemblePartPojo temp : assemblePartPojoList) {
                        AssemblePojo assemblePojoTemp = new AssemblePojo();
                        assemblePojoTemp.setAssembleId(assembleTestPojo.getAssemblePojo().getAssembleId());
                        temp.setAssemblePojo(assemblePojoTemp);
                    }
                    assemblePartDao.updateAssemblePart(assemblePartPojoList);
                }
                //更新关键部件
                assembleDao.updateAssembleCruxNo(String.valueOf(assembleTestPojo.getAssemblePojo().getAssembleId()), cruxNo);
                String statusCode = assembleTestPojo.getAssemblePojo().getAssembleStatus().getStatusCode();
                assembleDao.updateAssembleStatus(String.valueOf(assembleTestPojo.getAssemblePojo().getAssembleId()), statusCode);
            }
        }
        return result;
    }

    @Override
    public int deleteAssembleTest(List<String> idList) {
        return assembleTestDao.deleteAssembleTest(idList);
    }

    @Override
    public boolean canAssembleTestDelete(String assembleId) {
        boolean canDelete;
        if (assembleTestDao.canAssembleTestDelete(assembleId) > 0) {
            canDelete = true;
        } else {
            canDelete = false;
        }
        return canDelete;
    }

    @Override
    public boolean canAssembleTestModify(String assembleId) {
        boolean canModify;
        if (assembleTestDao.canAssembleTestModify(assembleId) > 0) {
            canModify = true;
        } else {
            canModify = false;
        }
        return canModify;
    }

    @Override
    public AssembleTestPojo getAssembleTestByAssembleId(String assembleId) {
        List<AssembleTestPojo> assembleTestPojoList = assembleTestDao.getAssembleTestByAssembleId(assembleId);
        if (assembleTestPojoList != null && assembleTestPojoList.size() > 0) {
            return assembleTestPojoList.get(0);
        } else {
            return null;
        }
    }

}
