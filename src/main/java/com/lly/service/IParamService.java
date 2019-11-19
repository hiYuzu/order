package com.lly.service;

import com.lly.pojo.ParamPojo;

import java.util.List;

public interface IParamService {

    /**
     * 查询系统参数个数
     *
     * @param paramPojo
     * @return int
     */
    int getParamCount(ParamPojo paramPojo);

    /**
     * 查询系统参数
     *
     * @param paramPojo
     * @return java.util.List<com.tcb.pojo.ParamPojo>
     */
    List<ParamPojo> getParam(ParamPojo paramPojo);

    /**
     * 查询系统参数(通过参数类型)
     *
     * @param paramTypeCode
     * @return java.util.List<com.tcb.pojo.ParamPojo>
     */
    List<ParamPojo> getParamByType(String paramTypeCode);

    /**
     * 新增系统参数
     *
     * @param paramPojo
     * @return int
     */
    int insertParam(ParamPojo paramPojo) throws Exception;

    /**
     * 更新系统参数
     *
     * @param paramPojo
     * @return int
     */
    int updateParam(ParamPojo paramPojo) throws Exception;

    /**
     * 删除系统参数
     *
     * @param idList
     * @return int
     */
    int deleteParam(List<String> idList) throws Exception;

    /**
     * 存在此系统参数（更新时）
     *
     * @param paramId
     * @param paramCode
     * @return
     */
    int existUpdateParam(String paramId, String paramCode);

    /**
     * 存在此系统参数(删除时)
     *
     * @param idList
     * @return
     */
    int existDeleteParam(List<String> idList);

}
