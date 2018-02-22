require_relative 'section_tree'

class RegionalTree < SectionTree

  def initialize(start, close, points)
    super start, close
    return if points.empty?
    insert_points points, tree
  end

  def insert_points(points, node)
    return if node.nil?
    new_points = node.obj.add_points points
    insert_points(new_points, node.left)
    insert_points(new_points, node.right)
  end
end
