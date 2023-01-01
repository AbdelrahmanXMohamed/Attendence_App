import { Component } from '@angular/core';

@Component({
  selector: 'app-dasboard',
  templateUrl: './dasboard.component.html',
  styleUrls: ['./dasboard.component.css']
})
export class DasboardComponent {
  state: string = "Today"
  getTeamStatusBy(state: string) {
    this.state = state
    console.log(this.state)
  }
}
