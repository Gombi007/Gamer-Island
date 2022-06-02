import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageLibraryComponent } from './page-library/page-library.component';

const routes: Routes = [
  {path: "", component:PageLibraryComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
