# polygon - array of hashes which represents polygon
# point - hash which represents point to be tested
def inside_polygon?(polygon, p)
  triangles_area(polygon, p) <= polygon_area(polygon)
end

def polygon_area(polygon)
  s = 0
  (0...polygon.length).each do |i|
    p1 = polygon[i]
    p2 = polygon[i - 1]
    s += (p1[:x] + p2[:x]) * (p1[:y] - p2[:y])
  end
  s.abs * 0.5
end

def triangles_area(polygon, p)
  s = 0
  (0...polygon.length).each do |i|
    p1 = polygon[i]
    p2 = polygon[i - 1]
    s += polygon_area([p1, p2, p])
  end
  s
end

