package com.github.lyrric;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {

    private final String title;
    private String[] content;

    private List<String> ads;

    public FileHandler(boolean b) {
        if(b){
            title = "【公主号：胖兔兔推文】";
            content = new String[]{
                    "===========================================",
                    "本小说由微信公众号：【胖兔兔推文】整理",
                    "每天定时更新超多好看的小说，关注我拒绝文荒。",
                    "附：【本作品来自互联网，本人不做任何负责】内容版权归作者所有！",
                    "==========================================="};
        }else{
            title = "【公主号：胖兔兔推文酱】";
            content = new String[]{
                    "===========================================",
                    "本小说由微信公众号：【胖兔兔推文酱】整理",
                    "每天定时更新超多好看的小说，关注我拒绝文荒。",
                    "附：【本作品来自互联网，本人不做任何负责】内容版权归作者所有！",
                    "==========================================="};
        }
        ads = getFilterText();
    }

    private String getNewFileName(String name){
        String[] split = name.split("\\.");
        String ext = split[split.length - 1];
        String fileName = String.join(".", Arrays.asList(split).subList(0, split.length - 1));
        return "处理后文件/"+fileName + title + "." + ext;
    }

    public boolean executeOne(File file) throws IOException {
        String name = file.getName();
        if(name.contains(title) || name.startsWith("novel") || name.endsWith(".jar")){
            return false;
        }
        if(!name.toLowerCase().endsWith(".txt")){
            return false;
        }
        //开头和结束加入content
        FileInputStream is = new FileInputStream(file);
        Charset charset = Charset.forName(getCharset(file));
        List<String> lines = IOUtils.readLines(is, charset);
        Iterator<String> iterator = lines.iterator();
        //移除广告
        while (iterator.hasNext()){
            String str = iterator.next();
            for (String ad : ads) {
                if(str.contains(ad)){
                    iterator.remove();
                    break;
                }
            }
        }
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

    private String getCharset(File file) throws IOException {
        byte[] first3Bytes = new byte[3];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return "GBK"; // 文件编码为 ANSI
            }

            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                return "UTF-16LE"; // 文件编码为 Unicode
            }

            if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                return "UTF-16BE"; // 文件编码为 Unicode big endian
            }

            if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                return "UTF-8"; // 文件编码为 UTF-8
            }

            bis.reset();

            while ((read = bis.read()) != -1) {
                if (read >= 0xF0) {
                    break;
                }
                if (0x80 <= read && read <= 0xBF) {
                    break;
                }
                if (0xC0 <= read && read <= 0xDF) {
                    read = bis.read();
                    if (0x80 <= read && read <= 0xBF) {
                        // (0x80 - 0xBF),也可能在GB编码内
                        continue;
                    }
                    break;
                } else if (0xE0 <= read) {// 也有可能出错，但是几率较小
                    read = bis.read();
                    if (0x80 <= read && read <= 0xBF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            return "UTF-8";
                        }
                        break;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "GBK";
    }

    private void createFile(File file) throws IOException {
        File path = new File("./处理后文件");
        path.mkdirs();
        file.createNewFile();
    }

    private List<String> getFilterText(){
        File file = new File("novel.txt");
        if(file.exists()){
            try {
                List<String> ads = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8);
                return ads.stream().filter(t->!t.trim().equals("")).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
