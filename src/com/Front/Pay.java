package com.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import com.db.JOracle;
import com.global.Timer_down;

public class Pay {
    JFrame jFrame=new JFrame("缴费");
    ImageIcon icon;
    JOracle jOracle=new JOracle();
    Login login=new Login();
    private static int All_bill;
    private String bank_card_new=login.get_user();
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;

    public Pay()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);


    }

    public int getAll_bill()
    {
        return All_bill;
    }
    public void Print()
    {
        Panels panels=new Panels();
        add_bill(panels);  //指向panels控件的引用
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
        JLabel title=new JLabel("天秀捞钱ATM登录界面");
        title.setFont(new Font("楷体",Font.BOLD,30));
        title.setForeground(Color.cyan);
        title.setBounds(320,60,400,150);

        jPanel.setLayout(null);
        JLabel remark=new JLabel("操作本系统时，请注意保管好私人物品！");
        remark.setBounds(500,120,500,70);
        remark.setFont(new Font("宋体",Font.BOLD,20));
        remark.setForeground(Color.red);
        jPanel.add(remark);

        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);

        JLabel infor=new JLabel("您的生活账单信息");
        infor.setFont(new Font("楷体",Font.BOLD,30));
        infor.setForeground(Color.cyan);
        infor.setBounds(600,200,400,80);



        JButton pay=new JButton("一键缴费");
        pay.setBounds(0,600,140,50);
        pay.setFont(new Font("楷体",Font.BOLD,21));
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  //String sql_old_asset="select * from bill where bank_card='"+bank_card_new+"'";
                 Input_pwd input_pwd=new Input_pwd();
                 input_pwd.Print();
                 input_pwd.set_state("Pay");
            }
        });

        JButton update=new JButton("返回");
        update.setBounds(1200,600,140,50);
        update.setFont(new Font("楷体",Font.BOLD,21));
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client= null;
                client = new Client();
                timer.stop();
                jFrame.dispose();
                client.Paint();
                //返回Client
            }
        });





        jPanel.add(infor);
        jPanel.add(title);
        jPanel.add(pay);
        jPanel.add(update);

    }
    public void add_bill(JPanel jPanel)
    {
        JList jList=new JList();
        //只能选择一个元素
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        String []List_infor=new String[5];
        jList.setBounds(380,320,600,300);
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        jPanel.add(scrollPane,BorderLayout.CENTER);    //将面板增加到边界布局中央
        String sql_bill="select * from bill where bank_card='"+bank_card_new+"'";
        List_infor=jOracle.Verify_list(sql_bill);
        jList.setListData(List_infor);

        All_bill=jOracle.getAll_bill();
        scrollPane.setViewportView(jList);//将jList放到scrollPane布局上
        jList.setFont(new Font("楷体",Font.BOLD,30));
        jPanel.add(jList);


        //return jOracle;
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
