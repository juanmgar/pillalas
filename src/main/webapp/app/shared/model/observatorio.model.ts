import { IUser } from 'app/core/user/user.model';
import { IAve } from 'app/shared/model/ave.model';

export interface IObservatorio {
  id?: number;
  nombre?: string;
  latitud?: string;
  longitud?: string;
  descripcion?: any;
  fotoContentType?: string;
  foto?: any;
  autor?: IUser;
  aves?: IAve[];
}

export class Observatorio implements IObservatorio {
  constructor(
    public id?: number,
    public nombre?: string,
    public latitud?: string,
    public longitud?: string,
    public descripcion?: any,
    public fotoContentType?: string,
    public foto?: any,
    public autor?: IUser,
    public aves?: IAve[]
  ) {}
}
