## TITLE
'Scheduling App'

## PURPOSE
Provides search enhancement for Java based scheduling application. 
## AUTHOR
Caleb Wardlaw
## CONTACT
cwardl4@wgu.edu

## APP. VERSION
1.2 (Capstone)

***Changes*** - 
1. Added search feature to Customers table
2. Added search feature to Appointments table

## DATE
01/29/2023

## IDE
IntelliJ IDEA 2022.2.3 (Ultimate Edition)

## JDK
17.04

## JAVAFX
17.0.2

## HOW TO RUN
**Pre-Condition(s)**
1. Intellij (or equivalent) IDE is installed and configured to run Java based projects
2. Local machine is configured to host a MySQL DB and you have the appropriate access. The connection credentials can be changed in the JDBC.java file
3. The local database has been seeded/populated with applicable mock data, or applicable mock data is already available. If needed the mock.sql file has suggested statements

After starting the application you will be directed to a login page. Enter credentials and you will navigate to the main screen with three options: 

### Appointments
Leads to the main appointments table. From here you can search, delete, add, and update appointments. 
### Customers
Leads to the main customers table. From here you can search, delete, add, and update customers. 
### Reports
Leads to the reporting table(s). There are two standard reports and one custom report. 

**Standard Reports**

1. ___Contact Schedule___ - Show appointments by contact. Change the contact by using the drop down option.
2. ___Appointments by Month and Type___ - Displays the count of unique types and the month they are scheduled in

**Custom Reports**
1. ___Customers by Country___ - Displays the customer count for a given country.


## ADDITIONAL REPORT
The ___Customers by Country___ report can be found in the 'Reports' screen. This report displays the customer count for a given country.

## MYSQL CONNECTOR
mysql-connector-java:8.0.30

