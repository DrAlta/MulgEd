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


MulgEd 2.2 - release notes
---------------------------

Contents:
  1. System requirements and Java explanations
  2. Running MulgEd 2.2
  3. General changes from MulgEd 1.5j preview versions
  4. Some FAQs
  5. Bugs, comments, suggestions
	
	
1. System requirements and Java explanations:
---------------------------------------------

MulgEd 2.2 was designed to run on all platforms that support the Java Virtua Machine
	
The java virtual machine runtime environment can be downloaded from sun's web site. It is
available for a wide variety of platforms. Simply go to http://java.sun.com/jre and download
the latest version  of the JRE (And install it, of course). I recommend using JRE or JDK
Version 1.2.2 or later.

If you are a java developer, and have already installed the JDK, you need not do any
further preparations in order to run MulgEd 2.2. 
Of course you already know that, but i just had to say it ... ;)

Note for Un*x-like OSs (linux, solaris, etc):
  You must start MulgEd 2.2 when the X environment is running, otherwise you will get an 
  error	about opening the display.
	
If you already have a JRE installed, and you get error messages about the javax.swing 
package, it is probably because you have a version that does not support the Swing user 
interface. Download and install a more recent version of the JRE, and the problem is gone.
	
	
2. Running MulgEd 2.2:
-----------------------

MulgEd 2.2 comes in a java archive (a JAR file), that contains all the code required to
run it. Other files (mostly images) are outside the JAR file.

If you have installed either JRE or JDK, run the following command:
	java -jar mulged.jar
	
If your OS knows how to run JAR files on its own, you might be able to run MulgEd simply by
double-clicking on the JAR file's icon.
	
	
	
3. General changes from MulgEd 1.5j preview versions:
----------------------------------------------

MulgEd 2.2 is totally different from the previous version 1.5j preview 1,2, and 3.
The difference is that version 2.2 uses the new JFC user interface called Swing, that is 
supported in Java 2 and is available for a lot of different computer platforms.
Those three preview versions used the MS-JVM extentions to emulate a Windows environment
and therefore were not compatible with other OSs. 

For other changes in this version, read the "What's New" file.



4. Some FAQs:
-------------

Q: I've just downloaded MulgEd and extracted the archive file, but there is no executable
   file. NOW WHAT ?!?!?!?
A: First of all, Relax. MulgEd is written in Java, therefore the executable file is the JAR
   file. Read part 1 of this file (System requirements), then try double-clicking on the JAR's
   icon. If this doesn't help, try typing in "java -jar mulged.jar" on your console.
  
Q: MulgEd is very slow. Is there a way to make it run faster?
A: Java software run in a virtual machine mode, not in native mode. This means that MulgEd 
   isn't running at your computer's full speed. I've seen it run on a Pentium-II 350 machine
   quite nicely. If you have a slower computer, think about upgrading. It's a good idea anyhow.

Q: MulgEd is COOL! But where can I find Mulg?
A: Mulg II is available for free in Till's website. The address is:
     http://www.harbaum.org/till/palm/mulg
    
Q: I want to run Mulg on my PC/Linux/Mac/etc. Can I ?
A: Mulg II is designed to run on a PalmOS PDA. You can find a PalmOS emulator in the Palm website.
   The PalmOS emulator is available for developers, but you can get it for free.

Q: Will there be a version of MulgEd for Palm ?
A: Probably not.

Q: I've found a bug in MulgEd !!! Can you fix it?
A: I might try. Contact me (see part 5 below), and ask. Try to be descriptive about the bug.
   If you are a Java developer, you can play with the source code and try to fix it yourself. If this
   is successful, please send me email with all the information including the fix, and I will incorporate 
   it in future versions.
   see http://mulged.sourceforge.net for more details

Q: My question isn't on this list. How can I ask you directly?
A: Read part 5 of this file (below) ...

That's it for now.
As more people ask questions, I'll add the most common ones to this list.



5. Bugs, questions, suggestions:
-------------------------------

I will gladly receive bug reports in my Emailbox:
	
	yonatanz@actcom.co.il
	
I also want to hear about your other questions and suggestions for future development of 
MulgEd. For example. if you think that a feature is too complicated and you thought of 
another way of implementing it - send me Email so users can enjoy your idea.

NOTE:
  The Swing packages sometime have a few bugs in the display (Mainly in the windows 
  version), such as changing fonts, and windows that do not paint themselves right. 
  If you think you have encountered this bug, don't send me Email. Instead, go to Sun's 
  website and search for a newer JRE.
	
Thanks for using MulgEd. I really appreciate all the comments from all users.

Ilan Tayary.
yonatanz@actcom.co.il
