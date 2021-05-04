grammar Minespeak;



prog
locals [Scope scope]
        : START newlines blocks? STOP
        ;

blocks : (block newlines?)+
       ;

block
locals [Scope scope]
        : mcFunc
        | func
        ;

mcFunc: MCKEY Newline func;

func
locals [Scope scope, Type type]
        : FUNC funcSignature DO newlines funcBody newlines? ENDFUNC Newline
        ;

funcSignature
locals [Type type, boolean isDuplicate]
        : ID LPAREN params RPAREN (RETARROW primaryType)?
        ;

params : param (COMMA param)*
       |
       ;

param
locals [Type type]
        : ID COLON primaryType
        ;

funcBody
locals [Scope scope, Type type]
        : (stmnts)? (retVal)? ;

retVal
returns [Type type]
        : RETURN (expr)?
        ;

stmnts : (stmnt newlines)+
       ;

stmnt : dcls
      | assign
      | instan
      | ifStmnt
      | loop
      | MCStmnt
      | funcCall
      ;

loop : (forStmnt | foreach | whileStmnt | doWhile)
     ;

doWhile
locals [Scope scope]
        : DO newlines body WHILE expr ENDWHILE
        ;

whileStmnt
locals [Scope scope]
        : WHILE expr DO newlines body ENDWHILE
        ;

foreach
locals [Scope scope]
        : FOREACH foreachInit DO newlines body ENDFOR
        ;

foreachInit
locals [Type type]
        : primaryType ID IN expr
        ;

forStmnt
locals [Scope scope]
        : FOR instan UNTIL expr WHERE assign DO newlines body ENDFOR
        ;

ifStmnt
locals [Scope scope]
        : IF expr DO newlines body (ELIF expr DO newlines body)* (ELSE DO newlines body)? ENDIF
        ;

body
locals [Scope scope]
        : stmnts?
        ;

modifiers : CONST
       | VAR
       ;

dcls : modifiers ID COLON primaryType (COMMA ID COLON primaryType)*
     ;

instan : modifiers ID COLON primaryType ASSIGN initialValue (COMMA ID COLON primaryType ASSIGN initialValue)*
       ;

initialValue : rArray
             | expr
             ;

rArray
returns [Type type]
        : LSQUARE (expr (COMMA expr)*)? RSQUARE
        ;

arrayAccess
returns [Type type]
        : ID LSQUARE expr RSQUARE
        ;

expr
returns [Type type]
        : op=(NOT | SUB)? factor                                # NotNegFac
        | <assoc=right> expr POW expr                        # Pow
        | expr op=(TIMES | DIV | MOD) expr                      # MulDivMod
        | expr op=(ADD | SUB) expr                              # AddSub
        | expr op=(LESSER | GREATER | LESSEQ | GREATEQ) expr    # relations
        | expr op=(EQUAL | NOTEQUAL) expr                       # equality
        | expr AND expr                                      # and
        | expr OR expr                                       # or
        ;

factor
returns [Type type]
        : (LPAREN expr RPAREN | rvalue | literal | funcCall | arrayAccess | rArray)
        ;

rvalue
returns [Type type]
        : ID
        ;

funcCall
returns [Type type]
        : ID LPAREN (expr (COMMA expr)*)? RPAREN
        ;

assign
        : ID (ASSIGN | compAssign) expr
        | arrayAccess (ASSIGN | compAssign) expr
        ;

compAssign : op=(MODASSIGN | MULTASSIGN | DIVASSIGN | ADDASSIGN | SUBASSIGN)
           ;

primaryType
returns [Type type]
        :  primitiveType
        |  primitiveType lArray
        |  primitiveType ARRAY
        ;

lArray
        : LSQUARE expr? RSQUARE
        ;

primitiveType
returns [Type type]
        : NUM
        | BLOCK
        | BOOL
        | STRING
        | VECTOR2
        | VECTOR3
        ;

literal
returns [Type type]
        :  booleanLiteral
        |  BlockLiteral
        |  numberLiteral
        |  StringLiteral
        |  vector2Literal
        |  vector3Literal
        ;

numberLiteral : DecimalDigit
              | HexadecimalDigit
              ;

booleanLiteral : TRUE | FALSE
               ;

vector2Literal : LESSER expr COMMA expr GREATER
               ;

vector3Literal : LESSER expr COMMA expr COMMA expr GREATER
               ;


newlines : Newline+ //-> skip
         ;

HexadecimalDigit : '0' ('x' | 'X') [1-9a-fA-F][0-9a-fA-F]*
                 ;

DecimalDigit : [0-9]+
             ;

