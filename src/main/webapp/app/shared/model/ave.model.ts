import { IUser } from 'app/core/user/user.model';
import { IFotografia } from 'app/shared/model/fotografia.model';
import { IObservatorio } from 'app/shared/model/observatorio.model';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';

export interface IAve {
  id?: number;
  nombreComun?: string;
  nombreCientifico?: string;
  descripcion?: any;
  fotoContentType?: string;
  foto?: any;
  sonidoContentType?: string;
  sonido?: any;
  autor?: IUser;
  fotos?: IFotografia[];
  observatorios?: IObservatorio[];
  avistamientos?: IAvistamiento[];
}

export class Ave implements IAve {
  constructor(
    public id?: number,
    public nombreComun?: string,
    public nombreCientifico?: string,
    public descripcion?: any,
    public fotoContentType?: string,
    public foto?: any,
    public sonidoContentType?: string,
    public sonido?: any,
    public autor?: IUser,
    public fotos?: IFotografia[],
    public observatorios?: IObservatorio[],
    public avistamientos?: IAvistamiento[]
  ) {}
}
