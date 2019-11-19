package com.tcb.service;

import com.tcb.pojo.AssemblePartPojo;
import com.tcb.pojo.AssembleTestPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装测试服务接口
 * @Date: Create in 2019/3/25 14:40
 * @Modify by WangLei
 */
public interface IAssembleTestService {

    /**
     * 查询组装测试信息个数
     *
     * @param assembleTestPojo
     * @param statusCodeList
     * @return
     */
    int getAssembleTestCount(
            AssembleTestPojo assembleTestPojo,
            List<String> statusCodeList);

    /**
     * 查询组装测试信息
     *
     * @param assembleTestPojo
     * @param statusCodeList
     * @return
     */
    List<AssembleTestPojo> getAssembleTest(
            AssembleTestPojo assembleTestPojo,
            List<String> statusCodeList);

    /**
     * 组装测试处理
     *
     * @param assembleTestPojo
     * @param assemblePartPojoList
     * @param cruxNo
     * @return
     */
    int operateAssembleTest(AssembleTestPojo assembleTestPojo, List<AssemblePartPojo> assemblePartPojoList, String cruxNo) throws Exception;

    /**
     * 删除组装测试信息
     *
     * @param idList
     * @return
     */
    int deleteAssembleTest(List<String> idList);

    /**
     * 组装测试是否能被删除
     *
     * @param assembleId
     * @return
     */
    boolean canAssembleTestDelete(String assembleId);

    /**
     * 组装测试是否能被修改
     *
     * @param assembleId
     * @return
     */
    boolean canAssembleTestModify(String assembleId);

    /**
     * 通过组装ID查询组装测试信息
     *
     * @param assembleId
     * @return
     */
    AssembleTestPojo getAssembleTestByAssembleId(String assembleId);

}