// Allow all characters but not \r \n \u0085 \u2028 \u2029
StringLiteral : QUOTE (~["\\\r\n\u0085\u2028\u2029])* QUOTE
              ;



Whitespace : [ \t] + -> skip
           ;

Newline : ('\n' | '\r\n' | '\r') //-> skip
        ;

BlockComment: '/*' .*? '*/' -> skip;

LineComment: '//' ~ [\r\n]* -> skip;

BlockLiteral : '#'('ACACIA_BUTTON' | 'AIR' | 'ACACIA_DOOR' | 'ACACIA_FENCE' | 'ACACIA_FENCE_GATE' | 'ACACIA_LEAVES' | 'ACACIA_LOG' | 'ACACIA_PLANKS' | 'ACACIA_SAPLING' | 'ACACIA_SLAB' | 'ACACIA_TRAPDOOR' | 'ACACIA_WOOD' | 'ACTIVATOR_RAIL' | 'ALLIUM' | 'ANCIENT_DEBRIS' | 'ANDESITE' | 'ANDESITE_SLAB' | 'ANDESITE_STAIRS' | 'ANDESITE_WALL' | 'ANVIL' | 'AZURE_BLUET' | 'BAMBOO' | 'BAMBOO_SAPLING' | 'BEETROOTS' | 'BARREL' | 'BARRIER' | 'BASALT' | 'BEACON' | 'BEDROCK' | 'BEEHIVE' | 'BEE_NEST' | 'BELL' | 'BIRCH_BUTTON' | 'BIRCH_DOOR' | 'BIRCH_FENCE' | 'BIRCH_FENCE_GATE' | 'BIRCH_LEAVES' | 'BIRCH_LOG' | 'BIRCH_PLANKS' | 'BIRCH_PRESSURE_PLATE' | 'BIRCH_SAPLING' | 'BIRCH_SIGN' | 'BIRCH_SLAB' | 'BIRCH_STAIRS' | 'BIRCH_TRAPDOOR' | 'BIRCH_WOOD' | 'BLACK_BANNER' | 'BLACK_BED' | 'BLACK_CARPET' | 'BLACK_CONCRETE' | 'BLACK_CONCRETE_POWDER' | 'BLACK_GLAZED_TERRACOTTA' | 'BLACK_SHULKER_BOX' | 'BLACK_STAINED_GLASS' | 'BLACK_STAINED_GLASS_PANE' | 'BLACK_TERRACOTTA' | 'BLACK_WOOL' | 'BLACKSTONE' | 'BLACKSTONE_SLAB' | 'BLACKSTONE_STAIRS' | 'BLACKSTONE_WALL' | 'BLAST_FURNACE' | 'COAL_BLOCK' | 'DIAMOND_BLOCK' | 'EMERALD_BLOCK' | 'GOLD_BLOCK' | 'IRON_BLOCK' | 'NETHERITE_BLOCK' | 'QUARTZ_BLOCK' | 'REDSTONE_BLOCK' | 'BLUE_BANNER' | 'BLUE_BED' | 'BLUE_CARPET' | 'BLUE_CONCRETE' | 'BLUE_CONCRETE_POWDER' | 'BLUE_GLAZED_TERRACOTTA' | 'BLUE_ICE' | 'BLUE_ORCHID' | 'BLUE_SHULKER_BOX' | 'BLUE_STAINED_GLASS' | 'BLUE_STAINED_GLASS_PANE' | 'BLUE_TERRACOTTA' | 'BLUE_WOOL' | 'BONE_BLOCK' | 'BOOKSHELF' | 'BRAIN_CORAL' | 'BRAIN_CORAL_BLOCK' | 'BRAIN_CORAL_FAN' | 'BREWING_STAND' | 'BRICK_SLAB' | 'BRICK_STAIRS' | 'BRICK_WALL' | 'BRICKS' | 'BROWN_BANNER' | 'BROWN_BED' | 'BROWN_CARPET' | 'BROWN_CONCRETE' | 'BROWN_CONCRETE_POWDER' | 'BROWN_GLAZED_TERRACOTTA' | 'BROWN_MUSHROOM' | 'BROWN_MUSHROOM_BLOCK' | 'BROWN_SHULKER_BOX' | 'BROWN_STAINED_GLASS' | 'BROWN_STAINED_GLASS_PANE' | 'BROWN_TERRACOTTA' | 'BROWN_WOOL' | 'BUBBLE_CORAL' | 'BUBBLE_CORAL_BLOCK' | 'BUBBLE_CORAL_FAN' | 'CACTUS' | 'CAKE' | 'CAMPFIRE' | 'CARROTS' | 'CARTOGRAPHY_TABLE' | 'CARVED_PUMPKIN' | 'CAULDRON' | 'CHAIN' | 'CHAIN_COMMAND_BLOCK' | 'CHEST' | 'CHIPPED_ANVIL' | 'CHISELED_NETHER_BRICKS' | 'CHISELED_POLISHED_BLACKSTONE' | 'CHISELED_QUARTZ_BLOCK' | 'CHISELED_RED_SANDSTONE' | 'CHISELED_SANDSTONE' | 'CHISELED_STONE_BRICKS' | 'CHORUS_FLOWER' | 'CHORUS_PLANT' | 'CLAY' | 'COAL_ORE' | 'COARSE_DIRT' | 'COBBLESTONE' | 'COBBLESTONE_SLAB' | 'COBBLESTONE_STAIRS' | 'COBBLESTONE_WALL' | 'COBWEB' | 'COCOA' | 'COMMAND_BLOCK' | 'COMPOSTER' | 'CONDUIT' | 'CORNFLOWER' | 'CRACKED_NETHER_BRICKS' | 'CRACKED_POLISHED_BLACKSTONE_BRICKS' | 'CRACKED_STONE_BRICKS' | 'CRAFTING_TABLE' | 'CREEPER_HEAD' | 'CRIMSON_BUTTON' | 'CRIMSON_DOOR' | 'CRIMSON_FENCE' | 'CRIMSON_FENCE_GATE' | 'CRIMSON_FUNGUS' | 'CRIMSON_HYPHAE' | 'CRIMSON_NYLIUM' | 'CRIMSON_PLANKS' | 'CRIMSON_PRESSURE_PLATE' | 'CRIMSON_ROOTS' | 'CRIMSON_SIGN' | 'CRIMSON_SLAB' | 'CRIMSON_STAIRS' | 'CRIMSON_STEM' | 'CRIMSON_TRAPDOOR' | 'CRYING_OBSIDIAN' | 'CUT_RED_SANDSTONE' | 'CUT_RED_SANDSTONE_SLAB' | 'CUT_SANDSTONE' | 'CUT_SANDSTONE_SLAB' | 'CYAN_BANNER' | 'CYAN_BED' | 'CYAN_CARPET' | 'CYAN_CONCRETE' | 'CYAN_CONCRETE_POWDER' | 'CYAN_GLAZED_TERRACOTTA' | 'CYAN_SHULKER_BOX' | 'CYAN_STAINED_GLASS' | 'CYAN_STAINED_GLASS_PANE' | 'CYAN_TERRACOTTA' | 'CYAN_WOOL' | 'DAMAGED_ANVIL' | 'DANDELION' | 'DARK_OAK_BUTTON' | 'DARK_OAK_DOOR' | 'DARK_OAK_FENCE' | 'DARK_OAK_FENCE_GATE' | 'DARK_OAK_LEAVES' | 'DARK_OAK_LOG' | 'DARK_OAK_PLANKS' | 'DARK_OAK_PRESSURE_PLATE' | 'DARK_OAK_SAPLING' | 'DARK_OAK_SIGN' | 'DARK_OAK_SLAB' | 'DARK_OAK_STAIRS' | 'DARK_OAK_TRAPDOOR' | 'DARK_OAK_WOOD' | 'DARK_PRISMARINE' | 'DARK_PRISMARINE_SLAB' | 'DARK_PRISMARINE_STAIRS' | 'DAYLIGHT_DETECTOR' | 'DEAD_BRAIN_CORAL' | 'DEAD_BRAIN_CORAL_BLOCK' | 'DEAD_BRAIN_CORAL_FAN' | 'DEAD_BUBBLE_CORAL' | 'DEAD_BUBBLE_CORAL_BLOCK' | 'DEAD_BUBBLE_CORAL_FAN' | 'DEAD_BUSH' | 'DEAD_FIRE_CORAL' | 'DEAD_FIRE_CORAL_BLOCK' | 'DEAD_FIRE_CORAL_FAN' | 'DEAD_HORN_CORAL' | 'DEAD_HORN_CORAL_BLOCK' | 'DEAD_HORN_CORAL_FAN' | 'DEAD_TUBE_CORAL' | 'DEAD_TUBE_CORAL_BLOCK' | 'DEAD_TUBE_CORAL_FAN' | 'DETECTOR_RAIL' | 'DIAMOND_ORE' | 'DIORITE' | 'DIORITE_SLAB' | 'DIORITE_STAIRS' | 'DIORITE_WALL' | 'DIRT' | 'DISPENSER' | 'DRAGON_EGG' | 'DRAGON_HEAD' | 'DRIED_KELP_BLOCK' | 'DROPPER' | 'EMERALD_ORE' | 'ENCHANTING_TABLE' | 'END_PORTAL_FRAME' | 'END_ROD' | 'END_STONE' | 'END_STONE_BRICK_SLAB' | 'END_STONE_BRICK_STAIRS' | 'END_STONE_BRICK_WALL' | 'END_STONE_BRICKS' | 'ENDER_CHEST' | 'FARMLAND' | 'FERN' | 'FIRE' | 'FIRE_CORAL' | 'FIRE_CORAL_BLOCK' | 'FIRE_CORAL_FAN' | 'FLETCHING_TABLE' | 'FLOWER_POT' | 'FURNACE' | 'GILDED_BLACKSTONE' | 'GLASS' | 'GLASS_PANE' | 'GLOWSTONE' | 'GOLD_ORE' | 'GRANITE' | 'GRANITE_SLAB' | 'GRANITE_STAIRS' | 'GRANITE_WALL' | 'GRASS' | 'GRASS_BLOCK' | 'GRASS_PATH' | 'GRAVEL' | 'GRAY_BANNER' | 'GRAY_BED' | 'GRAY_CARPET' | 'GRAY_CONCRETE' | 'GRAY_CONCRETE_POWDER' | 'GRAY_GLAZED_TERRACOTTA' | 'GRAY_SHULKER_BOX' | 'GRAY_STAINED_GLASS' | 'GRAY_STAINED_GLASS_PANE' | 'GRAY_TERRACOTTA' | 'GRAY_WOOL' | 'GREEN_BANNER' | 'GREEN_BED' | 'GREEN_CARPET' | 'GREEN_CONCRETE' | 'GREEN_CONCRETE_POWDER' | 'GREEN_GLAZED_TERRACOTTA' | 'GREEN_SHULKER_BOX' | 'GREEN_STAINED_GLASS' | 'GREEN_STAINED_GLASS_PANE' | 'GREEN_TERRACOTTA' | 'GREEN_WOOL' | 'GRINDSTONE' | 'HAY_BLOCK' | 'HEAVY_WEIGHTED_PRESSURE_PLATE' | 'HONEY_BLOCK' | 'HONEYCOMB_BLOCK' | 'HOPPER' | 'HORN_CORAL' | 'HORN_CORAL_BLOCK' | 'HORN_CORAL_FAN' | 'ICE' | 'INFESTED_CHISELED_STONE_BRICKS' | 'INFESTED_COBBLESTONE' | 'INFESTED_CRACKED_STONE_BRICKS' | 'INFESTED_MOSSY_STONE_BRICKS' | 'INFESTED_STONE' | 'INFESTED_STONE_BRICKS' | 'IRON_BARS' | 'IRON_DOOR' | 'IRON_ORE' | 'IRON_TRAPDOOR' | 'JACK_O_LANTERN' | 'JIGSAW' | 'JUKEBOX' | 'JUNGLE_BUTTON' | 'JUNGLE_DOOR' | 'JUNGLE_FENCE' | 'JUNGLE_FENCE_GATE' | 'JUNGLE_LEAVES' | 'JUNGLE_LOG' | 'JUNGLE_PLANKS' | 'JUNGLE_PRESSURE_PLATE' | 'JUNGLE_SAPLING' | 'JUNGLE_SIGN' | 'JUNGLE_SLAB' | 'JUNGLE_STAIRS' | 'JUNGLE_TRAPDOOR' | 'JUNGLE_WOOD' | 'LADDER' | 'LANTERN' | 'LAPIS_BLOCK' | 'LAPIS_ORE' | 'LARGE_FERN' | 'LAVA' | 'LECTERN' | 'LEVER' | 'LIGHT_BLUE_BANNER' | 'LIGHT_BLUE_BED' | 'LIGHT_BLUE_CARPET' | 'LIGHT_BLUE_CONCRETE' | 'LIGHT_BLUE_CONCRETE_POWDER' | 'LIGHT_BLUE_GLAZED_TERRACOTTA' | 'LIGHT_BLUE_SHULKER_BOX' | 'LIGHT_BLUE_STAINED_GLASS' | 'LIGHT_BLUE_STAINED_GLASS_PANE' | 'LIGHT_BLUE_TERRACOTTA' | 'LIGHT_BLUE_WOOL' | 'LIGHT_GRAY_BANNER' | 'LIGHT_GRAY_BED' | 'LIGHT_GRAY_CARPET' | 'LIGHT_GRAY_CONCRETE' | 'LIGHT_GRAY_CONCRETE_POWDER' | 'LIGHT_GRAY_GLAZED_TERRACOTTA' | 'LIGHT_GRAY_SHULKER_BOX' | 'LIGHT_GRAY_STAINED_GLASS' | 'LIGHT_GRAY_STAINED_GLASS_PANE' | 'LIGHT_GRAY_TERRACOTTA' | 'LIGHT_GRAY_WOOL' | 'LIGHT_WEIGHTED_PRESSURE_PLATE' | 'LILAC' | 'LILY_OF_THE_VALLEY' | 'LILY_PAD' | 'LIME_BANNER' | 'LIME_BED' | 'LIME_CARPET' | 'LIME_CONCRETE' | 'LIME_CONCRETE_POWDER' | 'LIME_GLAZED_TERRACOTTA' | 'LIME_SHULKER_BOX' | 'LIME_STAINED_GLASS' | 'LIME_STAINED_GLASS_PANE' | 'LIME_TERRACOTTA' | 'LIME_WOOL' | 'LODESTONE' | 'LOOM' | 'MAGENTA_BANNER' | 'MAGENTA_BED' | 'MAGENTA_CARPET' | 'MAGENTA_CONCRETE' | 'MAGENTA_CONCRETE_POWDER' | 'MAGENTA_GLAZED_TERRACOTTA' | 'MAGENTA_SHULKER_BOX' | 'MAGENTA_STAINED_GLASS' | 'MAGENTA_STAINED_GLASS_PANE' | 'MAGENTA_TERRACOTTA' | 'MAGENTA_WOOL' | 'MAGMA_BLOCK' | 'MELON' | 'MELON_STEM' | 'MOSSY_COBBLESTONE' | 'MOSSY_COBBLESTONE_SLAB' | 'MOSSY_COBBLESTONE_STAIRS' | 'MOSSY_COBBLESTONE_WALL' | 'MOSSY_STONE_BRICK_SLAB' | 'MOSSY_STONE_BRICK_STAIRS' | 'MOSSY_STONE_BRICK_WALL' | 'MOSSY_STONE_BRICKS' | 'MUSHROOM_STEM' | 'MYCELIUM' | 'NETHER_BRICK_FENCE' | 'NETHER_BRICK_SLAB' | 'NETHER_BRICK_STAIRS' | 'NETHER_BRICK_WALL' | 'NETHER_BRICKS' | 'NETHER_GOLD_ORE' | 'NETHER_QUARTZ_ORE' | 'NETHER_SPROUTS' | 'NETHER_WART' | 'NETHER_WART_BLOCK' | 'NETHERRACK' | 'NOTE_BLOCK' | 'OAK_BUTTON' | 'OAK_DOOR' | 'OAK_FENCE' | 'OAK_FENCE_GATE' | 'OAK_LEAVES' | 'OAK_LOG' | 'OAK_PLANKS' | 'OAK_PRESSURE_PLATE' | 'OAK_SAPLING' | 'OAK_SIGN' | 'OAK_SLAB' | 'OAK_STAIRS' | 'OAK_TRAPDOOR' | 'OAK_WOOD' | 'OBSERVER' | 'OBSIDIAN' | 'ORANGE_BANNER' | 'ORANGE_BED' | 'ORANGE_CARPET' | 'ORANGE_CONCRETE' | 'ORANGE_CONCRETE_POWDER' | 'ORANGE_GLAZED_TERRACOTTA' | 'ORANGE_SHULKER_BOX' | 'ORANGE_STAINED_GLASS' | 'ORANGE_STAINED_GLASS_PANE' | 'ORANGE_TERRACOTTA' | 'ORANGE_TULIP' | 'ORANGE_WOOL' | 'OXEYE_DAISY' | 'PACKED_ICE' | 'PEONY' | 'PETRIFIED_OAK_SLAB' | 'PINK_BANNER' | 'PINK_BED' | 'PINK_CARPET' | 'PINK_CONCRETE' | 'PINK_CONCRETE_POWDER' | 'PINK_GLAZED_TERRACOTTA' | 'PINK_SHULKER_BOX' | 'PINK_STAINED_GLASS' | 'PINK_STAINED_GLASS_PANE' | 'PINK_TERRACOTTA' | 'PINK_TULIP' | 'PINK_WOOL' | 'PISTON' | 'PLAYER_HEAD' | 'PODZOL' | 'POLISHED_ANDESITE' | 'POLISHED_ANDESITE_SLAB' | 'POLISHED_ANDESITE_STAIRS' | 'POLISHED_BASALT' | 'POLISHED_BLACKSTONE' | 'POLISHED_BLACKSTONE_BRICK_SLAB' | 'POLISHED_BLACKSTONE_BRICK_STAIRS' | 'POLISHED_BLACKSTONE_BRICK_WALL' | 'POLISHED_BLACKSTONE_BRICKS' | 'POLISHED_BLACKSTONE_BUTTON' | 'POLISHED_BLACKSTONE_PRESSURE_PLATE' | 'POLISHED_BLACKSTONE_SLAB' | 'POLISHED_BLACKSTONE_STAIRS' | 'POLISHED_BLACKSTONE_WALL' | 'POLISHED_DIORITE' | 'POLISHED_DIORITE_SLAB' | 'POLISHED_DIORITE_STAIRS' | 'POLISHED_GRANITE' | 'POLISHED_GRANITE_SLAB' | 'POLISHED_GRANITE_STAIRS' | 'POPPY' | 'POTATOES' | 'POWERED_RAIL' | 'PRISMARINE' | 'PRISMARINE_BRICK_SLAB' | 'PRISMARINE_BRICK_STAIRS' | 'PRISMARINE_BRICKS' | 'PRISMARINE_SLAB' | 'PRISMARINE_STAIRS' | 'PRISMARINE_WALL' | 'PUMPKIN' | 'PUMPKIN_STEM' | 'PURPLE_BANNER' | 'PURPLE_BED' | 'PURPLE_CARPET' | 'PURPLE_CONCRETE' | 'PURPLE_CONCRETE_POWDER' | 'PURPLE_GLAZED_TERRACOTTA' | 'PURPLE_SHULKER_BOX' | 'PURPLE_STAINED_GLASS' | 'PURPLE_STAINED_GLASS_PANE' | 'PURPLE_TERRACOTTA' | 'PURPLE_WOOL' | 'PURPUR_BLOCK' | 'PURPUR_PILLAR' | 'PURPUR_SLAB' | 'PURPUR_STAIRS' | 'QUARTZ_BRICKS' | 'QUARTZ_PILLAR' | 'QUARTZ_SLAB' | 'QUARTZ_STAIRS' | 'RAIL' | 'RED_BANNER' | 'RED_BED' | 'RED_CARPET' | 'RED_CONCRETE' | 'RED_CONCRETE_POWDER' | 'RED_GLAZED_TERRACOTTA' | 'RED_MUSHROOM' | 'RED_MUSHROOM_BLOCK' | 'RED_NETHER_BRICK_SLAB' | 'RED_NETHER_BRICK_STAIRS' | 'RED_NETHER_BRICK_WALL' | 'RED_NETHER_BRICKS' | 'RED_SAND' | 'RED_SANDSTONE' | 'RED_SANDSTONE_SLAB' | 'RED_SANDSTONE_STAIRS' | 'RED_SANDSTONE_WALL' | 'RED_SHULKER_BOX' | 'RED_STAINED_GLASS' | 'RED_STAINED_GLASS_PANE' | 'RED_TERRACOTTA' | 'RED_TULIP' | 'RED_WOOL' | 'COMPARATOR' | 'REDSTONE_LAMP' | 'REDSTONE_ORE' | 'REPEATER' | 'REDSTONE_TORCH' | 'REPEATING_COMMAND_BLOCK' | 'RESPAWN_ANCHOR' | 'ROSE_BUSH' | 'SAND' | 'SANDSTONE' | 'SANDSTONE_SLAB' | 'SANDSTONE_STAIRS' | 'SANDSTONE_WALL' | 'SCAFFOLDING' | 'SEA_LANTERN' | 'SEA_PICKLE' | 'SEAGRASS' | 'SHROOMLIGHT' | 'SHULKER_BOX' | 'SKELETON_SKULL' | 'SLIME_BLOCK' | 'SMITHING_TABLE' | 'SMOKER' | 'QUARTZ_BLOCK' | 'SMOOTH_QUARTZ_SLAB' | 'SMOOTH_QUARTZ_STAIRS' | 'SMOOTH_RED_SANDSTONE' | 'SMOOTH_RED_SANDSTONE_SLAB' | 'SMOOTH_RED_SANDSTONE_STAIRS' | 'SMOOTH_SANDSTONE' | 'SMOOTH_SANDSTONE_SLAB' | 'SMOOTH_SANDSTONE_STAIRS' | 'SMOOTH_STONE' | 'SMOOTH_STONE_SLAB' | 'SNOW' | 'SNOW_BLOCK' | 'SOUL_CAMPFIRE' | 'SOUL_FIRE' | 'SOUL_LANTERN' | 'SOUL_SAND' | 'SOUL_SOIL' | 'SOUL_TORCH' | 'SPAWNER' | 'SPONGE' | 'SPRUCE_BUTTON' | 'SPRUCE_DOOR' | 'SPRUCE_FENCE' | 'SPRUCE_FENCE_GATE' | 'SPRUCE_LEAVES' | 'SPRUCE_LOG' | 'SPRUCE_PLANKS' | 'SPRUCE_PRESSURE_PLATE' | 'SPRUCE_SAPLING' | 'SPRUCE_SIGN' | 'SPRUCE_SLAB' | 'SPRUCE_STAIRS' | 'SPRUCE_TRAPDOOR' | 'SPRUCE_WOOD' | 'STICKY_PISTON' | 'STONE' | 'STONE_BRICK_SLAB' | 'STONE_BRICK_STAIRS' | 'STONE_BRICK_WALL' | 'STONE_BRICKS' | 'STONE_BUTTON' | 'STONE_PRESSURE_PLATE' | 'STONE_SLAB' | 'STONE_STAIRS' | 'STONECUTTER' | 'STRIPPED_ACACIA_LOG' | 'STRIPPED_ACACIA_WOOD' | 'STRIPPED_BIRCH_LOG' | 'STRIPPED_BIRCH_WOOD' | 'STRIPPED_CRIMSON_HYPHAE' | 'STRIPPED_CRIMSON_STEM' | 'STRIPPED_DARK_OAK_LOG' | 'STRIPPED_DARK_OAK_WOOD' | 'STRIPPED_JUNGLE_LOG' | 'STRIPPED_JUNGLE_WOOD' | 'STRIPPED_OAK_LOG' | 'STRIPPED_OAK_WOOD' | 'STRIPPED_SPRUCE_LOG' | 'STRIPPED_SPRUCE_WOOD' | 'STRIPPED_WARPED_HYPHAE' | 'STRIPPED_WARPED_STEM' | 'STRUCTURE_BLOCK' | 'STRUCTURE_VOID' | 'SUGAR_CANE' | 'SUNFLOWER' | 'SWEET_BERRY_BUSH' | 'TALL_GRASS' | 'TALL_SEAGRASS' | 'TARGET' | 'TERRACOTTA' | 'TNT' | 'TORCH' | 'TRAPPED_CHEST' | 'TRIPWIRE' | 'TRIPWIRE_HOOK' | 'TUBE_CORAL' | 'TUBE_CORAL_BLOCK' | 'TUBE_CORAL_FAN' | 'TURTLE_EGG' | 'TWISTING_VINES' | 'VINE' | 'WARPED_BUTTON' | 'WARPED_DOOR' | 'WARPED_FENCE' | 'WARPED_FENCE_GATE' | 'WARPED_FUNGUS' | 'WARPED_HYPHAE' | 'WARPED_NYLIUM' | 'WARPED_PLANKS' | 'WARPED_PRESSURE_PLATE' | 'WARPED_ROOTS' | 'WARPED_SIGN' | 'WARPED_SLAB' | 'WARPED_STAIRS' | 'WARPED_STEM' | 'WARPED_TRAPDOOR' | 'WARPED_WART_BLOCK' | 'WATER' | 'WEEPING_VINES' | 'WET_SPONGE' | 'WHEAT' | 'WHITE_BANNER' | 'WHITE_BED' | 'WHITE_CARPET' | 'WHITE_CONCRETE' | 'WHITE_CONCRETE_POWDER' | 'WHITE_GLAZED_TERRACOTTA' | 'WHITE_SHULKER_BOX' | 'WHITE_STAINED_GLASS' | 'WHITE_STAINED_GLASS_PANE' | 'WHITE_TERRACOTTA' | 'WHITE_TULIP' | 'WHITE_WOOL' | 'WITHER_ROSE' | 'WITHER_SKELETON_SKULL' | 'YELLOW_BANNER' | 'YELLOW_BED' | 'YELLOW_CARPET' | 'YELLOW_CONCRETE' | 'YELLOW_CONCRETE_POWDER' | 'YELLOW_GLAZED_TERRACOTTA' | 'YELLOW_SHULKER_BOX' | 'YELLOW_STAINED_GLASS' | 'YELLOW_STAINED_GLASS_PANE' | 'YELLOW_TERRACOTTA' | 'YELLOW_WOOL' | 'ZOMBIE_HEAD')
             | '#'('acacia_button' | 'air' | 'acacia_door' | 'acacia_fence' | 'acacia_fence_gate' | 'acacia_leaves' | 'acacia_log' | 'acacia_planks' | 'acacia_sapling' | 'acacia_slab' | 'acacia_trapdoor' | 'acacia_wood' | 'activator_rail' | 'allium' | 'ancient_debris' | 'andesite' | 'andesite_slab' | 'andesite_stairs' | 'andesite_wall' | 'anvil' | 'azure_bluet' | 'bamboo' | 'bamboo_sapling' | 'beetroots' | 'barrel' | 'barrier' | 'basalt' | 'beacon' | 'bedrock' | 'beehive' | 'bee_nest' | 'bell' | 'birch_button' | 'birch_door' | 'birch_fence' | 'birch_fence_gate' | 'birch_leaves' | 'birch_log' | 'birch_planks' | 'birch_pressure_plate' | 'birch_sapling' | 'birch_sign' | 'birch_slab' | 'birch_stairs' | 'birch_trapdoor' | 'birch_wood' | 'black_banner' | 'black_bed' | 'black_carpet' | 'black_concrete' | 'black_concrete_powder' | 'black_glazed_terracotta' | 'black_shulker_box' | 'black_stained_glass' | 'black_stained_glass_pane' | 'black_terracotta' | 'black_wool' | 'blackstone' | 'blackstone_slab' | 'blackstone_stairs' | 'blackstone_wall' | 'blast_furnace' | 'coal_block' | 'diamond_block' | 'emerald_block' | 'gold_block' | 'iron_block' | 'netherite_block' | 'quartz_block' | 'redstone_block' | 'blue_banner' | 'blue_bed' | 'blue_carpet' | 'blue_concrete' | 'blue_concrete_powder' | 'blue_glazed_terracotta' | 'blue_ice' | 'blue_orchid' | 'blue_shulker_box' | 'blue_stained_glass' | 'blue_stained_glass_pane' | 'blue_terracotta' | 'blue_wool' | 'bone_block' | 'bookshelf' | 'brain_coral' | 'brain_coral_block' | 'brain_coral_fan' | 'brewing_stand' | 'brick_slab' | 'brick_stairs' | 'brick_wall' | 'bricks' | 'brown_banner' | 'brown_bed' | 'brown_carpet' | 'brown_concrete' | 'brown_concrete_powder' | 'brown_glazed_terracotta' | 'brown_mushroom' | 'brown_mushroom_block' | 'brown_shulker_box' | 'brown_stained_glass' | 'brown_stained_glass_pane' | 'brown_terracotta' | 'brown_wool' | 'bubble_coral' | 'bubble_coral_block' | 'bubble_coral_fan' | 'cactus' | 'cake' | 'campfire' | 'carrots' | 'cartography_table' | 'carved_pumpkin' | 'cauldron' | 'chain' | 'chain_command_block' | 'chest' | 'chipped_anvil' | 'chiseled_nether_bricks' | 'chiseled_polished_blackstone' | 'chiseled_quartz_block' | 'chiseled_red_sandstone' | 'chiseled_sandstone' | 'chiseled_stone_bricks' | 'chorus_flower' | 'chorus_plant' | 'clay' | 'coal_ore' | 'coarse_dirt' | 'cobblestone' | 'cobblestone_slab' | 'cobblestone_stairs' | 'cobblestone_wall' | 'cobweb' | 'cocoa' | 'command_block' | 'composter' | 'conduit' | 'cornflower' | 'cracked_nether_bricks' | 'cracked_polished_blackstone_bricks' | 'cracked_stone_bricks' | 'crafting_table' | 'creeper_head' | 'crimson_button' | 'crimson_door' | 'crimson_fence' | 'crimson_fence_gate' | 'crimson_fungus' | 'crimson_hyphae' | 'crimson_nylium' | 'crimson_planks' | 'crimson_pressure_plate' | 'crimson_roots' | 'crimson_sign' | 'crimson_slab' | 'crimson_stairs' | 'crimson_stem' | 'crimson_trapdoor' | 'crying_obsidian' | 'cut_red_sandstone' | 'cut_red_sandstone_slab' | 'cut_sandstone' | 'cut_sandstone_slab' | 'cyan_banner' | 'cyan_bed' | 'cyan_carpet' | 'cyan_concrete' | 'cyan_concrete_powder' | 'cyan_glazed_terracotta' | 'cyan_shulker_box' | 'cyan_stained_glass' | 'cyan_stained_glass_pane' | 'cyan_terracotta' | 'cyan_wool' | 'damaged_anvil' | 'dandelion' | 'dark_oak_button' | 'dark_oak_door' | 'dark_oak_fence' | 'dark_oak_fence_gate' | 'dark_oak_leaves' | 'dark_oak_log' | 'dark_oak_planks' | 'dark_oak_pressure_plate' | 'dark_oak_sapling' | 'dark_oak_sign' | 'dark_oak_slab' | 'dark_oak_stairs' | 'dark_oak_trapdoor' | 'dark_oak_wood' | 'dark_prismarine' | 'dark_prismarine_slab' | 'dark_prismarine_stairs' | 'daylight_detector' | 'dead_brain_coral' | 'dead_brain_coral_block' | 'dead_brain_coral_fan' | 'dead_bubble_coral' | 'dead_bubble_coral_block' | 'dead_bubble_coral_fan' | 'dead_bush' | 'dead_fire_coral' | 'dead_fire_coral_block' | 'dead_fire_coral_fan' | 'dead_horn_coral' | 'dead_horn_coral_block' | 'dead_horn_coral_fan' | 'dead_tube_coral' | 'dead_tube_coral_block' | 'dead_tube_coral_fan' | 'detector_rail' | 'diamond_ore' | 'diorite' | 'diorite_slab' | 'diorite_stairs' | 'diorite_wall' | 'dirt' | 'dispenser' | 'dragon_egg' | 'dragon_head' | 'dried_kelp_block' | 'dropper' | 'emerald_ore' | 'enchanting_table' | 'end_portal_frame' | 'end_rod' | 'end_stone' | 'end_stone_brick_slab' | 'end_stone_brick_stairs' | 'end_stone_brick_wall' | 'end_stone_bricks' | 'ender_chest' | 'farmland' | 'fern' | 'fire' | 'fire_coral' | 'fire_coral_block' | 'fire_coral_fan' | 'fletching_table' | 'flower_pot' | 'furnace' | 'gilded_blackstone' | 'glass' | 'glass_pane' | 'glowstone' | 'gold_ore' | 'granite' | 'granite_slab' | 'granite_stairs' | 'granite_wall' | 'grass' | 'grass_block' | 'grass_path' | 'gravel' | 'gray_banner' | 'gray_bed' | 'gray_carpet' | 'gray_concrete' | 'gray_concrete_powder' | 'gray_glazed_terracotta' | 'gray_shulker_box' | 'gray_stained_glass' | 'gray_stained_glass_pane' | 'gray_terracotta' | 'gray_wool' | 'green_banner' | 'green_bed' | 'green_carpet' | 'green_concrete' | 'green_concrete_powder' | 'green_glazed_terracotta' | 'green_shulker_box' | 'green_stained_glass' | 'green_stained_glass_pane' | 'green_terracotta' | 'green_wool' | 'grindstone' | 'hay_block' | 'heavy_weighted_pressure_plate' | 'honey_block' | 'honeycomb_block' | 'hopper' | 'horn_coral' | 'horn_coral_block' | 'horn_coral_fan' | 'ice' | 'infested_chiseled_stone_bricks' | 'infested_cobblestone' | 'infested_cracked_stone_bricks' | 'infested_mossy_stone_bricks' | 'infested_stone' | 'infested_stone_bricks' | 'iron_bars' | 'iron_door' | 'iron_ore' | 'iron_trapdoor' | 'jack_o_lantern' | 'jigsaw' | 'jukebox' | 'jungle_button' | 'jungle_door' | 'jungle_fence' | 'jungle_fence_gate' | 'jungle_leaves' | 'jungle_log' | 'jungle_planks' | 'jungle_pressure_plate' | 'jungle_sapling' | 'jungle_sign' | 'jungle_slab' | 'jungle_stairs' | 'jungle_trapdoor' | 'jungle_wood' | 'ladder' | 'lantern' | 'lapis_block' | 'lapis_ore' | 'large_fern' | 'lava' | 'lectern' | 'lever' | 'light_blue_banner' | 'light_blue_bed' | 'light_blue_carpet' | 'light_blue_concrete' | 'light_blue_concrete_powder' | 'light_blue_glazed_terracotta' | 'light_blue_shulker_box' | 'light_blue_stained_glass' | 'light_blue_stained_glass_pane' | 'light_blue_terracotta' | 'light_blue_wool' | 'light_gray_banner' | 'light_gray_bed' | 'light_gray_carpet' | 'light_gray_concrete' | 'light_gray_concrete_powder' | 'light_gray_glazed_terracotta' | 'light_gray_shulker_box' | 'light_gray_stained_glass' | 'light_gray_stained_glass_pane' | 'light_gray_terracotta' | 'light_gray_wool' | 'light_weighted_pressure_plate' | 'lilac' | 'lily_of_the_valley' | 'lily_pad' | 'lime_banner' | 'lime_bed' | 'lime_carpet' | 'lime_concrete' | 'lime_concrete_powder' | 'lime_glazed_terracotta' | 'lime_shulker_box' | 'lime_stained_glass' | 'lime_stained_glass_pane' | 'lime_terracotta' | 'lime_wool' | 'lodestone' | 'loom' | 'magenta_banner' | 'magenta_bed' | 'magenta_carpet' | 'magenta_concrete' | 'magenta_concrete_powder' | 'magenta_glazed_terracotta' | 'magenta_shulker_box' | 'magenta_stained_glass' | 'magenta_stained_glass_pane' | 'magenta_terracotta' | 'magenta_wool' | 'magma_block' | 'melon' | 'melon_stem' | 'mossy_cobblestone' | 'mossy_cobblestone_slab' | 'mossy_cobblestone_stairs' | 'mossy_cobblestone_wall' | 'mossy_stone_brick_slab' | 'mossy_stone_brick_stairs' | 'mossy_stone_brick_wall' | 'mossy_stone_bricks' | 'mushroom_stem' | 'mycelium' | 'nether_brick_fence' | 'nether_brick_slab' | 'nether_brick_stairs' | 'nether_brick_wall' | 'nether_bricks' | 'nether_gold_ore' | 'nether_quartz_ore' | 'nether_sprouts' | 'nether_wart' | 'nether_wart_block' | 'netherrack' | 'note_block' | 'oak_button' | 'oak_door' | 'oak_fence' | 'oak_fence_gate' | 'oak_leaves' | 'oak_log' | 'oak_planks' | 'oak_pressure_plate' | 'oak_sapling' | 'oak_sign' | 'oak_slab' | 'oak_stairs' | 'oak_trapdoor' | 'oak_wood' | 'observer' | 'obsidian' | 'orange_banner' | 'orange_bed' | 'orange_carpet' | 'orange_concrete' | 'orange_concrete_powder' | 'orange_glazed_terracotta' | 'orange_shulker_box' | 'orange_stained_glass' | 'orange_stained_glass_pane' | 'orange_terracotta' | 'orange_tulip' | 'orange_wool' | 'oxeye_daisy' | 'packed_ice' | 'peony' | 'petrified_oak_slab' | 'pink_banner' | 'pink_bed' | 'pink_carpet' | 'pink_concrete' | 'pink_concrete_powder' | 'pink_glazed_terracotta' | 'pink_shulker_box' | 'pink_stained_glass' | 'pink_stained_glass_pane' | 'pink_terracotta' | 'pink_tulip' | 'pink_wool' | 'piston' | 'player_head' | 'podzol' | 'polished_andesite' | 'polished_andesite_slab' | 'polished_andesite_stairs' | 'polished_basalt' | 'polished_blackstone' | 'polished_blackstone_brick_slab' | 'polished_blackstone_brick_stairs' | 'polished_blackstone_brick_wall' | 'polished_blackstone_bricks' | 'polished_blackstone_button' | 'polished_blackstone_pressure_plate' | 'polished_blackstone_slab' | 'polished_blackstone_stairs' | 'polished_blackstone_wall' | 'polished_diorite' | 'polished_diorite_slab' | 'polished_diorite_stairs' | 'polished_granite' | 'polished_granite_slab' | 'polished_granite_stairs' | 'poppy' | 'potatoes' | 'powered_rail' | 'prismarine' | 'prismarine_brick_slab' | 'prismarine_brick_stairs' | 'prismarine_bricks' | 'prismarine_slab' | 'prismarine_stairs' | 'prismarine_wall' | 'pumpkin' | 'pumpkin_stem' | 'purple_banner' | 'purple_bed' | 'purple_carpet' | 'purple_concrete' | 'purple_concrete_powder' | 'purple_glazed_terracotta' | 'purple_shulker_box' | 'purple_stained_glass' | 'purple_stained_glass_pane' | 'purple_terracotta' | 'purple_wool' | 'purpur_block' | 'purpur_pillar' | 'purpur_slab' | 'purpur_stairs' | 'quartz_bricks' | 'quartz_pillar' | 'quartz_slab' | 'quartz_stairs' | 'rail' | 'red_banner' | 'red_bed' | 'red_carpet' | 'red_concrete' | 'red_concrete_powder' | 'red_glazed_terracotta' | 'red_mushroom' | 'red_mushroom_block' | 'red_nether_brick_slab' | 'red_nether_brick_stairs' | 'red_nether_brick_wall' | 'red_nether_bricks' | 'red_sand' | 'red_sandstone' | 'red_sandstone_slab' | 'red_sandstone_stairs' | 'red_sandstone_wall' | 'red_shulker_box' | 'red_stained_glass' | 'red_stained_glass_pane' | 'red_terracotta' | 'red_tulip' | 'red_wool' | 'comparator' | 'redstone_lamp' | 'redstone_ore' | 'repeater' | 'redstone_torch' | 'repeating_command_block' | 'respawn_anchor' | 'rose_bush' | 'sand' | 'sandstone' | 'sandstone_slab' | 'sandstone_stairs' | 'sandstone_wall' | 'scaffolding' | 'sea_lantern' | 'sea_pickle' | 'seagrass' | 'shroomlight' | 'shulker_box' | 'skeleton_skull' | 'slime_block' | 'smithing_table' | 'smoker' | 'quartz_block' | 'smooth_quartz_slab' | 'smooth_quartz_stairs' | 'smooth_red_sandstone' | 'smooth_red_sandstone_slab' | 'smooth_red_sandstone_stairs' | 'smooth_sandstone' | 'smooth_sandstone_slab' | 'smooth_sandstone_stairs' | 'smooth_stone' | 'smooth_stone_slab' | 'snow' | 'snow_block' | 'soul_campfire' | 'soul_fire' | 'soul_lantern' | 'soul_sand' | 'soul_soil' | 'soul_torch' | 'spawner' | 'sponge' | 'spruce_button' | 'spruce_door' | 'spruce_fence' | 'spruce_fence_gate' | 'spruce_leaves' | 'spruce_log' | 'spruce_planks' | 'spruce_pressure_plate' | 'spruce_sapling' | 'spruce_sign' | 'spruce_slab' | 'spruce_stairs' | 'spruce_trapdoor' | 'spruce_wood' | 'sticky_piston' | 'stone' | 'stone_brick_slab' | 'stone_brick_stairs' | 'stone_brick_wall' | 'stone_bricks' | 'stone_button' | 'stone_pressure_plate' | 'stone_slab' | 'stone_stairs' | 'stonecutter' | 'stripped_acacia_log' | 'stripped_acacia_wood' | 'stripped_birch_log' | 'stripped_birch_wood' | 'stripped_crimson_hyphae' | 'stripped_crimson_stem' | 'stripped_dark_oak_log' | 'stripped_dark_oak_wood' | 'stripped_jungle_log' | 'stripped_jungle_wood' | 'stripped_oak_log' | 'stripped_oak_wood' | 'stripped_spruce_log' | 'stripped_spruce_wood' | 'stripped_warped_hyphae' | 'stripped_warped_stem' | 'structure_block' | 'structure_void' | 'sugar_cane' | 'sunflower' | 'sweet_berry_bush' | 'tall_grass' | 'tall_seagrass' | 'target' | 'terracotta' | 'tnt' | 'torch' | 'trapped_chest' | 'tripwire' | 'tripwire_hook' | 'tube_coral' | 'tube_coral_block' | 'tube_coral_fan' | 'turtle_egg' | 'twisting_vines' | 'vine' | 'warped_button' | 'warped_door' | 'warped_fence' | 'warped_fence_gate' | 'warped_fungus' | 'warped_hyphae' | 'warped_nylium' | 'warped_planks' | 'warped_pressure_plate' | 'warped_roots' | 'warped_sign' | 'warped_slab' | 'warped_stairs' | 'warped_stem' | 'warped_trapdoor' | 'warped_wart_block' | 'water' | 'weeping_vines' | 'wet_sponge' | 'wheat' | 'white_banner' | 'white_bed' | 'white_carpet' | 'white_concrete' | 'white_concrete_powder' | 'white_glazed_terracotta' | 'white_shulker_box' | 'white_stained_glass' | 'white_stained_glass_pane' | 'white_terracotta' | 'white_tulip' | 'white_wool' | 'wither_rose' | 'wither_skeleton_skull' | 'yellow_banner' | 'yellow_bed' | 'yellow_carpet' | 'yellow_concrete' | 'yellow_concrete_powder' | 'yellow_glazed_terracotta' | 'yellow_shulker_box' | 'yellow_stained_glass' | 'yellow_stained_glass_pane' | 'yellow_terracotta' | 'yellow_wool' | 'zombie_head')
             ;


MCStmnt : '$' (~["\\\r\n\u0085\u2028\u2029])*
        ;


/* SYMBOLS */
ADD : '+';
SUB : '-';
NOT : 'not';
POW : 'Pow';
TIMES: '*';
DIV: '/';
MOD: '%';
LESSER: '<';
GREATER: '>';
LESSEQ: '<=';
GREATEQ: '>=';
EQUAL: '==';
NOTEQUAL: '!=';
AND: 'and';
OR: 'or';

ASSIGN: '=';
MODASSIGN: '%=';
MULTASSIGN: '*=';
DIVASSIGN: '/=';
ADDASSIGN: '+=';
SUBASSIGN: '-=';

NUM: 'num';
BLOCK: 'block';
BOOL: 'bool';
STRING: 'string';
VECTOR2: 'vector2';
VECTOR3: 'vector3';
TRUE: 'true';
FALSE: 'false';
QUOTE: '"';
LSQUARE: '[';
RSQUARE: ']';
ARRAY: '[]';

RETARROW: '->';
CONST: 'const';
VAR: 'var';

START: 'minespeak';
STOP: 'closespeak';
MCKEY: '@mc';
FUNC: 'func';
LPAREN: '(';
RPAREN: ')';
DO: 'do';
ENDFUNC: 'endfunc';
COMMA: ',';
COLON: ':';
RETURN: 'return';
WHILE: 'while';
ENDWHILE: 'endwhile';
FOREACH: 'foreach';
IN: 'in';
ENDFOR: 'endfor';
FOR: 'for';
UNTIL: 'until';
WHERE: 'where';
IF: 'if';
ELIF: 'elif';
ELSE: 'else';
ENDIF: 'endif';
VOID: 'void';

ID         : [a-zA-Z_][a-zA-Z_0-9]*
           ;