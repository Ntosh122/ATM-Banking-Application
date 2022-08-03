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
public class ATMBankingApplication {
    static String fname;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        
        
        Bank objBank = new Bank("FNB Banking");
        
        // add a user, which also creates savings accounts
        
        System.out.print("Enter your first name: ");
        fname = sc.nextLine();
        
        System.out.print("Enter your last name: ");
        String lname = sc.nextLine();
        
        System.out.print("Enter your pin: ");
        String pin = sc.nextLine();
        
        User aUser = objBank.addUser(fname, lname, pin);
        
         // add a cheque account for the user
         Account newAccount = new Account("Cheque Account", aUser, objBank);
         aUser.addAccount(newAccount);
         objBank.addAccount(newAccount);
         
         User currentUser;
         while(true){
              //stay in the login prompt until login successful  
             currentUser = ATMBankingApplication.mainMenuPrompt(objBank, sc);
             
              //stay in the menu until user quits or exits
              ATMBankingApplication.printUserMenu(currentUser,sc);
         }
    }

    private static User mainMenuPrompt(Bank objBank, Scanner sc) {
        
        String userID,pin;
        User authUser;
        
        //prompt user to end ID/pin combo until a correct pin is reached
        do{
            System.out.printf("\n\nWelcome to %s\n\n", objBank.getNameOfBank());
            System.out.print("Please enter user ID: ");
            userID = sc.nextLine();
            
            System.out.print("Enter pin: ");
            pin = sc.nextLine();
            
        //get the user object corresponding to the ID and pin combo
        authUser = objBank.userLogin(userID, pin);
        if(authUser == null){
            System.out.println("Incorrect user ID/pin combination. " + "Please try again");
        }
        }while(authUser == null);//continue looping until successful login
        
        return authUser;
    }

    private static void printUserMenu(User currentUser, Scanner sc) {
        
        //print a statement of the user's accounts
        currentUser.printAccountSummary();
        
        int choice;
        
        //user menu
        do{
            System.out.printf("Welcome %s, Please select any of the options below:\n", currentUser.getFirstName());
            System.out.println("1. Show account transaction history");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Transfer - (CashSend, EasyTransfer)");
            System.out.println("5. Cancel Transaction");
            System.out.println();
            System.out.print("Select your choice:");
            choice = sc.nextInt();
            
            if(choice < 1 || choice > 5){
                System.out.println("Invalid Choice. Please choose between 1-5");
            }
        }while(choice < 1 || choice > 5);
        //process choice
        
        switch(choice){
            case 1:
                ATMBankingApplication.showMemo(currentUser,sc);
                break;
            case 2:
                ATMBankingApplication.Withdrawal(currentUser,sc);
                break;
            case 3:
                ATMBankingApplication.Deposit(currentUser,sc);
                break;
            case 4:
                ATMBankingApplication.Transfer(currentUser,sc);
                break;
            case 5:
                sc.nextLine();
                break;
                
        }
        //display menu again unless user quits
        if(choice != 5){
            //this implementation is called recursion
            ATMBankingApplication.printUserMenu(currentUser, sc);
        }else{
            System.out.println("Goodbye " + fname + ", Thank you for banking with us!!");
            System.exit(0);
        }
              
    }

    private static void showMemo(User currentUser, Scanner sc) {
        
        int theAcct;
        //get account whose transaction history to look at
        do{
            System.out.printf("Enter account number (1-%d) ", currentUser.numAccounts());
            
            theAcct = sc.nextInt()-1;
            if(theAcct < 0 || theAcct >= currentUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        }while(theAcct < 0 || theAcct >= currentUser.numAccounts());
        
        //print Transaction History
        currentUser.printAcctTransHistory(theAcct);
    }
    /**
     * Process a fund withdraw from an account
     * @param currentUser
     * @param sc 
     */
    private static void Withdrawal(User currentUser, Scanner sc) {
      
        int frmAcct;
        double amount,balance;
        String memo;
        
        //get the accounts to transfer from
        do{
            System.out.printf("Enter the account number (1-%d) you want to make a withdraw from: ", currentUser.numAccounts());
            frmAcct = sc.nextInt()-1;
            if(frmAcct < 0 || frmAcct >= currentUser.numAccounts()){
                 System.out.println("Invalid account. Please try again.");
            }
        }while(frmAcct < 0 || frmAcct >= currentUser.numAccounts());
        balance = currentUser.getAcctountBalance(frmAcct);
        
        do{
            System.out.printf("Enter the amount you want to withdraw (max R%.02f): R",
                    balance);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0. Please try again.");
            }else if(amount > balance){
                System.out.println("Amount must not be greater than\n"
                        + "the balance of $%.02f\n");
            }
           
        }while(amount < 0 || amount > balance);
        //gobble up the rest of the previous input line
        sc.nextLine();
        
        System.out.print("Add a reference of the transaction: ");
        memo = sc.nextLine();
        
        //do the withdrawal
        currentUser.addAcctTransaction(frmAcct, -1*amount, memo);
    }

    
    private static void Deposit(User currentUser, Scanner sc) {
       
         int toAcct;
        double amount,balance;
        String memo;
        
        //get the accounts to transfer from
        do{
            System.out.printf("Enter the account number (1-%d) you want to make a transfer to: ", currentUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= currentUser.numAccounts()){
                 System.out.println("Invalid account. Please try again.");
            }
        }while(toAcct < 0 || toAcct >= currentUser.numAccounts());
        balance = currentUser.getAcctountBalance(toAcct);
        
        do{
            System.out.printf("Enter the amount you want to transfer (max R%.02f): R",
                    balance);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0. Please try again.");
            }
        }while(amount < 0);
        //gobble up the rest of the previous input line
        sc.nextLine();
        
        System.out.print("Add a reference of the transaction:  ");
        memo = sc.nextLine();
        
        //do the withdrawal
        currentUser.addAcctTransaction(toAcct, amount, memo);
    }

    private static void Transfer(User currentUser, Scanner sc) {
       
        int frmAcct,toAcct;
        double amount,balance;
        
        //get the accounts to transfer from
        do{
            System.out.printf("Enter the account number (1-%d) you want to make a transfer from: ", currentUser.numAccounts());
            frmAcct = sc.nextInt()-1;
            if(frmAcct < 0 || frmAcct >= currentUser.numAccounts()){
                 System.out.println("Invalid account. Please try again.");
            }
        }while(frmAcct < 0 || frmAcct >= currentUser.numAccounts());
        balance = currentUser.getAcctountBalance(frmAcct);
        
        //get account to transfer to
        do{
            System.out.printf("Enter the account number (1-%d) you want to make a transfer to: ", currentUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= currentUser.numAccounts()){
                 System.out.println("Invalid account. Please try again.");
            }
        }while(toAcct < 0 || toAcct >= currentUser.numAccounts());
        //get amount to transfer
        
        do{
            System.out.printf("Enter the amount you want to transfer (max R%.02f): R",
                    balance);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0. Please try again.");
            }else if(amount > balance){
                System.out.println("Amount must not be greater than\n"
                        + "the balance of (R%.02f) R");
            }
        }while(amount < 0 || amount > balance);
        
        //perform the transfer
        currentUser.addAcctTransaction(frmAcct, -1*amount, String.format("Transfer to account %s", currentUser.getAcctUUID(toAcct)));
        currentUser.addAcctTransaction(toAcct, amount, String.format("Transfer to account %s", currentUser.getAcctUUID(frmAcct)));
    }
    
}
