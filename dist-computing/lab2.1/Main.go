package main

import (
	"fmt"
	"math/rand"
	"time"
	"sync"
)

const N = 100
var storage [N]int
var w sync.WaitGroup

func ivanovProducer(bufferChan chan int, done chan bool) {
	defer w.Done()
	for i := 0; i < N; i++ {
		time.Sleep(10 * time.Millisecond)
		bufferChan <- storage[i]
		fmt.Println("Stolen ", storage[i])
	}
	fmt.Println("Ivanov finished")
	done <- true
}

func petrovBuffer(inChan chan int, outChan chan int, doneIn chan bool, doneOut chan bool) {
	defer w.Done()
	for {
		select {
		case s := <- inChan:
			outChan <- s
			fmt.Println("Put ino car", s)
		case <- doneIn:
			fmt.Println("Petrov finished")
			doneOut <- true; return
		}
	}
}

func necheporukConsumer (inChan chan int, done chan bool) {
	defer w.Done()
	sum := 0
	for {
		select {
		case s:= <- inChan: sum += s
		case <- done:
			fmt.Println("Sum is", sum)
			fmt.Println("Necheporuk finished"); return
		}
	}
}

func initStorage() {
	for i := 0; i < N; i++ {
		storage[i] = rand.Intn(10)
	}
}
//Иванов выносит имущество со склада, Петров грузит его в грузовик,
// а Нечепорчук подсчитывает рыночную стоимость добычи.
func main() {
	initStorage()
	doneProd := make(chan bool)					//stolen everything
	doneCons := make(chan bool)					//calculated everything
	prodBuffChan := make(chan int)			//stolen item number
	buffConsChan := make(chan int, 100)	//put item into car

	w.Add(3)
	go ivanovProducer(prodBuffChan, doneProd)
	go petrovBuffer(prodBuffChan, buffConsChan, doneProd, doneCons)
	go necheporukConsumer(buffConsChan, doneCons)
	w.Wait()
}
