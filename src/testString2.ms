minespeak

    func Test(c:num) do
        var a:num = c+2-(-3*4) Pow 5*6
        if a < 2 and true do
            var b:num = 2
        elif a == 2 do
            var b:num = 3
        elif a > 2 do
            var b:num = 4
        endif

    endfunc

    func Test2() do
        Test(1)
    endfunc

    @mc
    func Main() do
        Test2()
    endfunc

closespeak