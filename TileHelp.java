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

import java.awt.event.*;
import java.awt.*;

public class TileHelp extends MyDialog {
	/**
	 *	help[tile][x] = 
	 *		x = 0 : name of tile
	 *		x = 1 : effect of tile on ball
	 *		x = 2 : what happens when activates / activated
	 *		x = 3 : other interactions with specific tiles
	 *		x = 4 : flags :
	 *						B - blocking
	 *						C - connectable
	 *						A - activated
	 *						R - activator
	 *						L - below
	 *						I - collectable
	 **/
	final static String[][] help = {
		/* 0x00 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x01 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x02 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x03 */
		{"Empty pit",
		 "If the ball falls into an empty pit, the user loses.",
		 "When activated, the pit turns into a floor",
		 "",
		 "CA"},
		/* 0x04 */
		{"Floor",
		 "The ball can roll over the floor undisturbed.",
		 "When activated, the floor turns into an empty pit",
		 "",
		 "CA"},
		/* 0x05 */
		{"Target cross",
		 "This is the goal of the level. Once the ball is on the target cross, the user has finished the level.",
		 "",
		 "",
		 "B"},
		/* 0x06 */
		{"Block",
		 "The block is the basic wall element, which does not let the ball pass through.",
		 "",
		 "",
		 "B"},
		/* 0x07 */
		{"Letter",
		 "A letter tile is connected to a note which represents the text in it. The user can collect the note, then read it.",
		 "",
		 "",
		 "CI"},
		/* 0x08 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x09 */
		{"Switch - low",
		 "Switches can activate and deactivate channels, when the ball touches them. This switch is in the low position (off).",
		 "",
		 "Scarab beatles can turn switches on and off too.",
		 "BR"},
		/* 0x0a */
		{"Switch - high",
		 "Switches can activate and deactivate channels, when the ball touches them. This switch is in the high position (on).",
		 "",
		 "Scarab beatles can turn switches on and off too.",
		 "BR"},
		/* 0x0b */
		{"Vert. gate - closed",
		 "This is a gate that can be opened.",
		 "When activated, it'll open up, letting the ball go through.",
		 "A bomb explosion near a gate will cause it to explode too.",
		 "CA"},
		/* 0x0c */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x0d */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x0e */
		{"Vert. gate - opened",
		 "This is a gate that can be closed.",
		 "When activated, it'll be closed, not letting the ball go through.",
		 "A bomb explosion near a gate will cause it to explode too.",
		 "CA"},
		/* 0x0f */
		{"Horz. gate - closed",
		 "This is a gate that can be opened.",
		 "When activated, it'll open up, letting the ball go through.",
		 "A bomb explosion near a gate will cause it to explode too.",
		 "CA"},
		/* 0x10 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x11 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x12 */
		{"Horz. gate - opened",
		 "This is a gate that can be closed.",
		 "When activated, it'll be closed, not letting the ball go through.",
		 "A bomb explosion near a gate will cause it to explode too.",
		 "CA"},
		/* 0x13 */
		{"Swing from left",
		 "The swing lets the ball go through only from one side to the other, and then back.",
		 "",
		 "",
		 ""},
		/* 0x14 */
		{"Swing from right",
		 "The swing lets the ball go through only from one side to the other, and then back.",
		 "",
		 "",
		 ""},
		/* 0x15 */
		{"Swing from up",
		 "The swing lets the ball go through only from one side to the other, and then back.",
		 "",
		 "",
		 ""},
		/* 0x16 */
		{"Swing from down",
		 "The swing lets the ball go through only from one side to the other, and then back.",
		 "",
		 "",
		 ""},
		/* 0x17 */
		{"Inactive Ventilator",
		 "The ventilator, if active, sucks the ball into it and the user looses",
		 "The ventilator can be turned on and off when connected to a channel",
		 "",
		 "CA"},
		/* 0x18 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x19 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x1a */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x1b */
		{"Hole",
		 "The hole in the ground pulls the ball towards its center.",
		 "",
		 "A hole can also be created as a result of a bomb explosion.",
		 ""},
		/* 0x1c */
		{"Bump",
		 "The bump interferes with the ball's movement by pushing it away from its center.",
		 "",
		 "",
		 ""},
		/* 0x1d */
		{"Die - 1",
		 "A die is rolled when the ball touches it. The number on the dice is then selected randomly.",
		 "When all the dice that are connected to the same channel have the same number on them, that channel activates.",
		 "",
		 "CR"},
		/* 0x1e */
		{"Die - 2",
		 "A die is rolled when the ball touches it. The number on the dice is then selected randomly.",
		 "When all the dice that are connected to the same channel have the same number on them, that channel activates.",
		 "",
		 "CR"},
		/* 0x1f */
		{"Die - 3",
		 "A die is rolled when the ball touches it. The number on the dice is then selected randomly.",
		 "When all the dice that are connected to the same channel have the same number on them, that channel activates.",
		 "",
		 "CR"},
		/* 0x20 */
		{"Die - 4",
		 "A die is rolled when the ball touches it. The number on the dice is then selected randomly.",
		 "When all the dice that are connected to the same channel have the same number on them, that channel activates.",
		 "",
		 "CR"},
		/* 0x21 */
		{"Die - 5",
		 "A die is rolled when the ball touches it. The number on the dice is then selected randomly.",
		 "When all the dice that are connected to the same channel have the same number on them, that channel activates.",
		 "",
		 "CR"},
		/* 0x22 */
		{"Die - 6",
		 "A die is rolled when the ball touches it. The number on the dice is then selected randomly.",
		 "When all the dice that are connected to the same channel have the same number on them, that channel activates.",
		 "",
		 "CR"},
		/* 0x23 */
		{"Key",
		 "A key can be used to open locks and turn them into switches.",
		 "",
		 "A bored scarab beatle might sometimes pick up a key and drop it when it meets the ball.",
		 "I"},
		/* 0x24 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x25 */
		{"Lock",
		 "The lock is turned into a switch when the ball touches it and a key is in the inventory.",
		 "After unlocked, it behaves the same as a switch",
		 "",
		 "CR"},
		/* 0x26 */
		{"Heavy box",
		 "The heavy box can be pushed onto a nearby floor, or into a nearby empty/switch pit",
		 "",
		 "If a bomb explodes near a heavy box, it explodes too.",
		 "L"},
		/* 0x27 */
		{"Heavy box in place",
		 "This is what a heavy box looks like when it is pushed into an empty space.",
		 "",
		 "",
		 ""},
		/* 0x28 */
		{"Bouncer",
		 "The bouncer causes an effect like pinbal. It bounces the ball when the ball hits it.",
		 "",
		 "",
		 ""},
		/* 0x29 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x2a */
		{"Death cube",
		 "Oooohh. This tile can cause the user to loose the level if the ball touches it",
		 "",
		 "",
		 ""},
		/* 0x2b */
		{"Icy floor",
		 "The icy floor is slippery, so no friction slows down the ball",
		 "",
		 "A bomb explosion nearby causes icy floors to turn to empty pits",
		 ""},
		/* 0x2c */
		{"One-way left",
		 "This tile does not let the ball go back against the direction of the arrow",
		 "",
		 "",
		 ""},
		/* 0x2d */
		{"One-way right",
		 "This tile does not let the ball go back against the direction of the arrow",
		 "",
		 "",
		 ""},
		/* 0x2e */
		{"One-way up",
		 "This tile does not let the ball go back against the direction of the arrow",
		 "",
		 "",
		 ""},
		/* 0x2f */
		{"One-way down",
		 "This tile does not let the ball go back against the direction of the arrow",
		 "",
		 "",
		 ""},
		/* 0x30 */
		{"Bomb",
		 "A bomb on the floor can be lit if there is a match in the inventory",
		 "",
		 "A lit bomb will explode and cause damage to tiles around it. A bomb can be collected from a bomb dispenser",
		 "C"},
		/* 0x31 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x32 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x33 */
		{"Match",
		 "The match is used to light up bombs",
		 "",
		 "When the ball passes over a bomb and there is a match in the inventory, the bomb will light up",
		 "C"},
		/* 0x34 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x35 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x36 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x37 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x38 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x39 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x3a */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x3b */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x3c */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x3d */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x3e */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x3f */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x40 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x41 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x42 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x43 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x44 */
		{"Groove",
		 "This is like a tunnel. It pulls the ball towards its center",
		 "",
		 "",
		 ""},
		/* 0x45 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x46 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x47 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x48 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x49 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x4a */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x4b */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x4c */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x4d */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x4e */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x4f */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x50 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x51 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x52 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x53 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x54 */
		{"Rampart",
		 "This is like a raised platform. It pushes the ball away from its center",
		 "",
		 "",
		 ""},
		/* 0x55 */
		{"Descending floor x2",
		 "This is an unstable floor. The ball can pass on it 2 more times. On the third time it turns into an empty pit and the user loses",
		 "",
		 "",
		 ""},
		/* 0x56 */
		{"Descending floor x1",
		 "This is an unstable floor. The ball can pass on it one more time. On the second time it turns into an empty pit and the user loses",
		 "",
		 "",
		 ""},
		/* 0x57 */
		{"Descending floor x0",
		 "This is an unstable floor. The ball can not pass on it any more. If the user tries, it turns into an empty pit and the user loses",
		 "",
		 "",
		 ""},
		/* 0x58 */
		{"Floor switch - up",
		 "This is a switch that is hidden as a floor. When the ball presses it, It activates.",
		 "",
		 "",
		 "CR"},
		/* 0x59 */
		{"Floor switch - down",
		 "This is what the floor switch looks like when it is pressed.",
		 "",
		 "",
		 "CR"},
		/* 0x5a */
		{"Flip - on",
		 "This is a flip tile. It is connected to an X and a Y channel. It flips on/off when the ball touches it.",
		 "When the flip is flipped, all other flips in the level with the same X channel or the same Y channel are flipped too.",
		 "When all the flips in the level have the same status (on/off), channel 1 is activated.",
		 "R"},
		/* 0x5b */
		{"Flip - off",
		 "This is a flip tile. It is connected to an X and a Y channel. It flips on/off when the ball touches it.",
		 "When the flip is flipped, all other flips in the level with the same X channel or the same Y channel are flipped too.",
		 "When all the flips in the level have the same status (on/off), channel 1 is activated.",
		 "R"},
		/* 0x5c */
		{"Bomb dispenser",
		 "This is a dispenser of bombs. Each time the ball touches it, a bomb is added to the inventory.",
		 "",
		 "If a bomb explodes near the dispenser, the user looses.",
		 ""},
		/* 0x5d */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x5e */
		{"Smiley",
		 "This is a nice decoration and can be used for the plot.",
		 "",
		 "",
		 ""},
		/* 0x5f */
		{"Parachute",
		 "The parachute enables the ball to hover above empty pits, however, the user does not have control over the ball's direction when it hovers.",
		 "",
		 "",
		 "I"},
		/* 0x60 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x61 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x62 */
		{"Coin - 1",
		 "The 1 cent coin can activate a coin-slot tile",
		 "",
		 "When inserted into a coin-slot, it activates the channel for about 5 seconds",
		 "I"},
		/* 0x63 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x64 */
		{"Coin - 5",
		 "The 5 cent coin can activate a coin-slot tile",
		 "",
		 "When inserted into a coin-slot, it activates the channel for about 40 seconds",
		 ""},
		/* 0x65 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x66 */
		{"Coin slot",
		 "The coin slot can be activated using a coin",
		 "The activation time depends on the coin's value",
		 "",
		 "CR"},
		/* 0x67 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x68 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x69 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x6a */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x6b */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x6c */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x6d */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x6e */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x6f */
		{"Oil",
		 "If the ball rolls on an oil stain, it will get very slippery and remain that way until all the oil comes off",
		 "",
		 "When the ball is on the oil tile, the user has very little influence on the ball",
		 ""},
		/* 0x70 */
		{"Mud",
		 "The mud slows down the ball to a stop almost immediately",
		 "",
		 "",
		 ""},
		/* 0x71 */
		{"Light box",
		 "This is the light version of the heavy box. If it gets hit too hard, it'll break apart",
		 "",
		 "As the heavy one, this box can be pushed around and into pits. It can also have a tile underneath it",
		 "L"},
		/* 0x72 */
		{"Light box in place",
		 "This is what the light box looks like when it is pushed into a pit.",
		 "",
		 "",
		 ""},
		/* 0x73 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x74 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x75 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x76 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x77 */
		{"Switch pit",
		 "This is a pit that activates its channel when a box is pushed inside",
		 "",
		 "",
		 "CR"},
		/* 0x78 */
		{"Scarab beatle",
		 "These bugs go around and chase the ball. They try to push the ball around the level",
		 "",
		 "If it gets bored, it might look for a key and take it until it meets the ball - then it'll drop it",
		 "L"},
		/* 0x79 */
		{"Scarab beatle",
		 "These bugs go around and chase the ball. They try to push the ball around the level",
		 "",
		 "If it gets bored, it might look for a key and take it until it meets the ball - then it'll drop it",
		 "L"},
		/* 0x7a */
		{"Scarab beatle",
		 "These bugs go around and chase the ball. They try to push the ball around the level",
		 "",
		 "If it gets bored, it might look for a key and take it until it meets the ball - then it'll drop it",
		 "L"},
		/* 0x7b */
		{"Scarab beatle",
		 "These bugs go around and chase the ball. They try to push the ball around the level",
		 "",
		 "If it gets bored, it might look for a key and take it until it meets the ball - then it'll drop it",
		 "L"},
		/* 0x7c */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x7d */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x7e */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x7f */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x80 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x81 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x82 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x83 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x84 */
		{"",
		 "",
		 "",
		 "",
		 ""},
		/* 0x85 */
		{"Hanoi tower - Bottom",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x86 */
		{"Hanoi tower - Middle",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x87 */
		{"Hanoi tower - Top",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x88 */
		{"Hanoi tower - Bottom and Middle",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x89 */
		{"Hanoi tower - Bottom and Top",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x8a */
		{"Hanoi tower - Moddle and Top",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x8b */
		{"Hanoi tower - Bottom, Middle and Top",
		 "The ball can push smaller tower pieces onto larger ones, and then detach the two by pushing the smaller piece away from the larger one.",
		 "When the three parts are one on top of the other, the channel associated with the bottom part activates.",
		 "",
		 "BCR"},
		/* 0x8c */
		{"Walker - right",
		 "These tiles go \"Walking\" in one direction. ",
		 "The ball can cause their direction to change counter-clockwise by touching them (or clockwise if a parachute is active)",
		 "They will go and go, until they reach anything other than a path",
		 "BL"},
		/* 0x8d */
		{"Walker - down",
		 "These tiles go \"Walking\" in one direction. ",
		 "The ball can cause their direction to change counter-clockwise by touching them (or clockwise if a parachute is active)",
		 "They will go and go, until they reach anything other than a path",
		 "BL"},
		/* 0x8e */
		{"Walker - left",
		 "These tiles go \"Walking\" in one direction. ",
		 "The ball can cause their direction to change counter-clockwise by touching them (or clockwise if a parachute is active)",
		 "They will go and go, until they reach anything other than a path",
		 "BL"},
		/* 0x8f */
		{"Walker - up",
		 "These tiles go \"Walking\" in one direction. ",
		 "The ball can cause their direction to change counter-clockwise by touching them (or clockwise if a parachute is active)",
		 "They will go and go, until they reach anything other than a path",
		 "BL"},
		/* 0x90 */
		{"Exchange",
		 "This is a nice decoration and can be used for the plot",
		 "",
		 "",
		 "B"},
		/* 0x91 */
		{"Magnet - positive",
		 "This magnet pulls the ball towards it. The closer the ball, the harder the pull",
		 "",
		 "",
		 "B"},
		/* 0x92 */
		{"Magnet - netgative",
		 "This magnet pushes the ball away from it. The closer the ball, the harder the push",
		 "",
		 "",
		 "B"}
	};
	
	final static String[][] shelp = {
		/* 1000 - starting point*/
		{"Starting point",
		 "This is where the ball is placed in the beginning of the level",
		 "",
		 "",
		 ""},
		/* 1001 - Descending floor x3*/
		{"Descending floor x3",
		 "This is an unstable floor. The ball can pass on it 3 more times. On the forth time it turns into an empty pit and the user loses",
		 "",
		 "",
		 ""},
		/* 1002 - Active vent*/
		{"Active ventilator",
		 "The active ventilator sucks the ball into it and the user looses",
		 "The ventilator can be turned on or off when connected to a channel",
		 "",
		 "CA"},
		/* 1003 - Open VGate*/
		{"Vert. gate - opened",
		 "This is a gate that can be closed.",
		 "When activated, it'll be closed, not letting the ball go through.",
		 "A bomb explosion near a gate will cause it to explode too.",
		 "CA"},
		/* 1004 - Open HGate*/
		{"Horz. gate - opened",
		 "This is a gate that can be closed.",
		 "When activated, it'll be closed, not letting the ball go through.",
		 "A bomb explosion near a gate will cause it to explode too.",
		 "CA"},
		/* 1005 Unreverser*/
		{"Un-reverser",
		 "When the ball goes over this tile, The pen strokes return to their original state (not-reversed)",
		 "",
		 "The reversed-strokes effect can be caused by going over a  kind of a Reverser",
		 ""},
		/* 1006 Reverser*/
		{"Reverser",
		 "When the ball goes over this tile, the pen stroke directions get reversed",
		 "",
		 "This effect can be cancelled by going over a kind of an Un-Reverser",
		 ""},
		/* 1007 Ice-ur*/
		{"Icy un-reverser",
		 "When the ball goes over this tile, The pen strokes return to their original state (not-reversed)",
		 "This tile is also slippery like an Icy floor tile",
		 "The reversed-strokes effect can be caused by going over a  kind of a Reverser",
		 ""},
		/* 1008 Ice-rv*/
		{"Icy reverser",
		 "When the ball goes over this tile, the pen stroke directions get reversed",
		 "This tile is also slippery like an Icy floor tile",
		 "This effect can be cancelled by going over a kind of an Un-Reverser",
		 ""},
		/* 1009 BoxFix-ur*/
		{"Box in place un-reverser",
		 "When the ball goes over this tile, The pen strokes return to their original state (not-reversed)",
		 "This tile looks like a box in place",
		 "The reversed-strokes effect can be caused by going over a  kind of a Reverser",
		 ""},
		/* 1010 BoxFix-rv*/
		{"Box in place reverser",
		 "When the ball goes over this tile, the pen stroke directions get reversed",
		 "This tile looks like a box in place",
		 "This effect can be cancelled by going over a kind of an Un-Reverser",
		 ""},
		/* 1011 Memo0*/
		{"Memorize cube",
		 "The memorize cube acts like the memory game. The ball has to touch all the memo-cubes of the same type",
		 "When all memo-cubes of the same type and channel are uncovered, that channel activates",
		 "NOTE: All memo cubes of the same type must be connected to the same channel !",
		 "CR"},
		/* 1012 Memo1*/
		{"Memorize cube",
		 "The memorize cube acts like the memory game. The ball has to touch all the memo-cubes of the same type",
		 "When all memo-cubes of the same type and channel are uncovered, that channel activates",
		 "NOTE: All memo cubes of the same type must be connected to the same channel !",
		 "CR"},
		/* 1013 Memo2*/
		{"Memorize cube",
		 "The memorize cube acts like the memory game. The ball has to touch all the memo-cubes of the same type",
		 "When all memo-cubes of the same type and channel are uncovered, that channel activates",
		 "NOTE: All memo cubes of the same type must be connected to the same channel !",
		 "CR"},
		/* 1014 Memo3*/
		{"Memorize cube",
		 "The memorize cube acts like the memory game. The ball has to touch all the memo-cubes of the same type",
		 "When all memo-cubes of the same type and channel are uncovered, that channel activates",
		 "NOTE: All memo cubes of the same type must be connected to the same channel !",
		 "CR"}
	};
	
	static final int MSG_OK = 101;
	
	BlackLabel name = new BlackLabel("");
	BlackLabel lb1 = new BlackLabel("");
	BlackLabel lb2 = new BlackLabel("");
	BlackLabel lb3 = new BlackLabel("");
	BlackLabel lb4 = new BlackLabel("");
	MyButton bOk = new MyButton(this, MSG_OK, "Ok", KeyEvent.VK_O);
	
	public TileHelp(MyDialog parent) {
		super(parent, "Tile help", true);
		setSize(300,200);
		addComponents();
	}
	
	void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
		
		layout.setConstraints(name,new GridBagConstraints(0,0,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(name);
		
		layout.setConstraints(lb1,new GridBagConstraints(0,1,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(lb1);
		layout.setConstraints(lb2,new GridBagConstraints(0,2,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(lb2);
		layout.setConstraints(lb3,new GridBagConstraints(0,3,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(lb3);
		layout.setConstraints(lb4,new GridBagConstraints(0,4,1,1,1,0,c.WEST,c.HORIZONTAL,new Insets(1,1,1,1),0,0));
		paneadd(lb4);
		
		layout.setConstraints(bOk,new GridBagConstraints(0,5,1,1,1,1,c.CENTER,c.NONE,new Insets(1,1,1,1),0,0));
		paneadd(bOk);
			
	}
	
	void setTile(int tile) {
		if (tile < 1000) {
			name.setText(help[tile][0]);
			lb1.setText(help[tile][1]);
			lb2.setText(help[tile][2]);
			lb3.setText(help[tile][3]);
			//lb4.setText(help[tile][4]);
		} else {
			tile -= 1000;
			name.setText(shelp[tile][0]);
			lb1.setText(shelp[tile][1]);
			lb2.setText(shelp[tile][2]);
			lb3.setText(shelp[tile][3]);
			//lb4.setText(shelp[tile][4]);
		}
		pack();
		setLocation();
	}
	
	public void process(int msg, int x, int y, int z) {
		switch(msg) {
			case MSG_OK:
				hide();
				break;
		}
	}
}