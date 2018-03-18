require 'rspec'
require_relative 'quick_hull'

# inside polygon method will be used to test quick hull correctness
require_relative '../lab1/../lab1/inside_polygon'
# points = [[0, 3], [1, 1], [2, 2], [4, 4],
#           [0, 0], [1, 2], [3, 1], [3, 3]]
#

describe 'QuickHull #hull' do
  let(:hull) { [[1, 1], [0, 2], [1, 4],
                [3, 5], [4, 4], [3, 1]] }

  let(:inside_points) { [[2,2], [3,3], [2,4]] }

  let(:polygon) {
    hull + inside_points
  }

  it 'polygon is hull itself' do
    qh = QuickHull.new(hull)
    expect(qh.hull.count).to eq hull.count
  end

  it 'hull with additional inside points' do
    qh = QuickHull.new(polygon)
    expect(qh.hull.count).to eq hull.count
  end
end