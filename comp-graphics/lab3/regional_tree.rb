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

  def search(quad)
    min_x = quad.map(&:x).min
    max_x = quad.map(&:x).max
    pr = PointRange.new(min_x, max_x)
    b_search(tree, pr)
  end

  def b_search(node, pr)
    return [] if node.nil?
    result = []
    if node.obj.close <= pr.close && node.obj.beg >= pr.beg
      result = node.obj.points
    else
      result += b_search(node.left, pr) if node.obj.beg < pr.close
      result += b_search(node.right, pr) if node.obj.close > pr.beg
    end
    result
  end

  private :b_search
end
