package com.xms.task.repository.mysql.mapper;

import com.xms.task.repository.mysql.criteria.Device;
import com.xms.task.repository.mysql.criteria.DeviceCriteria;
import java.util.List;

import com.xms.task.repository.mysql.criteria.DevicePOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DeviceMapper {
    int countByExample(DeviceCriteria example);

    int deleteByExample(DeviceCriteria example);

    int deleteByPrimaryKey(String deviceSn);

    int insert(Device record);

    int insertSelective(Device record);

    List<Device> selectByExampleWithRowbounds(DeviceCriteria example, RowBounds rowBounds);

    List<Device> selectByExample(DeviceCriteria example);

    Device selectByPrimaryKey(String deviceSn);

    int updateByExampleSelective(@Param("record") Device record, @Param("example") DeviceCriteria example);

    int updateByExample(@Param("record") Device record, @Param("example") DeviceCriteria example);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    void batchInsert(@Param("items") List<Device> items);

    void count(DevicePOJO devicePOJO);
}