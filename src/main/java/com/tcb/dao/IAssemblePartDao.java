package com.tcb.dao;

import com.tcb.pojo.AssemblePartPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装部件数据库映射接口
 * @Date: Create in 2019/3/18 13:22
 * @Modify by WangLei
 */
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
