package com.Front;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import javax.swing.*;
import com.db.JOracle;
import com.global.Timer_down;
import java.util.concurrent.locks.*;
public class Deposit {
    JFrame jFrame=new JFrame();
    JOracle jOracle=new JOracle();
    ImageIcon icon;
    public Timer timer;
    public JLabel Clock=new JLabel("60");
    public Thread thread=new Thread();
    private int time=1000;
    private int second=59;
    Login login=new Login();
    private static String bank_card_new;
    public  String get_bank_card_new()
    {
        return bank_card_new;
    }
    private static int money;
    public  int get_money()
    {
        return money;
    }
    private Lock locker=new ReentrantLock();
    JButton confirm;
    JTextField account_num;
    JTextField individual_money;
    public Deposit()
    {
        jFrame.setSize(1300,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
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

          JLabel title=new JLabel("Please Input The Deposit Account");
          title.setFont(new Font("楷体",Font.BOLD,20));
          title.setBounds(600,210,500,100);

        Clock.setFont(new Font("楷体",Font.BOLD,30));
        Clock.setForeground(Color.darkGray);
        Clock.setBounds(800,65,100,100);
        jPanel.add(Clock);


              JLabel reminder_cn = new JLabel("请输入存款账号");
              reminder_cn.setFont(new Font("楷体", Font.BOLD, 24));
              reminder_cn.setBounds(600, 300, 200, 80);
              account_num = new JTextField(30);
              account_num.setBounds(500, 410, 400, 40);
              account_num.setFont(new Font("楷体", Font.PLAIN, 20));


              JLabel reminder_money = new JLabel("请输入存款金额");
              reminder_money.setFont(new Font("楷体", Font.BOLD, 24));
              reminder_money.setBounds(600, 500, 200, 80);

              individual_money = new JTextField(10);
              individual_money.setFont(new Font("楷体", Font.BOLD, 22));
              individual_money.setBounds(500, 600, 200, 40);


          JButton NULL=new JButton("选择自己");
          NULL.setBounds(0,600,140,50);
          NULL.setFont(new Font("楷体",Font.BOLD,21));
          NULL.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  account_num.setText(login.get_user());
                  account_num.setEditable(false);

              }
          });

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


        JButton update=new JButton("更正");
        update.setBounds(1180,600,140,50);
        update.setFont(new Font("楷体",Font.BOLD,21));
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account_num.setEditable(true);
                account_num.setText("");
            }
        });

        confirm=new JButton("确认");
        confirm.setBounds(1180,700,140,50);
        confirm.setFont(new Font("楷体",Font.BOLD,21));
        confirm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                locker.lock();
                if ((account_num.getText().equals("")) && (individual_money.getText().equals("")))
                    JOptionPane.showMessageDialog(account_num, "银行卡号和存款金额不能为空！", "提示", 1);
                else {
                    bank_card_new = account_num.getText();
                    money = Integer.parseInt(individual_money.getText());

                    if (money % 100 == 0 && money <= 20000) {
                        String sql_user = "select * from USERS,BANK_ACCOUNT where USERS.BANK_CARD=BANK_ACCOUNT.BANK_CARD and BANK_ACCOUNT.bank_card='" + account_num.getText() + "' ";
                        String result_user = jOracle.Verify_(sql_user, "bank_card", "String");
                        if (!account_num.getText().equals(result_user)) {
                            JOptionPane.showMessageDialog(account_num, "银行卡号错误！", "提示", 1);
                        } else {
                            try {
                                timer.stop();
                                Thread.sleep(2000);
                                //thread.wait(2);//相当于timer.stop两秒，自动唤醒
                                JOptionPane.showMessageDialog(account_num, "正在处理请求，请稍后！", "提醒", 1);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            Input_pwd input_pwd = new Input_pwd();
                            timer.stop();
                            input_pwd.Print();
                            input_pwd.set_state("Deposit");
                        }
                    } else if(money%100!=0) {
                        JOptionPane.showMessageDialog(account_num, "存款金额必须为100整数倍！", "提醒", 1);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(account_num, "存款金额超出单次上限，单笔只允许0——20000！", "提醒", 1);
                    }

                }

                locker.unlock();
            }
        });



        jPanel.add(bank_name);
        jPanel.add(title);
        jPanel.add(reminder_cn);
        jPanel.add(account_num);
        jPanel.add(reminder_money);
        jPanel.add(individual_money);
        jPanel.add(NULL);
        jPanel.add(cancel);
        jPanel.add(update);
        jPanel.add(confirm);
    }
    public void confirm_mess(){

    }
    class Mydeposit implements Runnable
    {
        public void run()
        {
            confirm_mess();
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
