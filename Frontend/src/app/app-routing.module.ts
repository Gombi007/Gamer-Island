import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './login/service/auth.guard';
import { PageCommunityComponent } from './page-community/page-community.component';
import { PageLibraryComponent } from './page-library/page-library.component';
import { PageProfileComponent } from './page-profile/page-profile.component';
import { GameDetailComponent } from './page-store/game-detail/game-detail.component';
import { PageStoreComponent } from './page-store/page-store.component';

const routes: Routes = [
  { path: "", redirectTo: 'store', pathMatch: 'full' },
  { path: "store", component: PageStoreComponent, canActivate: [AuthGuard] },
  { path: "library", component: PageLibraryComponent, canActivate: [AuthGuard] },
  { path: "community", component: PageCommunityComponent, canActivate: [AuthGuard] },
  { path: "profile", component: PageProfileComponent, canActivate: [AuthGuard] },
  { path: "store/:steamAppid", component: GameDetailComponent, canActivate: [AuthGuard] },
  { path: "login", component: LoginComponent },
  { path: "**", component: LoginComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
