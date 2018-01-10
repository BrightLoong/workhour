/*
 * @(#)MainFrame.java 2017年12月29日下午4:16:12
 * workhour
 * Copyright 2017 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.github.brightloong.workhour.ui;

import io.github.brightloong.workhour.bean.ActionInfo;
import io.github.brightloong.workhour.bean.Config;
import io.github.brightloong.workhour.constants.INormalContants;
import io.github.brightloong.workhour.service.IWorkHourSerive;
import io.github.brightloong.workhour.utils.IOUtils;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MainFrame
 * @author 陈龙
 * @version 1.0
 *
 */

@Component("mainFrame")
public class MainFrame extends JFrame implements ActionListener{
    
    private static final String APP_NAME = "工时转换工具";
    
    private static final String AUTHOR = "--By BrightLoong";
    
    @Autowired
    private IWorkHourSerive workHourSerive;
    
    // 选择导入的按钮
    private JButton inputBtn;
    
    //转换按钮
    private JButton startBtn;
    
    //打开文件按钮
    private JButton openFileBtn;
    
    private JPanel operatePanel;
    
    // 显示文件的输入框
    private JTextField inputField;
    
    // 选择导出的按钮
    private JButton outputBtn;
    
    private JLabel msgLable;
    
    //private JLabel loadingLable;
    
   // 显示文件的输出框
    private JTextField outputField;
    
    private JTextField yearField;
    
    private JTextField monthField;
    
    private JFileChooser inputFileChooser = new JFileChooser();
    
    private JFileChooser outputFileChooser = new JFileChooser();
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    JButton open=null; 

    public void init() {
        Calendar now = Calendar.getInstance();
        JFrame frame = new JFrame(APP_NAME + AUTHOR);
        String logoPath = IOUtils.getFilePath(INormalContants.LOGO_PATH);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(logoPath));
        frame.setSize(789, 500);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // 大标题
        JLabel bigLabel = new JLabel(APP_NAME, JLabel.CENTER);
        bigLabel.setBounds(20, 0, 740, 100);
        bigLabel.setFont(new Font("宋体", Font.BOLD, 28));
        frame.getContentPane().add(bigLabel);
        
        operatePanel = new JPanel();
        operatePanel.setBounds(10, 120, 764, 331);
        frame.getContentPane().add(operatePanel);
        operatePanel.setLayout(null);
        
        JLabel dateLabel = new JLabel("日期:", JLabel.CENTER);
        dateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        dateLabel.setBounds(41, 12, 80, 30);
        operatePanel.add(dateLabel);
        
        // 输入框
        yearField = new JTextField();
        yearField.setBounds(117, 12, 80, 30);
        yearField.setText(String.valueOf(now.get(Calendar.YEAR)));
        operatePanel.add(yearField);
        
        // 输入标签
        JLabel yearLabel = new JLabel("年", JLabel.CENTER);
        yearLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        yearLabel.setBounds(197, 12, 30, 30);
        operatePanel.add(yearLabel);
        
        // 输入框
        monthField = new JTextField();
        monthField.setBounds(227, 12, 80, 30);
        monthField.setText(String.valueOf(now.get(Calendar.MONTH) + 1));
        operatePanel.add(monthField);
        
       // 输入标签
        JLabel monthLabel = new JLabel("月", JLabel.CENTER);
        monthLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        monthLabel.setBounds(307, 12, 30, 30);
        operatePanel.add(monthLabel);
        
        // 输入标签
        JLabel inputLabel = new JLabel("输入:", JLabel.CENTER);
        inputLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        inputLabel.setBounds(41, 42, 80, 50);
        operatePanel.add(inputLabel);
                
        // 输入框
        inputField = new JTextField();
        inputField.setBounds(117, 52, 445, 30);
        operatePanel.add(inputField);
                        
        // 输入框注释
        JLabel inputTip = new JLabel("(请选择需要转换的工时文件)");
        inputTip.setBounds(117, 80, 400, 30);
        operatePanel.add(inputTip);
        
        // 输入按钮
        inputBtn = new JButton("请选择文件");
        inputBtn.setBounds(603, 52, 120, 30);
        operatePanel.add(inputBtn);
        
       // 输出框
        outputField = new JTextField();
        outputField.setBounds(117, 134, 445, 30);
        operatePanel.add(outputField);
        
       // 输出框注释
        JLabel outputTip = new JLabel("(请选择结果输出文件夹)");
        outputTip.setBounds(117, 161, 400, 30);
        operatePanel.add(outputTip);
        
