package com.Front;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.*;
import com.db.JOracle;

public class Residual {
    JFrame jFrame=new JFrame("查询余额");
    ImageIcon icon;
    Login login=new Login();
    JOracle jOracle=new JOracle();
    public static String str1;  //用于存放当前账户的余额
    public static String str2;  //用于存放当前账户的姓名
    public static String str3;  //用于存放当前账户的性别
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;

    public String get_money()
    {
        return str1;
    }
    public Residual()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        Panels panels=new Panels();
        String sql1="select * from bank_account where bank_card='"+login.get_user()+"'";
        str1=jOracle.Verify_(sql1,"asset","number");
        String sql2="select * from users where bank_card='"+login.get_user()+"'";
        str2=jOracle.Verify_(sql2,"name","String");
        String sql3="select * from users where bank_card='"+login.get_user()+"'";
        str3=jOracle.Verify_(sql3,"sex","String");

        placecomponents(panels);
        start_timer_down();
        jFrame.add(panels);
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

        JLabel title=new JLabel("您的账户余额信息");
        title.setFont(new Font("楷体",Font.BOLD,20));
        title.setBounds(500,200,500,100);

        JLabel jLabel1 = new JLabel("尊敬的：");
        jLabel1.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel1.setBounds(300, 300, 200, 80);

        JTextField jtf1 = new JTextField(30);
        jtf1.setBounds(400, 320, 400, 40);
        jtf1.setFont(new Font("楷体", Font.PLAIN, 24));
        if(str3.equals("男")){
            jtf1.setText(str2+"先生");
        }
        else{
            jtf1.setText(str2+"女士");
        }
        jtf1.setEditable(false);

        JLabel jLabel2 = new JLabel("账户余额：");
        jLabel2.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel2.setBounds(280, 500, 200, 80);

        JTextField jtf2 = new JTextField(30);
        jtf2.setBounds(400, 520, 400, 40);
        jtf2.setFont(new Font("楷体", Font.BOLD, 20));
        jtf2.setText(str1);
        jtf2.setEditable(false);

        jPanel.setLayout(null);
        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);
        jPanel.add(remark);

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

        JButton jbt2=new JButton("取款");
        jbt2.setBounds(0,700,140,50);
        jbt2.setFont(new Font("楷体",Font.BOLD,21));
        jbt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Withdraw withdraw=new Withdraw();
                withdraw.Paint();
                jFrame.dispose();
            }
        });

        JButton jbt3=new JButton("转账");
        jbt3.setBounds(1180,600,140,50);
        jbt3.setFont(new Font("楷体",Font.BOLD,21));
        jbt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Trans_account trans_account=new Trans_account();
                jFrame.dispose();
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
                timer.stop();
                jFrame.dispose();
                client.Paint();
            }
        });

        jPanel.add(bank_name);
        jPanel.add(title);
        jPanel.add(jLabel1);
        jPanel.add(jtf1);
        jPanel.add(jLabel2);
        jPanel.add(jtf2);
        jPanel.add(jbt1);
        jPanel.add(jbt2);
        jPanel.add(jbt3);
        jPanel.add(jbt4);
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
