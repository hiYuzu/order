package com.lly.dao;

import com.lly.pojo.AssemblePojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装数据库映射接口
 * @Date: Create in 2019/3/18 10:09
 * @Modify by WangLei
 */
public interface IAssembleDao {

    /**
     * 查询组装信息个数
     *
     * @param assemblePojo
     * @param statusCodeList
     * @return
     */
    int getAssembleCount(
            @Param("assemblePojo") AssemblePojo assemblePojo,
            @Param("statusCodeList") List<String> statusCodeList);

    /**
     * 查询组装信息
     *
     * @param assemblePojo
     * @param statusCodeList
     * @return
     */
    List<AssemblePojo> getAssemble(
            @Param("assemblePojo") AssemblePojo assemblePojo,
            @Param("statusCodeList") List<String> statusCodeList);

    /**
     * 新增组装信息
     *
     * @param assemblePojo
     * @return
     */
    int insertAssemble(@Param("assemblePojo") AssemblePojo assemblePojo);

    /**
     * 更新组装信息
     *
     * @param assemblePojo
     * @return
     */
    int updateAssemble(@Param("assemblePojo") AssemblePojo assemblePojo);

    /**
     * 更新组装状态信息
     *
     * @param assembleId
     * @param assembleStatus
     * @return
     */
    int updateAssembleStatus(
            @Param("assembleId") String assembleId,
            @Param("assembleStatus") String assembleStatus);

    /**
     * 更新关键部件
     * @param assembleId
     * @param cruxNo
     * @return
     */
    int updateAssembleCruxNo(
            @Param("assembleId") String assembleId,
            @Param("cruxNo") String cruxNo);

    /**
     * 更新组装状态信息-批量
     *
     * @param assembleIdList
     * @param assembleStatus
     * @return
     */
    int updateAssembleStatusBatch(
            @Param("assembleIdList") String assembleIdList,
            @Param("assembleStatus") String assembleStatus);

    /**
     * 更新组装状态信息-发货单批量
     * @param deliverIdList
     * @param assembleStatus
     * @return
     */
    int updateAssembleStatusBatchByDeliver(
            @Param("deliverIdList") List<String> deliverIdList,
            @Param("assembleStatus") String assembleStatus);

    /**
     * 删除组装信息
     *
     * @param idList
     * @return
     */
    int deleteAssemble(@Param("idList") List<String> idList);

    /**
     * 组装单是否能被删除
     *
     * @param assembleId
     * @return
     */
    int canAssembleDelete(@Param("assembleId") String assembleId);

    /**
     * 组装单是否能被修改
     *
     * @param assembleId
     * @return
     */
    int canAssembleModify(@Param("assembleId") String assembleId);

}
