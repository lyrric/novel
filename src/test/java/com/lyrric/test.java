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
        File file = new File("C:\\Users\\xuanxin\\Desktop\\新建文件夹\\（1V1）《八月夜》作者：茶茶好萌--【完结】.txt");
        fileHandler.executeOne(file);
    }

}
