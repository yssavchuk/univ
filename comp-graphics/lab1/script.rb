#!/usr/bin/env ruby

# points - array of hashes which represents polygon
# p - hash which represents point which will be tested
def inside_polygon?(points, p)
  result = false
  (0...points.length).each do |i|
    p1 = points[i]
    p2 = points[i - 1]

    result = !result if ((p1[:y] <= p[:y] && p[:y] < p2[:y]) || (p2[:y] <= p[:y] && p[:y] < p1[:y])) &&
        (p[:x] - p1[:x] > ((p2[:x] - p1[:x]) * (p[:y] - p1[:y]) / (p2[:y] - p1[:y])))
  end
  result
end

points = [{ x: 1, y: 1 },
          { x: 3, y: 3 },
          { x: 2, y: 5 }]

point = { x: 1.5, y: 1.5 }
puts "#{inside_polygon?(points, point)} == true"

point = { x: 4, y: 4 }
puts "#{inside_polygon?(points, point)} == false"

points = [{ x: 10, y: 10 },
          { x: 10, y: -10 },
          { x: -10, y: -10 },
          { x: -10, y: 10 }]

point = { x: 1.5, y: 1.5 }
puts "#{inside_polygon?(points, point)} == true"

