minespeak
  /*
  Builds the Minespeak logo 5 blocks away from the player.
  */
  @mc
  func main() do
    var height:num = 20
    var rel:bool = true
    var start:vector3 = <5, 0, 0>
    var next:vector3 = createM(start, height, #crimson_stem, rel)
    createS(next + <2, -height + 1, 0>, height, height, #warped_stem, rel)
    fill(start + <-10, -10, -5>, 62, 40, 1, #black_concrete, rel)
  endfunc

  func createM(pos:vector3, height:num, b:block, rel:bool) -> vector3 do
    var test:vector3 = fill(pos, 1, height, 1, b, rel)

    fill(pos, 1, height, 1, b, rel)

    var current:vector3 = pos + <1, height - 1, 0>

    for var i:num = height until i == height/2 where i -= 1 do
      setB(current, b, rel)
      current = current + <1, -1, 0>
    endfor

    current = current + <0, 2, 0>

    for var i:num = height/2 until i == height - 1 where i += 1 do
      setB(current, b, rel)
      current = current + <1, 1, 0>
    endfor

    return fill(current + <0, -height, 0>, 1, height, 1, b, rel)

  endfunc


  func createS(pos:vector3, height:num, length:num, b:block, rel:bool) do
    var current:vector3 = fill(pos, length, 1, 1, b, rel)
    fill(pos + <0, height/2, 0>, length, 1, 1, b, rel)
    fill(pos + <0, height - 1, 0>, length, 1, 1, b, rel)

    fill(current, 1, height/2, 1, b, rel)
    fill(pos + <0, height/2, 0>, 1, height/2 - 1, 1, b, rel)
  endfunc

closespeak