package com.example.dztest;

import com.example.dztest.ui.UIDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestNgMain {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("TestNgMain 入参信息: " + arg);
        }

        org.testng.TestNG.main(args);
    }
}
