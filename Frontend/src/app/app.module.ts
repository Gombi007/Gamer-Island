import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LibraryComponent } from './library/library.component';
import { DetailsComponent } from './details/details.component';
import { NgStyle } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LibraryComponent,
    DetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
