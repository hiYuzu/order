package com.lly.dao;

import com.lly.pojo.GoodsRepairPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRepairDao {

    /**
     * 查询设备维修记录个数
     * @param goodsRepairPojo
     * @return
     */
    int getRepairGoodsCount(@Param("goodsRepairPojo")GoodsRepairPojo goodsRepairPojo);

    /**
     * 查询设备维修记录
     * @param goodsRepairPojo
     * @return
     */
    List<GoodsRepairPojo> getRepairGoods(@Param("goodsRepairPojo")GoodsRepairPojo goodsRepairPojo);

    /**
     * 新增设备维修记录
     * @param goodsRepairPojo
     * @return
     */
    int insertRepairGoods(@Param("goodsRepairPojo")GoodsRepairPojo goodsRepairPojo);

    /**
     * 修改设备维修记录
     * @param goodsRepairPojo
     * @return
     */
    int updateRepairGoods(@Param("goodsRepairPojo")GoodsRepairPojo goodsRepairPojo);

    /**
     * 删除设备维修记录
     * @param repairId
     * @return
     */
    int deleteRepairGoods(@Param("repairId")String repairId);

}
