package com.lly.dao;

import com.lly.pojo.DeliverPojo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * @Author: WangLei
 * @Description: 发货单商务数据库映射类
 * @Date: Create in 2018/2/26 15:06
 * @Modify by WangLei
 */
public interface IBusinessDao {

    /**
     * 发货单商务处理
     * @param deliverPojo
     * @return
     */
    int businessDeliver(@Param("deliverPojo")DeliverPojo deliverPojo);

    /**
     * 是否发货单能被商务处理
     * @param deliverId
     * @return
     */
    int canDeliverBusiness(@Param("deliverId")String deliverId);

    /**
     * 更新技术合同相关信息
     * @param deliverPojo
     * @return
     */
    int updateTsContract(@Param("deliverPojo")DeliverPojo deliverPojo);

    /**
     * 更新安装质保日期
     * @param deliverId
     * @param warrantyPeriod
     * @return
     */
    int updateInstallWarranty(@Param("deliverId")String deliverId,@Param("warrantyPeriod")Timestamp warrantyPeriod);

}
