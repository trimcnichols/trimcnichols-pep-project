package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public Message createMessage(Message message){
       
            Connection connection = ConnectionUtil.getConnection();
            try {
                //Write SQL logic here
                String sql = "insert  into message (posted_by,message_text,time_posted_epoch) values(?,?,?)" ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                //write preparedStatement's setString and setInt methods here.
                
                preparedStatement.setInt( 1,message.getPosted_by());
                preparedStatement.setString( 2,message.getMessage_text());
                preparedStatement.setLong( 3,message.getTime_posted_epoch());

                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_message_id = (int) pkeyResultSet.getLong(1);
                        return new Message(generated_message_id, message.getPosted_by(),message.getMessage_text(), message.getTime_posted_epoch());
                      
                } 
                return message;
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                   Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                   messages.add(message);   
                }
               
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return messages;
        }
        public Message getMessageById(int message_id){
            Connection connection = ConnectionUtil.getConnection();
            
            try {
                //Write SQL logic here
                String sql = "select * from message where message_id = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt( 1,message_id);

                ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()){
                        Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                        System.out.println(message+"at messageId");
                        return message;
                    }
                   
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
                return null;
            }
           
            public Message deleteMessageById(int message_id){
                Connection connection = ConnectionUtil.getConnection();
                
                try {
                    //Write SQL logic here
                    String sql = "delete from message where message_id = ?";
    
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt( 1,message_id);

                   int rowsdeleted = preparedStatement.executeUpdate();
                        if (rowsdeleted > 0) {
                            System.out.println("An existing message was deleted successfully!");
                          
                        }
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    return null;
                }       
               
                public Message  patchMessageById(Message message, int message_id){
                    Connection connection = ConnectionUtil.getConnection();  
                    try { 
                        String sql = "update message set message_text=? where message_id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);

                        preparedStatement.setString( 1,message.getMessage_text());
                        preparedStatement.setInt( 2,message_id);
                        
                        int rowsUpdated = preparedStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("An existing message was updated successfully!");
                         return getMessageById(message_id);
                        }
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    return null;
                }

                public List<Message> getAllMessagesByAccountId(int posted_by){
                    Connection connection = ConnectionUtil.getConnection();
                    List<Message> messages = new ArrayList<>();
                    try {
                        //Write SQL logic here
                        String sql = "select * from message where posted_by = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);

                         preparedStatement.setInt( 1,posted_by);
                        ResultSet rs = preparedStatement.executeQuery();
                        while(rs.next()){
                            Message message =  new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                           messages.add(message);
                        }
                       
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    return messages;
                } 
}
