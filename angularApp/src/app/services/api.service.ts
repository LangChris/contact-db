import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

const baseUrl = "http://localhost:8080/v1/api";

const endpoints = {
    contact: {
        get: "/contact/"
    },
    contacts: {
        create: "/contact",
        all: "/contacts",
        search: "/filtered-contacts"
    },
    fields: {
        create: "/custom-field",
        all: "/custom-fields"
    },
    data: {
        create: "/custom-data",
        get: "/custom-data/"
    }
};

@Injectable()
export class ApiService {

    constructor(private http: HttpClient) {}

    // Create A New Contact
    createContact(contact: {}) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.post(baseUrl + endpoints.contacts.create, contact, httpOptions )
    }

    // Get All Contacts Given Parameters (sortBy, direction, customFields)
    getContacts(sortBy: string, direction: string, customFields: boolean) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' }),
            params: new HttpParams()
        };
        if(sortBy) { httpOptions.params = httpOptions.params.append('sortBy', sortBy) }
        if(direction) { httpOptions.params = httpOptions.params.append('direction', direction) }
        if(customFields) { httpOptions.params = httpOptions.params.append('customFields', customFields.toString()) }

        return this.http.get(baseUrl + endpoints.contacts.all, httpOptions )
    }

    // Get All Contacts Given Parameters (sortBy, direction, customFields) and Search Criteria
    searchContacts(sortBy: string, direction: string, customFields: boolean, searchCriteria: {}) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' }),
            params: new HttpParams()
        };
        
        if(sortBy) { httpOptions.params = httpOptions.params.append('sortBy', sortBy) }
        if(direction) { httpOptions.params = httpOptions.params.append('direction', direction) }
        if(customFields) { httpOptions.params = httpOptions.params.append('customFields', customFields.toString()) }

        return this.http.post(baseUrl + endpoints.contacts.search, searchCriteria, httpOptions )
    }

    // Get Contact By Contact Id and customFields
    getContact(id: number, customFields: boolean) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' }),
            params: new HttpParams()
        };

        if(customFields) { httpOptions.params = httpOptions.params.append('customFields', customFields.toString()) }

        return this.http.get(baseUrl + endpoints.contact.get + id, httpOptions )
    }

    // Create A New Custom Field
    createField(field: {}) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.post(baseUrl + endpoints.fields.create, field, httpOptions )
    }

    // Get All Custom Fields
    getFields() {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.get(baseUrl + endpoints.fields.all, httpOptions )
    }

    // Create A New Custom Field Data
    createCustomData(data: {}) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.post(baseUrl + endpoints.data.create, data, httpOptions )
    }

    // Get Custom Field Data For A Certain Contact Id
    getCustomData(id: number) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.get(baseUrl + endpoints.data.get + id, httpOptions )
    }
}
