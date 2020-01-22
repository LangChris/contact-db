import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Custom Contact Database';

  constructor() {
    function toggleMore():void {
      let more = document.getElementById("more");
      more.addEventListener("click", (e:Event) => this.toggleMore());
    }
  }

  public toggleMore() {
    console.log("more clicked..");
  }
  
}
