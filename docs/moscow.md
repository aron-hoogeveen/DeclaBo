# MoSCoW Method Requirements

## Functional Requirements
### Must-Have
 - Users can have multiple roles;
 - There shall be a `ROLE_ADMIN`;
 - There shall be a `ROLE_CFO`;
 - There shall be a `ROLE_USER`;
 - Users with role `ROLE_ADMIN` shall be able to perform all possible user actions (this is implicit for all actions stated below);
 - Users shall be required to log in;
 - Users shall have a (valid) e-mail address;
 - Users shall be able to create invoice submissions;
 - There shall be Funds, which will contain the following information:
   - A name;
   - A balance;
 - Funds' balances will be altered by means of debiting and crediting;
 - There shall be EventFunds which are funds that are specific to a certain Event;
 - There shall be FundUsers which are users with a fund;
 - Users with role `ROLE_CFO` shall be able to create an event;
 - An event shall contain the following information:
   - The starting date of the event;
   - The name of the event;
   - A description of the event;
   - A list of `fundUsers` who will attend the event;
   - A list of EventFunds that are related to the event;
 - FundUsers with role `ROLE_CFO`, or `ROLE_USER` may submit invoice submissions;
 - Each Submission shall only consider a single purchase;
 - An invoice *submission* shall contain the following information:
   - The fund of the entity or person that/who paid for the purchase;
   - The date of the purchase;
   - A short description of the purchase;
   - A boolean indicating if the submission is already processed;
   - A boolean indicating if the submission is already settled;
   - A set of Transactions that belong to this submission (only applicable after the submission is processed);
   - The FundUser who created the submission;
   - The creation date;
   - The total amount that the paying party paid for this purchase;
   - (optional) The event for which the purchase was;
   - (optional) Attachments (.jpg, .png, .pdf) of the original receipts;
   - (optional) Any remarks on the submission;
 - Users shall be able to view their submitted submissions;
 - Users shall be able to delete unprocessed submissions;
 - The date of a purchase must be *after* the date of the last settlement;
 - Users with role `ROLE_CFO` shall be able to create a settlement for all Transactions that do not belong to an Event;
 - Users with role `ROLE_CFO` shall be able to create a settlement for all Transactions that belong to a specific Event;
 - Multiple Transactions that belong to a single Submission shall be listed as a single Transaction on the settlement;
 - Users with the role `ROLE_CFO` will be able to create new Funds;
 - Users with the role `ROLE_CFO` will be able to create new FundUsers;
 - Users shall be able to change their password;
 - Users with role `ROLE_ADMIN` will be able to block other users from logging in;
 - Users with role `ROLE_ADMIN` will always be able to log in;

### Should-Have
 - Users shall be able to look up at least the past 4 settlements they attended;
 - Users shall be able to download a pdf of the settlements they are able to look up;
 - Users with role `ROLE_CFO` will be able to create bulk transactions. Per user a weight can be set indicating how many times the user participates in the to be divided amount;
 - The backend shall be able to generate multiple transactions for a single 'bierlijst' from the following information:
   - A list of 'streepjes' per person;
   - The price of one 'streepje';
   - A date to use for the transactions;

### Could-Have
 - Users' e-mail address must be validated;
 - User accounts with unvalidated e-mail addresses shall not be able to log in;

### Won't Have
 - .

## Nonfunctional Requirements
 - The system shall have an api-based backend;
 - The backend shall be written in Java version 15;
 - The system shall have a web-based front-end;
 - The front-end shall make use of the secure https protocol;
 - For the backend the Spring framework shall be used (Spring Boot, Spring Web, Spring Security);
 - The system shall use UTF-8 as encoding (for source code as well as produced webpages or other documents such as invoices);