import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

const baseUrl = "https://09e491a6.ngrok.io/v1/api";

const endpoints = {
    contacts: {
        post: "/contact",
        get: {
            all: "/contacts",
            id: "/contact/"
        },
        search: "/filtered-contacts"
    },
    fields: {
        post: "/custom-field",
        get: {
            all: "/custom-fields"
        }
    },
    data: {
        post: "/custom-data",
        get: {
            id: "/custom-data/"
        }
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

        return this.http.post(baseUrl + endpoints.contacts.post, contact, httpOptions )
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

        return this.http.get(baseUrl + endpoints.contacts.get.all, httpOptions )
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

        return this.http.get(baseUrl + endpoints.contacts.get.id + id, httpOptions )
    }

    // Create A New Custom Field
    createField(field: {}) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.post(baseUrl + endpoints.fields.post, field, httpOptions )
    }

    // Get All Custom Fields
    getFields() {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.get(baseUrl + endpoints.fields.get.all, httpOptions )
    }

    // Create A New Custom Field Data
    createCustomData(data: {}) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.post(baseUrl + endpoints.data.post, data, httpOptions )
    }

    // Get Custom Field Data For A Certain Contact Id
    getCustomData(id: number) {
        let httpOptions = {
            headers: new HttpHeaders({ 'content-type': 'application/json' })
        };

        return this.http.get(baseUrl + endpoints.data.get.id + id, httpOptions )
    }
}
