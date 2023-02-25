package com.embaradj.velma;

import com.embaradj.velma.models.DataModel;
import com.embaradj.velma.models.Hve;
import com.embaradj.velma.models.Job;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observer;

import static javax.swing.SwingUtilities.isEventDispatchThread;

/**
 * Represents the view of the MVC pattern
 */
public class MainForm extends JFrame {

    private DataModel model;
    protected JPanel panel1;
    private JButton srcHveBtn;
    private JButton srcJobsBtn;
    private JButton settingsBtn;
    private JButton analyseBtn;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JButton quitBtn;
    private JButton helpBtn;
    private JScrollPane scrollPaneLeft;
    private JScrollPane scrollPaneRight;
    private DefaultListModel<String> listModel1 = new DefaultListModel<>(); // Used for the JLists
    private DefaultListModel<String> listModel2 = new DefaultListModel<>(); // Used for the JLists
    private JList list1;
    private JList list2;

    private ActionListener controller;

    public MainForm(Controller controller, DataModel model) {
        this.controller = controller;
        this.model = model;
        setTitle("HVE Matcher alpha");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        System.out.println("Running on EDT? " + isEventDispatchThread());
        System.out.println(Thread.currentThread().getName());

        setListener();

        controller.setView(this);
        addActionListeners();

        list1.setModel(listModel1);
        list2.setModel(listModel2);
    }

    protected void addController(ActionListener controller) {
        this.controller = controller;
    }

    /**
     * Let the controller setup listeners for the components
     */
    protected void addActionListeners() {
        srcHveBtn.addActionListener(controller);
        srcJobsBtn.addActionListener(controller);
        analyseBtn.addActionListener(controller);
        settingsBtn.addActionListener(controller);
        quitBtn.addActionListener(controller);
        helpBtn.addActionListener(controller);
    }

    private void setListener() {
        model.addListener(e -> {
            if (e.getPropertyName().equals("hve")) {
                String desc = ((Hve) e.getNewValue()).getDescription();

                // Update the JList on the EDT thread
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        listModel1.addElement(desc);
                    }
                });

            } else if (e.getPropertyName().equals("job")){
                String title = ( (Job) e.getNewValue()).getTitle();

                // Update the JList on the EDT thread
                SwingUtilities.invokeLater(new Runnable() {
                   @Override
                   public void run() {
                       listModel2.addElement(title);
                   }
               });

            }
        });
    }

}
