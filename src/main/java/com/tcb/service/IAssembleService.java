package com.tcb.service;

import com.tcb.pojo.AssemblePartPojo;
import com.tcb.pojo.AssemblePojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装服务接口
 * @Date: Create in 2019/3/18 14:40
 * @Modify by WangLei
 */
public interface IAssembleService {

    /**
     * 查询组装信息个数
     *
     * @param assemblePojo
     * @param statusCodeList
     * @return
     */
    int getAssembleCount(AssemblePojo assemblePojo, List<String> statusCodeList);

    /**
     * 查询组装信息
     *
     * @param assemblePojo
     * @param statusCodeList
     * @return
     */
    List<AssemblePojo> getAssemble(AssemblePojo assemblePojo, List<String> statusCodeList);

    /**
     * 新增组装信息
     *
     * @param assemblePojo
     * @param assemblePartPojoList
     * @return
     */
    int insertAssemble(AssemblePojo assemblePojo, List<AssemblePartPojo> assemblePartPojoList) throws Exception;

    /**
     * 更新组装信息
     *
     * @param assemblePojo
     * @param assemblePartPojoList
     * @param updatePartFlag
     * @return
     */
    int updateAssemble(AssemblePojo assemblePojo, List<AssemblePartPojo> assemblePartPojoList, boolean updatePartFlag) throws Exception;

    /**
     * 删除组装信息
     *
     * @param idList
     * @return
     */
    int deleteAssembles(List<String> idList) throws Exception;

    /**
     *  组装单是否能被删除
     * @param assembleId
     * @return
     * @throws Exception
     */
    boolean canAssembleDelete(String assembleId) throws Exception;

    /**
     * 发组装单是否能被修改
     * @param assembleId
     * @return
     * @throws Exception
     */
    boolean canAssembleModify(String assembleId) throws Exception;

}
