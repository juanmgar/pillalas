import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IObservatorio } from 'app/shared/model/observatorio.model';

type EntityResponseType = HttpResponse<IObservatorio>;
type EntityArrayResponseType = HttpResponse<IObservatorio[]>;

@Injectable({ providedIn: 'root' })
export class ObservatorioService {
  public resourceUrl = SERVER_API_URL + 'api/observatorios';

  constructor(protected http: HttpClient) {}

  create(observatorio: IObservatorio): Observable<EntityResponseType> {
    return this.http.post<IObservatorio>(this.resourceUrl, observatorio, { observe: 'response' });
  }

  update(observatorio: IObservatorio): Observable<EntityResponseType> {
    return this.http.put<IObservatorio>(this.resourceUrl, observatorio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObservatorio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObservatorio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
