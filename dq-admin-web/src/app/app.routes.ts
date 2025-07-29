import { Routes } from '@angular/router';
import {MainLayoutComponent} from './pages/layout/main-layout/main-layout.component';
import {LoginComponent} from './pages/auth/login/login.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  { path: '', component: MainLayoutComponent,
    children: [
      { path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.routes').then(m => m.WELCOME_ROUTES) }
    ]
  },
  { path: 'login', component: LoginComponent },
];
