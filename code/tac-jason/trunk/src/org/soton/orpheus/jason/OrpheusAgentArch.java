/**
 * 
 */
package org.soton.orpheus.jason;

import jason.JasonException;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;
import jason.asSemantics.Intention;
import jason.asSemantics.Message;
import jason.asSyntax.BeliefBase;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.runtime.Settings;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.soton.orpheus.jason.act.OrpheusActionExecutive;
import org.soton.orpheus.jason.act.OrpheusActionExecutiveImpl;
import org.soton.orpheus.tac.TACEvent;
import org.soton.orpheus.tac.TACProxyAgent;
import org.soton.orpheus.tac.TACProxyAgentFactory;

/**
 * An extension to the original Jason architecture AgArch to serve as a proxy
 * between a TAC agent client and the Jason reasoning engine.
 * @author frm05r
 *
 */
public class OrpheusAgentArch extends AgArch implements Runnable {
	
	protected static Logger logger = Logger.getLogger(OrpheusAgentArch.class.getName());
	protected TACProxyAgent proxyAgent = null;
	protected boolean running = false;
	
	protected OrpheusActionExecutive<OrpheusAgentArch> actionExecutive = null;
	
	public OrpheusAgentArch(String asSource) throws JasonException {
		super();
		this.actionExecutive = new OrpheusActionExecutiveImpl();
		this.initAg("jason.asSemantics.Agent", asSource, new Settings());
	}
	
	@Override
	public void initAg(String agClass, String asSrc, Settings stts) throws JasonException {
		super.initAg(agClass, asSrc, stts);
		try {
			//this.setArchInfraTier(this);
			TACProxyAgentFactory proxyAgentFactory = TACProxyAgentFactory.getTACProxyAgentFactory();
			URL config = OrpheusAgentArch.class.getResource("/agent.conf");
			proxyAgent = proxyAgentFactory.createTACProxyAgent(this, config);
		} catch (URISyntaxException e) {
			throw new JasonException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#perceive()
	 */
	public List perceive() {
		//TODO Modify this method to cope with events from TAC
		//return super.perceive();
		//proxyAgent.
		List<TACEvent> events = proxyAgent.getEvents();
		List<Literal> percepts = new Vector<Literal>();
		for (TACEvent event : events) {
			logger.finer("Handling event "+event);
			percepts.add(Literal.parseLiteral(event.getEvent()));
		}
		return percepts;
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#checkMail()
	 */
	public void checkMail() {
		//TODO Modify this method to cope with events from TAC
		//super.checkMail();
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#act()
	 */
	@SuppressWarnings("unchecked")
	public void act() {
		//TODO Modify this method to cope with events from TAC
		//super.act();
		//logger.info("Agent.act() invoked");
		ActionExec acExec = getTS().getC().getAction();
        if (acExec == null) {
            return;
        }
        Term acTerm = acExec.getActionTerm();
        logger.fine("doing: "+acTerm);
        boolean actionResult;
        try {
			actionResult = actionExecutive.executeAction(this, acExec);
			logger.fine("done: '"+acTerm+"' ->"+actionResult);
		} catch (JasonException e) {
			actionResult = false;
			e.printStackTrace();
		}
		acExec.setResult(actionResult);
		getTS().getC().getFeedbackActions().add(acExec);
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#canSleep()
	 */
	public boolean canSleep() {
		//TODO Modify this method to cope with events from TAC
		return true;
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#getAgName()
	 */
	public String getAgName() {
		return "Orpheus Agent";
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#sendMsg(jason.asSemantics.Message)
	 */
	public void sendMsg(Message message) throws Exception {
		//TODO Modify this method to cope with events from TAC
		//super.sendMsg(message);
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#broadcast(jason.asSemantics.Message)
	 */
	public void broadcast(Message message) throws Exception {
		//TODO Modify this method to cope with events from TAC
		//super.broadcast(message);
	}

	/* (non-Javadoc)
	 * @see jason.architecture.AgArchInterface#isRunning()
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	 * Adds the specified belief into the Agent's database.
	 * @param belief The belief to be added.
	 */
	public void addBelief(String belief) {
		logger.finer("Adding belief '"+belief+"'");
		this.addBelief(Literal.parseLiteral(belief));
	}
	
	/**
	 * Adds the specified belief into the Agent's database.
	 * @param belief The belief to be added.
	 */
	public void addBelief(Literal belief) {
		getTS().getAg().addBel(belief, BeliefBase.TSelf, getTS().getC(), Intention.EmptyInt);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		synchronized(this) {
			this.running = true;
		}
		while (isRunning()) {
			logger.fine("Reasoning...");
			fTS.reasoningCycle();
		}
	}
	
	public void stop() {
		synchronized(this) {
			this.running = false;
		}
	}

	/**
	 * @return Returns the proxyAgent.
	 */
	public TACProxyAgent getProxyAgent() {
		return proxyAgent;
	}

}
