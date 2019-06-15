import { IAve } from 'app/shared/model/ave.model';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';
import { IObservatorio } from 'app/shared/model/observatorio.model';

export interface IFotografia {
  id?: number;
  nombre?: string;
  ficheroContentType?: string;
  fichero?: any;
  ave?: IAve;
  avistamientos?: IAvistamiento[];
  observatorios?: IObservatorio[];
}

export class Fotografia implements IFotografia {
  constructor(
    public id?: number,
    public nombre?: string,
    public ficheroContentType?: string,
    public fichero?: any,
    public ave?: IAve,
    public avistamientos?: IAvistamiento[],
    public observatorios?: IObservatorio[]
  ) {}
}
