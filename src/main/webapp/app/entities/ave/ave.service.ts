import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAve } from 'app/shared/model/ave.model';

type EntityResponseType = HttpResponse<IAve>;
type EntityArrayResponseType = HttpResponse<IAve[]>;

@Injectable({ providedIn: 'root' })
export class AveService {
  public resourceUrl = SERVER_API_URL + 'api/aves';

  constructor(protected http: HttpClient) {}

  create(ave: IAve): Observable<EntityResponseType> {
    return this.http.post<IAve>(this.resourceUrl, ave, { observe: 'response' });
  }

  update(ave: IAve): Observable<EntityResponseType> {
    return this.http.put<IAve>(this.resourceUrl, ave, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAve>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAve[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findTextos(text?: any): Observable<EntityArrayResponseType> {
    return this.http.get<IAve[]>(`api/avesfiltro/${text}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
