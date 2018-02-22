class PointRange

  include Comparable

  attr_accessor :beg, :close, :points

  def initialize(beg, close)
    @beg = beg
    @close = close
    @points = []
  end

  def add_points(p)
    p.select { |ps| ps.x >= beg && ps.x < close }.tap { |ps| @points += ps }
  end

  # If the receiver of the call is
  # greater than the argument, it should return 1,
  # if they re the same, it should return 0,
  # if the receiver is less than the argument it should return -1
  # and if they’re not compatible it should return nil.
  def <=>(other)
    average <=> other.average
  end

  def average
    (beg + close).to_f / 2
  end
end
