package com.grm.springboot.smstest.service.impl;

import com.grm.springboot.smstest.dao.CusPhoneMapper;
import com.grm.springboot.smstest.dto.CusDto;
import com.grm.springboot.smstest.pojo.CusPhone;
import com.grm.springboot.smstest.pojo.CusPhoneExample;
import com.grm.springboot.smstest.service.CusPhoneService;
import com.grm.springboot.smstest.service.RedisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A unique of the lion
 *
 * @author grm
 */
@Service
public class CusPhoneServiceImpl implements CusPhoneService {
    @Autowired
    private CusPhoneMapper cusPhoneMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public void addCus(CusDto cusDto) {
        CusPhone cusPhone = new CusPhone();

        BeanUtils.copyProperties(cusDto, cusPhone);

        cusPhoneMapper.insert(cusPhone);
    }

    @Override
    public CusDto findByToken(String token) {

        Object object = redisService.get(token);

        if (object == null) {
            CusPhoneExample example = new CusPhoneExample();

            CusPhoneExample.Criteria cri=example.createCriteria();
            cri.andTokenEqualTo(token);

            List<CusPhone> list = cusPhoneMapper.selectByExample(example);

            CusDto cusDto = new CusDto();

            BeanUtils.copyProperties(list.get(0), cusDto);

            redisService.set(token,cusDto);

            return cusDto;
        }
        return (CusDto) object;
    }

    @Override
    public CusDto getCus(Long cid) {
        CusPhone cusPhone = cusPhoneMapper.selectByPrimaryKey(cid);
        CusDto cusDto = new CusDto();
        BeanUtils.copyProperties(cusPhone, cusDto);
        return cusDto;
    }
}
