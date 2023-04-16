import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//import javax.swing.JComboBox;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener{
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontLabel;
    JSpinner fontsizSpinner;
    JButton fontcolorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu filMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    
    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("New Text Editor");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        //textArea.setPreferredSize(new Dimension(450, 450));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        fontLabel = new JLabel("Font: ");

        fontsizSpinner = new JSpinner();
        fontsizSpinner.setPreferredSize(new Dimension(50, 25));
        fontsizSpinner.setValue(20);
        fontsizSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int)fontsizSpinner.getValue()));
            
            }
            
        });

        fontcolorButton = new JButton("Color");
        fontcolorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<>(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        //-------menubar------//
        menuBar = new JMenuBar();
        filMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        
        filMenu.add(openItem);
        filMenu.add(saveItem);
        filMenu.add(exitItem);
        menuBar.add(filMenu);

        //-------menunbar----//

        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontsizSpinner);
        this.add(fontcolorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);


    }










    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if(e.getSource() == fontcolorButton){
            JColorChooser colorChooser = new JColorChooser();

            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);

            textArea.setForeground(color);
        }

        if(e.getSource() == fontBox){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        if(e.getSource() == openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if(file.isFile()){
                        while (fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+ "\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }finally{
                    fileIn.close();
                }
            }

        }
        if(e.getSource() == saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }finally{
                    fileOut.close();
                }
            }
        }

        if(e.getSource() == exitItem){
            System.exit(0);
        }
    }
    
}
