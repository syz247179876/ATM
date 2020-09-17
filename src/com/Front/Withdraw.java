package com.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import com.db.JOracle;


public class Withdraw {
    JFrame jFrame=new JFrame("修改用户");
    ImageIcon icon;
    Login login=new Login();
    private int time=1000;
    private int second=59;
    Timer timer;


    private String bank_card_new=login.get_user();
    JOracle jOracle=new JOracle();
    private static int money;
    public static JLabel Clock=new JLabel("60");
    public  int get_money()
    {
        return money;
    }
    Thread thread;
    public Withdraw()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);
        start_timer_down();
    }

    public void Paint()
    {
        jFrame.setVisible(true);
        Panels panels=new Panels();
        placecomponents(panels);
        jFrame.add(panels);
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
                    JOptionPane.showMessageDialog(jFrame,"超时，即将退出系统","警告",0);
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
    public void placecomponents(JPanel jPanel)
    {
        jPanel.setLayout(null);
        JLabel bank_name=new JLabel("天秀酒店独家银行");
        bank_name.setForeground(Color.orange);
        bank_name.setFont(new Font("楷体",Font.BOLD,35));
        bank_name.setBounds(40,30,400,200);

        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);

        jPanel.setLayout(null);
        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);
        jPanel.add(remark);

        JLabel reminder_money = new JLabel("请输入取钱的金额");
        reminder_money.setFont(new Font("楷体", Font.BOLD, 24));
        reminder_money.setBounds(600, 300, 200, 70);

        JTextField individual_money = new JTextField(10);
        individual_money.setFont(new Font("楷体", Font.BOLD, 24));
        individual_money.setBounds(600, 400, 200, 40);

        JButton cancel=new JButton("返回");
        cancel.setBounds(0,700,140,50);
        cancel.setFont(new Font("楷体",Font.BOLD,21));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client= null;
                client = new Client();
                timer.stop();
                jFrame.dispose();
                client.Paint();
            }
        });

        JButton deposits=new JButton("存款");
        deposits.setBounds(0,600,140,50);
        deposits.setFont(new Font("楷体",Font.BOLD,21));
        deposits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit deposit=new Deposit();
                jFrame.dispose();
                timer.stop();
                deposit.Print();
            }
        });

        JButton update=new JButton("更正");
        update.setBounds(1180,600,140,50);
        update.setFont(new Font("楷体",Font.BOLD,21));
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //individual_money.setEditable(true);
                individual_money.setText("");
            }
        });


        JButton confirm=new JButton("确认");
        confirm.setBounds(1180,700,140,50);
        confirm.setFont(new Font("楷体",Font.BOLD,21));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                money=Integer.parseInt(individual_money.getText());
                if(money%100==0 &&money<=5000){
                    //String asset_sql="select * from BANK_ACCOUNT WHERE BANK_CARD='"+bank_card_new+"'";
                    //String result_asset=jOracle.Verify_(asset_sql,"asset","int");
                    try {
                        timer.stop();
                        Thread.sleep(2000);
                        //thread.wait(2);//相当于timer.stop两秒，自动唤醒
                        JOptionPane.showMessageDialog(individual_money, "正在处理请求，请稍后！", "提醒", 1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                        Input_pwd input_pwd = new Input_pwd();
                        input_pwd.Print();
                        input_pwd.set_state("Withdraw");
                }
                else if(money%100!=0)
                {
                    JOptionPane.showMessageDialog(individual_money,"取款额必须为100整数倍","提醒",1);
                }
                else
                {
                    JOptionPane.showMessageDialog(individual_money,"取款金额超出单次上限！单笔只允许0——5000","提醒",1);
                }
            }
        });
        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);

        jPanel.add(bank_name);
        jPanel.add(reminder_money);
        jPanel.add(individual_money);
        jPanel.add(cancel);
        jPanel.add(update);
        jPanel.add(confirm);
        jPanel.add(deposits);

    }
    public class Panels extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            int x=0,y=0;
            icon=new ImageIcon("EA6A2052D7054D43EF3626C76813C81A.jpg");
            g.drawImage(icon.getImage(),x,y,getSize().width,getSize().height,this);
        }
    }

}
