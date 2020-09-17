package com.Front;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import com.db.JOracle;
import com.global.Timer_down;

public class Input_pwd
{
    private String operator_state; //操作状态
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;
    JFrame jFrame=new JFrame("缴费");
    ImageIcon icon;
    Deposit deposit=new Deposit();
    Login login=new Login();
    JOracle jOracle=new JOracle();
    Withdraw withdraw=new Withdraw();
    Pay pay=new Pay();
    private String bank_cards_des=deposit.get_bank_card_new();
    private String bank_cards_with=login.get_user();
    private int dis_money=deposit.get_money();
    private int with_money=withdraw.get_money();
    private int Pay_money=pay.getAll_bill();
    JPasswordField password_text;
    JButton confirm;
    public Input_pwd()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);

    }

    public void set_state(String state)
    {
        //获取输入密码之前的操作是存钱状态还是取钱
        this.operator_state=state;
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

    public void Print()
    {
        jFrame.setVisible(true);
        Panels panels=new Panels();
        placecomponents(panels);
        start_timer_down();
        jFrame.add(panels);
    }

    public void placecomponents(JPanel jPanel)
    {
        jPanel.setLayout(null);
        JLabel bank_name=new JLabel("天秀酒店独家银行");
        bank_name.setForeground(Color.orange);
        bank_name.setFont(new Font("楷体",Font.BOLD,35));
        bank_name.setBounds(40,30,400,200);

        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);

        JLabel password_remark=new JLabel("请输入密码");
        password_remark.setForeground(Color.blue);
        password_remark.setFont(new Font("楷体",Font.BOLD,30));
        password_remark.setBounds(600,220,200,200);
        password_text=new JPasswordField(20);
        //password_text.setFont(new Font("楷体",Font.BOLD,25));
        password_text.setBounds(600,420,200,50);

        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);

        JButton cancel=new JButton("返回");
        cancel.setBounds(0,700,140,50);
        cancel.setFont(new Font("楷体",Font.BOLD,21));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client= null;
                client = new Client();
                client.Paint();
                timer.stop();
                jFrame.dispose();
            }
        });


        JButton update=new JButton("更正");
        update.setBounds(1180,600,140,50);
        update.setFont(new Font("楷体",Font.BOLD,21));
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password_text.setEditable(true);
                password_text.setText("");
            }
        });

        confirm=new JButton("确认");
        confirm.setBounds(1180,700,140,50);
        confirm.setFont(new Font("楷体",Font.BOLD,21));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(password_text.getText().equals(""))
                    JOptionPane.showMessageDialog(password_text, "密码错误，重新输入", "警告", 0);
                    else
                {
                    if (operator_state.equals("Deposit")) {
                        String sql_user = "select * from USERS,BANK_ACCOUNT where USERS.BANK_CARD=BANK_ACCOUNT.BANK_CARD and BANK_ACCOUNT.bank_card='" + bank_cards_des + "' ";
                        _operator("Deposit", sql_user);
                    }
                    if (operator_state.equals("Withdraw")) {
                        String sql_user = "select * from USERS,BANK_ACCOUNT where USERS.BANK_CARD=BANK_ACCOUNT.BANK_CARD and BANK_ACCOUNT.bank_card='" + bank_cards_with + "' ";
                        _operator("Withdraw", sql_user);
                    }
                    if (operator_state.equals("Pay")) {
                        String sql_user = "select * from USERS,BANK_ACCOUNT where USERS.BANK_CARD=BANK_ACCOUNT.BANK_CARD and BANK_ACCOUNT.bank_card='" + bank_cards_with + "' ";
                        _operator("Pay", sql_user);
                    }
                }

            }
        });


        jPanel.add(bank_name);
        jPanel.add(password_remark);
        jPanel.add(password_text);
        jPanel.add(cancel);
        jPanel.add(update);
        jPanel.add(confirm);
        jPanel.add(remark);

    }
    public void _operator(String operator,String sql_user) {

        String verify;
        String temp_pwd = password_text.getText();
        //String sql="select * from User_ where password='"+ bank_card+"'";
        String sql = "select * from users where password='" + temp_pwd + "'";
        verify = jOracle.Verify_(sql, "password", "String");
        if (password_text.getText().equals("")) {
            JOptionPane.showMessageDialog(password_text, "密码不能为空！", "警告", 0);
        } else if (!password_text.getText().equals(verify)) {
            JOptionPane.showMessageDialog(password_text, "密码错误！", "警告", 0);
        } else  {
            String result_asset = jOracle.Verify_(sql_user, "asset", "int");
            if (operator.equals("Deposit")) {
                String sql_asset = "update bank_account set asset=" + (dis_money + Integer.parseInt(result_asset)) + " where bank_card='" + bank_cards_des + "'";
                jOracle.Update_(sql_asset);
                try {
                    timer.stop();
                    Thread.sleep(2500);
                    JOptionPane.showMessageDialog(password_text, "正在处理存款请求，请稍后！", "提醒", 1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(password_text, "存款成功", "确认", JOptionPane.PLAIN_MESSAGE);
                Client client=new Client();
                client.Paint();
            } else if (operator.equals(("Withdraw")) && (Integer.parseInt(result_asset) - with_money) > 0) {
                String sql_asset = "update bank_account set asset=" + (Integer.parseInt(result_asset) - with_money) + " where bank_card='" + bank_cards_with + "'";
                jOracle.Update_(sql_asset);
                try {
                    timer.stop();
                    Thread.sleep(2500);
                    JOptionPane.showMessageDialog(password_text, "正在处理取款请求，请稍后！", "提醒", 1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(password_text, "取款成功", "确认", JOptionPane.PLAIN_MESSAGE);
                Client client=new Client();
                client.Paint();
            } else if (operator.equals(("Pay")) && (Integer.parseInt(result_asset) - Pay_money) > 0) {
                String sql_asset = "update bank_account set asset=" + (Integer.parseInt(result_asset) - Pay_money) + " where bank_card='" + bank_cards_with + "'";
                jOracle.Update_(sql_asset);
                try {
                    Thread.sleep(2500);
                    timer.stop();
                    JOptionPane.showMessageDialog(password_text, "正在处理缴费请求，请稍后！", "提醒", 1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(password_text, "缴费成功", "确认", JOptionPane.PLAIN_MESSAGE);
                Client client=new Client();
                client.Paint();
            } else {
                JOptionPane.showMessageDialog(password_text, "剩余资金不足，无法获取", "警告", 0);
            }


        }
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
