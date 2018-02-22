require_relative 'binary_tree'
require_relative 'point_range'
require_relative 'point'

class SectionTree
  include BinaryTree

  attr_accessor :tree

  def initialize(first, last)
    @tree = add_range(first, last)
  end

  def add_range(first, last)
    pr = PointRange.new(first, last)
    node = Node.new(pr)
    center = (first + last) / 2
    if last - first > 1
      node.insert(add_range(first, center)) if first != center
      node.insert(add_range(center, last)) if last != center
    end
    node
  end
end
