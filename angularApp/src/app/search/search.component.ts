import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service'
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  pageProperties =
  { 
    "title": "Search Contacts",
    "description": "Select your search criteria:"
  }

  fields = [
    { 
      "name": "First Name",
      "value": "firstName"
    },
    { 
      "name": "Last Name",
      "value": "lastName"
    },
    { 
      "name": "Email",
      "value": "email"
    },
    { 
      "name": "Phone",
      "value": "phone"
    },
    { 
      "name": "Address Line",
      "value": "addressLine"
    },
    { 
      "name": "City",
      "value": "city"
    },
    { 
      "name": "State",
      "value": "state"
    },
    { 
      "name": "Zip",
      "value": "zip"
    }
  ]

  operators: { name: string, value: string }[] = [
    { 
      "name": "Contains",
      "value": "contains"
    },
    { 
      "name": "Starts With",
      "value": "starts_with"
    },
    { 
      "name": "Ends With",
      "value": "ends_with"
    },
    { 
      "name": "Equals",
      "value": "equals:"
    }
  ]

  searchData: any;
  sortBy = 'last_name';
  direction = 'asc';
  customFields = true;
  field = '';
  search = '';

  constructor(private api: ApiService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    (document.getElementById('operand') as HTMLSelectElement).disabled = true;

    let field = this.route.snapshot.paramMap.get('field');
    let search = this.route.snapshot.paramMap.get('search');
    let sortBy = this.route.snapshot.paramMap.get('sortBy');
    let direction = this.route.snapshot.paramMap.get('direction');
    let customFields = this.route.snapshot.paramMap.get('customFields');

    this.field = field ? field : this.field;
    this.search = search ? search : this.field;
    this.sortBy = sortBy ? sortBy : this.sortBy;
    this.direction = direction ? direction : this.direction;
    this.customFields = customFields ? true : false;

    if(this.search != '' && this.search != null) {
      this.searchContacts();
    }
  }

  public navigateSearch(field: string, search: string, customFields: boolean) {
    this.field = field;
    this.search = search;
    this.customFields = customFields;
    this.router.navigate(['/search', { field: this.field, search: this.search, sortBy: this.sortBy, direction: this.direction, customFields: this.customFields}]);
   
    if(this.search != '') {
      this.searchContacts();
    }
  }

  public navigateToContact(index: number) {
    let contactId = this.searchData[index]['id'];
    this.router.navigate(['/contact/' + contactId]);
  }

  public updateSort(sortBy: string) {
    let element = document.getElementById('first_name_sort');
    this.sortBy = sortBy;
    this.direction = this.direction == 'asc' ? 'desc' : 'asc';
    if(element.classList.contains('asc')) {
      element.classList.remove('asc');
      element.classList.add('desc');
    } else if(element.classList.contains('desc')) {
      element.classList.remove('desc');
      element.classList.add('asc');
    } 
    
    this.router.navigate(['/search', { field: this.field, search: this.search, sortBy: this.sortBy, direction: this.direction, customFields: this.customFields}]);
   
    if(this.search != '') {
      this.searchContacts();
    }
  }

  public clearSearch() {
    this.searchData = null;
    this.router.navigate(['/search']);
    (document.getElementById('field') as HTMLSelectElement).selectedIndex = 0;
    (document.getElementById('operand') as HTMLSelectElement).selectedIndex = 0;
    (document.getElementById('search') as HTMLInputElement).value = '';
    (document.getElementById('customFields') as HTMLInputElement).checked = false;
  }

  public searchContacts() {
    let body = {};
    if(this.search != '') {
      if(
        this.field === 'addressLine' ||
        this.field === 'city' ||
        this.field === 'state' ||
        this.field === 'zip') {
        let address = {};
        address[this.field] = this.search;
        body = {
          address: address
        };
      } else {
        body[this.field] = this.search;
      }
    }

    this.api.searchContacts(this.sortBy, this.direction, this.customFields, body).subscribe(
      data => { 
        this.searchData = data;
      },
      error => console.log(error)
    );
  }
}
