package org.soton.orpheus.jason.monitor;

import jason.asSemantics.Agent;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class JasonMonitor extends JPanel {
	
	protected Agent agent = null;
	protected JasonMonitorThread monitorThread = null;
	private JLabel jlAgentName = null;
	private JPanel jAgentInfoPanel = null;
	private JList jBeliefsList = null;
	private JList jPlansList = null;
	private JList jIntentionsList = null;
	private JScrollPane jspBeliefs = null;
	private JScrollPane jspPlans = null;
	private JScrollPane jspIntentions = null;
	private JPanel jpBeliefs = null;
	private JPanel jpPlans = null;
	private JPanel jpIntentions = null;
	private JLabel jlBeliefs = null;
	private JLabel jlPlans = null;
	private JLabel jlIntentions = null;
	/**
	 * This is the default constructor
	 */
	public JasonMonitor() {
		super();
		initialize();
	}
	
	public JasonMonitor(Agent agent) {
		this();
		this.setAgent(agent);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jlAgentName = new JLabel();
		jlAgentName.setText("AgentName");
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
		this.add(jlAgentName, java.awt.BorderLayout.SOUTH);
		this.add(getJAgentInfoPanel(), java.awt.BorderLayout.CENTER);
		this.monitorThread = new JasonMonitorThread();
		this.monitorThread.setDaemon(true);
	}
	
	protected class JasonMonitorThread extends Thread {
		protected final static int POLLING_INTERVAL = 500;
		protected boolean running = false;
		
		public void run() {
			synchronized (this) {
				running = true;
			}
			
			while (running) {
				jBeliefsList.setListData(agent.getBS().getAllBeliefs().toArray());
				jPlansList.setListData(agent.getPS().getPlans().toArray());
				jIntentionsList.setListData(agent.getTS().getC().getIntentions().toArray());
				try {
					sleep(POLLING_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public synchronized void stopMonitor() {
			running = false;
		}
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.monitorThread.stopMonitor();
		this.agent = agent;
		this.jlAgentName.setText(agent.getTS().getUserAgArch().getAgName());
		this.monitorThread.start();
	}

	/**
	 * This method initializes jAgentInfoPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJAgentInfoPanel() {
		if (jAgentInfoPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setColumns(3);
			jAgentInfoPanel = new JPanel();
			jAgentInfoPanel.setLayout(gridLayout);
			jAgentInfoPanel.add(getJpBeliefs(), null);
			jAgentInfoPanel.add(getJpPlans(), null);
			jAgentInfoPanel.add(getJpIntentions(), null);
		}
		return jAgentInfoPanel;
	}

	/**
	 * This method initializes jBeliefsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJBeliefsList() {
		if (jBeliefsList == null) {
			jBeliefsList = new JList();
		}
		return jBeliefsList;
	}

	/**
	 * This method initializes jPlansList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJPlansList() {
		if (jPlansList == null) {
			jPlansList = new JList();
		}
		return jPlansList;
	}

	/**
	 * This method initializes jIntentionsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJIntentionsList() {
		if (jIntentionsList == null) {
			jIntentionsList = new JList();
		}
		return jIntentionsList;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspBeliefs() {
		if (jspBeliefs == null) {
			jspBeliefs = new JScrollPane();
			jspBeliefs.setViewportView(getJBeliefsList());
		}
		return jspBeliefs;
	}

	/**
	 * This method initializes jspPlans	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspPlans() {
		if (jspPlans == null) {
			jspPlans = new JScrollPane();
			jspPlans.setViewportView(getJPlansList());
		}
		return jspPlans;
	}

	/**
	 * This method initializes jspIntentions	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspIntentions() {
		if (jspIntentions == null) {
			jspIntentions = new JScrollPane();
			jspIntentions.setViewportView(getJIntentionsList());
		}
		return jspIntentions;
	}

	/**
	 * This method initializes jpBeliefs	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpBeliefs() {
		if (jpBeliefs == null) {
			jlBeliefs = new JLabel();
			jlBeliefs.setText("Beliefs");
			jpBeliefs = new JPanel();
			jpBeliefs.setLayout(new BorderLayout());
			jpBeliefs.add(getJspBeliefs(), java.awt.BorderLayout.CENTER);
			jpBeliefs.add(jlBeliefs, java.awt.BorderLayout.NORTH);
		}
		return jpBeliefs;
	}

	/**
	 * This method initializes jpPlans	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpPlans() {
		if (jpPlans == null) {
			jlPlans = new JLabel();
			jlPlans.setText("Plans");
			jpPlans = new JPanel();
			jpPlans.setLayout(new BorderLayout());
			jpPlans.add(getJspPlans(), java.awt.BorderLayout.CENTER);
			jpPlans.add(jlPlans, java.awt.BorderLayout.NORTH);
		}
		return jpPlans;
	}

	/**
	 * This method initializes jpIntentions	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpIntentions() {
		if (jpIntentions == null) {
			jlIntentions = new JLabel();
			jlIntentions.setText("Intentions");
			jpIntentions = new JPanel();
			jpIntentions.setLayout(new BorderLayout());
			jpIntentions.add(getJspIntentions(), java.awt.BorderLayout.CENTER);
			jpIntentions.add(jlIntentions, java.awt.BorderLayout.NORTH);
		}
		return jpIntentions;
	}

}
