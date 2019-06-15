import { IFotografia } from 'app/shared/model/fotografia.model';
import { IObservatorio } from 'app/shared/model/observatorio.model';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';

export interface IAve {
  id?: number;
  nombreComun?: string;
  nombreCientifico?: string;
  descripcion?: string;
  fotoContentType?: string;
  foto?: any;
  sonidoContentType?: string;
  sonido?: any;
  fotos?: IFotografia[];
  observatorios?: IObservatorio[];
  avistamientos?: IAvistamiento[];
}

export class Ave implements IAve {
  constructor(
    public id?: number,
    public nombreComun?: string,
    public nombreCientifico?: string,
    public descripcion?: string,
    public fotoContentType?: string,
    public foto?: any,
    public sonidoContentType?: string,
    public sonido?: any,
    public fotos?: IFotografia[],
    public observatorios?: IObservatorio[],
    public avistamientos?: IAvistamiento[]
  ) {}
}
