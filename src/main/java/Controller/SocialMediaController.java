package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
  
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
    
        app.post("/register", this::postAccountHandler);
      
       app.post("/login", this::postLoginHandler);
       app.post("/messages", this::createMessageHandler);
       app.get("/messages", this::getAllMessageHandler);
       app.get("/messages/{message_id}", this::getMessageByIdHandler);
       app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);

       app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
       app.get("/accounts/{account_id}/messages", this::getAllMessageByAccountIdHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

     private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            
        }else{
            ctx.status(400);
        }
    
    }
    
    
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginedAccount = accountService.loginAccount(account);
        if(loginedAccount!=null){
            ctx.json(mapper.writeValueAsString(loginedAccount));
            
        }else{
            ctx.status(401);
        }
    } 
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            
        }else{
            ctx.status(400);
        }
    } 
    private void getAllMessageHandler(Context ctx) {
       
        List<Message> messages = messageService.getAllMessages();
        if(messages!=null){
            ctx.json(messages);
            
        }else{
            ctx.status(200);
        }
    }
    private void getMessageByIdHandler(Context ctx) {

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
       Message messages =messageService.getMessageById(message_id);
     System.out.println(messages+"at get message");
    if(messages!=null){
        ctx.json(messages);
        
    
    }else{
        ctx.status(200);

        }
    }
    private void deleteMessageByIdHandler(Context ctx) {
       
     int message_id = Integer.parseInt(ctx.pathParam("message_id"));
     
      Message messages =messageService.deleteMessageById(message_id);
      
      System.out.println(messages+"at delete message");
      if(messages!=null){
       
        ctx.json(messages);
        
    }else{
        ctx.status(200);
      
        }
    }
    private void patchMessageByIdHandler(Context ctx)  throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
      Message message = mapper.readValue(ctx.body(), Message.class);
       int message_id = Integer.parseInt(ctx.pathParam("message_id"));
      //String message = ctx.pathParam("message_text");
         Message updatedMessageText = messageService.patchMessageById(message, message_id);
     
      System.out.println(updatedMessageText+ "at controller");
      
       if(updatedMessageText != null){
            ctx.json(mapper.writeValueAsString(updatedMessageText));
        
            
        }else{
           
            ctx.status(400);
        }
       
}

    private void getAllMessageByAccountIdHandler(Context ctx) {
        int posted_by = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(posted_by);
        if(messages!=null){
            ctx.json(messages);
            
        }else{
            ctx.status(200);
        }
    }
}