import { Component, OnInit } from '@angular/core';
import { FiltrosComponent } from './filtros/filtros.component';
import { ListadoCatalogoComponent } from './listado-catalogo/listado-catalogo.component';
import { SeparadorComponent } from 'src/app/shared/components/separador/separador.component';
import { CatalogoService } from 'src/app/shared/services/catalogo.service';

@Component({
  selector: 'app-content',
  standalone: true,
  imports: [FiltrosComponent, ListadoCatalogoComponent, SeparadorComponent],
  template: `
    <catalogo-filtros (filtroSeleccionado)="onFiltroSeleccionado($event)" />
    <separador [inputHeight]="30"/>
    <catalogo-listado [series]="seriesInicial" [seriesEncontradas]="seriesEncontradas" [titulo]="titulo" />
  `,
})
export class CatalogoComponent implements OnInit {
  seriesInicial: any[] = [];
  seriesEncontradas: any[] = [];
  titulo: string = '';

  constructor(private catalogoService: CatalogoService) { }

  ngOnInit(): void {
    this.catalogoService.obtenerSeriesPorInicial('A').then(series => {
      this.seriesInicial = series.sort((a, b) => a.nombre.localeCompare(b.nombre));
    });
  }

  onFiltroSeleccionado(filtro: { tipo: 'letra' | 'busqueda'; valor: string }) {
    if (filtro.tipo === 'letra') {
      this.catalogoService.obtenerSeriesPorInicial(filtro.valor).then(series => {
        this.seriesInicial = series.sort((a, b) => a.nombre.localeCompare(b.nombre));
        this.titulo = '';
      });
    } else if (filtro.tipo === 'busqueda') {
      this.catalogoService.obtenerSeriesPorBusqueda(filtro.valor).then(series => {
        this.seriesEncontradas = series;

        if (this.seriesEncontradas.length > 0) {
          this.catalogoService.obtenerSeriesPorInicial(filtro.valor.charAt(0)).then(series => {
            this.seriesInicial = series.sort((a, b) => a.nombre.localeCompare(b.nombre));
          });
          this.titulo = `Resultados de la búsqueda: "${filtro.valor}"`;
        } else {
          this.titulo = `No se encontraron resultados para: "${filtro.valor}"`;
        }
      });
      return;
    }
  }
}