@echo off
rem This file is part of MulgEd - The Mulg game and level editor.
rem Copyright (C) 1999-2003 Ilan Tayary, a.k.a. [NoCt]Yonatanz
rem Website: http://mulged.sourceforge.net
rem Contact: yonatanz at actcom.co.il (I do not like SPAM)
rem 
rem MulgEd is free software; you can redistribute it and/or modify
rem it under the terms of the GNU General Public License as published by
rem the Free Software Foundation; either version 2 of the License, or
rem (at your option) any later version.
rem 
rem MulgEd is distributed in the hope that it will be useful,
rem but WITHOUT ANY WARRANTY; without even the implied warranty of
rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
rem GNU General Public License for more details.
rem 
rem You should have received a copy of the GNU General Public License
rem along with MulgEd, in a file named COPYING; if not, write to the Free Software
rem Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

echo:
echo MulgEd's Make, Copyright (C) Ilan Tayary, 1999 - 2003
echo Website: http://mulged.sourceforge.net
echo Email: yonatanz@actcom.co.il
echo MulgEd comes with ABSOLUTELY NO WARRANTY
echo This is free software, and you are welcome to redistribute 
echo it under the terms of the GNU General Public License. 
echo See the COPYING.txt file for more details
echo:

echo Compiling Java files...
echo Compiling Java files... > makelog.txt
date /T >> makelog.txt
javac *.java >> makelog.txt

echo Creating "executable" JAR archive
echo Creating "executable" JAR archive >> makelog.txt
jar cvfm MulgEd.jar manifest *.class >> makelog.txt
