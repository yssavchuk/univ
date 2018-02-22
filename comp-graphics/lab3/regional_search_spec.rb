require 'rspec'
require_relative 'regional_tree'

describe 'RegionalTree#search' do

  let(:quad) { [Point.new(1,4), Point.new(4, 1)] }
  let(:outside) { [Point.new(4,3), Point.new(0, 0), Point.new(5,5)] }
  let(:inside) { [Point.new(2,3), Point.new(3, 2), Point.new(2,2)] }

  it 'points outside of quad' do
    rt = RegionalTree.new(0,10, outside)
    expect(rt.search(quad).count).to be 0
  end

  it 'points inside of quad' do
    rt = RegionalTree.new(0,10, inside)
    expect(rt.search(quad).count).to be inside.count
  end

  it 'both inside and outside of quad' do
    rt = RegionalTree.new(0,10, inside + outside)
    expect(rt.search(quad).count).to be inside.count
  end
end
