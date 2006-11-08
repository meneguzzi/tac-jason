package org.soton.orpheus.jason.monitor;

import jason.asSemantics.Agent;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JasonMonitorWindow extends JFrame {

	private JPanel jContentPane = null;
	private JasonMonitor jasonMonitor = null;

	/**
	 * This is the default constructor
	 */
	public JasonMonitorWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(769, 439);
		this.setContentPane(getJContentPane());
		this.setTitle("Jason Agent Monitor");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJasonMonitor(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jasonMonitor	
	 * 	
	 * @return org.soton.orpheus.jason.monitor.JasonMonitor	
	 */
	private JasonMonitor getJasonMonitor() {
		if (jasonMonitor == null) {
			jasonMonitor = new JasonMonitor();
		}
		return jasonMonitor;
	}

	/* (non-Javadoc)
	 * @see org.soton.orpheus.jason.monitor.JasonMonitor#getAgent()
	 */
	public Agent getAgent() {
		return jasonMonitor.getAgent();
	}

	/* (non-Javadoc)
	 * @see org.soton.orpheus.jason.monitor.JasonMonitor#setAgent(jason.asSemantics.Agent)
	 */
	public void setAgent(Agent agent) {
		jasonMonitor.setAgent(agent);
	}

}  //  @jve:decl-index=0:visual-constraint="9,12"
