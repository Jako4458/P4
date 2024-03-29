# EBNF

    Prog    -> {Blocks} €             // A program 

    Blocks  -> Stmnts Blocks
            |  MCFunc Newline Blocks 
            |  Func Newline Blocks
            |  λ

    MCFunc  -> '@mc' Newline Func                                    // Mark an MCFunc

    Func    -> 'func' 'id' '(' Params ')' [-> Type] FuncBody      // Signature of functions with optional return type

    Params  -> Param [',' Params]                                 // Allowing multiple parameters, seperating with ','
            |  λ                                                  // Allowing no parameters

    Param   -> 'id' : Type                                           // A parameter should declare a type and an id

    FuncBody-> 'do' [Newline Stmnts] [RetVal] Newline 'endfunc'      // FuncBody consists of optional statements
                                                                             // Optional RetVal which must be the last line in FuncBody

    RetVal  -> 'return' [Expr]                              // A return statement.
                                                                             // Can simply be return or followed by value

    FuncCall -> 'id''('[Expr {',' Expr}]')'

    Stmnts  -> Stmnt {Stmnt}                // Statements consists of a statement and then optinally statements seperated by newline
        
    Stmnt   -> (Dcls |  Assign |  Instan |  If-stmnt |  Loop | MCStmnt | FuncCall) Newline         
        // A statement can be: declarations, an assignment, an expression, or an instansiation
            
    Dcls    -> 'var' 'id' ':' Type {',' 'id' ':' Type}                      // Declare variable without value

    Assign  -> 'id' ('=' | CompAssign) Expr                              // Assignment of a variable

    CompAssign -> ('%=' | '*=' | '/=' | '+=' | '-=')                        // Compound assignment operators

    Instan  -> Access 'id' ':' Type '=' Expr {',' 'id' ':' Type '=' Expr}   // Declare variable or constant and assign value

    Access  -> 'const'              // An access modifier can either be var (variable) or const (constant).
            |  'var'                // A the value of a var can be changed, the value of a const cannot.

    Expr    -> [Expr ('+' | '-' | 'or')] L1Expr

    L1Expr  -> [L1Expr ('*' | '/' | '%' | 'and')] L2Expr

    L2Expr  -> [L2Expr ('Pow' | '>=' | '<=' | '<' | '>' | '==' | '!=')] Factor

    Factor  -> ['not'] ('('Expr')' | 'id' | Number_literal | Boolean_literal | FuncCall)

    IfExpr  -> 'if' Expr 'do' Newline [Stmnts] {'elif' Expr 'do' Newline [Stmnts]} ['else' do Newline [Stmnts]] 'endif
    
    Loop    -> (For | Foreach | While | DoWhile)

    For     -> 'for' Assign 'until' Expr 'where' Assign 'do' Newline [Stmnts] 'endfor'

    Foreach ->  'foreach' Type 'id' 'in' Expr 'do' Newline [Stmnts] 'endfor'
    
    While   -> 'while' Expr do Newline [Stmnts] endwhile

    DoWhile -> 'do' Newline [Stmnts] 'while' Expr 'endwhile'
    
    MCStmnt  -> $<function already defined in mcfunction>               // Line that will be directly written into the MCFunc file
    
    # unused
    Keyword -> 'if'       |  'elif'     |  'else'     |  'endif'    |  'until'
            |  'num'      |  'block'    |  'bool'     |  'string'   |  'in'
            |  'func'     |  'endfunc'  |  'return'   |  'foreach'  |  'vector2'
            |  'for'      |  'while'    |  'do'       |  'endfor'   |  'vector3'
            |  'endwhile' |  'true'     |  'false'    |  'anon'     |  'endanon' 

    Type    -> PrimitiveType
            |  PrimitiveType'['']'

    PrimitiveType   -> 'num'                     
                    |  'block'   
                    |  'bool'     
                    |  'string'  
                    |  'byte'                  
                    |  'file'                  
                    |  'vector2'  
                    |  'vector3'          

