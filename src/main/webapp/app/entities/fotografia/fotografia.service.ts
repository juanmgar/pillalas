import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFotografia } from 'app/shared/model/fotografia.model';

type EntityResponseType = HttpResponse<IFotografia>;
type EntityArrayResponseType = HttpResponse<IFotografia[]>;

@Injectable({ providedIn: 'root' })
export class FotografiaService {
  public resourceUrl = SERVER_API_URL + 'api/fotografias';

  constructor(protected http: HttpClient) {}

  create(fotografia: IFotografia): Observable<EntityResponseType> {
    return this.http.post<IFotografia>(this.resourceUrl, fotografia, { observe: 'response' });
  }

  update(fotografia: IFotografia): Observable<EntityResponseType> {
    return this.http.put<IFotografia>(this.resourceUrl, fotografia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotografia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotografia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
