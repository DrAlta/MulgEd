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


MulgEd 2.21 - source code release notes
--------------------------------------

Contents:
  1. Compiling Requirements
  2. Building the sources
  3. Bugs, comments, suggestions
	
	

1. Compiling requirements
-------------------------

MulgEd 2.21 requires JDK 1.2.2 or later to compile.
Please see http://java.sun.com/j2se/ for JDK download and install instructions.

The JDK binaries (specifically javac and jar) should be in your PATH environment.



2. Building the sources
-----------------------
If you are using a Windows operating system, use the make.bat batch file to compile.
make.bat compiles all java files in the directory, and then packs it all into a java
archive (JAR) along with the manifest file that tells the JVM which class to run.
make.bat must be run from the sources directory (where the .java files are)
make.bat outputs a log file, named makelog.txt. Consult this file if your compilation
didn't seem to work.

After you finished compiling the sources with make.bat, you can run MulgEd by typing:

  java -jar mulged.jar


If you want to build the full release, run the pack.bat batch file.
pack.bat runs make.bat, and then copies only the files needed for the release, in a 
sundirectory named Release.
Then pack,bat ZIPs this directory to a file named MulgEdXX.zip (XX being the version)
pack.bat outputs a log file, named packlog.txt. Consult this file if your packing
didn't seem to work.



3. Bugs, questions, suggestions:
-------------------------------

I will gladly receive bug reports in my Emailbox:
	
	yonatanz@actcom.co.il
	
I also want to hear about your other questions and suggestions for future development of 
MulgEd. For example. if you think that a feature is too complicated and you thought of 
another way of implementing it - send me Email so users can enjoy your idea.

Thanks for using MulgEd. I really appreciate all the comments from all users.

Ilan Tayary.
yonatanz@actcom.co.il