/* All possible literals */

    Literal -> Boolean_literal
            |  Number_literal
            |  String_literal
            |  Vector2_literal
            |  Vector3_literal
            |  Block_literal

    Boolean_literal -> true
                    |  false
    
    Number_literal -> Decimal_digit {Decimal_digit}
                    | '0x' Hexidecimal_digit {Hexidecimal_digit}
                    | '0X' Hexidecimal_digit {Hexidecimal_digit}

    Decimal_digit -> '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'

    Hexidecimal_digit -> '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
                       | 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'a' | 'b' | 'c' | 'd' | 'e' | 'f'
    


    String_literal -> '<Any character except " (U+0022), \\ (U+005C), and Newline>'     <!--- Stolen C# language specification -->

    Newline -> '\n' | '\r' | '\r\n'                             <!--- Line feed or carriage return or both -->
    
    Vector2_literal -> '<' (Number_literal | id) ',' (Number_literal | id) '>'

    Vector3_literal -> '<' (Number_literal | id) ',' (Number_literal | id) ',' (Number_literal | id) '>'


    // Valid blocks in Minecraft

    Block_literal -> 'ACACIA_BUTTON' | 'ACACIA_DOOR' | 'ACACIA_FENCE' | 'ACACIA_FENCE_GATE' | 'ACACIA_LEAVES' | 'ACACIA_LOG' | 'ACACIA_PLANKS' | 'ACACIA_SAPLING' | 'ACACIA_SLAB' | 'ACACIA_TRAPDOOR' | 'ACACIA_WOOD' | 'ACTIVATOR_RAIL' | 'ALLIUM' | 'ANCIENT_DEBRIS' | 'ANDESITE' | 'ANDESITE_SLAB' | 'ANDESITE_STAIRS' | 'ANDESITE_WALL' | 'ANVIL' | 'AZURE_BLUET' | 'BAMBOO' | 'BAMBOO_SHOOT' | 'BEETROOTS' | 'BARREL' | 'BARRIER' | 'BASALT' | 'BEACON' | 'BEDROCK' | 'BEEHIVE' | 'BEE_NEST' | 'BELL' | 'BIRCH_BUTTON' | 'BIRCH_DOOR' | 'BIRCH_FENCE' | 'BIRCH_FENCE_GATE' | 'BIRCH_LEAVES' | 'BIRCH_LOG' | 'BIRCH_PLANKS' | 'BIRCH_PRESSURE_PLATE' | 'BIRCH_SAPLING' | 'BIRCH_SIGN' | 'BIRCH_SLAB' | 'BIRCH_STAIRS' | 'BIRCH_TRAPDOOR' | 'BIRCH_WOOD' | 'BLACK_BANNER' | 'BLACK_BED' | 'BLACK_CARPET' | 'BLACK_CONCRETE' | 'BLACK_CONCRETE_POWDER' | 'BLACK_GLAZED_TERRACOTTA' | 'BLACK_SHULKER_BOX' | 'BLACK_STAINED_GLASS' | 'BLACK_STAINED_GLASS_PANE' | 'BLACK_TERRACOTTA' | 'BLACK_WOOL' | 'BLACKSTONE' | 'BLACKSTONE_SLAB' | 'BLACKSTONE_STAIRS' | 'BLACKSTONE_WALL' | 'BLAST_FURNACE' | 'BLOCK_OF_COAL' | 'BLOCK_OF_DIAMOND' | 'BLOCK_OF_EMERALD' | 'BLOCK_OF_GOLD' | 'BLOCK_OF_IRON' | 'BLOCK_OF_NETHERITE' | 'BLOCK_OF_QUARTZ' | 'BLOCK_OF_REDSTONE' | 'BLUE_BANNER' | 'BLUE_BED' | 'BLUE_CARPET' | 'BLUE_CONCRETE' | 'BLUE_CONCRETE_POWDER' | 'BLUE_GLAZED_TERRACOTTA' | 'BLUE_ICE' | 'BLUE_ORCHID' | 'BLUE_SHULKER_BOX' | 'BLUE_STAINED_GLASS' | 'BLUE_STAINED_GLASS_PANE' | 'BLUE_TERRACOTTA' | 'BLUE_WOOL' | 'BONE_BLOCK' | 'BOOKSHELF' | 'BRAIN_CORAL' | 'BRAIN_CORAL_BLOCK' | 'BRAIN_CORAL_FAN' | 'BREWING_STAND' | 'BRICK_SLAB' | 'BRICK_STAIRS' | 'BRICK_WALL' | 'BRICKS' | 'BROWN_BANNER' | 'BROWN_BED' | 'BROWN_CARPET' | 'BROWN_CONCRETE' | 'BROWN_CONCRETE_POWDER' | 'BROWN_GLAZED_TERRACOTTA' | 'BROWN_MUSHROOM' | 'BROWN_MUSHROOM_BLOCK' | 'BROWN_SHULKER_BOX' | 'BROWN_STAINED_GLASS' | 'BROWN_STAINED_GLASS_PANE' | 'BROWN_TERRACOTTA' | 'BROWN_WOOL' | 'BUBBLE_CORAL' | 'BUBBLE_CORAL_BLOCK' | 'BUBBLE_CORAL_FAN' | 'CACTUS' | 'CAKE' | 'CAMPFIRE' | 'CARROTS' | 'CARTOGRAPHY_TABLE' | 'CARVED_PUMPKIN' | 'CAULDRON' | 'CHAIN' | 'CHAIN_COMMAND_BLOCK' | 'CHEST' | 'CHIPPED_ANVIL' | 'CHISELED_NETHER_BRICKS' | 'CHISELED_POLISHED_BLACKSTONE' | 'CHISELED_QUARTZ_BLOCK' | 'CHISELED_RED_SANDSTONE' | 'CHISELED_SANDSTONE' | 'CHISELED_STONE_BRICKS' | 'CHORUS_FLOWER' | 'CHORUS_PLANT' | 'CLAY' | 'COAL_ORE' | 'COARSE_DIRT' | 'COBBLESTONE' | 'COBBLESTONE_SLAB' | 'COBBLESTONE_STAIRS' | 'COBBLESTONE_WALL' | 'COBWEB' | 'COCOA' | 'COMMAND_BLOCK' | 'COMPOSTER' | 'CONDUIT' | 'CORNFLOWER' | 'CRACKED_NETHER_BRICKS' | 'CRACKED_POLISHED_BLACKSTONE_BRICKS' | 'CRACKED_STONE_BRICKS' | 'CRAFTING_TABLE' | 'CREEPER_HEAD' | 'CRIMSON_BUTTON' | 'CRIMSON_DOOR' | 'CRIMSON_FENCE' | 'CRIMSON_FENCE_GATE' | 'CRIMSON_FUNGUS' | 'CRIMSON_HYPHAE' | 'CRIMSON_NYLIUM' | 'CRIMSON_PLANKS' | 'CRIMSON_PRESSURE_PLATE' | 'CRIMSON_ROOTS' | 'CRIMSON_SIGN' | 'CRIMSON_SLAB' | 'CRIMSON_STAIRS' | 'CRIMSON_STEM' | 'CRIMSON_TRAPDOOR' | 'CRYING_OBSIDIAN' | 'CUT_RED_SANDSTONE' | 'CUT_RED_SANDSTONE_SLAB' | 'CUT_SANDSTONE' | 'CUT_SANDSTONE_SLAB' | 'CYAN_BANNER' | 'CYAN_BED' | 'CYAN_CARPET' | 'CYAN_CONCRETE' | 'CYAN_CONCRETE_POWDER' | 'CYAN_GLAZED_TERRACOTTA' | 'CYAN_SHULKER_BOX' | 'CYAN_STAINED_GLASS' | 'CYAN_STAINED_GLASS_PANE' | 'CYAN_TERRACOTTA' | 'CYAN_WOOL' | 'DAMAGED_ANVIL' | 'DANDELION' | 'DARK_OAK_BUTTON' | 'DARK_OAK_DOOR' | 'DARK_OAK_FENCE' | 'DARK_OAK_FENCE_GATE' | 'DARK_OAK_LEAVES' | 'DARK_OAK_LOG' | 'DARK_OAK_PLANKS' | 'DARK_OAK_PRESSURE_PLATE' | 'DARK_OAK_SAPLING' | 'DARK_OAK_SIGN' | 'DARK_OAK_SLAB' | 'DARK_OAK_STAIRS' | 'DARK_OAK_TRAPDOOR' | 'DARK_OAK_WOOD' | 'DARK_PRISMARINE' | 'DARK_PRISMARINE_SLAB' | 'DARK_PRISMARINE_STAIRS' | 'DAYLIGHT_DETECTOR' | 'DEAD_BRAIN_CORAL' | 'DEAD_BRAIN_CORAL_BLOCK' | 'DEAD_BRAIN_CORAL_FAN' | 'DEAD_BUBBLE_CORAL' | 'DEAD_BUBBLE_CORAL_BLOCK' | 'DEAD_BUBBLE_CORAL_FAN' | 'DEAD_BUSH' | 'DEAD_FIRE_CORAL' | 'DEAD_FIRE_CORAL_BLOCK' | 'DEAD_FIRE_CORAL_FAN' | 'DEAD_HORN_CORAL' | 'DEAD_HORN_CORAL_BLOCK' | 'DEAD_HORN_CORAL_FAN' | 'DEAD_TUBE_CORAL' | 'DEAD_TUBE_CORAL_BLOCK' | 'DEAD_TUBE_CORAL_FAN' | 'DETECTOR_RAIL' | 'DIAMOND_ORE' | 'DIORITE' | 'DIORITE_SLAB' | 'DIORITE_STAIRS' | 'DIORITE_WALL' | 'DIRT' | 'DISPENSER' | 'DRAGON_EGG' | 'DRAGON_HEAD' | 'DRIED_KELP_BLOCK' | 'DROPPER' | 'EMERALD_ORE' | 'ENCHANTING_TABLE' | 'END_PORTAL_FRAME' | 'END_ROD' | 'END_STONE' | 'END_STONE_BRICK_SLAB' | 'END_STONE_BRICK_STAIRS' | 'END_STONE_BRICK_WALL' | 'END_STONE_BRICKS' | 'ENDER_CHEST' | 'FARMLAND' | 'FERN' | 'FIRE' | 'FIRE_CORAL' | 'FIRE_CORAL_BLOCK' | 'FIRE_CORAL_FAN' | 'FLETCHING_TABLE' | 'FLOWER_POT' | 'FURNACE' | 'GILDED_BLACKSTONE' | 'GLASS' | 'GLASS_PANE' | 'GLOWSTONE' | 'GOLD_ORE' | 'GRANITE' | 'GRANITE_SLAB' | 'GRANITE_STAIRS' | 'GRANITE_WALL' | 'GRASS' | 'GRASS_BLOCK' | 'GRASS_PATH' | 'GRAVEL' | 'GRAY_BANNER' | 'GRAY_BED' | 'GRAY_CARPET' | 'GRAY_CONCRETE' | 'GRAY_CONCRETE_POWDER' | 'GRAY_GLAZED_TERRACOTTA' | 'GRAY_SHULKER_BOX' | 'GRAY_STAINED_GLASS' | 'GRAY_STAINED_GLASS_PANE' | 'GRAY_TERRACOTTA' | 'GRAY_WOOL' | 'GREEN_BANNER' | 'GREEN_BED' | 'GREEN_CARPET' | 'GREEN_CONCRETE' | 'GREEN_CONCRETE_POWDER' | 'GREEN_GLAZED_TERRACOTTA' | 'GREEN_SHULKER_BOX' | 'GREEN_STAINED_GLASS' | 'GREEN_STAINED_GLASS_PANE' | 'GREEN_TERRACOTTA' | 'GREEN_WOOL' | 'GRINDSTONE' | 'HAY_BALE' | 'HEAVY_WEIGHTED_PRESSURE_PLATE' | 'HONEY_BLOCK' | 'HONEYCOMB_BLOCK' | 'HOPPER' | 'HORN_CORAL' | 'HORN_CORAL_BLOCK' | 'HORN_CORAL_FAN' | 'ICE' | 'INFESTED_CHISELED_STONE_BRICKS' | 'INFESTED_COBBLESTONE' | 'INFESTED_CRACKED_STONE_BRICKS' | 'INFESTED_MOSSY_STONE_BRICKS' | 'INFESTED_STONE' | 'INFESTED_STONE_BRICKS' | 'IRON_BARS' | 'IRON_DOOR' | 'IRON_ORE' | 'IRON_TRAPDOOR' | 'JACK_O_LANTERN' | 'JIGSAW_BLOCK' | 'JUKEBOX' | 'JUNGLE_BUTTON' | 'JUNGLE_DOOR' | 'JUNGLE_FENCE' | 'JUNGLE_FENCE_GATE' | 'JUNGLE_LEAVES' | 'JUNGLE_LOG' | 'JUNGLE_PLANKS' | 'JUNGLE_PRESSURE_PLATE' | 'JUNGLE_SAPLING' | 'JUNGLE_SIGN' | 'JUNGLE_SLAB' | 'JUNGLE_STAIRS' | 'JUNGLE_TRAPDOOR' | 'JUNGLE_WOOD' | 'LADDER' | 'LANTERN' | 'LAPIS_LAZULI_BLOCK' | 'LAPIS_LAZULI_ORE' | 'LARGE_FERN' | 'LAVA' | 'LECTERN' | 'LEVER' | 'LIGHT_BLUE_BANNER' | 'LIGHT_BLUE_BED' | 'LIGHT_BLUE_CARPET' | 'LIGHT_BLUE_CONCRETE' | 'LIGHT_BLUE_CONCRETE_POWDER' | 'LIGHT_BLUE_GLAZED_TERRACOTTA' | 'LIGHT_BLUE_SHULKER_BOX' | 'LIGHT_BLUE_STAINED_GLASS' | 'LIGHT_BLUE_STAINED_GLASS_PANE' | 'LIGHT_BLUE_TERRACOTTA' | 'LIGHT_BLUE_WOOL' | 'LIGHT_GRAY_BANNER' | 'LIGHT_GRAY_BED' | 'LIGHT_GRAY_CARPET' | 'LIGHT_GRAY_CONCRETE' | 'LIGHT_GRAY_CONCRETE_POWDER' | 'LIGHT_GRAY_GLAZED_TERRACOTTA' | 'LIGHT_GRAY_SHULKER_BOX' | 'LIGHT_GRAY_STAINED_GLASS' | 'LIGHT_GRAY_STAINED_GLASS_PANE' | 'LIGHT_GRAY_TERRACOTTA' | 'LIGHT_GRAY_WOOL' | 'LIGHT_WEIGHTED_PRESSURE_PLATE' | 'LILAC' | 'LILY_OF_THE_VALLEY' | 'LILY_PAD' | 'LIME_BANNER' | 'LIME_BED' | 'LIME_CARPET' | 'LIME_CONCRETE' | 'LIME_CONCRETE_POWDER' | 'LIME_GLAZED_TERRACOTTA' | 'LIME_SHULKER_BOX' | 'LIME_STAINED_GLASS' | 'LIME_STAINED_GLASS_PANE' | 'LIME_TERRACOTTA' | 'LIME_WOOL' | 'LODESTONE' | 'LOOM' | 'MAGENTA_BANNER' | 'MAGENTA_BED' | 'MAGENTA_CARPET' | 'MAGENTA_CONCRETE' | 'MAGENTA_CONCRETE_POWDER' | 'MAGENTA_GLAZED_TERRACOTTA' | 'MAGENTA_SHULKER_BOX' | 'MAGENTA_STAINED_GLASS' | 'MAGENTA_STAINED_GLASS_PANE' | 'MAGENTA_TERRACOTTA' | 'MAGENTA_WOOL' | 'MAGMA_BLOCK' | 'MELON' | 'MELON_STEM' | 'MOSSY_COBBLESTONE' | 'MOSSY_COBBLESTONE_SLAB' | 'MOSSY_COBBLESTONE_STAIRS' | 'MOSSY_COBBLESTONE_WALL' | 'MOSSY_STONE_BRICK_SLAB' | 'MOSSY_STONE_BRICK_STAIRS' | 'MOSSY_STONE_BRICK_WALL' | 'MOSSY_STONE_BRICKS' | 'MUSHROOM_STEM' | 'MYCELIUM' | 'NETHER_BRICK_FENCE' | 'NETHER_BRICK_SLAB' | 'NETHER_BRICK_STAIRS' | 'NETHER_BRICK_WALL' | 'NETHER_BRICKS' | 'NETHER_GOLD_ORE' | 'NETHER_QUARTZ_ORE' | 'NETHER_SPROUTS' | 'NETHER_WART' | 'NETHER_WART_BLOCK' | 'NETHERRACK' | 'NOTE_BLOCK' | 'OAK_BUTTON' | 'OAK_DOOR' | 'OAK_FENCE' | 'OAK_FENCE_GATE' | 'OAK_LEAVES' | 'OAK_LOG' | 'OAK_PLANKS' | 'OAK_PRESSURE_PLATE' | 'OAK_SAPLING' | 'OAK_SIGN' | 'OAK_SLAB' | 'OAK_STAIRS' | 'OAK_TRAPDOOR' | 'OAK_WOOD' | 'OBSERVER' | 'OBSIDIAN' | 'OMINOUS_BANNER' | 'ORANGE_BANNER' | 'ORANGE_BED' | 'ORANGE_CARPET' | 'ORANGE_CONCRETE' | 'ORANGE_CONCRETE_POWDER' | 'ORANGE_GLAZED_TERRACOTTA' | 'ORANGE_SHULKER_BOX' | 'ORANGE_STAINED_GLASS' | 'ORANGE_STAINED_GLASS_PANE' | 'ORANGE_TERRACOTTA' | 'ORANGE_TULIP' | 'ORANGE_WOOL' | 'OXEYE_DAISY' | 'PACKED_ICE' | 'PEONY' | 'PETRIFIED_OAK_SLAB' | 'PINK_BANNER' | 'PINK_BED' | 'PINK_CARPET' | 'PINK_CONCRETE' | 'PINK_CONCRETE_POWDER' | 'PINK_GLAZED_TERRACOTTA' | 'PINK_SHULKER_BOX' | 'PINK_STAINED_GLASS' | 'PINK_STAINED_GLASS_PANE' | 'PINK_TERRACOTTA' | 'PINK_TULIP' | 'PINK_WOOL' | 'PISTON' | 'PLAYER_HEAD' | 'PODZOL' | 'POLISHED_ANDESITE' | 'POLISHED_ANDESITE_SLAB' | 'POLISHED_ANDESITE_STAIRS' | 'POLISHED_BASALT' | 'POLISHED_BLACKSTONE' | 'POLISHED_BLACKSTONE_BRICK_SLAB' | 'POLISHED_BLACKSTONE_BRICK_STAIRS' | 'POLISHED_BLACKSTONE_BRICK_WALL' | 'POLISHED_BLACKSTONE_BRICKS' | 'POLISHED_BLACKSTONE_BUTTON' | 'POLISHED_BLACKSTONE_PRESSURE_PLATE' | 'POLISHED_BLACKSTONE_SLAB' | 'POLISHED_BLACKSTONE_STAIRS' | 'POLISHED_BLACKSTONE_WALL' | 'POLISHED_DIORITE' | 'POLISHED_DIORITE_SLAB' | 'POLISHED_DIORITE_STAIRS' | 'POLISHED_GRANITE' | 'POLISHED_GRANITE_SLAB' | 'POLISHED_GRANITE_STAIRS' | 'POPPY' | 'POTATOES' | 'POWERED_RAIL' | 'PRISMARINE' | 'PRISMARINE_BRICK_SLAB' | 'PRISMARINE_BRICK_STAIRS' | 'PRISMARINE_BRICKS' | 'PRISMARINE_SLAB' | 'PRISMARINE_STAIRS' | 'PRISMARINE_WALL' | 'PUMPKIN' | 'PUMPKIN_STEM' | 'PURPLE_BANNER' | 'PURPLE_BED' | 'PURPLE_CARPET' | 'PURPLE_CONCRETE' | 'PURPLE_CONCRETE_POWDER' | 'PURPLE_GLAZED_TERRACOTTA' | 'PURPLE_SHULKER_BOX' | 'PURPLE_STAINED_GLASS' | 'PURPLE_STAINED_GLASS_PANE' | 'PURPLE_TERRACOTTA' | 'PURPLE_WOOL' | 'PURPUR_BLOCK' | 'PURPUR_PILLAR' | 'PURPUR_SLAB' | 'PURPUR_STAIRS' | 'QUARTZ_BRICKS' | 'QUARTZ_PILLAR' | 'QUARTZ_SLAB' | 'QUARTZ_STAIRS' | 'RAIL' | 'RED_BANNER' | 'RED_BED' | 'RED_CARPET' | 'RED_CONCRETE' | 'RED_CONCRETE_POWDER' | 'RED_GLAZED_TERRACOTTA' | 'RED_MUSHROOM' | 'RED_MUSHROOM_BLOCK' | 'RED_NETHER_BRICK_SLAB' | 'RED_NETHER_BRICK_STAIRS' | 'RED_NETHER_BRICK_WALL' | 'RED_NETHER_BRICKS' | 'RED_SAND' | 'RED_SANDSTONE' | 'RED_SANDSTONE_SLAB' | 'RED_SANDSTONE_STAIRS' | 'RED_SANDSTONE_WALL' | 'RED_SHULKER_BOX' | 'RED_STAINED_GLASS' | 'RED_STAINED_GLASS_PANE' | 'RED_TERRACOTTA' | 'RED_TULIP' | 'RED_WOOL' | 'REDSTONE_COMPARATOR' | 'REDSTONE_LAMP' | 'REDSTONE_ORE' | 'REDSTONE_REPEATER' | 'REDSTONE_TORCH' | 'REPEATING_COMMAND_BLOCK' | 'RESPAWN_ANCHOR' | 'ROSE_BUSH' | 'SAND' | 'SANDSTONE' | 'SANDSTONE_SLAB' | 'SANDSTONE_STAIRS' | 'SANDSTONE_WALL' | 'SCAFFOLDING' | 'SEA_LANTERN' | 'SEA_PICKLE' | 'SEAGRASS' | 'SHROOMLIGHT' | 'SHULKER_BOX' | 'SKELETON_SKULL' | 'SLIME_BLOCK' | 'SMITHING_TABLE' | 'SMOKER' | 'SMOOTH_QUARTZ_BLOCK' | 'SMOOTH_QUARTZ_SLAB' | 'SMOOTH_QUARTZ_STAIRS' | 'SMOOTH_RED_SANDSTONE' | 'SMOOTH_RED_SANDSTONE_SLAB' | 'SMOOTH_RED_SANDSTONE_STAIRS' | 'SMOOTH_SANDSTONE' | 'SMOOTH_SANDSTONE_SLAB' | 'SMOOTH_SANDSTONE_STAIRS' | 'SMOOTH_STONE' | 'SMOOTH_STONE_SLAB' | 'SNOW' | 'SNOW_BLOCK' | 'SOUL_CAMPFIRE' | 'SOUL_FIRE' | 'SOUL_LANTERN' | 'SOUL_SAND' | 'SOUL_SOIL' | 'SOUL_TORCH' | 'SPAWNER' | 'SPONGE' | 'SPRUCE_BUTTON' | 'SPRUCE_DOOR' | 'SPRUCE_FENCE' | 'SPRUCE_FENCE_GATE' | 'SPRUCE_LEAVES' | 'SPRUCE_LOG' | 'SPRUCE_PLANKS' | 'SPRUCE_PRESSURE_PLATE' | 'SPRUCE_SAPLING' | 'SPRUCE_SIGN' | 'SPRUCE_SLAB' | 'SPRUCE_STAIRS' | 'SPRUCE_TRAPDOOR' | 'SPRUCE_WOOD' | 'STICKY_PISTON' | 'STONE' | 'STONE_BRICK_SLAB' | 'STONE_BRICK_STAIRS' | 'STONE_BRICK_WALL' | 'STONE_BRICKS' | 'STONE_BUTTON' | 'STONE_PRESSURE_PLATE' | 'STONE_SLAB' | 'STONE_STAIRS' | 'STONECUTTER' | 'STRIPPED_ACACIA_LOG' | 'STRIPPED_ACACIA_WOOD' | 'STRIPPED_BIRCH_LOG' | 'STRIPPED_BIRCH_WOOD' | 'STRIPPED_CRIMSON_HYPHAE' | 'STRIPPED_CRIMSON_STEM' | 'STRIPPED_DARK_OAK_LOG' | 'STRIPPED_DARK_OAK_WOOD' | 'STRIPPED_JUNGLE_LOG' | 'STRIPPED_JUNGLE_WOOD' | 'STRIPPED_OAK_LOG' | 'STRIPPED_OAK_WOOD' | 'STRIPPED_SPRUCE_LOG' | 'STRIPPED_SPRUCE_WOOD' | 'STRIPPED_WARPED_HYPHAE' | 'STRIPPED_WARPED_STEM' | 'STRUCTURE_BLOCK' | 'STRUCTURE_VOID' | 'SUGAR_CANE' | 'SUNFLOWER' | 'SWEET_BERRY_BUSH' | 'TALL_GRASS' | 'TALL_SEAGRASS' | 'TARGET' | 'TERRACOTTA' | 'TNT' | 'TORCH' | 'TRAPPED_CHEST' | 'TRIPWIRE' | 'TRIPWIRE_HOOK' | 'TUBE_CORAL' | 'TUBE_CORAL_BLOCK' | 'TUBE_CORAL_FAN' | 'TURTLE_EGG' | 'TWISTING_VINES' | 'VINES' | 'WARPED_BUTTON' | 'WARPED_DOOR' | 'WARPED_FENCE' | 'WARPED_FENCE_GATE' | 'WARPED_FUNGUS' | 'WARPED_HYPHAE' | 'WARPED_NYLIUM' | 'WARPED_PLANKS' | 'WARPED_PRESSURE_PLATE' | 'WARPED_ROOTS' | 'WARPED_SIGN' | 'WARPED_SLAB' | 'WARPED_STAIRS' | 'WARPED_STEM' | 'WARPED_TRAPDOOR' | 'WARPED_WART_BLOCK' | 'WATER' | 'WEEPING_VINES' | 'WET_SPONGE' | 'WHEAT_CROPS' | 'WHITE_BANNER' | 'WHITE_BED' | 'WHITE_CARPET' | 'WHITE_CONCRETE' | 'WHITE_CONCRETE_POWDER' | 'WHITE_GLAZED_TERRACOTTA' | 'WHITE_SHULKER_BOX' | 'WHITE_STAINED_GLASS' | 'WHITE_STAINED_GLASS_PANE' | 'WHITE_TERRACOTTA' | 'WHITE_TULIP' | 'WHITE_WOOL' | 'WITHER_ROSE' | 'WITHER_SKELETON_SKULL' | 'YELLOW_BANNER' | 'YELLOW_BED' | 'YELLOW_CARPET' | 'YELLOW_CONCRETE' | 'YELLOW_CONCRETE_POWDER' | 'YELLOW_GLAZED_TERRACOTTA' | 'YELLOW_SHULKER_BOX' | 'YELLOW_STAINED_GLASS' | 'YELLOW_STAINED_GLASS_PANE' | 'YELLOW_TERRACOTTA' | 'YELLOW_WOOL' | 'ZOMBIE_HEAD'
                        
    
  
    
    λ = emptyString
    € = EOF
    \s = whitespace
    Newline = new line
    Assign = assign
    Dcls = declarations
    Expr = expression
    Func = function
    Funcs = functions
    Stmnts = statements
    Stmnt = statement



