package main

import (
	"time"
	"fmt"
	"math/rand"
)

var toHairdresser = make(chan int)

var seed = rand.NewSource(time.Now().UnixNano());

var random = rand.New(seed);

func runHairdresser() {
	for {
		select {
		case client := <- toHairdresser:
			fmt.Println("Hairdresser started work with client ", client)
			time.Sleep(1000 * time.Millisecond)
			fmt.Println("Hairdresser done work with client ", client)
		default:
			fmt.Println("Hairdresser is sleeping")
		}
	}

}

func main() {
	var i = 0
	go runHairdresser()
	for {
		var sleepTime = time.Duration(random.Intn(5000) + 500)
		time.Sleep(sleepTime)
		toHairdresser <- i
		i++
		if i == 4 {
			time.Sleep(10000 * time.Millisecond)
		}

	}
}
