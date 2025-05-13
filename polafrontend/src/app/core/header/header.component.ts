import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule],
  template: `
    <header class="header">
        <div class="header-left">
            <img class="brand-logo" src="/assets/polaflix-white.svg" alt="Logo de Polaflix" routerLink="/" title="Ir a la página principal" aria-hidden="true" />
        </div>
        <div class="header-right">
            <p class="username" title="Mi perfil">Bienvenido, {{ username }}</p>
            <div class="profile-pic"><p class="profile-pic-letter" title="Mi perfil">{{ username[0].toUpperCase() }}</p></div>
        </div>
    </header>
  `,
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  @Input() username!: string;
}
