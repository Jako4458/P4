minespeak

    func Test(c:num) do
        var a:num = 3

        for var i:num = 2 until i > a where i += 1 do
            var b:block = #DIRT
        endfor

    endfunc

    func Test2() do
        Test(1)
    endfunc

    @mc
    func Main() do
        Test2()
    endfunc

closespeak