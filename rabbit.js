/*
The 'nextMove' Object type
nextMove = 'left' | 'right' | 'up' | 'down' | 'sleep'

The 'rabbitPosition' type 
rabbitPosition.x: int The x coordinate 
rabbitPosition.y: int The y coordinate 

And just be clear so my x and y numbering makes sense, I'm considering the matrix like this: 
 x ------->
y 
|
|
|
|
v

So for the example matric above, the first 5 is in postition x = 0, y = 0, and the other
5 is in the position x = 3, y = 3, and the rabbit is starting on the 7 in postion x = 2, y = 1

*/

function hungyRabbit(garden) {
  // our rabbits starting position in the form of an object
  // containing coordinates x and y
  var currPosition = getRabbitStartingPosition(garden);

  var sleeping = false;
  var carrotsEaten = garden[currPosition.x][currPosition.y];
  garden[currPosition.x][currPosition.y] = 0;
  while (!sleeping) {
    var nextMove = nextMove(garden, currPosition);
    if(nextMove === 'sleep') {
      sleeping = true;
    } else if (nextMove === 'up') {
      carrotsEaten += garden[currPosition.x][currPosition.y - 1];
      currPosition.y = currPosition.y - 1;
      // If we needed to preserve the garden we could also keep a seperate
      // array of boolean flags marking where we have already gone
      garden[currPosition.x][currPosition.y - 1] = 0;

    } else if (nextMove === 'right') {
      carrotsEaten += garden[currPosition.x + 1][currPosition.y];
      currPosition.y = currPosition.x + 1;
      garden[currPosition.x + 1][currPosition.y] = 0;

    } else if (nextMove === 'down') {
      carrotsEaten += garden[currPosition.x][currPosition.y + 1];
      currPosition.y = currPosition.y + 1;
      garden[currPosition.x][currPosition.y + 1] = 0;

    } else if (nextMove === 'left') {
      carrotsEaten += garden[currPosition.x - 1][currPosition.y];
      currPosition.y = currPosition.x - 1;
      garden[currPosition.x - 1][currPosition.y] = 0;
    }
  }
  return carrotsEaten;
}

// takes in a garden and returns a rabbit position object with
// the starting position for that garden
function getRabbitStartingPosition(garden) {
  var gardenlength = garden.length; // The x variable
  var gardenHeight = 0; // the y variable
  // Make sure we don't make an out of bounds check
  if (gardenlength > 0) {
      // We can just get the length of the array at garden[0] since
      // we are allowed to assume this is rectangular 2d array
      gardenHeight = garden[0].length;
  }

  var lengthOdd = gardenLength % 2 == 1;
  var heightOdd = gardenHeight % 2 == 1;

  var midLength = Math.floor(gardenlength / 2);
  var midHeight = Math.floor(gardenHeight / 2);
  // The simplest case, a true middle in the garden
  if(lengthOdd && heightOdd) {
    var result = {
      x: midLength,
      y: midHeight,
    }
    return result;
  } else if (lengthOdd && !heightOdd) {
    var yResult = garden[midLength][midHeight] > garden[midLength][midHeight - 1] ?
      midHeight :
      midHeight - 1;
    var result = {
      x: midLength,
      y: yResult,
    }
    return result;
  } else if (!lengthOdd && heightOdd) {
    var xResult = garden[midLength][midHeight] > garden[midLength -1][midHeight] ?
      midLength :
      midLength - 1;
    var result = {
      x: xResult,
      y: midHeight,
    }
    return result;
  } else {
    // The case where we need to check all 4 values to see
    // which one is the greatest 
    // we need to check the values at :
    // garden[midLength][midHeight]
    // garden[midLength][midHeight - 1]
    // garden[midLength - 1][midHeight]
    // garden[midLength - 1][midHeight - 1]
    var maxResult = garden[midLength][midHeight];
    var xResult = midLength;
    var yResult = midHeight;

    // For the sake of time I'm not gonna bother checking 
    // bounds here rn, but would want to in production
    if (garden[midLength][midHeight - 1] > maxResult) {
      maxResult = garden[midLength][midHeight - 1];
      xResult = midLength;
      yResult = midHeight - 1;
    }
    if (garden[midLength - 1][midHeight] > maxResult) {
      maxResult = garden[midLength -1][midHeight];
      xResult = midLength - 1;
      yResult = midHeight;
    }
    if (garden[midLength - 1][midHeight - 1] > maxResult) {
      maxResult = garden[midLength - 1][midHeight - 1];
      xResult = midLength - 1;
      yResult = midHeight - 1;
    }
    var result = {
      x: xResult, 
      y: yResult,
    }
  }
}

// Helper function 

// Function to get the values surrounding the rabbit in it's current position
// Would take in the garden, and the position of the rabbit as an x coordinate and y coordinate
// Would return an Object representing where the rabbit should go, or return sleep? 
function nextMove(garden, rabbitPosition) {
    // We set move to sleep by default, if there isn't a better move then we return that
    // and are done
    var moveResult = 0; 
    var move = 'sleep';
    var gardenlength = garden.length; // The x variable
    var gardenHeight = 0; // the y variable
    // Make sure we don't make an out of bounds check
    if (gardenlength > 0) {
        // We can just get the length of the array at garden[0] since
        // we are allowed to assume this is rectangular 2d array
        gardenHeight = garden[0].length;
    }
    
    // Now we need to check the four locations, making sure they are in bounds, and 
    // then seeing if they are higher than our current moveResult. We will check both the
    // values that we are changing and the ones we are not just in case we somehow got passed
    // an out of bounds rabbit position. We would want some kind of error handing for this case
    // in our production rabbit garden checker :p
    
    // up = rabbitPosition.x, rabbitPosition.y - 1
    if (inBounds(gardenlength, gardenHeight, rabbitPosition.x, rabbitPosition.y - 1)) {
      if(garden[rabbitPosition.x][rabbitPosition.y - 1] > moveResult) {
        moveResult = garden[rabbitPosition.x][rabbitPosition.y - 1];
        move = 'up';
      }
    }
    // right = rabbitPosition.x + 1, rabbitPosition.y
    if (inBounds(gardenlength, gardenHeight, rabbitPosition.x + 1, rabbitPosition.y)) {
      if(garden[rabbitPosition.x + 1][rabbitPosition.y] > moveResult) {
        moveResult = garden[rabbitPosition.x + 1][rabbitPosition.y];
        move = 'right';
      }
    }
    // down = rabbitPosition.x, rabbitPosition.y + 1
    if (inBounds(gardenlength, gardenHeight, rabbitPosition.x, rabbitPosition.y + 1)) {
      if(garden[rabbitPosition.x][rabbitPosition.y + 1] > moveResult) {
        moveResult = garden[rabbitPosition.x][rabbitPosition.y + 1];
        move = 'down';
      }
    }
    // left = rabbitPosition.x - 1, rabbitPosition.y
    if (inBounds(gardenlength, gardenHeight, rabbitPosition.x - 1, rabbitPosition.y)) {
      if(garden[rabbitPosition.x - 1][rabbitPosition.y] > moveResult) {
        moveResult = garden[rabbitPosition.x - 1][rabbitPosition.y];
        move = 'left';
      }
    }
    return move;
}


// checks if a value is in bounds, returns true if it is, and false otherwise
function inBounds(length, height, x, y) {
    return x >= 0 && x < length && y >= 0 && y < height;
}

