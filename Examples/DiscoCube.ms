minespeak
   // Builds a gold-dirt discocube at coordinates <100, 245, 0>
   @mc
   func Test2() do
        var myBlock: block = #DIRT
        var dim: num = 10
        for var i: num = 0 until i == dim where i += 1 do
            for var j: num = 0 until j == dim where j += 1 do
                for var k: num = 0 until k == dim where k += 1 do
                    if (i + j + k) % 2 == 0 do
                        myBlock = #gold_block
                    else do
                        myBlock = #dirt
                    endif
                    setB(<100 + j, 245 + k, i>, myBlock, false)
                endfor
            endfor
        endfor
   endfunc
closespeak