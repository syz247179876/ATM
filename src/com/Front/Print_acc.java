package com.Front;

import com.global.Timer_down;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.db.JOracle;
public class Print_acc {
    JOracle jOracle=new JOracle();
    JFrame jFrame=new JFrame("打印账单");
    ImageIcon icon;
    Date date=new Date();
    Login login=new Login();
    Deposit deposit=new Deposit();
    Withdraw withdraw=new Withdraw();
    Residual residual=new Residual();
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Print_acc()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);


    }
    public void Paint()
    {
        jFrame.setVisible(true);
        Panels panels=new Panels();
        placecomponents(panels);
        add_bill(panels);
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
        title.setBounds(250,60,400,150);
        jPanel.add(title);

        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);

        JLabel bill=new JLabel("您的账单如下");
        bill.setFont(new Font("楷体",Font.BOLD,30));
        bill.setForeground(Color.cyan);
        bill.setBounds(600,120,200,40);
        jPanel.add(title);


        JLabel Operation_date_remark=new JLabel("操作日期");
        Operation_date_remark.setFont(new Font("楷体",Font.ITALIC,22));
        Operation_date_remark.setBounds(550,170,120,30);
        JLabel Operation_date=new JLabel(format.format(date));
        Operation_date.setFont(new Font("楷体",Font.ITALIC,22));
        Operation_date.setBounds(450,210,300,30);



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

        JButton Printf=new JButton("打印");
        Printf.setBounds(1200,700,100,50);
        Printf.setFont(new Font("楷体",Font.BOLD,21));

        jPanel.add(title);
        jPanel.add(Clock);
        jPanel.add(bill);
        jPanel.add(Operation_date_remark);
        jPanel.add(cancel);
        jPanel.add(Printf);
        jPanel.add(Operation_date);


    }

    public void add_bill(JPanel jPanel)
    {
        String Deposit_num="本次存款额为："+deposit.get_money();
        String Withdraw_num="本次取款额为："+withdraw.get_money();
        String Pay_num="本次已缴费用明细如下：";
        DefaultListModel<String> defaultListModel=new DefaultListModel<>(); //固定string类型
        JList<String> jList=new JList<>(); //泛型
        JList<String> jList_other=new JList<String>();
        //只能选择一个元素
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        String []List_infor=new String[6];
        jList_other.setBounds(400,300,600,200);
        jList.setBounds(400,500,600,300);
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        jPanel.add(scrollPane,BorderLayout.CENTER);    //将面板增加到边界布局中央
        String sql_bill="select * from bill where bank_card='"+login.get_user()+"'";
        List_infor=jOracle.Verify_list(sql_bill);
        defaultListModel.addElement(Pay_num);
        if(!(deposit.get_money()==0)) {
            defaultListModel.addElement((Deposit_num));
        }
        if(!(withdraw.get_money()==0)) {
            defaultListModel.addElement(Withdraw_num);
        }
        if(!residual.get_money().equals("")) {
            String Residual = "本次查询了卡内余额：" + residual.get_money();
            defaultListModel.addElement(Residual);
        }

        jList_other.setListData(List_infor);
        jList.setModel(defaultListModel);
        scrollPane.setViewportView(jList);//将jList放到scrollPane布局上
        jList.setFont(new Font("楷体",Font.BOLD,25));
        jList_other.setFont(new Font("楷体",Font.BOLD,25));
        jPanel.add(jList_other);
        jPanel.add(jList);

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
