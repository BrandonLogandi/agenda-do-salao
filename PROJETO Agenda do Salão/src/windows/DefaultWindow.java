package windows;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import system.operations.DataPersistence;
import system.operations.DataSystem;

public abstract class DefaultWindow extends JFrame implements ActionListener {
	
	private DataSystem sys = null;
	private DataPersistence dp = null;
	
	public DefaultWindow(DataSystem sys, DataPersistence dp) {
		this.setSys(sys);
		this.setDp(dp);
		
		setLayout(null);
		setResizable(false);
	}
	
	protected abstract void createLabels();
	protected abstract void createFields();
	protected abstract void createButtons();
	
	public DataSystem getSys() {
		return sys;
	}

	public DataPersistence getDp() {
		return dp;
	}

	public void setSys(DataSystem sys) {
		this.sys = sys;
	}

	public void setDp(DataPersistence dp) {
		this.dp = dp;
	}

	
}
