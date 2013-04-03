package ReactorEE.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import ReactorEE.Networking.GamestateListener;
import ReactorEE.Networking.Message;
import ReactorEE.Networking.SabotageListener;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.model.Plant;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;

public class MultiplayerTest {

	/**
	 * Tests whether gamestate listener correctly deals with a plant message that has been sent over the network - setting the recived plant as the plant of the game.
	 * Also tests whether sending "anchovy kill" stops the gamestate listener. 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testGameStateRecieveing() throws IOException, InterruptedException {
		
		PlantController player2PlantController = new PlantController(new ReactorUtils());
		String localHost = InetAddress.getLocalHost().getHostAddress();
		GamestateListener gsl = new GamestateListener(localHost, player2PlantController);
		gsl.start();
		
		PlantController player1PlantController = new PlantController(new ReactorUtils());
		player1PlantController.step(10);
		
		assertTrue(!player1PlantController.getPlant().equals(player2PlantController.getPlant()));
		
		new Message().run(SocketUtil.toBypeArray(player1PlantController.getPlant()), localHost, SocketUtil.GAMESTATE_LISTENER_PORT_NO);
		Thread.sleep(20); // Wait long enough for networking to happen.
		assertTrue(player1PlantController.getPlant().equals(player2PlantController.getPlant()));
		
		new Message().run("Anchovy kill", localHost, SocketUtil.GAMESTATE_LISTENER_PORT_NO);
		Thread.sleep(10);// Wait long enough for networking to happen.
		assertTrue(!gsl.isAlive()); //tests whether the game state listener is killed by sending "anchovy kill" over the netowrk
	}
	
	/**
	 * Tests whether sending the strings "pump 1", "pump 2", "pump 3", "turbine", and "operator software" over the network are corrently recieved by player 1's game by being failed.
	 * Also tests whether sending "anchovy kill" stops the sabotage listener.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testSabatageMessageRecieving() throws IOException, InterruptedException {
		
		PlantController player1PlantController = new PlantController(new ReactorUtils());
		Plant p1plant = player1PlantController.getPlant();
		String localHost = InetAddress.getLocalHost().getHostAddress();
		SabotageListener sl = new SabotageListener("/" + localHost, player1PlantController);
		sl.start();
		
		
		assertTrue(p1plant.getFailedComponents().isEmpty());
		
		new Message().run("pump 1", localHost, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
		Thread.sleep(10); // Wait long enough for networking to happen.
		assertTrue(p1plant.getFailedComponents().contains(p1plant.getPumps().get(0)));
		
		new Message().run("pump 2", localHost, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
		Thread.sleep(10); // Wait long enough for networking to happen.
		assertTrue(p1plant.getFailedComponents().contains(p1plant.getPumps().get(1)));
		
		new Message().run("pump 3", localHost, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
		Thread.sleep(10); // Wait long enough for networking to happen.
		assertTrue(p1plant.getFailedComponents().contains(p1plant.getPumps().get(2)));
		
		new Message().run("turbine", localHost, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
		Thread.sleep(10); // Wait long enough for networking to happen.
		assertTrue(p1plant.getFailedComponents().contains(p1plant.getTurbine()));
		
		new Message().run("operator software", localHost, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
		Thread.sleep(10); // Wait long enough for networking to happen.
		assertTrue(p1plant.getFailedComponents().contains(p1plant.getOperatingSoftware()));
		
		new Message().run("anchovy kill", localHost, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
		Thread.sleep(10);// Wait long enough for networking to happen.
		assertTrue(!sl.isAlive());
	}
	

}
