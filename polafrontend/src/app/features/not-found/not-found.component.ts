import { Component } from '@angular/core';

@Component({
  selector: 'not-found',
  standalone: true,
  template: `
    <div class="not-found-container">
      <h1 class="not-found-title">404</h1>
      <p class="not-found-message">Lo sentimos, la página que buscas no existe.</p>
    </div>
  `,
  styleUrls: ['./not-found.component.css'],
})
export class NotFoundComponent { }