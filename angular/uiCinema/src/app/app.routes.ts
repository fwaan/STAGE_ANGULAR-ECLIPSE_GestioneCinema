import { Routes } from '@angular/router';
import { Home } from './home/home';
import { FilmDetails } from './film-details/film-details';
import { AdminHome } from './admin-home/admin-home';
import { adminGuard } from './admin-guard';

export const routes: Routes = [
    {
    path: '',
    component: Home,
    title: 'Home page',
  },
  {
    path: 'details/:title',
    component: FilmDetails,
    title: 'Film details',
  },
  {
    path: 'admin',
    component: AdminHome,
    title: 'Admin home',
    canActivate: [adminGuard]
  }
];
