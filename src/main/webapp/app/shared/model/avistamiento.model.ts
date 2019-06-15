import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAve } from 'app/shared/model/ave.model';

export interface IAvistamiento {
  id?: number;
  nombre?: string;
  fecha?: Moment;
  latitud?: string;
  longitud?: string;
  descripcion?: any;
  autor?: IUser;
  aves?: IAve[];
}

export class Avistamiento implements IAvistamiento {
  constructor(
    public id?: number,
    public nombre?: string,
    public fecha?: Moment,
    public latitud?: string,
    public longitud?: string,
    public descripcion?: any,
    public autor?: IUser,
    public aves?: IAve[]
  ) {}
}
