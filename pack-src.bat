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
echo Deleting old Release-src...
echo Deleting old Release-src... >> packlog-src.txt

deltree /Y Release-src >> packlog-src.txt

echo Creating new Release-src...
echo Creating new Release-src... >> packlog-src.txt
md Release-src >> packlog-src.txt
md Release-src\2BitTiles >> packlog-src.txt
md Release-src\4BitTiles >> packlog-src.txt
md Release-src\8BitTiles >> packlog-src.txt
md Release-src\Cursors >> packlog-src.txt

echo Copying new Release-src contents...
echo Copying new Release-src contents... >> packlog-src.txt
copy 2BitTiles\*.* Release-src\2BitTiles >> packlog-src.txt
copy 4BitTiles\*.* Release-src\4BitTiles >> packlog-src.txt
copy 8BitTiles\*.* Release-src\8BitTiles >> packlog-src.txt
copy Cursors\*.* Release-src\Cursors >> packlog-src.txt

copy COPYING.txt Release-src >> packlog-src.txt
copy level.h Release-src >> packlog-src.txt
copy tiles.h Release-src >> packlog-src.txt
copy *.java Release-src >> packlog-src.txt
copy manifest Release-src >> packlog-src.txt
copy MulgEdIcon.gif Release-src >> packlog-src.txt
copy readme.txt Release-src >> packlog-src.txt
copy "What's New.txt" Release-src >> packlog-src.txt
	
echo Creating release-src ZIP package...
echo Creating release-src ZIP package... >> packlog-src.txt
cd Release-src >> packlog-src.txt
jar cf ..\MulgEd221-src.zip *.* >> ../packlog-src.txt
cd ..
