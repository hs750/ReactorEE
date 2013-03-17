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
		
	}
	
}

