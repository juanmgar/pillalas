import { Moment } from 'moment';
import { IFotografia } from 'app/shared/model/fotografia.model';
import { IAve } from 'app/shared/model/ave.model';

export interface IAvistamiento {
  id?: number;
  nombre?: string;
  fecha?: Moment;
  latitud?: string;
  longitud?: string;
  descripcion?: string;
  fotos?: IFotografia[];
  aves?: IAve[];
}

export class Avistamiento implements IAvistamiento {
  constructor(
    public id?: number,
    public nombre?: string,
    public fecha?: Moment,
    public latitud?: string,
    public longitud?: string,
    public descripcion?: string,
    public fotos?: IFotografia[],
    public aves?: IAve[]
  ) {}
}
