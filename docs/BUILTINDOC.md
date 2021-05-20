## Built-in functions ##

### Table of Contents
- test
- test


### setB(location, block, relative)
- `location` \<Vector3\> The location to place the block.
- `block` \<block\> The type of block to place.
- `relative` \<bool\> Specify whether the location should be relative to the player or absolute.

Places a block in Minecraft.

Example usage:
```java
// Place a dirt block below the player
setB(<0, -1, 0>, #dirt, true)
```

```java
// Place a dirt block at world coordinate <0, 240, 0>
setB(<0, 240, 0>, #dirt, true)
```




