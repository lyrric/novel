package com.github.lyrric;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FileHandler {

    private final String title;
    private String[] content;

    public FileHandler(boolean b) {
        if(b){
            title = "【公主号：胖兔兔推文】";
            content = new String[]{"本小说由微信公众号：【胖兔兔推文】整理",
                    "每天定时更新超多好看的小说，关注我拒绝文荒。",
                    "附：【本作品来自互联网，本人不做任何负责】内容版权归作者所有！",
                    "==========================================="};
        }else{
            title = "【公主号：胖兔兔推文酱】";
            content = new String[]{"本小说由微信公众号：【胖兔兔推文酱】整理",
                    "每天定时更新超多好看的小说，关注我拒绝文荒。",
                    "附：【本作品来自互联网，本人不做任何负责】内容版权归作者所有！",
                    "==========================================="};
        }
    }

    private String getNewFileName(String name){
        String[] split = name.split("\\.");
        String ext = split[split.length - 1];
        String fileName = String.join(".", Arrays.asList(split).subList(0, split.length - 1));
        return "处理后文件/"+fileName + title + "." + ext;
    }

    public boolean executeOne(File file) throws IOException {
        String name = file.getName();
        if(name.contains(title) || name.endsWith(".jar")){
            return false;
        }
        //开头和结束加入content
        FileInputStream is = new FileInputStream(file);
        List<String> lines = IOUtils.readLines(is, getCharset(file));
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            //随机插入广告
            int num = random.nextInt(lines.size());
            lines.add(num, title);
        }
        File newFile = new File(getNewFileName(name));
        createFile(newFile);
        OutputStream os = new FileOutputStream(newFile);
        lines.addAll(0, Arrays.asList(content));
        lines.addAll(Arrays.asList(content));
        IOUtils.writeLines(lines, null, os, StandardCharsets.UTF_8);
        is.close();
        os.close();
        return true;
    }

    private Charset getCharset(File file) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return Charset.forName(code);
    }

    private void createFile(File file) throws IOException {
        File path = new File("./处理后文件");
        path.mkdirs();
        file.createNewFile();
    }
}
