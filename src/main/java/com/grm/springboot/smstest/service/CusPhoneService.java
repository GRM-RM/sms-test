package com.grm.springboot.smstest.service;

import com.grm.springboot.smstest.dto.CusDto;

/**
 * A unique of the lion
 *
 * @author grm
 */
public interface CusPhoneService {

    void addCus(CusDto cusDto);

    CusDto findByToken(String token);

    CusDto getCus(Long cid);

}
