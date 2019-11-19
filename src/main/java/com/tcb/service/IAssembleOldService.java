package com.tcb.service;

import com.tcb.pojo.AssembleOldPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装老化服务接口
 * @Date: Create in 2019/3/28 13:42
 * @Modify by WangLei
 */
public interface IAssembleOldService {

    /**
     * 查询组装老化信息个数
     *
     * @param assembleOldPojo
     * @param statusCodeList
     * @return
     */
    int getAssembleOldCount(
            AssembleOldPojo assembleOldPojo,
            List<String> statusCodeList);

    /**
     * 查询组装老化信息
     *
     * @param assembleOldPojo
     * @param statusCodeList
     * @return
     */
    List<AssembleOldPojo> getAssembleOld(
            AssembleOldPojo assembleOldPojo,
            List<String> statusCodeList);

    /**
     * 组装老化处理
     *
     * @param assembleOldPojo
     * @return
     */
    int operateAssembleOld(AssembleOldPojo assembleOldPojo) throws Exception;

    /**
     * 删除组装老化信息
     *
     * @param idList
     * @return
     */
    int deleteAssembleOld(List<String> idList);

    /**
     * 组装老化是否能被删除
     *
     * @param assembleId
     * @return
     */
    boolean canAssembleOldDelete(String assembleId);

    /**
     * 组装老化是否能被修改
     *
     * @param assembleId
     * @return
     */
    boolean canAssembleOldModify(String assembleId);

}
