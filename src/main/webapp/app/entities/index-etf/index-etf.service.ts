import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIndexETF } from 'app/shared/model/index-etf.model';

type EntityResponseType = HttpResponse<IIndexETF>;
type EntityArrayResponseType = HttpResponse<IIndexETF[]>;

@Injectable({ providedIn: 'root' })
export class IndexETFService {
  public resourceUrl = SERVER_API_URL + 'api/index-etfs';

  constructor(protected http: HttpClient) {}

  create(indexETF: IIndexETF): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indexETF);
    return this.http
      .post<IIndexETF>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(indexETF: IIndexETF): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indexETF);
    return this.http
      .put<IIndexETF>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIndexETF>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIndexETF[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(indexETF: IIndexETF): IIndexETF {
    const copy: IIndexETF = Object.assign({}, indexETF, {
      asAtDate: indexETF.asAtDate && indexETF.asAtDate.isValid() ? indexETF.asAtDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.asAtDate = res.body.asAtDate ? moment(res.body.asAtDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((indexETF: IIndexETF) => {
        indexETF.asAtDate = indexETF.asAtDate ? moment(indexETF.asAtDate) : undefined;
      });
    }
    return res;
  }
}