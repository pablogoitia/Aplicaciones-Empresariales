import { Routes } from '@angular/router';
import { ProfileComponent } from './features/profile/profile.component';
import { CatalogoComponent } from './features/catalogo/catalogo.component';

const routeConfig: Routes = [
  {
    path: '',
    component: ProfileComponent,
    title: 'Inicio | Polaflix',
  },
  {
    path: 'catalogo',
    component: CatalogoComponent,
    title: 'Catálogo de Series | Polaflix',
  },
];

export default routeConfig;