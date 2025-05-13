import { Component } from '@angular/core';
import { ListadoSeriesComponent } from './listado-series/listado-series.component';
import { AdBannerComponent } from '../../shared/components/ad-banner/ad-banner.component';
import { SeparadorComponent } from '../../shared/components/separador/separador.component';
import { UsuarioService } from '../../shared/services/usuario.service';
import { OnInit } from '@angular/core';
import { ResumenSerieInterface } from '../../shared/interfaces/serie.interface';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'content-profile',
  standalone: true,
  imports: [AdBannerComponent, ListadoSeriesComponent, SeparadorComponent],
  template: `
    <content-listado-series [titulo]="'Series Pendientes'" [series]="pendientes"></content-listado-series>
    <content-listado-series [titulo]="'Series Empezadas'" [series]="empezadas"></content-listado-series>
    <content-listado-series [titulo]="'Series Terminadas'" [series]="terminadas"></content-listado-series>
    <separador [inputHeight]="40"/>
    <content-ad-banner/>
  `,
})

export class ProfileComponent implements OnInit {
  pendientes: ResumenSerieInterface[] = [];
  empezadas: ResumenSerieInterface[] = [];
  terminadas: ResumenSerieInterface[] = [];

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.usuarioService.getInfoUsuario(environment.def_username).then((usuario) => {
      this.pendientes = usuario.seriesPendientes.map((serie: any) => ({
        id: serie.id,
        nombre: serie.nombre,
      }));

      this.empezadas = usuario.seriesEmpezadas.map((item: any) => ({
        id: item.serie.id,
        nombre: item.serie.nombre,
      }));

      this.terminadas = usuario.seriesTerminadas.map((item: any) => ({
        id: item.serie.id,
        nombre: item.serie.nombre,
      }));
    });
  }
}