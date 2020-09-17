package com.Front;

import com.db.JOracle;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.locks.*;
import com.global.Timer_down;



public class Client {
    JFrame frame=new JFrame("天秀ATM客户端");
    ImageIcon icon;
    private Lock locker=new ReentrantLock();
    //Login login=new Login();
    JOracle jOracle=new JOracle();
    Login login=new Login();
    private String bank_num=login.get_user();
    Timer_down timer_down=new Timer_down();

    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;


    public Client() {
        frame.setVisible(true);
        frame.setSize(1300,900);
        frame.setLocationRelativeTo(null);
        start_timer_down();

    }
    public ActionListener timer_down()
    {
        ActionListener count_down=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clock.setText(Integer.toString(second));
                second-=1;
                if(second==-1)
                {
                    JOptionPane.showMessageDialog(frame,"超时，即将退出系统","警告",0);
                    timer.stop();
                    System.exit(0);
                }
            }
        };
        return count_down;
    }
    public void start_timer_down()
    {
        MyTime myTime=new MyTime();
        thread=new Thread(myTime);
        thread.start();
    }
    class MyTime implements Runnable
    {
        ActionListener count_down=timer_down();
        public void run()
        {
            timer=new Timer(time,count_down);
            timer.start();
        }
    }
    public void Paint()
    {
        Panels Main_Client=new Panels();
        Title(Main_Client);
        placecomponents(Main_Client);
        //T.start_timer_down(Clock,frame,timer,thread);
        frame.add(Main_Client);
        frame.setVisible(true);
    }

    public void Title(JPanel jPanel)
    {
        JLabel title;
        jPanel.setLayout(null);

        String sql="select * from users where bank_card='"+bank_num+"'";
        String name=jOracle.Verify_(sql,"name","String");
        String sex=jOracle.Verify_(sql,"sex","String");
        if(sex.equals("男")) {
            title = new JLabel("尊敬的" +name+ "先生欢迎您");
        }
        else
        {
            title = new JLabel("尊敬的" +name+ "女士欢迎您");
        }
        title.setBounds(600,60,350,45);
        title.setFont(new Font("楷体", Font.CENTER_BASELINE, 32));
        title.setForeground(Color.blue);
        jPanel.add(title);
    }

    public void placecomponents(JPanel jPanel)
    {
         locker.lock();
        jPanel.setLayout(null);
        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);
        jPanel.add(remark);


        JButton save_money=new JButton("存款");
        save_money.setBounds(120,100,120,40);
        save_money.setFont(new Font("宋体",Font.BOLD,25));
        save_money.setSize(160,60);
        jPanel.add(save_money);
        save_money.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit deposit=new Deposit();
                deposit.Print();
                timer.stop();
                frame.dispose();
            }
        });

         Clock.setFont(new Font("楷体",Font.BOLD,30));
         Clock.setForeground(Color.darkGray);
         Clock.setBounds(800,65,100,100);
         jPanel.add(Clock);
       
        JButton wd_money=new JButton("取款");
        wd_money.setBounds(120,300,120,40);
        wd_money.setFont(new Font("宋体",Font.BOLD,25));
        wd_money.setSize(160,60);
        jPanel.add(wd_money);
        wd_money.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Withdraw withdraw=new Withdraw();
                withdraw.Paint();
                timer.stop();
                frame.dispose();
            }
        });


        JButton Residuals=new JButton("查询余额");
        Residuals.setBounds(120,500,160,40);
        Residuals.setFont(new Font("宋体",Font.BOLD,25));
        Residuals.setSize(160,60);
        jPanel.add(Residuals);
        Residuals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Residual residual=new Residual();
                timer.stop();
                frame.dispose();


            }
        });


        JButton trans=new JButton("转账");
        trans.setBounds(120,700,120,40);
        trans.setFont(new Font("宋体",Font.BOLD,25));
        trans.setSize(160,60);
        jPanel.add(trans);
        trans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trans_account trans_account=new Trans_account();
                timer.stop();
                frame.dispose();
            }
        });

        JButton pay=new JButton("缴费");
        pay.setBounds(1000,100,120,40);
        pay.setFont(new Font("宋体",Font.BOLD,25));
        pay.setSize(160,60);
        jPanel.add(pay);
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pay pay=new Pay();
                pay.Print();
                timer.stop();
                frame.dispose();
            }
        });

        JButton Print=new JButton("打印账单");
        Print.setBounds(1000,300,120,40);
        Print.setFont(new Font("宋体",Font.BOLD,25));
        Print.setSize(160,60);
        jPanel.add(Print);
        Print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Print_acc print_acc=new Print_acc();
                print_acc.Paint();
                timer.stop();
                frame.dispose();
            }
        });

        JButton modify=new JButton("修改密码");
        modify.setBounds(1000,500,120,40);
        modify.setFont(new Font("宋体",Font.BOLD,25));
        modify.setSize(160,60);
        jPanel.add(modify);
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Modify_users modify_users=new Modify_users();
                modify_users.Print();
                timer.stop();
                frame.dispose();

            }
        });


        JButton back=new JButton("退出");
        back.setBounds(1000,700,120,40);
        back.setFont(new Font("宋体",Font.BOLD,25));
        back.setSize(160,60);
        jPanel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login=new Login();
                try {
                    login.Print();
                    frame.dispose();
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }


        });

      locker.unlock();
    }
    class Panels extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            int x=0,y=0;
            icon=new ImageIcon("EA6A2052D7054D43EF3626C76813C81A.jpg");
            g.drawImage(icon.getImage(),x,y,getSize().width,getSize().height,this);  //自动缩放

        }
    }




}
