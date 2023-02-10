package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
     public  Account createAccount(Account account) {
        if( account.getUsername()!= "" && account.getPassword().length()> 4 ){
         return  accountDAO.createAccount(account);
        }
  
        return null;
    }

    
    public  Account loginAccount(Account account) {
   
       return  accountDAO.getLogin( account);
    
    }
}
