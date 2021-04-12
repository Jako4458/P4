minespeak

    func Test(b:num) do
        var a:num = 0x41 + 4
        var c:bool = true
        $tp ~v{a} ~v{c} ~0
    endfunc

    func Test2() do
        Test(2)
    endfunc

closespeak