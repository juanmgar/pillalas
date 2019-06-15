import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';

type EntityResponseType = HttpResponse<IAvistamiento>;
type EntityArrayResponseType = HttpResponse<IAvistamiento[]>;

@Injectable({ providedIn: 'root' })
export class AvistamientoService {
  public resourceUrl = SERVER_API_URL + 'api/avistamientos';

  constructor(protected http: HttpClient) {}

  create(avistamiento: IAvistamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avistamiento);
    return this.http
      .post<IAvistamiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(avistamiento: IAvistamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(avistamiento);
    return this.http
      .put<IAvistamiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAvistamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAvistamiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(avistamiento: IAvistamiento): IAvistamiento {
    const copy: IAvistamiento = Object.assign({}, avistamiento, {
      fecha: avistamiento.fecha != null && avistamiento.fecha.isValid() ? avistamiento.fecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((avistamiento: IAvistamiento) => {
        avistamiento.fecha = avistamiento.fecha != null ? moment(avistamiento.fecha) : null;
      });
    }
    return res;
  }
}
