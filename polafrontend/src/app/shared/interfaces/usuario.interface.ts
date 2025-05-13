import { ResumenSerieInterface } from './serie.interface';

export interface UsuarioInterface {
  nombreUsuario: string;
  seriesPendientes: ResumenSerieInterface[];
  seriesEmpezadas: ResumenSerieInterface[];
  seriesTerminadas: ResumenSerieInterface[];
}