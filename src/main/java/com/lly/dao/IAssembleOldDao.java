package com.lly.dao;

import com.lly.pojo.AssembleOldPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装老化数据库映射接口
 * @Date: Create in 2019/3/25 10:09
 * @Modify by WangLei
 */
public interface IAssembleOldDao {


    /**
     * 查询组装老化信息个数
     *
     * @param assembleOldPojo
     * @param statusCodeList
     * @return
     */
    int getAssembleOldCount(
            @Param("assembleOldPojo") AssembleOldPojo assembleOldPojo,
            @Param("statusCodeList") List<String> statusCodeList);

    /**
     * 查询组装老化信息
     *
     * @param assembleOldPojo
     * @param statusCodeList
     * @return
     */
    List<AssembleOldPojo> getAssembleOld(
            @Param("assembleOldPojo") AssembleOldPojo assembleOldPojo,
            @Param("statusCodeList") List<String> statusCodeList);

    /**
     * 新增组装老化信息
     *
     * @param assembleOldPojo
     * @return
     */
    int insertAssembleOld(@Param("assembleOldPojo") AssembleOldPojo assembleOldPojo);

    /**
     * 更新组装老化信息
     *
     * @param assembleOldPojo
     * @return
     */
    int updateAssembleOld(@Param("assembleOldPojo") AssembleOldPojo assembleOldPojo);

    /**
     * 删除组装老化信息
     *
     * @param idList
     * @return
     */
    int deleteAssembleOld(@Param("idList") List<String> idList);

    /**
     * 组装老化是否能被删除
     *
     * @param assembleId
     * @return
     */
    int canAssembleOldDelete(@Param("assembleId") String assembleId);

    /**
     * 组装老化是否能被修改
     *
     * @param assembleId
     * @return
     */
    int canAssembleOldModify(@Param("assembleId") String assembleId);

    /**
     * 是否存在老化记录
     * @param assembleId
     * @return
     */
    int existAssembleOld(@Param("assembleId") String assembleId);

}
