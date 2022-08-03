/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.banking.application;

import java.util.*;

/**
 *
 * @author Ntokozo
 */
public class Bank {
    private String name;
    
    private ArrayList<User>users;
    
    private ArrayList<Account>accounts;
    
    Bank(String name){
       this.name = name;
       this.users = new ArrayList<User>();
       this.accounts = new ArrayList<Account>();
    }
    public String getNewUserUUID(){
        String uuid;
        Random rand = new Random();
        int len = 6;
        boolean nonUnique;
        
        //looping until get a new unique IDs
        nonUnique = false;
        do{
           //generate number
           uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rand.nextInt(10)).toString();
            }
            //check if unique
            
            for(User u : this.users){
                if(uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);
        
        return uuid;
    }
    
    public String getNewAccountUUID(){
       
       String uuid;
       Random rand = new Random();
        int len = 10;
        boolean nonUnique;
        
        //looping until get a new unique ID
        nonUnique = false;
        do{
           //generate number
           uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rand.nextInt(10)).toString();
            }
            //check if unique
            
            for(Account a : this.accounts){
                if(uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);
        
        return uuid;
    }
    //account to add
    void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }
   public User addUser(String fname, String lname, String pin){
       User newUser = new User(fname,lname,pin,this);
       this.users.add(newUser);
       
    //       create savings account for the user
        Account newAccount = new Account("Saving Account", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        
        return newUser;
   }
    /**
     * Get the user object associated with a particular userID & pin if they are valid
     * 
     * @param userID uuid of the user to login
     * @param pin of the user
     * @return the user object if login was successful or null if it's not valid
     */      
   public User userLogin(String userID, String pin){
       
    //searching through list of users
    for(User u : this.users){
        
        //check if the ID is correct 
        if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
            return u;
        }
    }
    //if the user is not found or pin is incorrect
    return null;
   }
   /**
    * returns the name of the bank
    * @return 
    */
   public String getNameOfBank(){
       return this.name;
   }
}
