import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class ContactForm extends JFrame implements ActionListener {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel dataPanel;
    private JPanel searchPanel;

    private JButton addButton;
    private JButton searchButton;
    private JLabel searchResults;

    private JButton createRecordButton;
    private JButton deleteRecordButton;
    private JButton cancelButton;
    private JButton findButton;
    private JButton nextButton;
    private JButton prevButton;
    private JButton updateButton;

    private JTextField resultsName;
    private JTextField resultsEmail;
    private JTextField resultsNumber;
    private JTextField resultsBirthday;
    private JTextField resultsStreet;
    private JTextField resultsCity;
    private JTextField resultsState;
    private JTextField resultsZip;
    private JTextField nameField;
    private JTextField numberField;
    private JTextField birthdayField;
    private JTextField emailField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JComboBox<String> searchCombo;
    private JTextField searchField;
    private JLabel rname;
    private JLabel remail;
    private JLabel rnumber;
    private JLabel rbirthday;
    private JLabel rstreet;
    private JLabel rcity;
    private JLabel rstate;
    private JLabel rzip;
    private int recordsFound = 0; //records found in search function
    private int cr=0; //current record
    private Contact[] results = new Contact[100]; // copy of json array
    private int[] keys = new int[100]; //keys to match to json for updating or deleting

    public ContactForm() {
        //Create the GUI and set all the properties and listeners
        setTitle("Jamie's Phone Directory");
        tabbedPane = new JTabbedPane();

        // Menu setup
        mainPanel = new JPanel();
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(1);
            }
        });
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
            }
        });


        mainPanel.add(addButton);
        mainPanel.add(searchButton);

        JLabel emailLabel = new JLabel("<html><a href='mailto:thecelticflier@gmail.com'>Send Email</a></html>");
        emailLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        emailLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().mail(new URI("mailto:thecelticflier@gmail.com"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        mainPanel.add(emailLabel);
        ImageIcon icon = new ImageIcon("CF.jpg");
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setIcon(icon);
        mainPanel.add(iconLabel);
        tabbedPane.addTab("Phone Directory", mainPanel);
        //End of main panel

        //Start of panel for adding records
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        nameField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        dataPanel.add(nameField, c);

        numberField = new JTextField(13);
        c.gridx = 1;
        c.gridy = 1;
        dataPanel.add(numberField, c);

        birthdayField = new JTextField(10);
        c.gridx = 1;
        c.gridy = 2;
        dataPanel.add(birthdayField, c);

        emailField = new JTextField(25);
        c.gridx = 1;
        c.gridy = 3;
        dataPanel.add(emailField, c);

        streetField = new JTextField(25);
        c.gridx = 1;
        c.gridy = 4;
        dataPanel.add(streetField, c);

        cityField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 5;
        dataPanel.add(cityField, c);

        stateField = new JTextField(2);
        c.gridx = 1;
        c.gridy = 6;
        dataPanel.add(stateField, c);

        zipField = new JTextField(10);
        c.gridx = 1;
        c.gridy = 7;
        dataPanel.add(zipField, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        dataPanel.add(new JLabel("Name:"), c);

        c.gridy = 1;
        dataPanel.add(new JLabel("Number:"), c);

        c.gridy = 2;
        dataPanel.add(new JLabel("Birthday:"), c);

        c.gridy = 3;
        dataPanel.add(new JLabel("Email:"), c);

        c.gridy = 4;
        dataPanel.add(new JLabel("Street:"), c);

        c.gridy = 5;
        dataPanel.add(new JLabel("City:"), c);

        c.gridy = 6;
        dataPanel.add(new JLabel("State:"), c);

        c.gridy = 7;
        dataPanel.add(new JLabel("Zip:"), c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                numberField.setText("");
                birthdayField.setText("");
                emailField.setText("");
                streetField.setText("");
                cityField.setText("");
                stateField.setText("");
                zipField.setText("");
                tabbedPane.setSelectedIndex(0);
            }
        });
        dataPanel.add(cancelButton, c);

        c.gridx = 1;
        createRecordButton = new JButton("Create Record");
        createRecordButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Main.contacts[Main.contactsCount] = new Contact(
                       nameField.getText(),
                       numberField.getText(),
                       birthdayField.getText(),
                       emailField.getText(),
                       streetField.getText(),
                       cityField.getText(),
                       stateField.getText(),
                       zipField.getText());
               Main.contactsCount++;
               Main.saveContacts(Main.contacts);
               System.out.println("Record Added");
               resultsName.setText("");
               resultsNumber.setText("");
               resultsBirthday.setText("");
               resultsEmail.setText("");
               resultsStreet.setText("");
               resultsCity.setText("");
               resultsState.setText("");
               resultsZip.setText("");

           }
        });
        dataPanel.add(createRecordButton, c);
        tabbedPane.addTab("Add", dataPanel);
        //end of data entry panel


        // Search panel
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        JPanel comboAndTextPanel = new JPanel();
        searchCombo = new JComboBox<>(new String[]{"Name", "Number", "Birthday", "Email", "City", "State"});
        searchField = new JTextField(20);
        comboAndTextPanel.add(searchCombo);
        comboAndTextPanel.add(searchField);

        searchPanel.add(comboAndTextPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        deleteRecordButton = new JButton("Delete Record");
        deleteRecordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = resultsName.getText();
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this record?", "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Perform the delete operation
                if (keys[cr]  < Main.contactsCount) {
                    for (int x = keys[cr]; x < Main.contactsCount-1; x++) {
                        Main.contacts[x].setName(Main.contacts[x + 1].getName());
                        Main.contacts[x].setNumber(Main.contacts[x + 1].getNumber());
                        Main.contacts[x].setEmail(Main.contacts[x + 1].getEmail());
                        Main.contacts[x].setBirthday(Main.contacts[x + 1].getBirthday());
                        Main.contacts[x].setStreet(Main.contacts[x + 1].getStreet());
                        Main.contacts[x].setCity(Main.contacts[x + 1].getCity());
                        Main.contacts[x].setState(Main.contacts[x + 1].getState());
                        Main.contacts[x].setZip(Main.contacts[x + 1].getZip());
                    }

                Main.contactsCount--;
                Main.contacts[keys[cr]].setCity(null);
                Main.contacts[keys[cr]].setState(null);
                Main.contacts[keys[cr]].setStreet(null);
                Main.contacts[keys[cr]].setZip(null);
                Main.contacts[keys[cr]].setEmail(null);
                Main.contacts[keys[cr]].setBirthday(null);
                Main.contacts[keys[cr]].setNumber(null);
                Main.contacts[keys[cr]].setName(null);
                Main.saveContacts(Main.contacts);}
                searchResults.setText("Record for " + name + " Deleted.");
                resultsName.setText("");
                resultsNumber.setText("");
                resultsBirthday.setText("");
                resultsEmail.setText("");
                resultsStreet.setText("");
                resultsCity.setText("");
                resultsState.setText("");
                resultsZip.setText("");
                searchField.setText("");
                Main.loadContacts();
            }}
        });
        buttonsPanel.add(deleteRecordButton);
        updateButton = new JButton("Update Record");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.contacts[keys[cr]].setName(resultsName.getText());
                Main.contacts[keys[cr]].setNumber(resultsNumber.getText());
                Main.contacts[keys[cr]].setBirthday(resultsBirthday.getText());
                Main.contacts[keys[cr]].setEmail(resultsEmail.getText());
                Main.contacts[keys[cr]].setStreet(resultsStreet.getText());
                Main.contacts[keys[cr]].setCity(resultsCity.getText());
                Main.contacts[keys[cr]].setState(resultsState.getText());
                Main.contacts[keys[cr]].setZip(resultsZip.getText());
                Main.saveContacts(Main.contacts);
                Main.loadContacts();
                searchResults.setText("Record for " + resultsName.getText() + " Updated.");
            }
        });
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(8,2));

        rname = new JLabel("Name:");
        resultsName  = new JTextField(20);
        resultsPanel.add(rname);
        resultsPanel.add(resultsName);

        rnumber = new JLabel("Number:");
        resultsNumber = new JTextField(20);
        resultsPanel.add(rnumber);
        resultsPanel.add(resultsNumber);
        rbirthday = new JLabel("Birthday:");
        resultsBirthday = new JTextField(20);
        resultsPanel.add(rbirthday);
        resultsPanel.add(resultsBirthday);
        remail= new JLabel("Email:");
        resultsEmail = new JTextField(20);
        resultsPanel.add(remail);
        resultsPanel.add(resultsEmail);
        rstreet = new JLabel("Street:");
        resultsStreet = new JTextField(20);
        resultsPanel.add(rstreet);
        resultsPanel.add(resultsStreet);
        rcity = new JLabel("City:");
        resultsCity = new JTextField(20);
        resultsPanel.add(rcity);
        resultsPanel.add(resultsCity);
        rstate= new JLabel("State:");
        resultsState = new JTextField(20);
        resultsPanel.add(rstate);
        resultsPanel.add(resultsState);
        rzip = new JLabel("Zip:");
        resultsZip = new JTextField(20);
        resultsPanel.add(rzip);
        resultsPanel.add(resultsZip);
        showData(false);
        findButton = new JButton("Find Record");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String key = searchField.getText().trim().toLowerCase();
                //go through the array
                for (int i=0;i<100;i++){
                    results[i]=null;
                    keys[i]=0;
                }
                recordsFound=0;
                boolean found = false;
                int z = 0;
                int searchBy = searchCombo.getSelectedIndex();
                for (int i = 0; i < Main.contactsCount; i++) {
                    if (searchBy ==0 && Main.contacts[i].getName().toLowerCase().contains(key)) {
                        //Searching by name
                        recordsFound++;
                        found = true;
                    }else if (searchBy == 1 && Main.contacts[i].getNumber().toLowerCase().contains(key)) {
                        //Searching by phone number
                        recordsFound++;
                        found = true;
                    }else if (searchBy == 2 && Main.contacts[i].getBirthday().toLowerCase().contains(key)) {
                        //Search by birthday
                        recordsFound++;
                        found = true;
                    }else if (searchBy == 3 && Main.contacts[i].getEmail().toLowerCase().contains(key)) {
                        //search by email address
                        recordsFound++;
                        found = true;
                    }else if (searchBy == 4 && Main.contacts[i].getCity().toLowerCase().contains(key)) {
                        //search by city
                        recordsFound++;
                        found = true;
                    }else if (searchBy == 5 && Main.contacts[i].getState().toLowerCase().contains(key)) {
                        //Search by state
                        recordsFound++;
                        found = true;
                    }
                    if (found) {
                        results[z] = Main.contacts[i];
                        found = false;
                        keys[z] = i;
                        z++;
                    }else{
                        resultsName.setText("");
                        resultsNumber.setText("");
                        resultsBirthday.setText("");
                        resultsEmail.setText("");
                        resultsStreet.setText("");
                        resultsCity.setText("");
                        resultsState.setText("");
                        resultsZip.setText("");
                    }
                    searchResults.setText(recordsFound + " records found.");
                }//end for
               if (recordsFound>0){
                showData(true);
                resultsName.setText(results[0].getName());
                resultsNumber.setText(results[0].getNumber());
                resultsBirthday.setText(results[0].getBirthday());
                resultsEmail.setText(results[0].getEmail());
                resultsStreet.setText(results[0].getStreet());
                resultsCity.setText(results[0].getCity());
                resultsState.setText(results[0].getState());
                resultsZip.setText(results[0].getZip());
                cr=0;
               }
               }
        });
        prevButton = new JButton("Previous Record");
        prevButton.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent e)
            {
                if (cr>0){
                    cr--;
                    resultsName.setText(results[cr].getName());
                    resultsNumber.setText(results[cr].getNumber());
                    resultsBirthday.setText(results[cr].getBirthday());
                    resultsEmail.setText(results[cr].getEmail());
                    resultsStreet.setText(results[cr].getStreet());
                    resultsCity.setText(results[cr].getCity());
                    resultsState.setText(results[cr].getState());
                    resultsZip.setText(results[cr].getZip());

                } else {
                    searchResults.setText("This is the first result.");
                }
            }
        });
        nextButton = new JButton("Next Record");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                if (cr<results.length-1 && cr<recordsFound-1){
                    cr++;
                    resultsName.setText(results[cr].getName());
                    resultsNumber.setText(results[cr].getNumber());
                    resultsBirthday.setText(results[cr].getBirthday());
                    resultsEmail.setText(results[cr].getEmail());
                    resultsStreet.setText(results[cr].getStreet());
                    resultsCity.setText(results[cr].getCity());
                    resultsState.setText(results[cr].getState());
                    resultsZip.setText(results[cr].getZip());
                } else {
                    searchResults.setText("You're at the last record.");
                }
            }

        });
        buttonsPanel.add(findButton);
        buttonsPanel.add(prevButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(updateButton);
        //prevButton.setVisible(false);
        //nextButton.setVisible(false);
        searchPanel.add(buttonsPanel, BorderLayout.SOUTH);
        searchPanel.add(buttonsPanel, BorderLayout.CENTER);
        searchPanel.add(resultsPanel, BorderLayout.WEST);
        searchResults = new JLabel("Search Results");
        searchPanel.add(searchResults,BorderLayout.SOUTH);

        tabbedPane.addTab("Search", searchPanel);

        setContentPane(tabbedPane);


    }//End Constructor

    public void showData (boolean b) {
        rzip.setVisible(b);
        resultsZip.setVisible(b);
        rname.setVisible(b);
        resultsName.setVisible(b);
        rnumber.setVisible(b);
        resultsNumber.setVisible(b);
        rbirthday.setVisible(b);
        resultsBirthday.setVisible(b);
        remail.setVisible(b);
        resultsEmail.setVisible(b);
        rstreet.setVisible(b);
        resultsStreet.setVisible(b);
        rstate.setVisible(b);
        resultsState.setVisible(b);
        rcity.setVisible(b);
        resultsCity.setVisible(b);
        //prevButton.setVisible(b);
        //nextButton.setVisible(b);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Implement add, search, edit, delete, and exit functions
    }
}