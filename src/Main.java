/* ***************************************************************
 **                                                              **
 ** Class Name:     Main                                         **
 ** Purpose:        Main module for Phone Directory Project      **
 ** Method(s):       {}                                          **
 ** Property(ies):   {}                                          **
 **                                                              **
 **Author:           James Robinson                              **
 **Written:           1/27/23                                    **
 **                                                              **
 *****************************************************************/


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;

public class Main {
// Declare class variables for text menu operations

    static Contact[] contacts = new Contact[100];
   static int contactsCount = 0;

   //Run main menu
    public static void main(String[] args) {
        //Load contacts from file
        //Launch main code and open menu
        boolean showMenu = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Phone Directory Project!");
        System.out.println("Would you like to use text or gui?");
        System.out.println("1. Text");
        System.out.println("2. GUI");
        System.out.println("\nSelection: ");
        int choice = scan.nextInt();
        loadContacts();
        if (contactsCount==1){
            saveContacts(contacts);
        }
        switch (choice) {
            case 1:
                //Text menu
                showMenu = true;
                break;
            case 2:
                //GUI menu
                showMenu = false;
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        if(!showMenu) {
          //  runGui();
            ContactForm form = new ContactForm();
            form.setTitle("Robinson's Phone Directory");
            form.setSize(640, 800);
            form.setLocationRelativeTo(null);
            form.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            form.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int confirm = JOptionPane.showOptionDialog(form, "Are you sure you want to exit?",
                            "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }    //If user clicks yes, exit
                }
            });
            form.setVisible(true);
        }else{
            while (showMenu) {
                System.out.println("Current number of contacts: \033[33m" + contactsCount);
                System.out.println("\033[0mMenu:");
                System.out.println("\033[32m1. Add contact");
                System.out.println("\033[35m2. Edit contact");
                System.out.println("\033[31m3. Delete contact");
                System.out.println("\033[36m4. Search contacts");
                System.out.println("\033[33m5. Exit");
                System.out.println();
                System.out.print("\033[0mSelect: ");
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        addContact();
                          break;
                    case 2:
                        editContact();
                        break;
                    case 3:
                        System.out.println(contactsCount);
                        deleteContact();
                        break;
                    case 4:
                        findContact();
                        break;
                    case 5:
                        showMenu = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }

            }
        }
        //Save contacts to disk
        saveContacts(contacts);
        System.out.println("Thank you for using Robinson Software");
        System.out.println("For questions or comments email:");
        System.out.println("\033[36mthecelticflier@gmail.com");
    }//End of main method


    public static void saveContacts(Contact[] contacts) {
        //Method to save the contacts array to a file
        //Creates a json formatted file
        try (FileWriter file = new FileWriter("contacts.json",false )) {
            JSONArray jsonArray = new JSONArray();
            int x = 0;
            for (Contact c : contacts) {
                if (x < contactsCount) {
                    x++;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", c.getName());
                    jsonObject.put("number", c.getNumber());
                    jsonObject.put("street", c.getStreet());
                    jsonObject.put("city", c.getCity());
                    jsonObject.put("state", c.getState());
                    jsonObject.put("zip", c.getZip());
                    jsonObject.put("email", c.getEmail());
                    jsonObject.put("birthday", c.getBirthday());
                    jsonArray.put(jsonObject);
                }
            }
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  System.out.println(contactsCount + " contacts saved to contacts.json file");
    }//End of saveContacts

    public static void loadContacts(){
        //Method to load the contacts array from a file
        //Also sets a global variable for the number of contacts in the array
        contactsCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("contacts.json"));
            JSONArray jsonArray = new JSONArray(reader.readLine());
            reader.close();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    contacts[i] = new Contact(jsonArray.getJSONObject(i).getString("name"),
                            jsonArray.getJSONObject(i).getString("number"),
                            jsonArray.getJSONObject(i).getString("email"),
                            jsonArray.getJSONObject(i).getString("birthday"),
                            jsonArray.getJSONObject(i).getString("street"),
                            jsonArray.getJSONObject(i).getString("city"),
                            jsonArray.getJSONObject(i).getString("state"),
                            jsonArray.getJSONObject(i).getString("zip"));
                    contactsCount++;
                }//end for
            } else {
                // empty file
                System.out.println("File is empty or not valid JSON");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        } catch (JSONException e) {
            System.out.println("Error parsing JSON");
        }
      //  System.out.println(contactsCount + " contacts loaded from contacts.json file");
    }//end loadContacts
    private static String capitalize(String s) {
        String name = "";
        int i = s.indexOf(" ");
        if (s.indexOf(" ") > -1){
            name = s.substring(0,i+1) + s.substring(i+1,i+2).toUpperCase() + s.substring(i+2);
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }else{
            name = s.substring(0,1).toUpperCase() + s.substring(1);
        }
        return name;
    }
    private static void addContact(){
        Scanner scan = new Scanner(System.in);
        if (contactsCount < 100) {
            System.out.print("Enter name: ");
            String name = scan.nextLine().trim();
            name=capitalize(name);
            System.out.print("Enter number: (xxx)xxx-xxxx ");
            String number = scan.nextLine().trim();
            System.out.println("Enter email: ");
            String email = scan.nextLine().trim();
            System.out.println("Enter Birthday: ");
            String birthday = scan.nextLine().trim();
            System.out.println("Enter Street Address: ");
            String street = scan.nextLine().trim();
            System.out.println("Enter City: ");
            String city = scan.nextLine().trim();
            System.out.println("Enter State: ");
            String state = scan.nextLine().trim();
            System.out.println("Enter Zip: ");
            String zip = scan.nextLine().trim();

            //add entry to records array
            contacts[contactsCount] = new Contact(name, number, email, birthday, street, city, state, zip);
            contactsCount++;
        } else {
            System.out.println("Database is full.");
        }

    }//End of addContact

    private static void editContact(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter name to look up: ");
        String key = scan.nextLine().trim().toLowerCase();
        int numMatches = 0;
        int position = 0;
        //this is the for loop to go through the database
        for (int i = 0; i < contactsCount; i++) {
            String name = contacts[i].getName().toLowerCase();
            //if we find the match, print out the matching records
            if (name.startsWith(key)) {
                System.out.println(contacts[i].getName() + " " + contacts[i].getNumber());
                numMatches++;
                position = i;
            }
        }

        //if only one name matches, prompt user for new name and number
        //if user presses enter when asked for a new name,
        //the name is not changed. similarly if the user presses enter
        //when asked for a new number, the number is not changed.
        if (numMatches == 1) {
            System.out.print("\nEnter new name: ");
            String name = scan.nextLine().trim();
            //update the name or number based on the user input
            if (name.length() > 0)
                contacts[position].setName(name);
            System.out.print("Enter new phone number: ");
            String number = scan.nextLine().trim();
            if (number.length() > 0)
                contacts[position].setNumber(number);
        } else
            System.out.println("No unique matching record; " + "try again");
    } //End of editContact()

    private static void deleteContact(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter name to look up: ");
        String key = scan.nextLine().trim().toLowerCase();
        int numMatches = 0;
        int position = 0;
        //this is the for loop to go through the database
        for (int i = 0; i < contactsCount; i++) {
            String name = contacts[i].getName().toLowerCase();
            //if we find the match, print out the matching records
            if (name.startsWith(key)) {
                System.out.println(contacts[i].getName() + " " + contacts[i].getNumber());
                numMatches++;
                position = i;
            }
        }
        //If only one name matches, erase record
        if (numMatches == 1) {
            if (position < contactsCount) {
                for (int x = position; x < contactsCount-1; x++) {
                    contacts[x].setName(contacts[x + 1].getName());
                    contacts[x].setNumber(contacts[x + 1].getNumber());
                    contacts[x].setEmail(contacts[x + 1].getEmail());
                    contacts[x].setBirthday(contacts[x + 1].getBirthday());
                    contacts[x].setStreet(contacts[x + 1].getStreet());
                    contacts[x].setCity(contacts[x + 1].getCity());
                    contacts[x].setState(contacts[x + 1].getState());
                    contacts[x].setZip(contacts[x + 1].getZip());
                }
            }
                contactsCount--;
                contacts[position].setCity(null);
                contacts[position].setState(null);
                contacts[position].setStreet(null);
                contacts[position].setZip(null);
                contacts[position].setEmail(null);
                contacts[position].setBirthday(null);
                contacts[position].setNumber(null);
                contacts[position].setName(null);
        } else
            System.out.println("No unique matching record; " + "try again");
    } //End of deleteContact()

    private static void findContact(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter name to look up: ");
        String key = scan.nextLine().trim().toLowerCase();
        //go through the array
        for (int i = 0; i < contactsCount; i++) {
            String name = contacts[i].getName().toLowerCase();
            //find the match
            if (name.startsWith(key)){
                System.out.println("\033[31;1;4m "+contacts[i].getName() + " " + contacts[i].getNumber());
            System.out.println("\033[35mAddress: " + contacts[i].getStreet() + "\n" +
                    contacts[i].getCity() + " " + contacts[i].getState() + ", " + contacts[i].getZip());
            System.out.println("\033[34mEmail: " + contacts[i].getEmail());
            System.out.println("\033[32mBirthday:\033[0m " + contacts[i].getBirthday());
            }
        }

    }//end of findContact()
}//end of class
