package com.example.dztest.service.page.iframe;

import com.example.dztest.ui.UIDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseIFrame {
    @Autowired
    UIDriver ui;
    private boolean isInFrame = false;
    protected abstract void onGoToIFrame();
    //默认实现
    protected  void onExitIFrame(){
        ui.switch_to_default_content(); //默认退出到默认iframe
    }

    public void goToIFrame(){
        if(!isInFrame){
            onGoToIFrame();
            isInFrame=true;
        }
    }

    public void exitIFrame(){
        if(isInFrame){
            onExitIFrame();
            isInFrame=false;
        }
    }
}
