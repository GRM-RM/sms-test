package com.grm.springboot.smstest.action;

import com.grm.springboot.smstest.dto.CusDto;
import com.grm.springboot.smstest.service.CusPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * A unique of the lion
 *
 * @author grm
 */
@Controller
public class IndexController {

    @Autowired
    private CusPhoneService cusPhoneService;

    @RequestMapping("/prereg")
    public String index(Model model){
        String token= UUID.randomUUID().toString();
        model.addAttribute("token",token);
        return "registercus";
    }

    @RequestMapping("/register")
    public String register(CusDto cusDto){
        cusPhoneService.addCus(cusDto);
        //return "findByToken?token="+cusDto.getToken();
        return "findByToken";
    }

    @RequestMapping("findByToken")
    @ResponseBody
    public CusDto findByToken(String token){
        CusDto cusDto=cusPhoneService.findByToken(token);
        return cusDto;
    }

}
