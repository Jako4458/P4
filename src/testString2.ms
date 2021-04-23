minespeak

    func Test(c:num) do
        var a:num = c+2-(-3) Pow 3*4
        if false do
            var b:vector3 = <1,2,3>-1*<1,2,3>
        elif a <= 2 do
            var b:num = 2
        else do
            var b:bool = true
        endif
    endfunc

    func Test2() do
        Test(2)
    endfunc

    @mc
    func Main() do
        Test2()
    endfunc

closespeak