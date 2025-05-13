import { Component, Input } from '@angular/core';

@Component({
  selector: 'content-serie',
  standalone: true,
  template: `
    <h3 class="titulo-serie">{{ tituloSerie }}</h3>
  `,
  styleUrls: ['./serie.component.css'],
})
export class SerieComponent {
  @Input() tituloSerie: string = '';
}