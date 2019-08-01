# Recursive-Puzzle-Solver
A recursive Puzzle solver in Java

The Puzzle is given in a long one-line list of numbers.

This is an example of a small Puzzle:

0,[0, 0, 2, 1]; 1,[2, 0, 0, 3]; 2,[3, 0, 0, 4]; 3,[1, 4, 0, 0];

In this Puzzle there are 4 pieces.
Every Piece is given with a number (an ID), and an array of 4 numbers that are the slices of which the piece is composed.
For example, Piece number 0:

0,[0, 0, 2, 1];

The Piece is composed of 4 slices; 0, 0, 2, 1.

This is a visual representation of the Piece:
```
-----------
|  \ 0 /  |
| 1 \ /  0|
|   / \   |
|  / 2 \  |
-----------
```
Every Piece is composed of upper slice, right slice, bottom slice and left slice.

This is a visual representation of the Puzzle in its initial state:
```
----------------------
|  \ 0 /  ||  \ 2 /  |
| 1 \ /  0|| 3 \ /  0|
|   / \   ||   / \   |
|  / 2 \  ||  / 0 \  |
----------------------
----------------------
|  \ 3 /  ||  \ 1 /  |
| 4 \ /  0|| 0 \ /  4|
|   / \   ||   / \   |
|  / 0 \  ||  / 0 \  |
----------------------
```
This is a visual representation of one of the solutions of the Puzzle:
```
----------------------
|  \ 0 /  ||  \ 0 /  |
| 0 \ /  2|| 2 \ /  0|
|   / \   ||   / \   |
|  / 1 \  ||  / 3 \  |
----------------------
----------------------
|  \ 1 /  ||  \ 3 /  |
| 0 \ /  4|| 4 \ /  0|
|   / \   ||   / \   |
|  / 0 \  ||  / 0 \  |
----------------------
```
As you can see, all the zeros are in the edges and every two slices that are touching each other have the same number.

This is The solution in one line:

0,3; 1,3; 3,0; 2,0;

This is a list of numbers in the format: "a,b; c,d;...", where 'a' is the id of the first Piece and 'b' is the number of clockwise rotation of the piece (compared to its intial state).

I solved this problem in a recursive way.
I used some Java 8 features to make the solver shorter and more readable.
I may be adding a Puzzle generator sometime.
