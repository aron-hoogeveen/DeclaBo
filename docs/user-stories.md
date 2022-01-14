# User Stories
This document contains several user stories for the *DeclaBo* project. The stories are sorted by the executing user. 

## Stories
### Filling in Receipts
**Actor**: PayingHousemate  
**Parties**: CFO, PayingHousemate  
**Story**: A Housemate went to the Makro and bought several items for Huisch' inventory. The following items were bought:
 - Toiletpaper;
 - Coffee;
 - Tea;
 - Spices.
 
They paid with their own card. The housemate opens their browser and surfs to [https://declabo.bolkhuis.ch](https://declabo.bolkhuis.ch). They are served with a login page in which they enter their login credentials. The username will be their email address (e.g. hendrik@bolkhuis.ch). After logging in they navigate to [https://declabo.bolkhuis.ch/declareer](https://declabo.bolkhuis.ch/declareer) where they can invoice their purchases for Huisch. They are presented with a form in which they need to provide the following information:
 - The date of the purchase. This must be after the date of the last settlement;
 - The paying party (in this case themselves, but could also be e.g. "huischpas");
 - A short description of the purchase (e.g. "Makrorun");
 - The total amount paid;
 - (optional) the event for which the purchase was (e.g. "Huischweekend");
 - (optional) The receipt of the purchase. The following file formats can be uploaded: .jpg, .png, .jpeg, .pdf;
 - (optional) Any remarks on the purchase;

After they submit the form they get a message that their submission was successfully received and that they will get a notification (e.g. through email) at the time their submission has been processed by the CFO.

### Checking your balance
**Actor**: PayingHousemate  
**Parties**: PayingHousemate  
**Story**: The housemate surfs to [https://declabo.bolkhuis.ch](https://declabo.bolkhuis.ch) and logins with their login credentials. They navigate to [https://declabo.bolkhuis.ch/balance](https://declabo.bolkhuis.ch/balance) and are presented with a web page on which their current balance is found. 

### Checking submitted invoices
**Actor**: PayingHousemate  
**Parties**: PayingHousemate  
**Story**: The housemate surfs to [https://declabo.bolkhuis.ch/submissions](https://declabo.bolkhuis.ch/submissions) and is presented with a login page. After logging in they are redirected back to the submissions webpage. There they are presented with an overview of all their past submissions. They can sort on the following properties:
 - unprocessed/processed submissions;
 - before date;
 - after date;
 - between dates;
 - on date;
 - from event;
 - unsettled/settled

The submissions show the following properties: are they processed, are they settled, their description, who paid, the date, possible event. Clicking on a submission will direct them to [https://declabo.bolkhuis.ch/submissions/{id}](https://declabo.bolkhuis.ch/submissions/{id}), with `{id}` being the id of the submission.

### Deleting submitted invoices
**Actor**: PayingHousemate  
**Parties**: PayingHousemate  
**Story**: The housemate wants to delete a previous submission due to some problems with it. It surfs to [https://declabo.bolkhuis.ch/submissions](https://declabo.bolkhuis.ch/submissions) and searches for the specific submission. After clicking on the submission it is forwarded to [https://declabo.bolkhuis.ch/submissions/{id}](https://declabo.bolkhuis.ch/submissions/{id}) where it can check that it is the correct submission. On that page there may be a button called "delete". This button is only active if the following conditions are met: The submission is not yet settled. If the submission is not yet settled, the user may use the delete button. After having clicked the delete button it receives a response that the deletion was successful. 

If the submission was not yet processed, the submission can be deleted without any other actions. However, if the submission was already processed the following things need to happen: the processing needs to be undone. For every transaction in the submission it has to be rolled back. Any money that has been moved do to the transactions must be moved back to where it came from. After all transactions have been rolled back, the submission can be fully deleted, and the user receives back that the deletion was successful.

Invoices can only be deleted by the person who created them, by a user with role admin, or by a user with role CFO.

### Updating a submitted invoice
**Actor**: PayingHousemate  
**Parties**: PayingHousemate  
**Story**: The Housemate navigates to [https://declabo.bolkhuis.ch/submissions/{id}](https://declabo.bolkhuis.ch/submissions/{id}) and clicks on the "update" button. The update function can only be used when the submission is not yet settled. When they click the button they are forwarded to the page [https://declabo.bolkhuis.ch/submissions/{id}/edit](https://declabo.bolkhuis.ch/submissions/{id}/edit) on which they can update the following properties of the submission: date (must be after the last settlement), description, remarks. After editing the submission they receive a response that the changes have been saved.

### Processing a submission
**Actor**: Admin, CFO  
**Parties**: Admin, CFO, PayingHousemate  
**Story**: A Housemate made a submission with id `{id}`. The CFO navigates to [https://declabo.bolkhuis.ch/admin/submissions](https://declabo.bolkhuis.ch/admin/submissions) and gets an overview of all the submissions. It sorts the submissions so to only show the unprocessed submissions and clicks on the submission that is unprocessed. They are forwarded to [https://declabo.bolkhuis.ch/admin/submissions/{id}](https://declabo.bolkhuis.ch/admin/submissions/{id}) where they can process the submission. On that page all the info regarding the submission is shown including the attached files (if available). If needed, the CFO can change the name of the submission to better represent the submission. Furthermore, they can change the amount of the submission if deemed incorrect. If the name and or ammount is changed the user should get a notification of that change. 

Under the submission the CFO can create new transactions that belong to this submission. A transaction can hold the following properties:
 - debtor (the accounting entity that received money);
 - creditor (the accounting entity that gave money);
 - amount;
 - description.

Either the debtor or the creditor must be equal to the entity that is marked as the payer in the submission. If they are done creating the transactions they can hit the "process" button. If the button is activated the following checks are performed: the total amount of money that the payer paid or received should be equal to the amount specified on the submission. If everything is correct the submission is processed and after processing it is marked as processed. The submitting user will then get a notification that their submission was processed.

### Adding new Accounting Entities
**Actor**: Admin, CFO  
**Parties**: Admin, CFO  
**Story**: The CFO wants to add a new accounting entity (e.g. "initiatievenpotje"). They navigate to [https://declabo.bolkhuis.ch/admin/entity](https://declabo.bolkhuis.ch/admin/entity) where they are presented with an overview of all accounting entities that exist. They click on the button "add entity" and are forwarded to [https://declabo.bolkhuis.ch/admin/entity/create](https://declabo.bolkhuis.ch/admin/entity/create) on which a form is present with the following fields:
 - type (debit/credit);
 - name (e.g. "initiatievenpotje" or "ing rekening");
 - balance (most likely should be zero for new entities).
 - (optional) event (the event this accounting entity belongs to. Once an event is settled and will be deleted, all related accounting entities will also be deleted)

After posting the form they are greeted with the form again with a banner notification stating the success/failure of the action.

### Adding new Housemates
**Actor**: Admin, CFO  
**Parties**: Admin, CFO, Housemate, PayingHousemate  
**Story**: The CFO navigates to http://declabo.bolkhuis.ch/admin/users/create where they are greeted with a form containing the following fields:
 - email (will be used for logging in and for sending email);
 - (optional) name;
 - (optional) surname;
 - (optional) nickname;
 - tickbox: paying housemate (ticked by default, must be ticked if the user will need to use the receipt submission system);
 - (optional) room number (the number of the room in Huisch).

After hitting the "create" button, an inactive user account is made and an activation email is send to the email address. When the housemate opens the email and clicks on the activation link it is forwarded to [https://declabo.bolkhuis.ch/user/activate](https://declabo.bolkhuis.ch/user/activate). There it is presented with a form with the following fields (if the admin already filled in the optional fields they will be filled in here upfront):
 - password;
 - repeat password;
 - name (may be filled in);
 - surname (may be filled in);
 - (optional) nickname (may be filled in);
 - (optional) room number (may be filled in). Is needed for the generation of the cleaning schedule and the garbage bin schedule reminder email. 

After filling in all required fields they may click the "activate" button and after validation of the fields, their account will be activated. Upon successful activation they will be redirected to [https://declabo.bolkhuis.ch/login](https://declabo.bolkhuis.ch/login) where they can log in. After logging in they will be forwarded to [https://declabo.bolkhuis.ch/account](https://declabo.bolkhuis.ch/account).

### Adding new events
**Actor**: Admin, CFO  
**Parties**: Admin, CFO  
**Story**: There is an upcoming Huischweekend so that will mean that people will be submitting receipts for this specific event. An event will be invoiced separately, and housemates are expected to pay the invoice in one go. The CFO navigates to [https://declabo.bolkhuis.ch/admin/events/create](https://declabo.bolkhuis.ch/admin/events/create) and is presented with a form with the following fields:
 - Name;
 - Date;
 - Description;
 - Attendants (this list of PayingHousemates will be used to simplify the automatic settling of the event, or for a button "split evenly over attendants");

### Viewing all events
**Actor**: Admin, CFO  
**Parties**: Admin, CFO  
**Story**: The CFO navigates to [https://declabo.bolkhuis.ch/admin/events](https://declabo.bolkhuis.ch/admin/events) and is greeted with a page on which all events are listed. On this page are also buttons available which link to pages for creating new events. After each event there are links to multiple actions: view/update/settle/delete. 

### Viewing a single event
**Actor**: Admin, CFO  
**Parties**: Admin, CFO, PayingHousemates  
**Story**: The CFO navigates to [https://declabo.bolkhuis.ch/admin/events/{id}](https://declabo.bolkhuis.ch/admin/events/{id}) to view the details about the event with id `{id}`. The CFO is presented with multiple links:
 - view submissions; 
 - view accounting entities;

#### View Submissions
If the CFO clicks on "view submissions" it is forwarded to [https://declabo.bolkhuis.ch/admin/events/{id}/submissions](https://declabo.bolkhuis.ch/admin/events/{id}/submissions) where it can find a list of all submissions that are involved in this event.

#### View Accounting Entities
If the CFO clicks on "view accounting entities" they are forwarded to [https://declabo.bolkhuis.ch/admin/events/{id}/accounting_entities](https://declabo.bolkhuis.ch/admin/events/{id}/accounting_entities) where they can find a list of all accounting entities that are linked to this event and their current balance. If the balance is non-zero the CFO can click on a link "split" after which it is forwarded to [https://declabo.bolkhuis.ch/admin/events/{id}/accounting_entities/{e_id}/split](https://declabo.bolkhuis.ch/admin/events/{id}/accounting_entities/{e_id}/split) with `{e_id}` the id of the accounting entity. On this page they can select from all the PayingHousemates who attend this event the people who participated in the sub-event that this accounting entity is for.. For each of the housemates they must specify an integer weight (or maybe an amount). After filling in all weights and clicking "split" the following happens: if the amount can be split evenly over all attendants, then for each of the attendants a transaction is constructed with the amount they must pay or receive. If the amount cannot be evenly split over all attendants the amounts are rounded up to cents (so €0.991 becomes €1.00). Note that the rounding up has to be done *per 1 weight*, so that someone who participates twice has to pay €2,00 instead of €1,99. For the leftover money that will arise due to this rounding up, a transaction from the accounting entity to the "initiatievenpotje" will be made. This way everyone pays exactly the same, and the leftover money will be made to use later.

### Processing an Event
**Actor**: Admin, CFO  
**Parties**: Admin, CFO, PayingHousemates  
**Story**: If all accounting entities that are connected to this event have balance 0, the CFO can settle the event. The CFO navigates to [https://declabo.bolkhuis.ch/admin/events/{id}](https://declabo.bolkhuis.ch/admin/events/{id}) and hits the button "process". It is checked that all related accounting entities (except PayingHousemates) have balance 0, and all transactions and submissions for this event are retrieved. Per attendant an invoice is generated. For each of the transactions that belong to a submission a single place-holder transaction is created which contains as the amount the resulting amount of all the related transactions of the submission. The original transactions are removed from the set of transactions and this new transaction is added. This makes sure that the PayingHousemate only gets to see one entry for the submission on its invoice, instead of all the related transactions.

Each of the attendants now gets an email or notification that the event has been settled and the request to pay the attached invoice. They should pay the invoice and mention the payment reference as specified on the invoice. 

### Settling the Reckoning
**Actor**: Admin, CFO  
**Parties**: Admin, CFO, PayingHousemates  
**Story**: The CFO navigates to [https://declabo.bolkhuis.ch/admin/settlements/create](https://declabo.bolkhuis.ch/admin/settlements/create) and is presented with settlement page. They can select a starting and ending date for which all submissions/transactions in that timespan (excluding ones that belong to an event) will be considered. If in the past a settlement has already been made, the starting date will be one day later than the previous ending date. Otherwise, all qualifying submissions/transactions up to and including the ending date will be considered. A settlement creates invoices for all the PayingHousemates. For each of the submissions a single place-holder transaction is created, and together with the standalone transactions an invoice is created per PayingHousemate. If all invoices are created the PayingHousemates get an email containing the invoice and the request to pay up if their balance is negative.

# Notes
## Types of Users and AccountingEntities
There should be general `Housemate`s, `PayingHousemate`s (they can receive money or invoices), `OldHousemate`s (they do not have a room number attached to them, and they will not be considered for e.g. generating the cleaning schedules).

There should be a `CFO` role. 

There should be an `admin` role.

A user might have multiple roles.

A normal user is only allowed to create submissions for themselves, or some pre-determined funds (e.g. ing-bankrekening)

## Invoice
For every invoice the following information should be saved:
 - Date;
 - Payer;
 - (optional) event;
 - Payment reference;
 - Paid (if the invoice has been paid or not).

## Settlement
A settlement must create invoices for *all* housemates. This way, a housemate can never be missed when creating the invoices.