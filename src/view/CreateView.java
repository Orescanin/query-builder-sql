package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import controller.CreateQueryAreaAction;
import database.Compiler;
import database.MSSQLrepository;
import database.Validator;

public class CreateView extends JFrame{
	
	private JScrollPane scrollPane;
	private JTextArea querySpace;
	private JButton validate;
	private String query;
	private MyTabPane tabPane;
	private MyTabPane tabPane2;
	public CreateView() {
		
		querySpace = new JTextArea("Query space");
		scrollPane= new JScrollPane();
		
		validate=new JButton("Create Query");
		validate.addActionListener(new CreateQueryAreaAction(querySpace));
	
			
			
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension d=kit.getScreenSize();
		int sirina=d.width;
		int visina=d.height;
		setSize(sirina/4, visina/4);
		setTitle("Make a query");
		setLocationRelativeTo(null);
	    getContentPane().setBackground(Color.LIGHT_GRAY);
	    JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollPane.add(querySpace), BorderLayout.CENTER);
        centerPanel.add(validate,BorderLayout.SOUTH);
		this.add(centerPanel);
		setVisible(true);
		
		
		
		
	}
	

}
