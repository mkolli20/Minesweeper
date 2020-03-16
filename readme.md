Sorry for having to reupload, while trying to delete my readme file so I could re-do it, my comands
ended up deleting much of the work in my respository, so I made a new one.

To run this game, download the files, put them into eclipse, with a src directory
in the same folder as the class files, with the images in there. Then, update the path
location at line 72 to match the path on your machine.

Minesweeper by Mihir Kolli

This mini projet implements a fully functional game of Minesweeper in a JPanel. The game
is quite simplistic in that it only allows the player to play the game, there is no
scoreboard, multiplayer integration, or menu. A new game is started the moment the previous
game is won or lost.

The game works by storing numbers in a field array, which a function then associates
with images in an image array, which are drawn to the JPanel. A player inputs commands
with their mouse, whose position is determined by counting pixels and using integer
division. If statements determine what to do upon player input.

The First A-ha moment I had was that I could store images in an external folder, then call them
to an image array. This allowed me to open my game in a JPanel with working graphics.

A second A-ha moment was when I realized I could determine the Mouse position by
taking the quotient of the mouse position in pixels divided by the cell size.
This yields an integer value corresponding to the cells x/y position.

A third A-ha moment I had was realizing that to find and uncover all empty cells
around a givencell, I could recursively call a check function which would then
uncover all adjacent empty cells. This allowed me to uncover patches of clear cells
of any shape or size.