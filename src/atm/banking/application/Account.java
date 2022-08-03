/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.banking.application;

import java.util.ArrayList;

/**
 *
 * @author Ntokozo
 */
public class Account {
    private String name;//name of the account: savings or cheque
    
    private String uuid; //accoun ID number
    
    private User holder; //user object that owns the account
    private ArrayList<Transaction>transactions;//list of transactions of the user
    
   
    Account(String name,User holder, Bank bank){
        //set account name & holder
        this.name = name;
        this.holder = holder;
        
        this.uuid = bank.getNewAccountUUID(); //get new account UUID
        
        //init transactions
        this.transactions = new ArrayList<Transaction>();
        
        // add to holder and bank list
       
        
    }
    String getUUID(){
        return this.uuid;
    }

    String getSummaryLine() {
       //get the accounts balance
       double balance  = this.getBalance();
       
       //format summary balance incase account is overdrawn
       if(balance >= 0){
           return String.format("%s : R%.02f : %s", this.uuid, balance, this.name);
       }else{
           return String.format("%s : R(%.02f) : %s", this.uuid, balance, this.name);
       }
    }

    public double getBalance() {
      double balance  = 0;
      balance = this.transactions.stream().map(t -> t.getAmount()).reduce(balance, (accumulator, _item) -> accumulator + _item);
      return balance;
    }

    void printTransHistory() {
       System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int i = this.transactions.size()-1; i >= 0; i--) {
             System.out.println(this.transactions.get(i).getSummaryLine());
       }
       System.out.println();
    }

    void addTransaction(double amt, String memo) {
        //create a new transaction object
        //then add to the list
        Transaction newTrans = new Transaction(amt, memo, this);
        this.transactions.add(newTrans);
    }
}
    

