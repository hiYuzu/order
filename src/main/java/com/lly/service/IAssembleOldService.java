package com.lly.service;

import com.lly.pojo.AssembleOldPojo;

import java.util.List;

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
