import { Component } from '@angular/core';

@Component({
  selector: 'content-ad-banner',
  standalone: true,
  template: `
    <div class="ad-banner">
      <h3>Publicidad</h3>
    </div>
  `,
  styleUrls: ['./ad-banner.component.css'],
})
export class AdBannerComponent { }