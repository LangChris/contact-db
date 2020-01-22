import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-new-field',
  templateUrl: './new-field.component.html',
  styleUrls: ['./new-field.component.css']
})
export class NewFieldComponent implements OnInit {

  pageProperties =
  { 
    "title": "New Field",
    "description": ""
  }

  fieldTypes = [
    { 
      "name": "Text",
      "value": "STRING"
    },
    { 
      "name": "Number",
      "value": "INT"
    },
    { 
      "name": "True/False",
      "value": "BOOLEAN"
    },
    { 
      "name": "Text List",
      "value": "STRING_LIST"
    },
    { 
      "name": "Number List",
      "value": "INT_LIST"
    }
  ]

  field: string;

  constructor(private api: ApiService) { }

  ngOnInit() {
  }

  public newField(name: string, type: string, value: string) {
    let body = {
      name: name,
      type: type,
      value: value
    };
    this.api.createField(body).subscribe(
      data => { 
        this.field = "success"
      },
      error => {
        if(error.status == 201) {
          this.field = "success"
        } else {
          this.field = "fail";
          console.log(error)
        }
      }
    );
  }

}
