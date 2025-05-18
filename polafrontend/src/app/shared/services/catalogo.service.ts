import { Injectable } from '@angular/core';
import { SerieInterface } from '../interfaces/serie.interface';

@Injectable({
  providedIn: 'root',
})

export class CatalogoService {
  async obtenerSeriesPorInicial(inicial: string): Promise<SerieInterface[]> {
    const response = await fetch(`http://localhost:8080/series?inicial=${inicial}`);
    if (!response.ok) {
      throw new Error('Error al obtener las series');
    }
    return await response.json();
  }

  async obtenerSeriesPorBusqueda(criterio: string): Promise<SerieInterface[]> {
    const response = await fetch(`http://localhost:8080/series?nombre=${criterio}`);
    if (!response.ok) {
      throw new Error('Error al obtener las series');
    }
    return await response.json();
  }
}