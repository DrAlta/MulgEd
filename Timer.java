/*
This file is part of MulgEd - The Mulg game and level editor.
Copyright (C) 1999-2003 Ilan Tayary, a.k.a. [NoCt]Yonatanz
Website: http://mulged.sourceforge.net
Contact: yonatanz at actcom.co.il (I do not like SPAM)

MulgEd is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

MulgEd is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MulgEd, in a file named COPYING; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

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