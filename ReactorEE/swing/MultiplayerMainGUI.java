package ReactorEE.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ReactorEE.Networking.Message;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;

public class MultiplayerMainGUI extends MainGUI{

	private String saboteurIP;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					MultiplayerMainGUI window = new MultiplayerMainGUI(new PlantController(new ReactorUtils()), "localhost");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public MultiplayerMainGUI(final PlantController plantController, String IP) {
		super(plantController);
		this.saboteurIP = IP;
		btnRepairPump1.removeActionListener(btnRepairPump1.getActionListeners()[0]);
		btnRepairPump1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!");
                try {
					new Message().run("pump1", saboteurIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();}
            }
        });
		
		btnRepairPump2.removeActionListener(btnRepairPump2.getActionListeners()[0]);
		btnRepairPump2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!2");
            	try {
					new Message().run("pump2", saboteurIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();}
            }
        });
		
		btnRepairPump3.removeActionListener(btnRepairPump3.getActionListeners()[0]);
		btnRepairPump3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!3");
            	try {
					new Message().run("pump3", saboteurIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();}
            }
        });
		
		btnRepairTurbine.removeActionListener(btnRepairTurbine.getActionListeners()[0]);
		btnRepairTurbine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!T");
            	try {
					new Message().run("turbine", saboteurIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();}
            }
        });
		
		btnRepairOperatingSoftware.removeActionListener(btnRepairOperatingSoftware.getActionListeners()[0]);
		btnRepairOperatingSoftware.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.print("Hello World!OS");
            	try {
					new Message().run("operator software", saboteurIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();}
            }
        });
		
		btnStep.setEnabled(false);
	}
	
	@Override
	public void endGameHandler(){
		@SuppressWarnings("unused")
		EndGameGUI endGameGui = new EndGameGUI(this, plantController.getUIData().getScore());
	}

	
}

