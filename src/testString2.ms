minespeak

    func Test(c:num) do
        var a:block
        a = #DIRT
    endfunc

    func Test2() do
        Test(1)
    endfunc

    @mc
    func Main() do
        Test2()
    endfunc

closespeak