minespeak

    @mc
    func Build_Castle() do 
        const wallSize:num = 20
        const wallHeight:num = 10
        const wallTowerSize:num = 10
        const wallTowerHeight:num = 15
        const mainTowerWallSize:num = 15
        const mainTowerWallHeight:num = 20

        const mat:block = #DIAMOND_BLOCK


        const wallTowerPos:vector3[4] =  [ (((wallSize + wallTowerSize)/2)*< -1, 0, -1>), (((wallSize + wallTowerSize)/2)*(< -1, 0, 1>)), (((wallSize + wallTowerSize)/2)*< 1, 0, 1>), (((wallSize + wallTowerSize)/2)*(< 1, 0, -1>)) ]
        
        // North/South Walls (expanding the x-axis)
        const NSWallStartPos:vector3[4] = [(wallSize/2) * < -1, 0, -1> - < 0, 0, 1>, ((wallSize/2) * < -1, 0, -1>) - < 0, 0, 3>, ((wallSize/2) * < -1, 0, 1>) + < 0, 0, 1>, ((wallSize/2)*< -1, 0, 1>) + < 0, 0, 3> ]

        // East/West   Walls (expanding the z-axis)
        const EWWallStartPos:vector3[4] = [ ((wallSize/2)*< -1, 0, -1> - < 1, 0, 0>), ((wallSize/2)*< -1, 0, -1>) - < 3, 0, 0>, ((wallSize/2)*<  1, 0, -1>) + < 1, 0, 0>, ((wallSize/2)*<  1, 0, -1>) + < 3, 0, 0> ]


        //build walls 
        foreach vector3 wallStartPos in NSWallStartPos do
            var wallEndPos:vector3 = wallStartPos + (wallSize * <1, 0, 0>) + (wallHeight * <0, 1, 0>) 
            fill(self, wallStartPos, wallEndPos, mat)
        endfor

        foreach vector3 wallStartPos in EWWallStartPos do
            var wallEndPos:vector3 = wallStartPos + (wallSize * <0, 0, 1>) + (wallHeight * <0, 1, 0>) 
            fill(self, wallStartPos, wallEndPos, mat)
        endfor

        // build wall towers
        foreach vector3 towerPos in wallTowerPos do
            BuildTower(towerPos, mainTowerWallSize, mat)
        endfor
        
        // build main tower
        BuildTower(wallTowerPos, mainTowerWallSize, mat)

    endfunc

    func BuildTower(startPos:vector3, wallSize:num, wallHeight:num, mat:block) do
        // North/South Walls
        const NSWallRelativePos:vector3[4] = [ (wallSize/2) * < -1, 0, -1>, (wallSize/2) * < -1, 0, -1>, (wallSize/2) * < -1, 0, 1>, (wallSize/2)*< -1, 0, 1> ]

        // East/West   Walls
        const EWWallRelativePos:vector3[4] = [ (wallSize/2)*< -1, 0, -1>, (wallSize/2)*< -1, 0, -1>, (wallSize/2)*<  1, 0, -1>, (wallSize/2)*<  1, 0, -1> ]

        //build walls 
        foreach vector3 wallRelativePos in NSWallStartPos do
            var wallStartPos:vector3 = startPos + wallRelativePos
            var wallEndPos:vector3 = wallStartPos + (wallSize * <1, 0, 0>) + (wallHeight * <0, 1, 0>) 
            fill(self, wallStartPos, wallEndPos, mat)
        endfor

        foreach vector3 wallRelativePos in EWWallStartPos do
            var wallStartPos:vector3 = startPos + wallRelativePos
            var wallEndPos:vector3 = wallStartPos + (wallSize * <0, 0, 1>) + (wallHeight * <0, 1, 0>) 
            fill(self, wallStartPos, wallEndPos, mat)
        endfor

    endfunc

closespeak