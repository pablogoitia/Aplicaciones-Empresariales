import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SerieComponent } from './serie-catalogo/serie-catalogo.component';
import { SerieInterface } from '../../../shared/interfaces/serie.interface';

@Component({
  selector: 'catalogo-listado',
  standalone: true,
  imports: [CommonModule, SerieComponent],
  template: `
    <h3 class="titulo">{{ titulo }}</h3>
    <div class="listado">
      <ng-container *ngFor="let serie of series">
        <content-serie
          [idSerie]="serie.id"
          [tituloSerie]="serie.nombre"
          [sinopsis]="serie.sinopsis"
          [creadores]="serie.creadores"
          [actores]="serie.actores"
          [destacar]="esSerieEncontrada(serie)"
        />
      </ng-container>
    </div>
  `,
  styleUrls: ['./listado-catalogo.component.css'],
})
export class ListadoCatalogoComponent {
  @Input() series: SerieInterface[] = [];
  @Input() seriesEncontradas: SerieInterface[] = [];
  @Input() titulo: string = '';

  esSerieEncontrada(serie: SerieInterface): boolean {
    return this.seriesEncontradas.some(s => s.nombre === serie.nombre);
  }
}