import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-new-contact',
  templateUrl: './new-contact.component.html',
  styleUrls: ['./new-contact.component.css']
})
export class NewContactComponent implements OnInit {

  pageProperties =
  { 
    "title": "New Contact",
    "description": ""
  }

  contact: any;

  constructor(private api: ApiService) { }

  ngOnInit() {
  }

  public newContact(firstName: string, lastName: string, email: string, phone: string, addressLine: string, city: string, state: string, zip: number) {
    let address = {
      addressLine: addressLine,
      city: city,
      state: state, 
      zip: zip
    };
    let body = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      phone: phone,
      address: address
    };
    this.api.createContact(body).subscribe(
      data => { 
        this.contact = data;
      },
      error => console.log(error)
    );
  }
}
