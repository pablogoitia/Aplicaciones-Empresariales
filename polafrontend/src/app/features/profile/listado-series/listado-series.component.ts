import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SerieComponent } from './serie/serie.component';
import { ResumenSerieInterface } from '../../../shared/interfaces/serie.interface';

@Component({
  selector: 'content-listado-series',
  standalone: true,
  imports: [CommonModule, SerieComponent],
  template: `
    <div class="listado">
      <h3 class="titulo">{{ titulo }}</h3>
      <content-serie 
        *ngFor="let serie of series" 
        [tituloSerie]="serie.nombre"
      ></content-serie>
    </div>
  `,
  styleUrls: ['./listado-series.component.css'],
})
export class ListadoSeriesComponent {
  @Input() titulo: string = '';
  @Input() series: ResumenSerieInterface[] = [];
}