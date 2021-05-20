## Built-in functions ##

### Table of Contents
- [setB(location, block, relative)](#setblocation-block-relative)
- [tp(location, relative)](#tplocation-relative)
- [fill(from, x, y, z, block, relative)](#fillfrom-x-y-z-block-relative)
- [dig(from, x, y, z, relative)](#digfrom-x-y-z-relative)


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

### tp(location, relative)
- `location` \<Vector3\> The location to teleport to.
- `relative` \<bool\> Specify whether the location should be relative to the player or absolute.

Teleports the player to a location.

Example usage:
```java
// Teleport the player 5 blocks up
tp(<0, 5, 0>, true)
```

```java
// Teleport the player to coordinates <0, 255, 0>
tp(<0, 255, 0>, false)
```

### fill(from, x, y, z, block, relative)
- `from` \<Vector3\> The southwest most point of the fill region.
- `x` \<num\> x coordinate of the fill region end corner.
- `y` \<num\> y coordinate of the fill region end corner.
- `z` \<num\> z coordinate of the fill region end corner.
- `block` \<block\> The type of block to fill with.
- `relative` \<bool\> Specify whether the fill region should be relative to the player or absolute.

Fills an area with blocks.

Example usage:
```java
// Fill a 10x10x10 area with dirt blocks two blocks in front of the player
fill(<2, 0, 0>, 12, 10, 10, #dirt, true)
```

```java
// Fill a 10x10x10 area with dirt blocks from world coordinates <0, 240, 0> to <10, 250, 10>
fill(<0, 240, 0>, 10, 10, 10, #dirt, false)
```

### dig(from, x, y, z, relative)
- `from` \<Vector3\> The southwest most point of the dig region.
- `x` \<num\> x coordinate of the dig region end corner.
- `y` \<num\> y coordinate of the dig region end corner.
- `z` \<num\> z coordinate of the dig region end corner.
- `relative` \<bool\> Specify whether the dig region should be relative to the player or absolute.

Fills an area with air blocks (nothing).

Example usage:
```java
// Dig a 10x10x10 area in front of the player
dig(<2, 0, 0>, 12, 10, 10, true)
```

```java
// Dig a 10x10x10 area from world coordinates <0, 240, 0> to <10, 250, 10>
dig(<0, 240, 0>, 10, 10, 10, false)
```



