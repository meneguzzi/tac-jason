package org.soton.orpheus.tac;

import java.util.List;

import org.soton.orpheus.jason.OrpheusAgentArch;

public interface TACProxyAgent {
	public static final String usage = "TAC Proxy Options\n"
				+ "where options include:\n"
				+ "    -config <configfile>      set the config file to use\n"
				+ "    -agentimpl <className>    set the agent implementation\n"
				+ "    -agent <agentname>        set the agent name\n"
				+ "    -password <password>      set the agent password\n"
				+ "    -host <host>              set the TAC Info Server host\n"
				+ "    -port <port>              set the TAC Info Server port\n"
				// + " -gameType <type> set the game type to play\n"
				+ "    -exitAfterGames <games>   set the number of games to play\n"
				+ "    -connection <className>   set the TAC connection handler\n"
				+ "    -consoleLogLevel <level>  set the console log level\n"
				+ "    -fileLogLevel <level>     set the file log level\n"
				+ "    -logPrefix <prefix>       set the prefix to log files\n"
				+ "    -nogui                    do not show agent gui\n"
				+ "    -h                        show this help message\n";
	
	public void init(OrpheusAgentArch agentArch);
	
	/**
	 * Returns a list of events that transpired since the last invocation of this method.
	 * @return
	 */
	public List<TACEvent> getEvents();
	
	/**
	 * Adds an event to the list of outstanding events.
	 * @param event
	 */
	public void addEvent(TACEvent event);
	
	/**
	 * Submits a bid to the TAC server.
	 * @param auction
	 * @param allocation
	 * @param price
	 */
	public void submitBid(int auction, int allocation, float price);
}
