package com.lly.dao;

import com.lly.pojo.AssembleTestPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装测试数据库映射接口
 * @Date: Create in 2019/3/25 10:09
 * @Modify by WangLei
 */
public interface IAssembleTestDao {

    /**
     * 查询组装测试信息个数
     *
     * @param assembleTestPojo
     * @param statusCodeList
     * @return
     */
    int getAssembleTestCount(
            @Param("assembleTestPojo") AssembleTestPojo assembleTestPojo,
            @Param("statusCodeList") List<String> statusCodeList);

    /**
     * 查询组装测试信息
     *
     * @param assembleTestPojo
     * @param statusCodeList
     * @return
     */
    List<AssembleTestPojo> getAssembleTest(
            @Param("assembleTestPojo") AssembleTestPojo assembleTestPojo,
            @Param("statusCodeList") List<String> statusCodeList);

    /**
     * 新增组装测试信息
     *
     * @param assembleTestPojo
     * @return
     */
    int insertAssembleTest(@Param("assembleTestPojo") AssembleTestPojo assembleTestPojo);

    /**
     * 更新组装测试信息
     *
     * @param assembleTestPojo
     * @return
     */
    int updateAssembleTest(@Param("assembleTestPojo") AssembleTestPojo assembleTestPojo);

    /**
     * 删除组装测试信息
     *
     * @param idList
     * @return
     */
    int deleteAssembleTest(@Param("idList") List<String> idList);

    /**
     * 组装测试是否能被删除
     *
     * @param assembleId
     * @return
     */
    int canAssembleTestDelete(@Param("assembleId") String assembleId);

    /**
     * 组装测试是否能被修改
     *
     * @param assembleId
     * @return
     */
    int canAssembleTestModify(@Param("assembleId") String assembleId);

    /**
     * 是否存在测试记录
     *
     * @param assembleId
     * @return
     */
    int existAssembleTest(@Param("assembleId") String assembleId);

    /**
     * 通过组装ID查询组装测试信息
     *
     * @param assembleId
     * @return
     */
    List<AssembleTestPojo> getAssembleTestByAssembleId(@Param("assembleId") String assembleId);

}
