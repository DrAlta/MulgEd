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
echo MulgEd's Pack, Copyright (C) Ilan Tayary, 1999 - 2003
echo Website: http://mulged.sourceforge.net
echo Email: yonatanz@actcom.co.il
echo MulgEd comes with ABSOLUTELY NO WARRANTY
echo This is free software, and you are welcome to redistribute 
echo it under the terms of the GNU General Public License. 
echo See the COPYING.txt file for more details
echo:
echo Running Make...
echo Running Make... > packlog.txt
call make

echo:
echo Deleting old Release...
echo Deleting old Release... >> packlog.txt

deltree /Y Release >> packlog.txt

echo Creating new Release...
echo Creating new Release... >> packlog.txt
md Release >> packlog.txt
md Release\BwTiles >> packlog.txt
md Release\ColTiles >> packlog.txt
md Release\Cursors >> packlog.txt

echo Copying new Release contents...
echo Copying new Release contents... >> packlog.txt
copy BwTiles\*.* Release\BwTiles >> packlog.txt
copy ColTiles\*.* Release\ColTiles >> packlog.txt
copy Cursors\*.* Release\Cursors >> packlog.txt

copy COPYING.txt Release >> packlog.txt
copy level.h Release >> packlog.txt
copy tiles.h Release >> packlog.txt
copy mulged.jar Release >> packlog.txt
copy MulgEdIcon.gif Release >> packlog.txt
copy readme.txt Release >> packlog.txt
copy "What's New.txt" Release >> packlog.txt
	
echo Creating release ZIP package...
echo Creating release ZIP package... >> packlog.txt
cd Release >> packlog.txt
jar cf ..\MulgEd22.zip *.* >> packlog.txt
cd ..
