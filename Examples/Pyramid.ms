minespeak

    @mc
    func Pyramid() do

        var max_height: num = 10, size: num = 10
        const mat: block = #BLOCK_OF_DIAMOND

        for var height:num = 0 until height == max_height where height += 1 do
            BuildLayer(size, height, height)
        endfor
    endfunc

    func BuildLayer(size: num, offset: num, height: num) do
        Fill(self, <offset, height, offset>, <size, height, size>, mat)
    endfunc

closespeak