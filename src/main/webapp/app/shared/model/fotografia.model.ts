import { IAvistamiento } from 'app/shared/model/avistamiento.model';
import { IObservatorio } from 'app/shared/model/observatorio.model';
import { IUser } from 'app/core/user/user.model';
import { IAve } from 'app/shared/model/ave.model';

export interface IFotografia {
  id?: number;
  nombre?: string;
  ficheroContentType?: string;
  fichero?: any;
  avistamiento?: IAvistamiento;
  observatorio?: IObservatorio;
  autor?: IUser;
  aves?: IAve[];
}

export class Fotografia implements IFotografia {
  constructor(
    public id?: number,
    public nombre?: string,
    public ficheroContentType?: string,
    public fichero?: any,
    public avistamiento?: IAvistamiento,
    public observatorio?: IObservatorio,
    public autor?: IUser,
    public aves?: IAve[]
  ) {}
}
