package com.example.dztest.service.api_action.speech;

import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.speech.SpeechTaskApi;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Service
public class ASpeechTaskApi {
    @Autowired
    private SpeechTaskApi speechTaskApi;
    //上传外呼任务名单
    public HttpResponse uploadList(String filename){
        String base_path = getClass().getResource("/").getPath();

        String filePath = base_path.replaceFirst("test-classes", "classes") + "static/" + filename;
        HttpResponse httpResponse = speechTaskApi.uploadList(filePath);
        return httpResponse;
    }
}
