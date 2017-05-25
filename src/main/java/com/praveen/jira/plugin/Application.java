package com.praveen.jira.plugin;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.PrintStream;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Application {
	
	public static StringBuilder consoleText = new StringBuilder();
	
	private JiraApiService jiraService;

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
    	jiraService = new JiraApiService();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(null);
                
                addComponentsToPane(frame.getContentPane());
                frame.pack();
                frame.setSize( 500, 300);
                
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
                Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
                int x = (int) rect.getMaxX() - frame.getWidth();
                int y = (int) rect.getMaxY() - frame.getHeight();
                frame.setBounds(x, y, 500, 300);
                
                JTextField textField = new JTextField();
                
                frame.add(textField);
                
                
                frame.setVisible(true);
            }
        });
    }
    
    
    public void addComponentsToPane(Container pane) {
        pane.setLayout(null);
        
        
        JLabel title = new JLabel("Jira Tracking plugin");
        title.setFont(title.getFont().deriveFont(30f));
 
 
        pane.add(title);
        final JTextField textField = new JTextField();
        
        textField.setBounds(10,10,470,40);
        
        
        int condition = JComponent.WHEN_FOCUSED;
        InputMap iMap = textField.getInputMap(condition);
        ActionMap aMap = textField.getActionMap();
        
        
        String enter = "enter";
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        aMap.put(enter, new AbstractAction() {

           @Override
           public void actionPerformed(ActionEvent event) {
        	   System.out.println("Entered command .... " + textField.getText());
        	   jiraService.callJiraApi(textField.getText());
              
           }
        });
        pane.add(textField);
        
        
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, "" );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
        
        JScrollPane scroll = new JScrollPane( ta ) ;
        scroll.setBounds(10,60,470,200);
        
        pane.add(scroll);
 
    }

}