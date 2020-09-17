package com.Front;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.*;
import com.db.JOracle;

public class Modify_users {
    JFrame jFrame=new JFrame("修改密码");
    ImageIcon icon;
    Login login=new Login();
    JOracle jOracle=new JOracle();
    public static String str1;
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;
    public Modify_users()
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
    public void Print()
    {
        Panels panels=new Panels();
        placecomponents(panels);
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

        jPanel.setLayout(null);
        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);
        jPanel.add(remark);

        JLabel title=new JLabel("欢迎使用修改密码服务！");
        title.setFont(new Font("楷体",Font.BOLD,20));
        title.setBounds(600,200,500,100);

        JLabel jLabel1 = new JLabel("请输入旧密码：");
        jLabel1.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel1.setBounds(300, 300, 200, 80);

        JPasswordField jtf1 = new JPasswordField(30);
        //jtf1.setFont(new Font("楷体", Font.BOLD, 24));
        jtf1.setBounds(500, 320, 400, 40);

        JLabel jLabel2 = new JLabel("请输入新密码：");
        jLabel2.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel2.setBounds(300, 400, 200, 80);

        JPasswordField jtf2 = new JPasswordField(30);
       // jtf2.setFont(new Font("楷体", Font.BOLD, 24));
        jtf2.setBounds(500, 420, 400, 40);

        JLabel jLabel3 = new JLabel("请确认新密码：");
        jLabel3.setFont(new Font("楷体", Font.BOLD, 24));
        jLabel3.setBounds(300, 500, 200, 80);

        JPasswordField jtf3 = new JPasswordField(30);
       // jtf3.setFont(new Font("楷体", Font.BOLD, 24));
        jtf3.setBounds(500, 520, 400, 40);

        JButton jbt1=new JButton("更正");
        jbt1.setBounds(0,600,140,50);
        jbt1.setFont(new Font("楷体",Font.BOLD,21));
        jbt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtf1.setText("");
                jtf2.setText("");
                jtf3.setText("");
            }
        });

        JButton jbt2=new JButton("确认");
        jbt2.setBounds(1180,600,140,50);
        jbt2.setFont(new Font("楷体",Font.BOLD,21));
        jbt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*1、旧密码要与原密码匹配，匹配则进入下一步
                 * 2、新密码是否为不相同的6位，符合则进入下一步
                 * 3.两次输入的新密码要相同，相同则修改数据库*/
                String sql1="select * from users where bank_card='"+login.get_user()+"'";
                str1=jOracle.Verify_(sql1,"password","varchar");    //用于存放旧密码
                String str2=jtf2.getText();
                boolean flag=false;
                if(!str1.equals(jtf1.getText())){
                    JOptionPane.showMessageDialog(null,"旧密码错误！","密码错误",JOptionPane.WARNING_MESSAGE,new ImageIcon("apex.jpg"));
                }
                else{
                    if(str2.length()!=6) {
                        JOptionPane.showMessageDialog(null, "密码长度必须为6位！", "长度错误", JOptionPane.WARNING_MESSAGE, new ImageIcon("apex.jpg"));
                    }
                    else{
                        for(int i=0;i<6;i++){
                            if(jtf2.getText().charAt(0)==jtf2.getText().charAt(i)){
                                flag=true;
                            }
                            else{
                                flag=false;
                                break;
                            }
                        }
                        if(flag){
                            JOptionPane.showMessageDialog(null,"6位密码不能全相同！","密码难度过低",JOptionPane.WARNING_MESSAGE,new ImageIcon("apex.jpg"));
                        }
                        else{
                            if(!jtf2.getText().equals(jtf3.getText())){
                                JOptionPane.showMessageDialog(null,"两次输入的新密码不一致！","密码不一致",JOptionPane.WARNING_MESSAGE,new ImageIcon("apex.jpg"));
                            }
                            else{
                                JOptionPane.showMessageDialog(null,"密码修改成功！","成功",JOptionPane.WARNING_MESSAGE,new ImageIcon("apex.jpg"));
                                String sql2="update users set password='"+jtf2.getText()+"'"+"where bank_card='"+login.get_user()+"'";
                                jOracle.Update_(sql2);  //修改当前账户密码
                                Client client= null;
                                client = new Client();
                                client.Paint();
                                timer.stop();
                                jFrame.dispose();
                            }
                        }
                    }
                }
            }
        });

        JButton jbt3=new JButton("返回");
        jbt3.setBounds(1180,700,140,50);
        jbt3.setFont(new Font("楷体",Font.BOLD,21));
        jbt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client= null;
                client = new Client();
                timer.stop();
                client.Paint();
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
