require 'rspec'
require_relative 'inside_polygon'

describe '#inside_polygon' do
  let(:polygon1) { [{ x: 1, y: 1 },
                    { x: -1, y: 4 },
                    { x: 2, y: 5 }] }

  let(:polygon2) { [{ x: 10, y: 10 },
                    { x: 10, y: -10 },
                    { x: -10, y: -10 },
                    { x: -10, y: 10 }] }

  def move_outside(p)
    { x: p[:x] * 2, y: p[:y] * 2 }
  end

  it 'points which represents polygon' do
    polygon1.each { |p| expect(inside_polygon?(polygon1, p)).to be true }
    polygon2.each { |p| expect(inside_polygon?(polygon2, p)).to be true }
  end

  it 'points of polygon which belongs to other' do
    polygon1.each { |p| expect(inside_polygon?(polygon2, p)).to be true }
  end

  it 'points of polygon which covers other polygon' do
    polygon2.each { |p| expect(inside_polygon?(polygon1, p)).to be false }
  end

  it 'outside points' do
    polygon1.each { |p| expect(inside_polygon?(polygon1, move_outside(p))).to be false }
    polygon2.each { |p| expect(inside_polygon?(polygon2, move_outside(p))).to be false }
  end
end