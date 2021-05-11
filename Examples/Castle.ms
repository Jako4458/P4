minespeak

    @mc
    func Build_Castle() do 
        const wallSize:num = 20
        const wallHeight:num = 10

        // WT = WallTower
        const WTSize:num = 11
        const WTHeight:num = 15

        //MT = MainTower
        const MTWallSize:num = 15
        const MTWallHeight:num = 15

        const mat:block = #cobblestone


        const WTPos1:vector3 = ((wallSize + WTSize)/2) * < -1, 0, -1>
        const WTPos2:vector3 = ((wallSize + WTSize)/2) * < -1, 0, 1>
        const WTPos3:vector3 = ((wallSize + WTSize)/2) * < 1, 0, 1>
        const WTPos4:vector3 = ((wallSize + WTSize)/2) * < 1, 0, -1>
        
        // North/South Walls (expanding the x-axis)
        const NSWallSP1:vector3 = ((wallSize/2) * < -1, 0, -1>) - < 0, 0, 1>
        const NSWallSP2:vector3 = ((wallSize/2) * < -1, 0, -1>) - < 0, 0, 3>
        const NSWallSP3:vector3 = ((wallSize/2) * < -1, 0, 1>)  + < 0, 0, 1>
        const NSWallSP4:vector3 = ((wallSize/2) * < -1, 0, 1>)  + < 0, 0, 3>

        // East/West Walls (expanding the z-axis)
        const EWWallSP1:vector3 = ((wallSize/2)*< -1, 0, -1>) - < 1, 0, 0>
        const EWWallSP2:vector3 = ((wallSize/2)*< -1, 0, -1>) - < 3, 0, 0>
        const EWWallSP3:vector3 = ((wallSize/2)*<  1, 0, -1>) + < 1, 0, 0>
        const EWWallSP4:vector3 = ((wallSize/2)*<  1, 0, -1>) + < 3, 0, 0>


        //build walls
        fill(true, NSWallSP1, wallSize, wallHeight, 1, mat)
        fill(true, NSWallSP2, wallSize, wallHeight, 1, mat)
        fill(true, NSWallSP3, wallSize, wallHeight, 1, mat)
        fill(true, NSWallSP4, wallSize, wallHeight, 1, mat)

        fill(true, EWWallSP1, 1, wallHeight, wallSize, mat)
        fill(true, EWWallSP2, 1, wallHeight, wallSize, mat)
        fill(true, EWWallSP3, 1, wallHeight, wallSize, mat)
        fill(true, EWWallSP4, 1, wallHeight, wallSize, mat)


        // build wall towers
        BuildTower(WTPos1, WTSize, WTHeight, mat)
        BuildTower(WTPos2, WTSize, WTHeight, mat)
        BuildTower(WTPos3, WTSize, WTHeight, mat)
        BuildTower(WTPos4, WTSize, WTHeight, mat)

        // build main tower
        BuildTower(<0, 0, 0>, MTWallSize, MTWallHeight, mat)

    endfunc

    func BuildTower(startPos:vector3, wallSize:num, wallHeight:num, mat:block) do
        // North/South Walls

        var NSWallRPos1:vector3 = startPos + ((wallSize/2) * < -1, 0, -1>)
        var NSWallRPos2:vector3 = startPos + ((wallSize/2) * < -1, 0, 1>)

        // East/West   Walls
        var EWWallRPos1:vector3 = startPos + ((wallSize/2) * < -1, 0, -1>)
        var EWWallRPos2:vector3 = startPos + ((wallSize/2) * <  1, 0, -1>)

        if ((wallSize % 2) == 0) do
            wallSize += 1
        endif

        //build walls
        fill(true, NSWallRPos1, wallSize, wallHeight, 1, mat)
        fill(true, NSWallRPos2, wallSize, wallHeight, 1, mat)
        fill(true, EWWallRPos1, 1, wallHeight, wallSize, mat)
        fill(true, EWWallRPos2, 1, wallHeight, wallSize, mat)

    endfunc

    func fill(isRelative: bool, offset: vector3, length: num, height: num, depth: num, material: block) do
        for var i: num = 0 until i == length where i += 1 do
            for var j: num = 0 until j == height where j += 1 do
                for var k: num = 0 until k == depth where k += 1 do
                    setB(offset + <i, j, k>, material, isRelative)
                endfor
            endfor
        endfor
   endfunc

closespeak