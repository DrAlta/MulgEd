import java.util.*;

public class Timer implements Runnable {
	
	Thread t;
	int interval;
	boolean enabled;
	int msg;
	MyDialog dl;
	
	public Timer(int interval,boolean start) {
		this.interval = interval;
		if (start)
			start();
	}
	
	public void start() {
		if (!enabled) {
			enabled = true;
			t = new Thread(this);
			t.start();
		}
	}
	
	public void stop() {
		if (enabled) {
			enabled = false;
//			t.interrupt();
		}
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setDialog(MyDialog dl, int msg) {
		this.msg = msg;
		this.dl = dl;
	}
	
	void fireDialog() {
		if (dl != null) {
			dl.process(msg,0,0,0);
		}
	}
	
	public void run() {
		while (enabled) {
			try {
				Thread.sleep(interval);
				fireDialog();
			} catch (InterruptedException ie) {
			}
		}
		enabled = false;
	}
	
}