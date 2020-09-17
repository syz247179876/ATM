package com.global;

import com.Front.Login;
import com.Front.Pay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer_down {
    private Timer timer;
    //Login login=new Login();
    public static double second=60;
    public int times=1;
    public int time=1000;
    Thread thread;
    private int state;
    public ActionListener timer_down(JFrame jFrame,JLabel Clock,int states)
    {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Clock.setText(Integer.toString((int) second));
                    second -= 1;
                    System.out.println(second);
                    if (second == -1) {
                        JOptionPane.showMessageDialog(jFrame, "超时，即将退出系统", "警告", 0);
                        timer.stop();
                        System.exit(0);
                    }
            }
        };
    }

    public void start_timer_down(JFrame jFrame,JLabel Clock)
    {

        ActionListener count_down=timer_down(jFrame,Clock,state);
            timer=new Timer(time,count_down);
            timer.start();

    }
    public Timer getTimer()
    {
        return timer;
    }
    public void close_thread()
    {
        assert false;
        thread.interrupt();
    }
    public void use_timer(JFrame frame,JLabel Clock)
    {
        start_timer_down(frame,Clock);
    }
    public void use_thread(JFrame frame,JLabel Clock)
    {
        MyTime myTime=new MyTime(frame,Clock);
        thread=new Thread(myTime);
        thread.start();
    }
    public void receive_state(int state)
    {
        this.state=state;
        if(state==0)
        {
            timer.stop();
        }
    }
    public void close_timer()
    {
        timer.stop();
    }
    public void restart_timer(JFrame jFrame,JLabel Clock)
    {
        second=60;
        timer.restart();
        //use_thread(jFrame,Clock);
    }
    class MyTime implements Runnable
    {
        private JFrame frame;
        private JLabel Clock;
        public MyTime(JFrame frame,JLabel Clock)
        {
            this.frame=frame;
            this.Clock=Clock;
        }
        public void run()
        {
            try {
                Thread.sleep(1);
                start_timer_down(frame, Clock);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
