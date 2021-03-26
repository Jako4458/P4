minespeak
    
    @mc
    func Build_Castle() do 
        //wall startpos and endpos is part of cornertowers (these values are calculated so maybe add 2 to the wallsize x and z)
        //wallThickness will be 1 

        const wallSize:vector3 = <1, 10, 20>
        const towerWallSize:vector3 = <10, 15, 10>
        const mainTowerWallSize:vector3 = <15, 20, 15>

        const mat:block = BLOCK_OF_DIAMOND
        const startPos:vector3 = self

        const conrnerTowerPos:vector3[4] = {startPos - <  wallSize.x/2,  0,   wallSize.z/2> - <  towerSize.x/2,  0,   towerSize.z/2>,
                                            startPos + <-(wallSize.x/2), 0,   wallSize.z/2> + <-(towerSize.x/2), 0,   towerSize.z/2>,
                                            startPos + <  wallSize.x/2,  0, -(wallSize.z/2) + <  towerSize.x/2,  0, -(towerSize.z/2)>,
                                            startPos + <  wallSize.x/2,  0,   wallSize.z/2> + <  towerSize.x/2,  0,   towerSize.z/2>
                                        }

        var NSWallStartPos:vector3[4] // North/South Walls (expanding the x-axis)
        var EWWallStartPos:vector3[4] // East/West   Walls (expanding the z-axis)
        
        NSWallStartPos = { startPos - <  wallSize.x/2,  0,   wallSize.z/2> - <0,0,1>,
                            startPos - <  wallSize.x/2,  0,   wallSize.z/2> - <0,0,3>,
                            startPos + <-(wallSize.x/2), 0,   wallSize.z/2> + <0,0,1>,
                            startPos + <-(wallSize.x/2), 0,   wallSize.z/2> + <0,0,3>
                        }

        EWWallStartPos = { startPos + <  wallSize.x/2,  0, -(wallSize.z/2)> + <1,0,0>,
                            startPos + <  wallSize.x/2,  0, -(wallSize.z/2)> + <3,0,0>,
                            startPos - <  wallSize.x/2,  0,   wallSize.z/2>  - <1,0,0>,
                            startPos - <  wallSize.x/2,  0,   wallSize.z/2>  - <3,0,0>
                        }
        
        //build walls 
        foreach wallStartPos in NSWallStartPos do
            var wallEndPos = wallStartPos + <wallSize.x, wallSize.y, 0>
            fill(wallStartPos, wallEndPos, mat)
        endfor

        foreach wallStartPos in EWWallStartPos do
            var wallEndPos = wallStartPos + <0, wallsize.y, wallSize.z>
            fill(wallStartPos, wallEndPos, mat)
        endfor

        // build corner towers
        foreach towerPos in conrnerTowerPos do
            BuildTowerCornerArray(towerPos, towerWallSize, mat)
        endfor

        // build main tower
        BuildTowerCornerArray(startPos, mainTowerWallSize, mat)

    endfunc
    

    func BuildTower(pos:vector3, towerSize:vector3, wallThickness:num, mat:block) do 

        var wallStartPos:vector3 = pos - <towerSize.x/2, 0, towerSize.z/2> 

        # build from <-x/2,y,-z/2>
        fill(wallStartPos, wallStartPos + <towerSize.x, towerSize.y, wallThickness>, mat)
        fill(wallStartPos, wallStartPos + <wallThickness, towerSize.y, towerSize.z>, mat)
        
        # set startPos to <x/2,y,z/2>
        wallStartPos = wallStartPos + <towerSize.x, 0, towerSize.z>
        fill(wallStartPos, wallStartPos + <(-towerSize.x), towerSize.y, wallThickness>, mat)
        fill(wallStartPos, wallStartPos + <wallThickness, towerSize.y, (-towerSize.z)>, mat)
    
    endfunc

    // wallThickness is 1 
    func BuildTowerCornerArray(pos:vector3, towerSize:vector3, mat:block) do
        const conrners:vector3[4] = {pos - <  towerSize.x/2,  0,   towerSize.z/2>,
                                     pos + <-(towerSize.x/2), 0,   towerSize.z/2>,
                                     pos + <  towerSize.x/2,  0, -(towerSize.z/2)>,
                                     pos +   <towerSize.x/2,  0,   towerSize.z/2>
                                    }
        
        for i:num = 0 until i = conrners.length where i += 1 do
            var wallStart:vector3 = conrners[i % conrners.length]
            var wallEnd:vector3 = conrners[i+1 % conrners.length] + <0, towerSize.y, 0>
            fill(wallStart, wallEnd, mat)
        endfor
    
    endfunc

closespeak