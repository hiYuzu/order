package com.tcb.dao;

import com.tcb.pojo.DeliverPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单数据库映射类
 * @Date: Create in 2018/1/24 10:57
 * @Modify by WangLei
 */
public interface IDeliverDao {

    /**
     * 查询发货单数据个数
     * @param deliverPojo
     * @param statusCodeList
     * @return
     */
    int getDeliverCount(@Param("deliverPojo")DeliverPojo deliverPojo,@Param("statusCodeList")List<String> statusCodeList);

    /**
     * 查询发货单数据
     * @param deliverPojo
     * @param statusCodeList
     * @return
     */
    List<DeliverPojo> getDeliver(@Param("deliverPojo")DeliverPojo deliverPojo,@Param("statusCodeList")List<String> statusCodeList);

    /**
     * 新增发货单
     * @param deliverPojo
     * @return
     */
    int insertDeliver(@Param("deliverPojo")DeliverPojo deliverPojo);

    /**
     * 更新发货单
     * @param deliverPojo
     * @return
     */
    int updateDeliver(@Param("deliverPojo")DeliverPojo deliverPojo);

    /**
     * 删除发货单
     * @param idList
     * @return
     */
    int deleteDelivers(@Param("idList")List<String> idList);

    /**
     * 是否发货单能被删除
     * @param deliverId
     * @return
     */
    int canDeliverDelete(@Param("deliverId")String deliverId);

    /**
     * 是否发货单能被修改
     * @param deliverId
     * @return
     */
    int canDeliverModify(@Param("deliverId")String deliverId);

    /**
     * 更新发货单状态
     * @param deliverId
     * @param statusCoe
     * @return
     */
    int updateDeliveryStatus(@Param("deliverId")String deliverId,@Param("statusCoe")String statusCoe);

}
