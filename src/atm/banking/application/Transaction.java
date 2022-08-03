/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.banking.application;

import java.util.Date;

/**
 *
 * @author Ntokozo
 */
public class Transaction {
    private double amount;//amount of the transaction
    private Date timestamp;//time and date of the transaction

    private String memo;//a memo of the transaction
    private Account inAccount;//the account in which the transaction was performed
    
    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
        
    }
    public Transaction(double amount, String memo, Account inAccount){
        
//        evoke the two arguement constructor
        this(amount,inAccount);
//        set the memo of the transaction
        this.memo = memo;

    }
    double getAmount(){
        return this.amount;
    }
    /**
     * Summarizes the transaction
     * @return 
     */
    public String getSummaryLine(){
        if(this.amount >= 0){
            return String.format("%s : R%.02f : %s", timestamp.toString(),amount, memo);
        }else{
            return String.format("%s : (R%.02f) : %s", timestamp.toString(),-amount, memo);
        }
    }
}


