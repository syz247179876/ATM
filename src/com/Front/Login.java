package com.Front;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.db.JOracle;
import com.global.Timer_down;
public class Login{

    private static String _user;
    private String _password;
    private int time=1000;
    private int second=59;
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    ImageIcon icon;
    public JFrame frame=new JFrame("天秀ATM");
    JLabel temp;
    JOracle joracle=new JOracle();
    public String get_user()
    {
        return _user;
    }
    File f;
    URI uri;
    URL url;
    public Login()
    {
        frame.setSize(1000,700);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();  //获取屏幕对象
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int x = (int) screensize.getWidth() / 2 - frame.getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - frame.getHeight() / 2;
        frame.setLocation(x,y);
        //frame.setLocationRelativeTo(null);  //自动居中对齐

    }

    public void Print() throws MalformedURLException {
        Panels total=new Panels();
        placecomponents(total);
        start_timer_down();
        frame.add(total);
        frame.setVisible(true);
        f = new File("王笑文 - 耿耿于怀.wav");
        URI uri = f.toURI();
        url = uri.toURL();  //解析地址
        AudioClip aau;
        aau = Applet.newAudioClip(url);
        aau.play();
        //frame.setDefaultCloseOperation(JOptionPane.showConfirmDialog(frame,"啊朋友再见~","吻别",1));
        //start_thread();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
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
    public void placecomponents(JPanel panel)
    {
        panel.setLayout(null);
        JLabel title=new JLabel("天秀捞钱ATM登录界面");
        title.setFont(new Font("楷体",Font.BOLD,30));
        title.setForeground(Color.cyan);
        title.setBounds(320,60,400,150);
        panel.add(title);


        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        panel.add(Clock);


        JLabel name=new JLabel("银行卡号");
        name.setFont(new Font("楷体",Font.PLAIN,20));
        name.setBounds(320,220,160,30);
        panel.add(name);

        JTextField user_name=new JTextField();
        user_name.setFont(new Font("楷体",Font.PLAIN,20));
        user_name.setBounds(400,220,150,30);
        panel.add(user_name);

        JLabel pas=new JLabel("密码");
        pas.setFont(new Font("楷体",Font.PLAIN,20));
        pas.setBounds(320,320,100,30);
        panel.add(pas);

        JPasswordField pass_user=new JPasswordField();
        //pass_user.setFont(new Font("楷体",Font.PLAIN,20));
        pass_user.setBounds(400,320,150,30);
        panel.add(pass_user);

        JLabel tail=new JLabel("本系统版权属于syz");
        tail.setFont(new Font("楷体",Font.PLAIN,15));
        tail.setBounds(500,600,200,40);
        tail.setForeground(Color.red);
        panel.add(tail);

        JButton confirm=new JButton("登录");
        //Dimension dimension=new Dimension(80,40);
        //confirm.setPreferredSize(dimension);   //设置按钮大小
        confirm.setFont(new Font("楷体",Font.BOLD,30));
        confirm.setBounds(300,450,100,45);
        panel.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                _user=user_name.getText();
                _password=pass_user.getText();
                System.out.println("77777"+_password);
                JLabel mistake=joracle.login(_user,_password);

                if(temp!=null)
                    temp.setVisible(false);
                else{
                    mistake.setVisible(true);
                }
                if (mistake.getText().equals("0"))
                {
                    JOptionPane.showMessageDialog(frame,"您已输错密码5次，自动退出！","警告",0);

                    System.exit(0);
                }
                else if ((!mistake.getText().equals("1"))){
                    mistake.setForeground(Color.red);
                    mistake.setBounds(600, 270, 300, 100);
                    mistake.setFont(new Font("宋体",Font.PLAIN,16));
                    //mistake.setVisible(true);
                    temp=mistake;
                    panel.add(mistake);
                    frame.setVisible(true);
                    frame.setSize(1000,700);
                }
                else
                {
                   //timer_down.restart_timer(frame,Clock);
                    //timer.stop();
                    //thread.interrupt();
                    timer.stop();
                    Client client = null;
                    client = new Client();
                    frame.dispose();
                    assert client != null;
                    client.Paint();
                }
            }
        });

        JButton cancel=new JButton("退出");
        Dimension cdimension=new Dimension(80,40);
        cancel.setPreferredSize(cdimension);   //设置按钮大小
        cancel.setFont(new Font("楷体",Font.BOLD,30));
        cancel.setBounds(500,450,100,45);
        panel.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



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
    /*class MyTime implements Runnable
    {
      public void run()
        {
            try {
                Thread.sleep(1);
                timer_down.start_timer_down(frame, Clock);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }*/

    public static void main(String[] args) throws MalformedURLException {
	// write your code here
        Login login=new Login();
        login.Print();


    }
}
