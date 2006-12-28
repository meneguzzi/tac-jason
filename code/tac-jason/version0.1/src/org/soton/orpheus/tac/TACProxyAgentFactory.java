package org.soton.orpheus.tac;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.soton.orpheus.Orpheus;
import org.soton.orpheus.jason.OrpheusAgentArch;

import se.sics.tac.aw.AgentImpl;
import se.sics.tac.aw.TACAgent;
import se.sics.tac.util.ArgEnumerator;

public class TACProxyAgentFactory {
	public static final Logger logger = Logger.getLogger(TACProxyAgent.class.getName());
	
	protected static TACProxyAgentFactory agentFactorySingleton = null;
	
	public static TACProxyAgentFactory getTACProxyAgentFactory() {
		if(agentFactorySingleton == null) {
			agentFactorySingleton = new TACProxyAgentFactory();
		}
		return agentFactorySingleton;
	}
	
	/**
	 * Creates a new TAC Proxy Agent for interfacing with Jason using a
	 * default config file.
	 * @param agentArch    A reference to an OrpheusAgentArchitecture for 
	 * 					   interfacing with Jason 
	 */
	public TACProxyAgent createTACProxyAgent(OrpheusAgentArch agentArch) {
		try {
			return this.createTACProxyAgent(agentArch, "/agent.conf");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates a new TAC Proxy Agent for interfacing with Jason using the
	 * specified config file.
	 * @param agentArch    A reference to an OrpheusAgentArchitecture for 
	 * 					   interfacing with Jason 
	 * @param configFile   A filename pointing to a properties file with 
	 *                     TAC Agent configuration.
	 * @throws URISyntaxException If the supplied configuration file is 
	 * 						not a valid URL
	 */
	public TACProxyAgent createTACProxyAgent(OrpheusAgentArch agentArch, String configFile) throws URISyntaxException {
		URL url = TACProxyAgentFactory.class.getResource(configFile);
		return this.createTACProxyAgent(agentArch, url);
	}
	
	/**
	 * Creates a new TAC Proxy Agent for interfacing with Jason using the
	 * specified config file.
	 * @param agentArch    A reference to an OrpheusAgentArchitecture for 
	 * 					   interfacing with Jason 
	 * @param configFile   A filename pointing to a properties file with 
	 *                     TAC Agent configuration.
	 * @throws URISyntaxException If the supplied configuration file is 
	 * 						not a valid URI
	 */
	public TACProxyAgent createTACProxyAgent(OrpheusAgentArch agentArch, URL configFile) throws URISyntaxException {
		File configFP = new File(configFile.toURI());
		Properties config = getConfig(configFP);
		if (config == null) {
			config = new Properties();
		}
		String agentClass =  config.getProperty("agentimpl");
		
		//Try to create the agent
		TACProxyAgent agent;
		try {
			agent = (TACProxyAgent) Class.forName(agentClass).newInstance();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "could not create AgentImpl object of class "
					+ agentClass, e);
			Orpheus.getInstance().fatalError("no agent implementation available");
			return null;
		}
		
		agent.init(agentArch);

		boolean gui = !"true".equals(config.getProperty("nogui"));

		TACAgent agentWare = new TACAgent((AgentImpl) agent, new ArgEnumerator(new String[] {}, ""), config);
		if (gui) {
			agentWare.showGUI();
		}
		return agent;
	}
	
	protected static Properties getConfig(File configFile) {
		try {
			if (configFile.exists()) {
				InputStream input = new BufferedInputStream(
						new FileInputStream(configFile));
				try {
					Properties p = new Properties();
					p.load(input);
					p.setProperty("CONFIG_FILE", configFile.getName());
					return p;
				} finally {
					input.close();
				}
			}
		} catch (Exception e) {
			System.err.println("could not read config file '" + configFile
					+ "':");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
