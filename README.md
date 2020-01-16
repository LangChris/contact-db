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
```
Creates a new contact given the contact info
#### Url: "/v1/api/contact"

```
### Get All Contacts
```
Gets all contacts given the sortBy and direction

```
### Search Contacts
```
Filters the contacts given the sortBy, direction and search criteria

```

### Create Custom Field 
```
Creates a new custom field given the name and type

```
### Get All Custom Fields
```
Displays all custom fields

```
### Create Custom Data 
```
Creates Custom Field Data given the contactId, fieldId and value

```
### Get Custom Data By Id
```
Displays all custom field data given a contactId

```
