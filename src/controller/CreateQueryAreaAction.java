package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import database.Compiler;
import database.Validator;
import view.CreateView;
import view.MainFrame;
import view.MyTabPane;
import view.TableModel;

public class CreateQueryAreaAction extends AbstractAction {

	
	Compiler compiler;
	MyTabPane tabPane;
	JTextArea querySpace;
	String query;
	
	public CreateQueryAreaAction(JTextArea querySpace) {
		this.querySpace = querySpace;
	}
		
		
	
	
	
	public void actionPerformed(ActionEvent e) {
		compiler = new Compiler();
		if(Validator.Validate(querySpace.getText())) {
		query = compiler.complie(querySpace.getText());
		
		tabPane = MainFrame.getInstance().getTabPane();
		
		JTable jTable=new JTable();
		TableModel model=new TableModel();
		MainFrame.getInstance().getAppCore().setTableModel(model);
		model.setRows(MainFrame.getInstance().getAppCore().getDatabase().test(query, "", "", ""));
		MainFrame.getInstance().setJTableModel(jTable);
		JScrollPane jsc=new JScrollPane(jTable);
		
		tabPane.addTab("query Result", jsc);
		
	}

}
}