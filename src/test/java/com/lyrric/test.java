package com.lyrric;

import com.github.lyrric.FileHandler;

import java.io.*;

/**
 * @author wangxiaodong
 * @version 1.0
 * @date 2021/4/26 10:35
 */
public class test {
    public static void main(String[] args) throws Exception {
        FileHandler fileHandler = new FileHandler(true);
        File file = new File("C:\\Users\\xuanxin\\Desktop\\需要替换的内容\\【GL】纯属意外.txt");
        fileHandler.executeOne(file);
    }

}
