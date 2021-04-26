minespeak

    func Test(c:num) do
        var b:block = #DIRT
        var a:bool = #DIRT == b

    endfunc

    func Test2() do
        Test(1)
    endfunc

    @mc
    func Main() do
        Test2()
    endfunc

closespeak