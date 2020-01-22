import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service'
import { Router } from '@angular/router';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {

  pageProperties =
  { 
    "title": "Contacts",
    "description": ""
  }

  contacts: any;

  constructor(private api: ApiService, private router: Router) { }

  ngOnInit() {
    this.getContacts("last_name", "asc", true);
  }

  public getContacts(sortBy: string, direction: string, customFields: boolean) {
    this.api.getContacts(sortBy, direction, customFields).subscribe(
      data => { 
        this.contacts = data;
      },
      error => console.log(error)
    );
  }

  public navigateToContact(index: number) {
    let contactId = this.contacts[index]['id'];
    this.router.navigate(['/contact/' + contactId]);
  }
}
