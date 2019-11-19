package com.tcb.service;

import com.tcb.pojo.AssemblePartPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装部件服务接口
 * @Date: Create in 2019/3/18 14:40
 * @Modify by WangLei
 */
public interface IAssemblePartService {

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
     *
     * @param assembleId
     * @return
     */
    List<AssemblePartPojo> getPartsByAssembleId(String assembleId);

}
