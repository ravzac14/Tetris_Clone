Here's the current state (4/4/15) of the project:

Bugs:
* Block vs DeadBlock collision is not quite right
* Doesn't seem like it knows where the edges of the blocks are...
* If it generates a block too quickly (ie theres another block underneath it) = infinite block generation (which is pretty to look at)
* Probably more...these ones are just glaring

Solutions:
* Doing major in refactoring in Block and DeadBlock
** Making deadblock = Block w/ m_IsDead = true...instead of its own wonky class. 
* Should rework BlockType to not have to be explicit about where the edges are after each rotation (should be able to just define the shape, and let the rotating figure it out

