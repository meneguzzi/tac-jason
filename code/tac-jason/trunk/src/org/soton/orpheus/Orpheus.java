package org.soton.orpheus;

import jason.JasonException;

import java.util.logging.Logger;

import org.soton.orpheus.jason.OrpheusAgentArch;
import org.soton.orpheus.jason.monitor.JasonMonitorWindow;

/**
 * The main point of entry into the Jason TAC proxy agent.
 * @author frm05r
 *
 */
public class Orpheus {
	
	protected static Logger logger = Logger.getLogger(Orpheus.class.getName());
	
	protected static Orpheus orpheusSingleton = null;
	
	public static Orpheus getInstance() {
		if(orpheusSingleton == null) {
			orpheusSingleton = new Orpheus();
		}
		
		return orpheusSingleton;
	}
	
	private Orpheus() {
		
	}
	
	/**
	 * Method for handling fatal errors in Orpheus execution. Do a global cleanup 
	 * and aborts the program displaying the specified message.
	 * 
	 * @param message The message to be displayed as the program aborts.
	 */
	public void fatalError(String message) {
		logger.severe("Aborting Orpheus");
		logger.severe(message);
		System.exit(1);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		OrpheusAgentArch agentArch = null;
		try {
			agentArch = new OrpheusAgentArch("./src/tac-agent.asl");
			JasonMonitorWindow monitorWindow = new JasonMonitorWindow();
			monitorWindow.setAgent(agentArch.getTS().getAg());
			monitorWindow.setVisible(true);
			Thread agentThread = new Thread(agentArch);
			agentThread.setDaemon(true);
			agentThread.start();
			//agentArch.run();
			//agentArch.getTS().reasoningCycle();
			/*Thread.sleep(5000);
			System.out.println(agentArch.getTS().getAg().getBS());*/
		} catch (JasonException e) {
			logger.severe(e.getMessage());
		}
	}
}
