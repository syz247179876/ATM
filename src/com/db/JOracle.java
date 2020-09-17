package com.db;
import javax.swing.*;
import java.sql.*;
import static java.sql.DriverManager.*;

public class JOracle {
    String _dbURL="jdbc:oracle:thin:@localhost:1521:orcl";//连接字符串
    String _driver="oracle.jdbc.OracleDriver";    //驱动标识符
    String _user="SYZ";
    String _password="password";
    Connection _con = null;   //连接对象
    PreparedStatement _pstm = null;
    Statement _stam=null;
    ResultSet _rs = null;
    public static int All_bill;
    public int getAll_bill()
    {
        return All_bill;
    }
    public static int count=5;

    public JOracle()
    {
        connection();
    }
    public void connection()
    {
        try {
          //  Class.forName(_driver);  //可以不用写
            System.out.println("加载数据库驱动成功！");
            _con= getConnection(_dbURL,_user,_password);
            System.out.println("数据库连接成功！");
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.out.println("找不到驱动程序类，加载驱动失败！");
        }
    }


    public JLabel login(String User_name,String User_password) {
        while (count>1) {
            if (User_name.equals("") || User_password.equals("")) {
                count-=1;
                return new JLabel("用户名或密码为空，你还剩下"+count+"次机会");
            }
            else {
                try {
                    String sql = "select * from USERS WHERE BANK_CARD ='"+User_name+"' and PASSWORD='"+User_password+"'";
                    System.out.println(sql);
                    //assert _con != null;
                    //assert _con != null;
                    //_stam=_con.createStatement();
                    _pstm = _con.prepareStatement(sql);
                    //_pstm.setString(1, User_name);
                    //_pstm.setString(2, User_password);
                    _rs = _pstm.executeQuery(sql);
                        if (_rs.next()) {
                            if (User_password.equals(_rs.getString("password"))) {
                                return new JLabel("1");
                            }
                        }
                    else {
                        count -= 1;
                        return new JLabel("银行卡号和密码不匹配，你还剩下" + count + "次机会");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                }


        }
        if (count==1)
            return new JLabel("0");

        return new JLabel("1");

    }
    public String Verify_(String sql,String need_field,String type)
        {
        try
        {
            String temp_string;
            int temp_int;
            _stam=_con.prepareStatement(sql);
            _rs=_stam.executeQuery(sql);
            while(_rs.next())
            {
                if (type.equals("String")) {
                    temp_string = _rs.getString(need_field);
                    return temp_string;
                }
                else {
                    temp_int = _rs.getInt(need_field);
                    return Integer.toString(temp_int);
                }
                //需要改善关闭数据库
                // _rs.close();
                //_stam.close();
                //_con.close();

            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String[] Verify_list(String sql)
    {
        String temp_[]=new String[5];
        try{
            _stam=_con.prepareStatement(sql);
            _rs=_stam.executeQuery(sql);
            while (_rs.next())
            {
                temp_[0]="水费"+_rs.getInt("water_rate");
                temp_[1]="电费"+_rs.getInt("power_rate");
                temp_[2]="物业管理费"+_rs.getInt("ADMINISTRATIVE_FEE");
                temp_[3]="电话费"+_rs.getInt("TELEPHONE_CHARGE");
                All_bill+=_rs.getInt("water_rate")+_rs.getInt("power_rate")+_rs.getInt("ADMINISTRATIVE_FEE")+_rs.getInt("TELEPHONE_CHARGE");

            }
            return temp_;
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        }
        return temp_;
    }


    public void Update_(String sql)
    {
        try
        {
            _stam=_con.createStatement();
            _stam.executeUpdate(sql);
            //_rs.close();
            //_stam.close();
            //_con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
