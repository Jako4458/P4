minespeak

    @mc
    func Pyramid() do
        var max_height: num = 10, size: num = 30
        const mat: block = #DIAMOND_BLOCK

        for var height:num = 0 until height == max_height where height += 1 do
            BuildLayer(size - height * 2, <100 + height, 220 + height, 100 + height>, 1, mat)
        endfor
    endfunc

    func BuildLayer(size: num, offset: vector3, height: num, mat: block) do
        Fill(false, offset, size, height, size, mat)
    endfunc

    func Fill(isRelative: bool, offset: vector3, length: num, height: num, depth: num, material: block) do
        for var i: num = 0 until i == length where i += 1 do
            for var j: num = 0 until j == height where j += 1 do
                for var k: num = 0 until k == depth where k += 1 do
                    setB(offset + <i, j, k>, material, isRelative)
                endfor
            endfor
        endfor
   endfunc

closespeak