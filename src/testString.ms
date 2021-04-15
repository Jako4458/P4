minespeak

    func Test(b:num) do
        var a:num = 0x41 + 4
        var c:bool = true
        var e:vector2 = <1,2>
        var d:vector3 = <a,21,0x3A>
        $tp v{d}

    endfunc

    func Test2() do
        Test(2)
    endfunc

closespeak