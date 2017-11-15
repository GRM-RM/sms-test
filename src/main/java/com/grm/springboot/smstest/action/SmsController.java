package com.grm.springboot.smstest.action;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.grm.springboot.smstest.dto.CusDto;
import com.grm.springboot.smstest.service.RedisService;
import com.grm.springboot.smstest.utils.AliAccessKey;
import com.grm.springboot.smstest.utils.RandomStringTLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author grm
 */

@Controller
public class SmsController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/index")
    public String index1() {
        return "index";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(String mobile, String code) {
        Map<String, Object> map = new HashMap<String, Object>();

        long subCount = redisService.incr("error" + mobile);

        if (subCount > 3) {
            map.put("codeMsg", "验证超过3次,请重新获取");
            return map;
        }

        String value = (String) redisService.get(mobile);
        if (value == null) {
            map.put("codeMsg", "验证码已过期,请重新获取");
            return map;
        }


        if (!code.equals(value)) {
            map.put("codeMsg", "验证码错误");
            map.put("subCount", subCount);
            return map;
        }

        map.put("status", true);
        redisService.del(mobile);
        redisService.del("error" + mobile);

        return map;
    }

    @RequestMapping("/mt")
    public String index() {
        return "/mtlogin";
    }

    private String send(String phoneNum) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数

        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";
        //替换成你的AK
        //你的accessKeyId,参考本文档步骤2
        final String accessKeyId = AliAccessKey.accessKeyId;
        //你的accessKeySecret，参考本文档步骤2
        final String accessKeySecret = AliAccessKey.accessKeySecret;

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("聂释隆");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_109365148");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //生成验证码：
        String numeric = RandomStringTLUtils.randomNumeric(6);
        //动态传递验证码给手机
        request.setTemplateParam("{\"code\":\"" + numeric + "\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)

        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("java1712");

//        //请求失败这里会抛ClientException异常
//        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//
//            //将电话号码和验证码存入redis中
//            redisService.set(phoneNum, numeric);
//            //并且设置有效时间为5分钟
//            //redisService.expire(phoneNum,300);
//            //请求成功
//            return numeric;
//        }
        redisService.del(phoneNum);
        redisService.del("error" + phoneNum);
        redisService.set(phoneNum, numeric);
        System.out.println(numeric);
        return "ok";
    }



    @RequestMapping("/ajaxNum")
    @ResponseBody
    public String sendMsg(String phoneNum, String token) throws ClientException {

        CusDto cusDto = (CusDto) redisService.get(token);

        //设置每个手机号每天只能接受5次验证码,每个手机号在redis存的key为: maxphone+phonenum
        long maxphone = redisService.incr("maxphone" + phoneNum);
        if (maxphone > 5) {
            return "该手机号今天验证已经超过上限";
        }

        //计算每天剩余的时间 单位为秒
        double curTime = System.currentTimeMillis() / 1000.0 + 8 * 3600;
        double oneday = 24 * 3600;
        double times = oneday - curTime % oneday;

        if (maxphone == 1) {
            redisService.expire("maxphone" + phoneNum, (long) times);
        }

        long maxday = redisService.incr("maxday" + token);

        //每一天的第一次访问设置，通过判断maxday==1时,设置有效期 每个部门一天的访问量在redis中存的key为: maxday+token
        if (maxday == 1) {
            redisService.expire("maxday" + token, (long) times);
        }

        //但每天的短信量大于限制的访问量，返回错误页面
        if (maxday > cusDto.getMaxday()) {
            return "error";
        }

        return send(phoneNum);
    }
}
