import { Routes } from '@angular/router';
import { ProfileComponent } from './features/profile/profile.component';
import { CatalogoComponent } from './features/catalogo/catalogo.component';
import { NotFoundComponent } from './features/not-found/not-found.component';

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
  { path: '**',
    component: NotFoundComponent,
    title: 'Página no encontrada | Polaflix',
  },
];

export default routeConfig;