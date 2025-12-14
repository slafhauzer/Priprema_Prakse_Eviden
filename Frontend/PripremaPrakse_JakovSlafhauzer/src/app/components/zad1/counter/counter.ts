import { Component } from '@angular/core';

@Component({
  selector: 'app-counter',
  imports: [],
  templateUrl: './counter.html',
  styleUrl: './counter.css',
})
export class Counter {
  count = 0;

  increment() {
    this.count++;
  }

  decrement(){
    this.count--;
  }

  reset(){
    this.count = 0;
  }
}
