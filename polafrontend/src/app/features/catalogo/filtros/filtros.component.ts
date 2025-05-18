import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'catalogo-filtros',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="filtros-container">
      <div class="letras">
        <button
          *ngFor="let letra of letras"
          (click)="onLetraSeleccionada(letra)"
          class="letra-btn"
          [class.selected]="letra === letraSeleccionada"
        >
          {{ letra }}
        </button>
      </div>
      <div class="busqueda">
        <input
          type="text"
          placeholder="Buscar serie..."
          (keyup.enter)="onBusqueda($event)"
        />
      </div>
    </div>
  `,
  styleUrls: ['./filtros.component.css'],
})
export class FiltrosComponent {
  @Output() filtroSeleccionado = new EventEmitter<{ tipo: 'letra' | 'busqueda', valor: string }>();

  letras: string[] = Array.from({ length: 26 }, (_, i) =>
    String.fromCharCode(65 + i)
  );

  letraSeleccionada: string | null = "A";

  onLetraSeleccionada(letra: string) {
    this.letraSeleccionada = letra;
    this.filtroSeleccionado.emit({ tipo: 'letra', valor: letra });
  }

  onBusqueda(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    if (!valor.trim()) return;
    this.letraSeleccionada = valor.charAt(0).toUpperCase();
    this.filtroSeleccionado.emit({ tipo: 'busqueda', valor });
  }
}
