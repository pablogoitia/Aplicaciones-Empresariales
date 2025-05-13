import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './core/header/header.component';
import { MainMenuComponent } from './core/main-menu/main-menu.component';
import { FooterComponent } from './core/footer/footer.component';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, MainMenuComponent, FooterComponent],
  template: `
    <body class="main">
      <app-header [username]="username"/>
      <app-main-menu/>
      <section class="content">
        <router-outlet></router-outlet>
      </section>
      <app-footer/>
    </body>
  `,
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  username = environment.def_username;
}