package com.lly.service;

import com.lly.pojo.ParamPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 系统参数服务接口
 * @Date: Create in 2018/2/11 14:34
 * @Modify by WangLei
 */
public interface IParamService {

    /**
     * 查询系统参数个数
     * @author WangLei
     * @date 2018/2/9 15:17
     * @param paramPojo
     * @return int
     */
    int getParamCount(ParamPojo paramPojo);

    /**
     * 查询系统参数
     * @author WangLei
     * @date 2018/2/9 15:19
     * @param paramPojo
     * @return java.util.List<com.tcb.pojo.ParamPojo>
     */
    List<ParamPojo> getParam(ParamPojo paramPojo);

    /**
     * 查询系统参数(通过参数类型)
     * @author WangLei
     * @date 2018/2/9 15:19
     * @param paramTypeCode
     * @return java.util.List<com.tcb.pojo.ParamPojo>
     */
    List<ParamPojo> getParamByType(String paramTypeCode);

    /**
     *  新增系统参数
     * @author WangLei
     * @date 2018/2/9 15:21
     * @param paramPojo
     * @return int
     */
    int insertParam(ParamPojo paramPojo) throws Exception;

    /**
     * 更新系统参数
     * @author WangLei
     * @date 2018/2/9 15:22
     * @param paramPojo
     * @return int
     */
    int updateParam(ParamPojo paramPojo) throws Exception;

    /**
     * 删除系统参数
     * @author WangLei
     * @date 2018/2/9 15:23
     * @param idList
     * @return int
     */
    int deleteParam(List<String> idList) throws Exception;

    /**
     * 存在此系统参数（更新时）
     * @param paramId
     * @param paramCode
     * @return
     */
    int existUpdateParam(String paramId,String paramCode);

    /**
     * 存在此系统参数(删除时)
     * @param idList
     * @return
     */
    int existDeleteParam(List<String> idList);

}
