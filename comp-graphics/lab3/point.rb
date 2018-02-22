class Point
  attr_accessor :x, :y

  def initialize(x = 10, y = 10)
    @x = x
    @y = y
  end

  def to_s
    "Point (#{x},#{y})"
  end
end