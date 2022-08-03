/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.banking.application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ntokozo
 */
public class User {
    private String _firstname;
    private String _lastname;
    private String uuid;//id number of the user
    private byte pinHash[];//md5 hash method of user's pin number
    private ArrayList<Account>accounts;//list of accounts of the user
    
    //setting up a new user
    User(String fname, String lname, String pin, Bank bank){
        
//        setting the user's name
        this._firstname = fname;
        this._lastname = lname;
       
         //        store the pin's MD5 hash, rather than the original value
         //        security reasons
        try {
           
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("error, caught NoSuchAlgorithm");
            System.exit(1);
        }
        //retrieve a new unique universal ID of the user
        this.uuid = bank.getNewUserUUID();
        
        //creating an empty list of account
        this.accounts = new ArrayList<Account>();
        
        //print Message
        System.out.printf("\nNew user %s, %s with ID %s created. \n", lname, fname, this.uuid);
    }
    void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }
    //
    public String getUUID(){
        return this.uuid;
    }
    /**
     * Checks whether a given pin matches the true user pin
     * @param pin the pin to check
     * @return whether the pin is valid or not
     */
    boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    void printAccountSummary() {
       
        /**
         * print account summary
         */
        System.out.printf("\n\n%s's accounts summary\n", this._firstname);
        for(int x = 0; x < this.accounts.size(); x++){
            System.out.printf("%d) %s\n", x+1, this.accounts.get(x).getSummaryLine());
        }
        System.out.println();
    }

    String getFirstName() {
       return this._firstname;
    }

    int numAccounts() {
       return this.accounts.size();
    }
    //print index for a particular account
    void printAcctTransHistory(int theAcct) {
        this.accounts.get(theAcct).printTransHistory();
    }

    double getAcctountBalance(int frmAcct) {
        return this.accounts.get(frmAcct).getBalance();
    }
    /**
     * get uuid of a particular account
     * @param toAcct
     * @return 
     */
    String getAcctUUID(int toAcct) {
        return this.accounts.get(toAcct).getUUID();
    }

    void addAcctTransaction(int acctIdx, double amt, String memo) {
        this.accounts.get(acctIdx).addTransaction(amt,memo);
    }
}
