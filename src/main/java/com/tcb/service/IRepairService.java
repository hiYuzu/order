package com.tcb.service;

import com.tcb.pojo.GoodsRepairPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 设备维修服务接口
 * @Date: Create in 2018/3/2 9:04
 * @Modify by WangLei
 */
public interface IRepairService {

    /**
     * 查询设备维修记录个数
     * @param goodsRepairPojo
     * @return
     */
    int getRepairGoodsCount(GoodsRepairPojo goodsRepairPojo);

    /**
     * 查询设备维修记录
     * @param goodsRepairPojo
     * @return
     */
    List<GoodsRepairPojo> getRepairGoods(GoodsRepairPojo goodsRepairPojo);

    /**
     * 新增设备维修记录
     * @param goodsRepairPojo
     * @return
     */
    int insertRepairGoods(GoodsRepairPojo goodsRepairPojo) throws Exception;

    /**
     * 修改设备维修记录
     * @param goodsRepairPojo
     * @return
     */
    int updateRepairGoods(GoodsRepairPojo goodsRepairPojo) throws Exception;

    /**
     * 删除设备维修记录
     * @param repairId
     * @return
     */
    int deleteRepairGoods(String repairId) throws Exception;

}
