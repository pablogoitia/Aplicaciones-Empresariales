import { Injectable } from '@angular/core';
import { UsuarioInterface } from '../interfaces/usuario.interface';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})

export class UsuarioService {
  async getInfoUsuario(username: string): Promise<UsuarioInterface> {
    const response = await fetch(`${environment.url}/usuarios/${username}`);
    if (!response.ok) {
      throw new Error(`Error al obtener la información del usuario: ${response.statusText}`);
    }
    const contentType = response.headers.get('Content-Type');
    if (!contentType || !contentType.includes('application/json')) {
      throw new Error('La respuesta no es JSON válida.');
    }
    return await response.json();
  }
}