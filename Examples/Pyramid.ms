minespeak
    // Builds a pyramid at coordinates <100, 220, 100>
    @mc
    func Pyramid() do
        var max_height: num = 10, size: num = 30
        const mat: block = #DIAMOND_BLOCK

        for var height:num = 0 until height == max_height where height += 1 do
            BuildLayer(size - height * 2, <100 + height, 220 + height, 100 + height>, 1, mat)
        endfor
    endfunc

    func BuildLayer(size: num, offset: vector3, height: num, mat: block) do
        fill(offset, size, height, size, mat, false)
    endfunc

closespeak