import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LibraryComponent } from './page-library/library/library.component';
import { DetailsComponent } from './page-library/details/details.component';
import { NgStyle } from '@angular/common';
import { LibrarySearchComponent } from './page-library/library/library-search/library-search.component';
import { HttpClientModule } from '@angular/common/http';
import { PageLibraryComponent } from './page-library/page-library.component';
import { LoginComponent } from './login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PageStoreComponent } from './page-store/page-store.component';
import { PageCommunityComponent } from './page-community/page-community.component';
import { PageProfileComponent } from './page-profile/page-profile.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LibraryComponent,
    DetailsComponent,
    LibrarySearchComponent,
    PageLibraryComponent,
    LoginComponent,
    PageStoreComponent,
    PageCommunityComponent,
    PageProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
   
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
