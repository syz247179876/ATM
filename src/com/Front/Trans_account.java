package com.Front;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.*;
import com.db.JOracle;

public class Trans_account {
    JFrame jFrame=new JFrame("转账");
    ImageIcon icon;
    Login login=new Login();
    JOracle jOracle=new JOracle();
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;
    public static String str1;  //用于存放转入账户的卡号
    public static String str2;  //用于存放当前账户的余额
    public static String str3;  //用于存放当前账户的密码
    public static String str4;  //用于存放转入账户的余额

    public Trans_account()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        Panels panels=new Panels();
        placecomponents(panels);
        start_timer_down();
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
        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);

        JLabel bank_name=new JLabel("天秀酒店独家银行");
        bank_name.setForeground(Color.orange);
        bank_name.setFont(new Font("楷体",Font.BOLD,35));
        bank_name.setBounds(40,30,400,200);

        jPanel.setLayout(null);
        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);
        jPanel.add(remark);

        JLabel title=new JLabel("欢迎使用转账服务！");
        title.setFont(new Font("楷体",Font.BOLD,20));
        title.setBounds(600,200,500,100);

        JLabel jLabel1 = new JLabel("请输入转入账户：");
        jLabel1.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel1.setBounds(300, 300, 200, 80);

        JTextField jtf1 = new JTextField(30);
        jtf1.setFont(new Font("楷体", Font.BOLD, 24));
        jtf1.setBounds(500, 320, 400, 40);

        JLabel jLabel2 = new JLabel("请输入转出金额：");
        jLabel2.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel2.setBounds(300, 400, 200, 80);

        JTextField jtf2 = new JTextField(30);
        jtf2.setFont(new Font("楷体", Font.BOLD, 24));
        jtf2.setBounds(500, 420, 400, 40);

        JLabel jLabel3 = new JLabel("确 认 密 码：");
        jLabel3.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel3.setBounds(300, 500, 200, 80);

        JPasswordField jtf3 = new JPasswordField(30);
        // jtf3.setFont(new Font("楷体", Font.BOLD, 24));
        jtf3.setBounds(500, 520, 400, 40);

        JButton jbt1=new JButton("存款");
        jbt1.setBounds(0,600,140,50);
        jbt1.setFont(new Font("楷体",Font.BOLD,21));
        jbt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit deposit=new Deposit();
                deposit.Print();
                jFrame.dispose();
            }
        });

        JButton jbt2=new JButton("更正");
        jbt2.setBounds(0,700,140,50);
        jbt2.setFont(new Font("楷体",Font.BOLD,21));
        jbt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf1.setText("");
                jtf2.setText("");
                jtf3.setText("");
            }
        });


        JButton jbt3=new JButton("确认");
        jbt3.setBounds(1180,600,140,50);
        jbt3.setFont(new Font("楷体",Font.BOLD,21));
        jbt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*1、转入帐户是否存在
                 * 2、转出金额是否超出余额
                 * 3、密码确认是否正确*/
                String sql1="select * from users where bank_card='"+jtf1.getText()+"'";
                str1=jOracle.Verify_(sql1,"bank_card","String");
                String sql2="select * from bank_account where bank_card='"+login.get_user()+"'";
                str2=jOracle.Verify_(sql2,"asset","number");
                String sql3="select * from users where bank_card='"+login.get_user()+"'";
                str3=jOracle.Verify_(sql3,"password","String");
                String sql4="select * from bank_account where bank_card='"+jtf1.getText()+"'";
                str4=jOracle.Verify_(sql4,"asset","number");
                if(str1.equals("")){
                    JOptionPane.showMessageDialog(null,"抱歉，您选择转入的帐户不存在！","用户不存在",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    if(jtf2.getText()==null || Integer.parseInt(jtf2.getText())%100!=0){
                        JOptionPane.showMessageDialog(null,"抱歉，转出金额必须为100的整数倍！","金额规定",JOptionPane.WARNING_MESSAGE);
                    }
                    else if(Integer.parseInt(str2)<Integer.parseInt(jtf2.getText())){
                        JOptionPane.showMessageDialog(null,"抱歉，您的余额不足，转出失败！","余额不足",JOptionPane.WARNING_MESSAGE);
                    }
                    else if(!str3.equals(jtf3.getText())){
                        JOptionPane.showMessageDialog(null,"密码不匹配，交易失败！","密码错误",JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        int n=JOptionPane.showConfirmDialog(null,"您将转账给账户："+str1+"\n转账金额为："+jtf2.getText(),"确认转账信息",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                        if(n==0){
                            JOptionPane.showMessageDialog(null,"转账成功！","交易成功",JOptionPane.WARNING_MESSAGE);
                            String sql5="update bank_account set asset='"+(Integer.parseInt(str2)-Integer.parseInt(jtf2.getText()))+"'"+"where bank_card='"+login.get_user()+"'";
                            jOracle.Update_(sql5);  //修改当前账户余额
                            String sql6="update bank_account set asset='"+(Integer.parseInt(str4)+Integer.parseInt(jtf2.getText()))+"'"+"where bank_card='"+jtf1.getText()+"'";
                            jOracle.Update_(sql6);  //修改转入账户余额
                            Client client= null;
                            client = new Client();
                            client.Paint();
                            timer.stop();
                            jFrame.dispose();
                        }
                        else{
                            Client client= null;
                            client = new Client();
                            client.Paint();
                            timer.stop();
                            jFrame.dispose();
                        }
                    }
                }
            }
        });

        JButton jbt4=new JButton("返回");
        jbt4.setBounds(1180,700,140,50);
        jbt4.setFont(new Font("楷体",Font.BOLD,21));
        jbt4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client= null;
                client = new Client();
                client.Paint();
                timer.stop();
                jFrame.dispose();
            }
        });

        jPanel.add(bank_name);
        jPanel.add(title);
        jPanel.add(jLabel1);
        jPanel.add(jtf1);
        jPanel.add(jLabel2);
        jPanel.add(jtf2);
        jPanel.add(jLabel3);
        jPanel.add(jtf3);
        jPanel.add(jbt1);
        jPanel.add(jbt2);
        jPanel.add(jbt3);
        jPanel.add(jbt4);
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
