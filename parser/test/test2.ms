minespeak

var max_height: num = 10, size: num = 10
const mat: block = SANDSTONE

@mc
func Pyramid() do
    for height = 0 until height == max_height where height += 1 do
        BuildLayer(size, height, height)
    endfor
endfunc

func BuildLayer(size : num, offset : num, height : num) do

endfunc

closespeak