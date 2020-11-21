package ui;

import model.Event;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//represents a scheduling app that keeps track of the events added to it

//classes implementing ActionListener taken from ListDemo launcher
//https://docs.oracle.com/javase/tutorial/uiswing/components/list.html

public class SchedulerGUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel = new DefaultListModel();
    private static final String addString = "Add Event";
    private static final String removeString = "Remove Event";
    private static final String saveString = "Save Schedule";
    private static final String loadString = "Load Schedule";

    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;

    private JTextField eventName;
    private JTextField eventTime;

    private Schedule sch = new Schedule("schedule");
    private static final String JSON_STORE = "./data/schedule.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public SchedulerGUI() throws IOException {
        super(new BorderLayout());
        listModel.addElement("Scheduled Events:");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        makeList();
        JScrollPane listScrollPane = new JScrollPane(list);
        AddListener addListener = makeButtons();
        makeTextFields(addListener);
        JPanel buttonPane = makeJPanel();

        BufferedImage myPicture;
        myPicture = ImageIO.read(new File("./data/project.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));

        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        add(picLabel,BorderLayout.NORTH);
    }

    //MODIFIES: this
    //EFFECTS: sets up JList of events to add to schedule
    public void makeList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(12);
    }

    //MODIFIES: this
    //EFFECTS: creates the text fields for entering event names and times
    public void makeTextFields(AddListener addListener) {
        eventName = new JTextField(18);
        eventName.addActionListener(addListener);
        eventName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();

        eventTime = new JTextField(18);
        eventTime.addActionListener(addListener);
        eventTime.getDocument().addDocumentListener(addListener);
        String time = listModel.getElementAt(
                list.getSelectedIndex()).toString();

        eventName.setText("enter event name!");
        eventTime.setText("enter event time! (HH:MM:SS:)");
    }

    //MODIFIES: this
    //EFFECTS: creates all the buttons and sets up their effects
    public AddListener makeButtons() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
        return addListener;
    }

    //MODIFIES: this
    //EFFECTS: sets up the button panel and adds all buttons and text fields to it
    public JPanel makeJPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(eventName);
        buttonPane.add(eventTime);
        buttonPane.add(addButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        return buttonPane;
    }

    class RemoveListener implements ActionListener {

        //EFFECTS: action of removeButton; removes the selected event from the schedule
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);

            if (index >= 0) {
                sch.removeEvent(index - 1);
            }

            int size = listModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //EFFECTS: action of addButton; adds the event at the selected spot in the schedule
        // in the form "Event at Time"
        public void actionPerformed(ActionEvent e) {
            String name = eventName.getText();
            String time = eventTime.getText();
            String toSched = eventName.getText();

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listModel.insertElementAt(name + " at " + time, index);
            eventName.requestFocusInWindow();
            eventName.setText("");
            eventTime.setText("");
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            Event event = new Event(toSched,time);
            sch.schedule(event);
        }

        //MODIFIES: this
        //EFFECTS: disables addButton if a text field is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        //Required by implementing DocumentListener.
        //MODIFIES: this
        //EFFECTS: enables addButton
        public void insertUpdate(DocumentEvent e) {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        //Required by implementing DocumentListener.
        //MODIFIES: this
        //EFFECTS: calls handleEmptyTextField method to change state of addButton
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by implementing DocumentListener.
        //MODIFIES: this
        //EFFECTS: updates state of addButton
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                if (!alreadyEnabled) {
                    button.setEnabled(true);
                }
            }
        }
    }

    class SaveListener implements ActionListener {
        //EFFECTS: saves the schedule to file
        public void actionPerformed(ActionEvent e) {
            save();
        }
    }

    class LoadListener implements ActionListener {
        //MODIFIES: this
        //EFFECTS: loads the saved schedule from file
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            load();

            for (int i = 0; i < sch.length(); i++) {
                if (sch.get(i) != null) {
                    listModel.insertElementAt(sch.get(i), index);
                } else {
                    listModel.insertElementAt("There is no data saved on file.", index);
                }
                index = index + 1;
            }
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    //Required by implementing ListSelectionListener.
    //EFFECTS: disables removeButton if no valid index/slot in schedule is selected
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);

            } else {
                removeButton.setEnabled(true);
            }
        }
    }

    //EFFECTS: creates and shows schedule GUI
    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new SchedulerGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: saves the schedule to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(sch);
            jsonWriter.close();
            System.out.println("Saved " + sch.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads schedule from file
    private void load() {
        try {
            sch = jsonReader.read();
            System.out.println("Loaded " + sch.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: runs SchedulerGUI
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}