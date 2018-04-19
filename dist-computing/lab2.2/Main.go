package main

import (
	"fmt"
	"time"
	"math/rand"
)

const N = 100
const M = 100
var seed = rand.NewSource(time.Now().UnixNano())
var random = rand.New(seed)

type Point struct {
	row int
	col int
}

func worker(band [M]int, col int, notFound chan int, found chan Point) {
	for i, region := range band {
		if region == 1 {
			found <- Point{row: i, col: col}
			return
		}
	}
	notFound <- col
}

func main() {
	var forest [N][M]int
	forest[random.Intn(N)][random.Intn(M)] = 1

	var runCounter = 0
	var found = false
	var notFoundChannel = make(chan int)
	var foundChannel = make(chan Point)

	const taskAmount = 5

	for i := 0; i < taskAmount; i++ {
		go worker(forest[i], i, notFoundChannel, foundChannel)
		runCounter++
	}

	for found == false {
		select {
		case res := <- notFoundChannel:
			fmt.Println("Not found in ", res, " region")
			go worker(forest[runCounter], runCounter, notFoundChannel, foundChannel)
			runCounter++
		case res := <- foundChannel:
			found = true
			fmt.Println("Found in ", res.col, res.row)
			if forest[res.col][res.row] == 1 { fmt.Println("Confirmed") }
		}
	}

}
