import { Routes } from '@angular/router';
import { HousingHomeComponent } from './housing-home/housing-home.component';
import { DetailsComponent } from './details/details.component';

const routeConfig: Routes = [
  {
    path: 'housing',
    component: HousingHomeComponent,
    title: 'Home page',
  },
  {
    path: 'housing/details/:id',
    component: DetailsComponent,
    title: 'Home details',
  },
];

export default routeConfig;