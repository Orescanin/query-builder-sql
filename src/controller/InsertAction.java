package controller;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import view.MainFrame;
import view.MyPopUp;
import view.TableModel;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import resource.DBNodeComposite;



public class InsertAction extends AbstractAction {
	
	private MyPopUp prozor;
	private DBNodeComposite selectedNode;

	  public InsertAction(MyPopUp prozor) {
		this.prozor=prozor;
		prozor.setLayout(new FlowLayout());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	
	selectedNode=(DBNodeComposite) MainFrame.getInstance().getStablo().getLastSelectedPathComponent();
	JTable jTable=new JTable();
	TableModel model=new TableModel();
	MainFrame.getInstance().getAppCore().setTableModel(model);
	model.setRows(MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(selectedNode.getName()));
	MainFrame.getInstance().setJTableModel(jTable);
	
	int brojKolona=MainFrame.getInstance().getjTable().getColumnCount();
	
		prozor=new MyPopUp();
	
	prozor.setLayout(new FlowLayout());
	
	for(int i=0;i<brojKolona;i++)
	{
		JTextField textField=new JTextField(i+1+".kolona");
		textField.setPreferredSize(new Dimension(200,200));
		prozor.add(textField);
	}
		prozor.setVisible(true);
		
	}

	
}
