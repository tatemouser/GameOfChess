# GameOfChess

## Table of Contents

- [Project Description](#project-description)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)
- [Contact](#contact)

## Project Description

This is a brute force version of a chess game created for simple practicing of logic in object oriented programming. The game's fundamental variable is the double 8x8 array that holds Piece objects. This is referenced throughout for checking moves and updating the display to match the array. Upon starting, you can then enter a move by typing "a2 a3" which will then update the display and prompt the second player to type a move. This continues until a checkmate occures, all while keeping score of the two players within the Player object and checking to make sure the moves are all legal by scanning the array.

Game Example: 

<img src="https://github.com/tatemouser/GameOfChess/assets/114375692/5aa811cb-6c85-436c-aa1d-fa1c0fe7de43" alt="Image" width="300" height="300">

## Features

- Game Display
- Scores / Winner and Loser
- Detects and prevents moves that are not allowed
- Detects Checkmate

## Installation
Traditional Eclipse project set up.

## Usage

Project is initiated from the Index class which calls NewBoard once to set up the game display, from there the program is primarily ran in the class GameDisplay. Once here the code loops through lines 124-202 until there is a winner of the game. Between these lines each Piece object and Score object is kept track of and updated accordingly. Lastly the Movement object is referenced with the input of each loop through these lines to see whether the move is valid through check various logic conditions set prior.

## License
None.

## Contact
tatesmouser@gmail.com
