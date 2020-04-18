import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConstituent } from 'app/shared/model/constituent.model';

type EntityResponseType = HttpResponse<IConstituent>;
type EntityArrayResponseType = HttpResponse<IConstituent[]>;

@Injectable({ providedIn: 'root' })
export class ConstituentService {
  public resourceUrl = SERVER_API_URL + 'api/constituents';

  constructor(protected http: HttpClient) {}

  create(constituent: IConstituent): Observable<EntityResponseType> {
    return this.http.post<IConstituent>(this.resourceUrl, constituent, { observe: 'response' });
  }

  update(constituent: IConstituent): Observable<EntityResponseType> {
    return this.http.put<IConstituent>(this.resourceUrl, constituent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConstituent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConstituent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
