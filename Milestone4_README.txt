Plants_and_Zombies game 

SYSC 3110 MileStone 4 

Date: December 9, 2013

 



AUTHORS



David Falardeau 100830500 

Murdock Walsh 100852907 



README



This is the readme file for Milestone 4 of the Plant_and_Zombies programming project.
Included with this file are the final draft UML diagrams (Class and Sequence) for this fourth milestone, 
the Eclipse project files containing all the coding for this fourth milestone, as well as unit testing 
for PnZModel, Enemy, Npc, Plant, Sunflower, PeaShooter, and PotatoMine, and a file providing an explanation of design choices, class hierarchy and changes to the
code files and UML. This file will include some of the documentation on bugs noted in this draft of the 
code and an explanation of the user controls for the GUI implementation.



KNOWN BUGS

 

Pea Shooters placed behind the zombie still do damage to the zombie.
Implementing the GUI and the changes it made caused the text version to not work as it is. (probably could be fixed easily)




USER CONTROLS

	1. Run the main function in PnZView
	2. Click on the "Place plant here" button on the grid where you want to place a plant
	3. Choose what type of plant to add in the dialouge. CHOOSE WISELY CANNOT BE CHANGED!
	4. when satified with plant placements or out of money (that is a lot of plants), click start wave.
	5. At any point during the game before the end, you can click on the undo or redo buttons to go back
	   or forth through moves made during the game. 
	6. Click on the save button to create a file containing the current state of the instance of the game 
	   the button is clicked, or the load button to open a previous saved instance of the game. Click the 
	   Start Over button at any point to create a completely new instance of the game.
	5. continue adding plants and advancing the waves until your plants eliminate all of the zombies, or
	   until one of the zombies makes their way to the other side of the grid. 
