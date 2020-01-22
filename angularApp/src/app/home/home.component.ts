import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  pageProperties: { title: string, description: string } =
  { 
    "title": "Contact Database",
    "description": "What do you want to do:"
  }

  response: any;

  constructor(private route: ActivatedRoute, private router: Router, private api: ApiService) {

   }

  ngOnInit() {
    
  }

  public navigateToAllContacts() {
    this.router.navigate(['/contacts'])
  }

  public navigateToAddContact() {
    this.router.navigate(['/new-contact'])
  }

  public navigateToAllFields() {
    this.router.navigate(['/fields'])
  }

  public navigateToAddField() {
    this.router.navigate(['/new-field'])
  }

  public createContact(contact: {}) {
    this.api.createContact(contact).subscribe(
      data => { 
        this.response = data;
        console.log(this.response);
      },
      error => console.log(error)
    );
  }

  public getContacts(sortBy: string, direction: string, customField: boolean) {
    this.api.getContacts(sortBy, direction, customField).subscribe(
      data => { 
        this.response = data;
        console.log(this.response);
      },
      error => console.log(error)
    );
  }

  public searchContacts(sortBy: string, direction: string, customField: boolean, searchCriteria: {}) {
    this.api.searchContacts(sortBy, direction, customField, searchCriteria).subscribe(
      data => { 
        this.response = data;
        console.log(this.response);
      },
      error => console.log(error)
    );
  }

  public createField(field: {}) {
    this.api.createField(field).subscribe(
      data => { 
        console.log(data);
      },
      error => console.log(error)
    );
  }

  public getFields() {
    this.api.getFields().subscribe(
      data => { 
        this.response = data;
        console.log(this.response);
      },
      error => console.log(error)
    );
  }

  public createCustomData(data: {}) {
    this.api.createCustomData(data).subscribe(
      data => { 
        console.log(data);
      },
      error => console.log(error)
    );
  }

  public getCustomData(id: number) {
    this.api.getCustomData(id).subscribe(
      data => { 
        this.response = data;
        console.log(this.response);
      },
      error => console.log(error)
    );
  }

}
