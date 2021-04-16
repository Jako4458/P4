minespeak

    func Test2() -> num do
        var a:num = Test(2, <1,2,3>)
        Test(3, <3,2,1>)
        return 3
    endfunc

    func Test(b:num, f:vector3) -> num do
        var a:num = 0x41 + b
        var c:bool = true
        var e:vector2 = <1,2>
        $tp ~v{b} v{e}
        var d:vector3 = <a,21,0x3A>
        $tp ~v{f}

        return 4
    endfunc

    @mc
    func Main() do
        var a:num = Test2()
    endfunc

    @mc
    func Main2() do
        Main()
    endfunc


closespeak