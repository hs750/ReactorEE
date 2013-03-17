package ReactorEE.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;

public class MultiplayerMainGUI extends MainGUI{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MultiplayerMainGUI window = new MultiplayerMainGUI(new PlantController(new ReactorUtils()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public MultiplayerMainGUI(final PlantController plantController) {
		super(plantController);
		btnRepairPump1.removeActionListener(btnRepairPump1.getActionListeners()[0]);
		btnRepairPump1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!");
                if(!plantController.getUIData().getPumps().get(0).isOperational() && controlButtonsEnabled)
                {
                    
                    updateGUI();
                    
                } 
            }
        });
		
		btnRepairPump2.removeActionListener(btnRepairPump2.getActionListeners()[0]);
		btnRepairPump2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!2");
                if(!plantController.getUIData().getPumps().get(0).isOperational() && controlButtonsEnabled)
                {
                    
                    updateGUI();
                    
                } 
            }
        });
		
		btnRepairPump3.removeActionListener(btnRepairPump3.getActionListeners()[0]);
		btnRepairPump3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!3");
                if(!plantController.getUIData().getPumps().get(0).isOperational() && controlButtonsEnabled)
                {
                    
                    updateGUI();
                    
                } 
            }
        });
		
		btnRepairTurbine.removeActionListener(btnRepairTurbine.getActionListeners()[0]);
		btnRepairTurbine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!T");
                if(!plantController.getUIData().getPumps().get(0).isOperational() && controlButtonsEnabled)
                {
                    
                    updateGUI();
                    
                } 
            }
        });
		
		btnRepairOperatingSoftware.removeActionListener(btnRepairOperatingSoftware.getActionListeners()[0]);
		btnRepairOperatingSoftware.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!OS");
                if(!plantController.getUIData().getPumps().get(0).isOperational() && controlButtonsEnabled)
                {
                    
                    updateGUI();
                    
                } 
            }
        });
	}
	
}

