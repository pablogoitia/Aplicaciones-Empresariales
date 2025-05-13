import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-menu',
  standalone: true,
  imports: [RouterModule],
  template: `
    <div class="main-menu">
      <nav>
        <ul>
          <li [class.active]="isActive('/')"><a routerLink="/" routerLinkActive="active">Inicio</a></li>
          <li [class.active]="isActive('/catalogo')"><a routerLink="/catalogo" routerLinkActive="active">Agregar Serie</a></li>
          <li [class.active]="isActive('/facturacion')"><a routerLink="/facturacion" routerLinkActive="active">Ver Cargos</a></li>
        </ul>
      </nav>
    </div>
  `,
  styleUrls: ['./main-menu.component.css'],
})
export class MainMenuComponent {
  constructor(public router: Router) { }

  isActive(route: string): boolean {
    return this.router.url === route;
  }
}