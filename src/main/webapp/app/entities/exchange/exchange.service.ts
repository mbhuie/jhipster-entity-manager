import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExchange } from 'app/shared/model/exchange.model';

type EntityResponseType = HttpResponse<IExchange>;
type EntityArrayResponseType = HttpResponse<IExchange[]>;

@Injectable({ providedIn: 'root' })
export class ExchangeService {
  public resourceUrl = SERVER_API_URL + 'api/exchanges';

  constructor(protected http: HttpClient) {}

  create(exchange: IExchange): Observable<EntityResponseType> {
    return this.http.post<IExchange>(this.resourceUrl, exchange, { observe: 'response' });
  }

  update(exchange: IExchange): Observable<EntityResponseType> {
    return this.http.put<IExchange>(this.resourceUrl, exchange, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExchange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExchange[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
