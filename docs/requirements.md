# Requirements

This document lists all the requirements that are expected from the *DeclaBo* project.

## Requirements for the Ledger Subsystem

### Users
 - Users shall be identified by their email;
 - Users shall have a name and a surname;
 - Users may have a nickname;
 - Users shall be called by their nicknames (if they have any);

### Accounting Entities
 - There shall be two basic types: *debtor* and *creditor*;
 - There shall be the following subtypes of debit accounts:
   - Inventory;
   - Beer;
   - Banking account;
   - Packaging (NL: emballage);
   - subscriptions;
   - Housemates;
 - There shall be the following subtypes of credit accounts:
   - Accounts Payable;
   - Initiatievenpotje;

### General
 - There shall be user accounts;
 - User accounts shall have roles;
 - User accounts can have multiple roles;
 - There shall be a *CFO* user role. This role gives access to actions regarding settling of receipts;
 - There shall be a *admin* user role. This role gives access to all functionality;
 - The *admin* shall be able to create new user accounts;
 - The *admin* shall be able to suspense user accounts;
 - The *admin* shall be able to delete user accounts;
 - The *user* must specify a valid email address upon the first login;
 - The *user* shall only be able to use the system after they validated their email address;
 - *Users* shall be able to login to a web interface;
 - *Users* shall be able to **submit receipts** for expenses;
 - The submitted receipts shall consist of (an) image(s) or pdf file(s) of the original receipt(s);
 - The *CFO* shall be able to create/delete/update *events*. These are events such as a kerstdiner or huischvakantie;
 - The *CFO* shall be able to create settlements for all transactions that belong to a single event;
 - *Users** shall be able to choose from **events** to which their expense belongs;
 - *Users* must specify a description for their submitted receipts;
 - *Users* must specify a date for the submitted receipt;
 - The system shall have *transactions* which are a debtor-creditor type of transaction;
 - Transactions may be grouped into *transaction-receipts*;
 - The *CFO* shall be able to add new transactions to the ledger system;
 - The *CFO* shall be able to associate receipt submissions with transactions;
 - The *CFO* shall be able to process the user submitted receipts;
 - The *CFO* shall be able to **create a settlement**; 
 - The system must **generate invoices** when a settlement is created;
 - The system shall automatically **send emails** to the involved users when a settlement is created. These mails will include an **invoice** that the user must pay;
 - The system shall save the generated invoices on the filesystem;
 - The *CFO* shall be able to access the invoices of all users;
 - A user shall be able to access their own invoices.
 - A user with *CFO* or *admin* role must be able to split an amount evenly over selected funds without having to create a transaction for every fund but instead the system should generate all the transactions (which then have the same description that will be specified by the user).