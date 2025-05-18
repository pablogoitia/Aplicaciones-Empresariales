import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { CommonModule } from '@angular/common';
import { SeparadorComponent } from 'src/app/shared/components/separador/separador.component';
import { UsuarioService } from 'src/app/shared/services/usuario.service';

@Component({
  selector: 'content-serie',
  standalone: true,
  imports: [NgClass, CommonModule, SeparadorComponent],
  template: `
    <div [ngClass]="destacar ? 'serie-destacada' : 'serie'" >
      <h3 [ngClass]="destacar ? 'titulo-destacado' : 'titulo'" (click)="mostrarDetalle = !mostrarDetalle">{{ tituloSerie }}</h3>
      <button class="agregar-serie" (click)="agregarASeriesPendientes(idSerie)">Agregar</button>
    </div>
    <div class="detalle" *ngIf="mostrarDetalle">
      <button class="cerrar-detalle" (click)="mostrarDetalle = false">&#10005;</button>
      <p class="detalle-texto">{{ sinopsis }}</p>
      <separador [inputHeight]="10"/>
      <p class="detalle-texto"><b>Creadores:</b> {{ creadores }}</p>
      <p class="detalle-texto"><b>Actores:</b> {{ actores }}</p>
    </div>
  `,
  styleUrls: ['./serie-catalogo.component.css'],
})
export class SerieComponent {
  @Input() idSerie: number = -1;
  @Input() tituloSerie: string = '';
  @Input() sinopsis: string = '';
  @Input() creadores: string = '';
  @Input() actores: string = '';
  @Input() destacar: boolean = false;
  mostrarDetalle = false;

  constructor(private usuarioService: UsuarioService) { }

  agregarASeriesPendientes(idSerie: number) {
    this.usuarioService.agregaSeriePendiente(idSerie);
  }
}