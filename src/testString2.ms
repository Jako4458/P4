minespeak

    func Test(c:num) do
        var a:num = 3
        var b:block = #DIRT
        if (a > 2) do
            b = #COBBLESTONE
        endif
        //var a:bool = #DIRT == b

    endfunc

    func Test2() do
        Test(1)
    endfunc

    @mc
    func Main() do
        Test2()
    endfunc

closespeak