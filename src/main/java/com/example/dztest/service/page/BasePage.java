package com.example.dztest.service.page;

import com.example.dztest.service.actions.AICallPage;
import com.example.dztest.service.actions.ALoginPage;
import com.example.dztest.service.api_action.ABasicApi;
import com.example.dztest.service.interfaces.basic.BasicApi;
import com.example.dztest.service.interfaces.speech.UserCommunicateApi;
import com.example.dztest.service.interfaces.uss.UssApi;
import com.example.dztest.service.remote.RemoteSipp;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.screen_shot.ScreenShotUtils;
import com.example.dztest.utils.screen_shot.UIScreenShotByException;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.IHookCallBack;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Optional;


@Service
public class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    @Autowired
    protected UIDriver uiDriver;

    private WebDriver webDriver;

    @Autowired
    protected UserCommunicateApi userCommunicateApi;

    @Autowired
    protected UssApi ussApi;

    @Autowired
    protected BasicApi basicApi;

    @Autowired
    protected ABasicApi aBasicApi;

    @Autowired
    protected ALoginPage aLoginPage;

    @Autowired
    protected ICallPage iCallPage;

    @Autowired
    protected AICallPage aiCallPage;

    @Autowired
    protected RemoteSipp remoteSipp;

    @Autowired
    protected CallInfoPage callInfoPage;

}