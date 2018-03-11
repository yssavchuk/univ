require_relative '../lab3/point'




class QuickHull
  attr_accessor :hull

  def initialize(points)
    @points = points.map { |p| Point.new(p.first, p.last)}
  end

  def hull
    @hull ||= process
  end

  def process
    min = points.min_by { |p| p.x }
    max = points.max_by { |p| p.x }

    quick(min, max, -1)
    quick(min, max, 1)
  end

  def to_s
    @points
  end

  private
  def quick(p1, p2, side)
    with_max_dist =
        points.inject do |with_max, point|
          point if side_to_line(p1, p2, point) == side &&
            dist_to_line(p1, p2, point) > dist_to_line(p1, p2, with_max)
          with_max
        end

    if with_max_dist.nil?
      @hull += [p1, p2]
    else
      quick(with_max_dist, p1, -side_to_line(with_max_dist, p1, p2))
      quick(with_max_dist, p2, -side_to_line(with_max_dist, p1, p2))
    end

  end

  def pow_dist(p1, p2)
    (p1.x - p2.x) ** 2 - (p1.y - p2.y) ** 2
  end

  def vec_product(p1, p2, p)
    (p.y - p1.y) * (p2.x - p1.x) - (p.x - p1.x) * (p2.y - p1.y)
  end

  def side_to_line(p1, p2, p)
    a = vec_product(p1, p2, p)
    if a == 0
      0
    elsif a < 0
      -1
    else
      1
    end
  end

  # returns a value proportional to the distance
  # between the point p and the line joining the
  # points p1 and p2
  def dist_to_line(p1, p2, p)
    p.nil? ? 0 : vec_product(p1, p2, p).abs
  end
