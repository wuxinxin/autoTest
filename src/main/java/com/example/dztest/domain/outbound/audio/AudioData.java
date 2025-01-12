package com.example.dztest.domain.outbound.audio;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class AudioData {

    @ExcelProperty(value = "cellphone", index = 0)
    private String cellphone;

    @ExcelProperty(value = "sessionId", index = 1)
    private String sessionId;

    @ExcelProperty(value = "callId", index = 2)
    private String callId;

    @ExcelProperty(value = "url", index = 3)
    private String url;

    @ExcelProperty(value = "通话时长", index = 4)
    private String callDurationSec;

    @ExcelProperty(value = "录音时长", index = 5)
    private long time;

    public String getCallDurationSec() {
        return callDurationSec;
    }

    public void setCallDurationSec(String callDurationSec) {
        this.callDurationSec = callDurationSec;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
