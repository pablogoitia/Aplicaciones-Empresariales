import { Component, OnInit } from '@angular/core';
import { ListadoSeriesComponent } from './listado-series/listado-series.component';
import { AdBannerComponent } from '../../shared/components/ad-banner/ad-banner.component';
import { SeparadorComponent } from '../../shared/components/separador/separador.component';
import { UsuarioService } from '../../shared/services/usuario.service';
import { ResumenSerieInterface } from '../../shared/interfaces/serie.interface';
import { environment } from 'src/environments/environment';

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
      // Las series llegan ordenadas por fecha de adición a las listas
      this.pendientes = usuario.seriesPendientes;
      this.empezadas = usuario.seriesEmpezadas;
      this.terminadas = usuario.seriesTerminadas;
    });
  }
}