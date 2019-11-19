package com.lly.dao;

import com.lly.pojo.ParamPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IParamDao {

    /**
     * 查询系统参数个数
     * @param paramPojo
     * @return int
     */
    int getParamCount(@Param("paramPojo")ParamPojo paramPojo);

    /**
     * 查询系统参数
     * @param paramPojo
     * @return java.util.List<com.tcb.pojo.ParamPojo>
     */
    List<ParamPojo> getParam(@Param("paramPojo")ParamPojo paramPojo);

    /**
     * 查询系统参数(通过参数类型)
     * @param paramTypeCode
     * @return java.util.List<com.tcb.pojo.ParamPojo>
     */
    List<ParamPojo> getParamByType(@Param("paramTypeCode")String paramTypeCode);

    /**
     *  新增系统参数
     * @param paramPojo
     * @return int
     */
    int insertParam(@Param("paramPojo")ParamPojo paramPojo);

    /**
     * 更新系统参数
     * @param paramPojo
     * @return int
     */
    int updateParam(@Param("paramPojo")ParamPojo paramPojo);

    /**
     * 删除系统参数
     * @param idList
     * @return int
     */
    int deleteParam(@Param("idList")List<String> idList);

    /**
     * 存在此系统参数（更新时）
     * @param paramId
     * @param paramCode
     * @return
     */
    int existUpdateParam(@Param("paramId")String paramId, @Param("paramCode")String paramCode);

    /**
     * 存在此系统参数(删除时)
     * @param idList
     * @return
     */
    int existDeleteParam(@Param("idList")List<String> idList);

}
