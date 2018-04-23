package com.hfkj.bbt.rest;

import com.hfkj.bbt.remote.IRestService;
import com.hfkj.bbt.util.ComUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "rest")
public class BBTController {

    private static final Logger log=Logger.getLogger(BBTController.class);

    @Autowired
    private IRestService restService;
    /**
     * 接收网关的请求
     *
     * @param sendData
     * @return
     */
    @RequestMapping(value = "sendStatus")
    @ResponseBody
    public String sendStatus( String sendData) {
        log.info("收到来自设备的数据："+sendData);
        if (!ComUtil.stringIsNotNull(sendData)){

            return "数据为空";
        }
        return restService.doAnalysisData(sendData);
    }






}
