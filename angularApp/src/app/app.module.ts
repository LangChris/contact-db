import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ApiService } from './services/api.service';
import { ContactsComponent } from './contacts/contacts.component';
import { FieldsComponent } from './fields/fields.component';
import { NewContactComponent } from './new-contact/new-contact.component';
import { NewFieldComponent } from './new-field/new-field.component';
import { ContactComponent } from './contact/contact.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { FooterComponent } from './footer/footer.component';
import { VersionComponent } from './global/version/version.component';
import { CopywriteComponent } from './global/copywrite/copywrite.component';

const routes: Routes = [
  {
      path: "",
      component: HomeComponent
  },
  {
    path: "search",
    component: SearchComponent
  },
  {
    path: "contacts",
    component: ContactsComponent
  },
  {
    path: "contact/:index",
    component: ContactComponent
  },
  {
    path: "fields",
    component: FieldsComponent
  },
  {
    path: "new-contact",
    component: NewContactComponent
  },
  {
    path: "new-field",
    component: NewFieldComponent
  },
  {
    path: "**",
    component: NotFoundComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchComponent,
    NotFoundComponent,
    ContactsComponent,
    FieldsComponent,
    NewContactComponent,
    NewFieldComponent,
    ContactComponent,
    NavBarComponent,
    FooterComponent,
    VersionComponent,
    CopywriteComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
