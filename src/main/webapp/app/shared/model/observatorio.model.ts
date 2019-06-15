import { IFotografia } from 'app/shared/model/fotografia.model';
import { IAve } from 'app/shared/model/ave.model';

export interface IObservatorio {
  id?: number;
  nombre?: string;
  latitud?: string;
  longitud?: string;
  fotoContentType?: string;
  foto?: any;
  descripcion?: string;
  observatorios?: IFotografia[];
  aves?: IAve[];
}

export class Observatorio implements IObservatorio {
  constructor(
    public id?: number,
    public nombre?: string,
    public latitud?: string,
    public longitud?: string,
    public fotoContentType?: string,
    public foto?: any,
    public descripcion?: string,
    public observatorios?: IFotografia[],
    public aves?: IAve[]
  ) {}
}
