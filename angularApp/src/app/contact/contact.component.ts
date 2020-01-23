import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service'
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  pageProperties =
  { 
    "title": "Contact",
    "description": ""
  }

  contact: any;
  id: number;
  customFields = false;
  customData = [];

  constructor(private api: ApiService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    let id: any;
    if(window.location.href.includes(';')) {
      id = window.location.href.substring(window.location.href.lastIndexOf('/') + 1, window.location.href.indexOf(';'));
    } else {
      id = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    }
    this.id = +id;
    let customFields = this.route.snapshot.paramMap.get('customFields');
    this.customFields = customFields ? true : false;
    (document.getElementById('customFields') as HTMLInputElement).checked = this.customFields;

    this.getContact(this.id, this.customFields);
  }

  public updateCustomFields() {
    let id: any;
    if(window.location.href.includes(';')) {
      id = window.location.href.substring(window.location.href.lastIndexOf('/') + 1, window.location.href.indexOf(';'));
    } else {
      id = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    }
    this.id = +id;
    this.customFields = (document.getElementById('customFields') as HTMLInputElement).checked;
    this.router.navigate(['/contact/' + this.id, { customFields: this.customFields}]);

    this.getContact(this.id, this.customFields);
  }

  public getContact(id: number, customFields: boolean) {
    this.api.getContact(id, customFields).subscribe(
      data => { 
        this.contact = data;
        if(this.customFields) {
          for( var i = 0; i < Object.keys(this.contact.customData).length; i++) {
            this.customData.push(
              {
                name: Object.keys(this.contact.customData)[i],
                value: Object.values(this.contact.customData)[i]
              });
          }
        } else {
          this.customData = [];
        }
      },
      error => console.log(error)
    );
  }
  
}
