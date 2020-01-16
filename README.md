# Contact Database

_This is a contact database where you can manage all your contacts._

## Features:

- [x]  `Includes some default fields`
- [x]  `Capability to add your own custom fields`
- [x]  `Includes a search functionality to filter out results by some search criteria using default fields`
- [x]  `Working back end API's`
- - - -

## Upcoming Features:

- [ ]  `Capability to import your own set of contacts/fields from an exported excel spreadsheet or CSV file`
- [ ]  `Presentable front end user interface`
- - - -

## API List:

1. [Create Contact](#create-contact)
2. [Get All Contacts](#get-all-contacts)
3. [Search Contacts](#search-contacts)
4. [Create Custom Field](#create-custom-field)
5. [Get All Custom Fields](#get-all-custom-fields)
6. [Create Custom Data](#create-custom-data)
7. [Get Custom Data By Id](#get-custom-data-by-id)

### Create Contact 
Creates a new contact given the contact info
- **URL** ```/v1/api/contact```
- **Method** ```POST``` 
- **RequestBody** 
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "Email@Address.com",
  "phone": "7031231234",
  "address": {
    "addressLine": "123 Main St.",
    "city": "Fairfax",
    "state": "VA",
    "zip": 22033
  }
}
```
- **Success Response**
  - **Code** ```201 Created```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
### Get All Contacts
Gets all contacts given the sortBy and direction
- **URL** ```/v1/api/contacts```
- **Method** ```GET``` 
- **RequestParams** 
  - **sortBy** ```String```
    - Not Required
  - **direction** ```String```
    - Not Required
  - **customFields** ```Boolean```
    - Not Required
- **Success Response**
  - **Code** ```200 OK```
  - **Content**
  ```json
  [
    {
      "address": {
        "addressLine": "123 Main St.",
        "city": "Fairfax",
        "state": "Va",
        "zip": 22033
      },
      "createdDate": "2020-01-16T14:49:09.996Z",
      "customData": {
        "age": 25,
        "employer": "Google",
        "title": "Software Developer"
      },
      "email": "Email@Address.com",
      "firstName": "John",
      "lastName": "Doe",
      "phone": "7031231234"
    },
    {
      "address": {
        "addressLine": "123 Main St.",
        "city": "Fairfax",
        "state": "Va",
        "zip": 22033
      },
      "createdDate": "2020-01-16T14:49:09.996Z",
      "customData": {},
      "email": "Email@Address.com",
      "firstName": "Jane",
      "lastName": "Doe",
      "phone": "7031231234"
    }
  ]
  ```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
### Search Contacts
Filters the contacts given the sortBy, direction and search criteria
- **URL** ```/v1/api/filtered-contacts```
- **Method** ```POST``` 
- **RequestParams** 
  - **sortBy** ```String```
    - Not Required
  - **direction** ```String```
    - Not Required
  - **customFields** ```Boolean```
    - Not Required
- **RequestBody** 
```json
{
  "lastName": "Doe",
  "address": {
    "city": "Fairfax"
  }
}
```
- **Success Response**
  - **Code** ```200 OK```
  - **Content**
  ```json
  [
    {
      "address": {
        "addressLine": "123 Main St.",
        "city": "Fairfax",
        "state": "Va",
        "zip": 22033
      },
      "createdDate": "2020-01-16T14:49:09.996Z",
      "customData": {
        "age": 25,
        "employer": "Google",
        "title": "Software Developer"
      },
      "email": "Email@Address.com",
      "firstName": "John",
      "lastName": "Doe",
      "phone": "7031231234"
    },
    {
      "address": {
        "addressLine": "123 Main St.",
        "city": "Fairfax",
        "state": "Va",
        "zip": 22033
      },
      "createdDate": "2020-01-16T14:49:09.996Z",
      "customData": {},
      "email": "Email@Address.com",
      "firstName": "Jane",
      "lastName": "Doe",
      "phone": "7031231234"
    }
  ]
  ```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
### Create Custom Field 
Creates a new custom field given the name and type
- **URL** ```/v1/api/custom-field```
- **Method** ```POST``` 
- **RequestBody** 
```json
{
  "name": "age",
  "type": "INT"
}
```
- **Success Response**
  - **Code** ```201 Created```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
### Get All Custom Fields
Displays all custom fields
- **URL** ```/v1/api/custom-fields```
- **Method** ```GET``` 
- **Success Response**
  - **Code** ```200 OK```
  - **Content**
  ```json
  [
    {
      "name": "age",
      "value": null,
      "type": "INT"
    },
    {
      "name": "occupation",
      "value": null,
      "type": "STRING"
    },
    {
      "name": "birthday",
      "value": null,
      "type": "STRING"
    },
    {
      "name": "chores_completed",
      "value": "YES,NO",
      "type": "STRING_LIST"
    },
    {
      "name": "favorite_number",
      "value": "1,2,3,4,5,6,7",
      "type": "INT_LIST"
    },
    {
      "name": "employer",
      "value": null,
      "type": "STRING"
    },
    {
      "name": "language",
      "value": null,
      "type": "STRING"
    }       
  ]
  ```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
### Create Custom Data 
Creates custom field data given the contactId, fieldId and value
- **URL** ```/v1/api/custom-data```
- **Method** ```POST``` 
- **RequestBody** 
```json
{
  "contactId": 12,
  "fieldId": 1,
  "value": "01/12/1991"
}
```
- **Success Response**
  - **Code** ```201 Created```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
### Get Custom Data By Id
Displays all custom field data given a contactId
- **URL** ```/v1/api/custom-data/{id}```
- **Method** ```GET``` 
- **URLParams**
  - **id** ```Long```
    - Required
- **Success Response**
  - **Code** ```200 OK```
  - **Content**
  ```json
  {
    "birthday": "07/18/1994",
    "chores_completed": "NO",
    "occupation": "Software Developer",
    "favorite_number": 7,
    "employer": "Octo Consulting Group",
    "language": "English",
    "age": 25
  }
  ```
- **Error Response**
  - **Code** ```401 Unauthorized```
  - **Content**
  ```json
  {
    "timestamp": 1502445801140,
    "status": 401,
    "error": "Unauthorized",
    "message": "You are not authorized to view the resource"
  }
  ```
