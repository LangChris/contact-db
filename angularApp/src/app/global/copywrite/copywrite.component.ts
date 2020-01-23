import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-copywrite',
  templateUrl: './copywrite.component.html',
  styleUrls: ['./copywrite.component.css']
})
export class CopywriteComponent implements OnInit {

  copywrite = "Christopher Lang 2020 | All Rights Reserved";

  constructor() { }

  ngOnInit() {
  }

}
