package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import controller.CreateQueryAreaAction;
import controller.InsertAction;

public class MyToolBar extends JToolBar{
	
	private JButton create;
	
	public MyToolBar() {
		super(SwingConstants.HORIZONTAL);
		create=new JButton("Create Query");
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateView QueryArea= new CreateView();
				
			}
		});
		this.add(create);
		setFloatable(false);
		
	}

}
