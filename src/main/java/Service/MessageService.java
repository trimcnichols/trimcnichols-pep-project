package Service;

import java.util.List;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
       
    }
     public  Message createMessage(Message message) {
        if( message.getMessage_text()!= "" && message.getMessage_text().length()<256){
         return  messageDAO.createMessage(message);
        }
  
        return null;
    }
    public List<Message> getAllMessages() {
   
        return messageDAO.getAllMessages();
    
    }

    public Message getMessageById(int message_id){
        if( messageDAO.getMessageById(message_id) !=null){
    
          return messageDAO.getMessageById(message_id);
        }
        return null;
    }
    public Message deleteMessageById(int message_id){
      Message message = messageDAO.getMessageById(message_id);
      messageDAO.deleteMessageById(message_id);
      if( message!=null){
        System.out.println("at deleted service");
        
        return message;
       
      }
       return null;
  }
 
    public  Message patchMessageById(Message message, int message_id){
   
      if( messageDAO.getMessageById(message_id) != null && message.getMessage_text()!= "" && message.getMessage_text().length()<256){
           System.out.println("at patch service");
      
            return messageDAO.patchMessageById(message,message_id);
        }
        return null;
      }
  
    public List<Message> getAllMessagesByAccountId(int posted_by) {
  
        return messageDAO.getAllMessagesByAccountId(posted_by);
    }
}
