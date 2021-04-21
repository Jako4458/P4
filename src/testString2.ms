minespeak

    func Test(b:num) do
        var a:num = -b
        var c:num = 2
        $tp ~v{a}
    endfunc

    @mc
    func Main() do
        Test(2)
    endfunc

closespeak