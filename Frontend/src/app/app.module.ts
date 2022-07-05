import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LibraryComponent } from './page-library/library/library.component';
import { DetailsComponent } from './page-library/details/details.component';
import { LibrarySearchComponent } from './page-library/library-search/library-search.component';
import { HttpClientModule } from '@angular/common/http';
import { PageLibraryComponent } from './page-library/page-library.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PageStoreComponent } from './page-store/page-store.component';
import { PageCommunityComponent } from './page-community/page-community.component';
import { PageProfileComponent } from './page-profile/page-profile.component';
import { StoreSearchComponent } from './page-store/store-search/store-search.component';
import { StoreElementsComponent } from './page-store/store-elements/store-elements.component';
import { GameDetailComponent } from './page-store/game-detail/game-detail.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CartComponent } from './page-store/cart/cart.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';


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
    PageProfileComponent,
    StoreSearchComponent,
    StoreElementsComponent,
    GameDetailComponent,
    CartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule, 
    NgxPaginationModule,
    NgxSliderModule,
    FormsModule

   
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