        // 输出标签
        JLabel ouputLabel = new JLabel("输出:", JLabel.CENTER);
        ouputLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        ouputLabel.setBounds(41, 124, 80, 50);
        operatePanel.add(ouputLabel);
        
                
        // 输出按钮
        outputBtn = new JButton("选择文件夹");
        outputBtn.setBounds(603, 134, 120, 30);
        operatePanel.add(outputBtn);
        
        // 按钮
        openFileBtn = new JButton("打开结果目录");
        openFileBtn.setBounds(180, 219, 150, 30);
        operatePanel.add(openFileBtn);
        
        // 按钮
        startBtn = new JButton("开始转换");
        startBtn.setBounds(370, 219, 150, 30);
        operatePanel.add(startBtn);
        
        // 提示信息
        msgLable = new JLabel("", JLabel.CENTER);
        msgLable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        msgLable.setBounds(41, 250, 700, 50);
        operatePanel.add(msgLable);
        
       /*// 提示信息
        loadingLable = new JLabel("", JLabel.CENTER);
        loadingLable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        loadingLable.setBounds(41, 250, 700, 50);
        operatePanel.add(loadingLable);*/
        
        outputBtn.addActionListener(this);
        // 给按钮添加单机的监听事件
        inputBtn.addActionListener(this);
        startBtn.addActionListener(this);
        openFileBtn.addActionListener(this);
        
        frame.validate();
        frame.setVisible(true);
        /*open=new JButton("选择");  
        open.setBounds(500, 100, 50, 30);
        this.add(open);  
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setVisible(true);
        setTitle("工时转换工具");
        setState(Frame.NORMAL);
        open.addActionListener(this);  */
    }
    
    
    /**
     * 点击按钮触发
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(inputBtn)) {
            clickInputBtn();
        } else if (e.getSource().equals(outputBtn)) {
            clickOutputBtn();
        } else if (e.getSource().equals(startBtn)) {
            clickStartBtn();
        } else if (e.getSource().equals(openFileBtn)) {
            clickOpenFileBtn();
        }
    }
    
    
    private void clickOpenFileBtn() {
        ActionInfo actionInfo = workHourSerive.openFile(outputField.getText());
        showMsg(actionInfo);
    }
    
    /**
     * 转换按钮
     */
    private void clickStartBtn() {
        Config config = initConfig();
        ActionInfo actionInfo = workHourSerive.start(config);
        showMsg(actionInfo);
    }
    
  /*  private void showOrCancelLoading(boolean isShow) {
        if (isShow) {
            loadingLable.setForeground(Color.GREEN);
            loadingLable.setText("执行中，请稍后......");
        } else {
            loadingLable.setText("");
        }
    }*/
    
    
    /**
     * 设置提示信息.
     * @param actionInfo
     */
    private void showMsg(ActionInfo actionInfo) {
        if (actionInfo == null) {
            msgLable.setText("");
            return;
        }
        msgLable.setForeground(actionInfo.getMsgColor());
        msgLable.setText(actionInfo.getMsg());
    }
    
    
    /**
     * 选择导入文件夹
     */
    private void clickInputBtn() {
        this.selectFolder(inputField, inputFileChooser, new ExcelFileFilter(), JFileChooser.FILES_ONLY);
    }

    /**
     * 选择导出文件夹
     */
    private void clickOutputBtn() {
        this.selectFolder(outputField, outputFileChooser, null, JFileChooser.DIRECTORIES_ONLY);
    }
    
    /**
     * 选择文件夹，并将结果输出到对应的输入框中
     * @param field
     */
    private void selectFolder(JTextField field, JFileChooser chooser, FileFilter fileFilter, int selectionMode) {
        
        chooser.setFileSelectionMode(selectionMode);
        chooser.setFileFilter(fileFilter);
        chooser.showDialog(new JLabel(), "选择文件");
        File file = chooser.getSelectedFile();
        if(file != null) {
            field.setText(file.getAbsolutePath());
        }
    }
    
    /**
     * 初始化一些信息.
     */
    private Config initConfig() {
        Config config = Config.getInstance();
        config.setInputFilePath(inputField.getText());
        config.setOutputFilePaht(outputField.getText());
        config.setYear(yearField.getText());
        config.setMonth(monthField.getText());
        return config;
    }
    
    /**
     * 过滤excel
     * ExcelFileFilter
     * @author 陈龙
     * @version 1.0
     *
     */
    private class ExcelFileFilter extends FileFilter {  
        
        public String getDescription() {    
            return "*.xls;*.xlsx";    
        }    
        
        public boolean accept(File file) {    
            String name = file.getName();    
            return file.isDirectory() || name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx");  // 仅显示目录和xls、xlsx文件  
        }    
    }   
}
