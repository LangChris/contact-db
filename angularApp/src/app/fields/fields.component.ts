import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-fields',
  templateUrl: './fields.component.html',
  styleUrls: ['./fields.component.css']
})
export class FieldsComponent implements OnInit {

  pageProperties =
  { 
    "title": "Custom Fields",
    "description": ""
  }

  fields: any;

  constructor(private api: ApiService) { }

  ngOnInit() {
    this.getFields();
  }

  public getFields() {
    this.api.getFields().subscribe(
      data => { 
        this.fields = data;
      },
      error => console.log(error)
    );
  }
}
