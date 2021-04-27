package com.lyrric;

import com.github.lyrric.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author wangxiaodong
 * @version 1.0
 * @date 2021/4/26 10:35
 */
public class test {
    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler(true);
        File file = new File("C:\\Users\\Administrator\\Desktop\\2021.4.17言情书单~明月珰\\《百媚生》作者：明月珰.txt");
        fileHandler.executeOne(file);

    }
}
