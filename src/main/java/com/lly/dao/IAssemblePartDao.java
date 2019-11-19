package com.lly.dao;

import com.lly.pojo.AssemblePartPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAssemblePartDao {

    /**
     * 查询组装部件信息个数
     *
     * @param assemblePartPojo
     * @return
     */
    int getAssemblePartCount(@Param("assemblePartPojo") AssemblePartPojo assemblePartPojo);

    /**
     * 查询组装部件信息
     *
     * @param assemblePartPojo
     * @return
     */
    List<AssemblePartPojo> getAssemblePart(@Param("assemblePartPojo") AssemblePartPojo assemblePartPojo);

    /**
     * 查询组装部信息(根据组装Id)
     * @param assembleId
     * @return
     */
    List<AssemblePartPojo> getPartsByAssembleId(@Param("assembleId") String assembleId);

    /**
     * 新增组装部件信息
     *
     * @param assemblePartPojoList
     * @return
     */
    int insertAssemblePart(@Param("assemblePartPojoList") List<AssemblePartPojo> assemblePartPojoList);

    /**
     * 更新组装部件信息
     *
     * @param assemblePartPojoList
     * @return
     */
    int updateAssemblePart(@Param("assemblePartPojoList") List<AssemblePartPojo> assemblePartPojoList);

    /**
     * 删除组装部件信息
     *
     * @param assembleId
     * @param idList
     * @return
     */
    int deleteAssemblePart(@Param("assembleId") String assembleId, @Param("idList") List<String> idList);

}
