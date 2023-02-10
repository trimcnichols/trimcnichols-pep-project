package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    
    public Account createAccount(Account account){
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "insert  into account (username,password) values(?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
           
            preparedStatement.setString( 1,account.getUsername());
            preparedStatement.setString( 2,account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                  
            } 
            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account getLogin(Account account){
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from account where username = ? and password = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
           
            preparedStatement.setString( 1,account.getUsername());
            preparedStatement.setString( 2,account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account login = new Account(rs.getInt("account_id"),

                        rs.getString("username"),
                        rs.getString("password"));
                        
                return login;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
   
}
