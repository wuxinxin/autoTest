package com.example.dztest.service.api_action.outbound;

import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.outbound.OutboundTaskManagerV2ControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AOutboundTaskManagerV2ControllerApi {

    @Autowired
    private OutboundTaskManagerV2ControllerApi outboundTaskManagerV2ControllerApi;

    //上传外呼任务名单
    public HttpResponse importList(String robotid, String filename){
        String base_path = getClass().getResource("/").getPath();

        String filePath = base_path.replaceFirst("test-classes", "classes") + "static/outbound/" + filename;
        HttpResponse httpResponse = outboundTaskManagerV2ControllerApi.importList(robotid, filePath,"1");
        return httpResponse;
    }

}
