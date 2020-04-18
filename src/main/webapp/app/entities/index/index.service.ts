import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIndex } from 'app/shared/model/index.model';

type EntityResponseType = HttpResponse<IIndex>;
type EntityArrayResponseType = HttpResponse<IIndex[]>;

@Injectable({ providedIn: 'root' })
export class IndexService {
  public resourceUrl = SERVER_API_URL + 'api/indices';

  constructor(protected http: HttpClient) {}

  create(index: IIndex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(index);
    return this.http
      .post<IIndex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(index: IIndex): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(index);
    return this.http
      .put<IIndex>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIndex[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(index: IIndex): IIndex {
    const copy: IIndex = Object.assign({}, index, {
      asAtDate: index.asAtDate && index.asAtDate.isValid() ? index.asAtDate.format(DATE_FORMAT) : undefined
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
      res.body.forEach((index: IIndex) => {
        index.asAtDate = index.asAtDate ? moment(index.asAtDate) : undefined;
      });
    }
    return res;
  }
}
