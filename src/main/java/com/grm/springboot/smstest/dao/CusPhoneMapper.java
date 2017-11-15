package com.grm.springboot.smstest.dao;

import com.grm.springboot.smstest.pojo.CusPhone;
import com.grm.springboot.smstest.pojo.CusPhoneExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CusPhoneMapper {
    int countByExample(CusPhoneExample example);

    int deleteByExample(CusPhoneExample example);

    int deleteByPrimaryKey(Long cid);

    int insert(CusPhone record);

    int insertSelective(CusPhone record);

    List<CusPhone> selectByExample(CusPhoneExample example);

    CusPhone selectByPrimaryKey(Long cid);

    int updateByExampleSelective(@Param("record") CusPhone record, @Param("example") CusPhoneExample example);

    int updateByExample(@Param("record") CusPhone record, @Param("example") CusPhoneExample example);

    int updateByPrimaryKeySelective(CusPhone record);

    int updateByPrimaryKey(CusPhone record);
}