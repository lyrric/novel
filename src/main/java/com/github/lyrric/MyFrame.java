package com.github.lyrric;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFrame extends JFrame {

    private JRadioButton r1 = new JRadioButton("胖兔兔推文", true);
    private JRadioButton r2 = new JRadioButton("胖兔兔推文酱");
    private JButton btn = new JButton("开始");
    private ButtonGroup group = new ButtonGroup();
    public MyFrame() throws HeadlessException {
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Just For Fun");
        setBounds(500 , 500, 300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r1.setBounds(20, 10, 120, 50);
        r2.setBounds(150, 10, 120, 50);
        group.add(r1);
        group.add(r2);
        add(r1);
        add(r2);

        btn.setBounds(60, 60, 160, 70);
        btn.addActionListener(t->{
            start();
        });
        add(btn);
        setVisible(true);
    }

    private void start()  {
        try {
            FileHandler handler = new FileHandler(r1.isSelected());
            File[] files = new File("./").listFiles();
            if(files == null){
                return;
            }
            int count = 0;
            List<String> fileNames = new ArrayList<>(files.length);
            for (File file : files) {
                if (file.isFile()) {
                    if(handler.executeOne(file)){
                        fileNames.add(file.getName().replaceAll(".txt", ""));
                        count++;
                    }
                    System.out.println(file.getName() + "------处理完成");
                }
            }
            writeFileNames(fileNames);
            JOptionPane.showMessageDialog(this, "处理完成，供处理文件：" + count + "个", "提示", JOptionPane.PLAIN_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void writeFileNames(List<String> fileNames) {
        File file = new File("处理后文件/z全部文件名.txt");
        try {
            if(file.exists()) file.delete();
            file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            IOUtils.writeLines(fileNames, null, os, StandardCharsets.UTF_8);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
