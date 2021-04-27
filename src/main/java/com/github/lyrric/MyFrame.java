package com.github.lyrric;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
            for (File file : files) {
                if (file.isFile()) {
                    if(handler.executeOne(file)){
                        count++;
                    }
                    System.out.println(file.getName() + "------处理完成");
                }
            }
            JOptionPane.showMessageDialog(this, "处理完成，供处理文件：" + count + "个", "提示", JOptionPane.PLAIN_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
